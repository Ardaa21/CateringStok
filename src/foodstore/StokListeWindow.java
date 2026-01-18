package foodstore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class StokListeWindow extends JDialog {

    public StokListeWindow(JFrame owner) {
        super(owner, "Stoklar", true);

        setSize(650, 450);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(null);

        
        JLabel lblAra = new JLabel("Ara (Barkod veya İsim):");
        lblAra.setBounds(20, 15, 150, 30);
        getContentPane().add(lblAra);

        JTextField txtAra = new JTextField();
        txtAra.setBounds(170, 15, 300, 30);
        getContentPane().add(txtAra);
        

        String[] kolonlar = {"Barkod", "Ürün Adı", "STT", "Adet"};
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);

        //FİLTRELEME MEKANİZMASI SORTER
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 600, 290);
        getContentPane().add(scrollPane);

        JButton btnKapat = new JButton("Kapat");
        btnKapat.setBounds(520, 360, 100, 30);
        getContentPane().add(btnKapat);
        btnKapat.addActionListener(e -> dispose());

        //KLAVYE DİNLEYİCİSİ (HER TUŞA BASILDIĞINDA)
        txtAra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String arananMetin = txtAra.getText().trim();

                if (arananMetin.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) -> Büyük/Küçük harf duyarlılığını kapatır 
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + arananMetin));
                    } catch (java.util.regex.PatternSyntaxException pse) {
                        // Kullanıcı regex bozacak özel karakter girerse diye
                    }
                }
            }
        });

        
        File file = new File("data/stoklar.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    String barkod = parts[0].trim();
                    String ad = parts[1].trim();
                    String skt = parts[2].trim();
                    String adet = parts[3].trim();
                    
                    model.addRow(new Object[]{barkod, ad, skt, adet});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Stok dosyası okunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}