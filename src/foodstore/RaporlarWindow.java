package foodstore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class RaporlarWindow extends JDialog {

    public RaporlarWindow(JFrame owner) {
        super(owner, "Raporlar", true);

        setSize(780, 420);
        setLocationRelativeTo(owner);
        setLayout(null);

        String[] kolonlar = {"Tarih-Saat", "İşlem", "Kullanıcı", "Barkod", "Ürün", "SKT", "Adet"};
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 740, 320);
        add(sp);

        JButton btnKapat = new JButton("Kapat");
        btnKapat.setBounds(660, 350, 100, 30);
        add(btnKapat);
        btnKapat.addActionListener(e -> dispose());

        File f = new File("data/islem_kayitlari.txt");
        if (!f.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length >= 7) {
                	
                    model.addRow(new Object[]{
                            p[0].trim(), p[1].trim(), p[2].trim(),
                            p[3].trim(), p[4].trim(), p[5].trim(), p[6].trim()
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Rapor okunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}