package foodstore;

import java.util.List;

import javax.swing.*;

public class YeniUrunWindow extends JDialog {

    private final UrunDosyaIslemleri urunIslemleri;
    private DefaultListModel<String> listModel; 

    public YeniUrunWindow(JFrame owner) {
        super(owner, "Yeni Ürün Ekle", true);

        urunIslemleri = new UrunDosyaIslemleri("urunler.txt");

        setSize(600, 300);
        setLocationRelativeTo(owner);
        setLayout(null);

        JLabel lblBarkod = new JLabel(" Barkod Kodu:"); lblBarkod.setBounds(20, 30, 100, 25); add(lblBarkod);
        JTextField txtBarkod = new JTextField(); txtBarkod.setBounds(130, 30, 150, 25); add(txtBarkod);

        JLabel lblAd = new JLabel(" Ürün Adı:"); lblAd.setBounds(20, 70, 100, 25); add(lblAd);
        JTextField txtAd = new JTextField(); txtAd.setBounds(130, 70, 150, 25); add(txtAd);

        JButton btnKaydet = new JButton("Kaydet"); btnKaydet.setBounds(130, 110, 150, 35); add(btnKaydet);
        JButton btnIptal = new JButton("İptal"); btnIptal.setBounds(20, 110, 100, 35); add(btnIptal);

        

        JLabel lblListe = new JLabel("Kayıtlı Ürünler (Barkod - Ad):");
        lblListe.setBounds(300, 10, 250, 20);
        add(lblListe);

        listModel = new DefaultListModel<>();
        JList<String> listUrunler = new JList<>(listModel);
        
        JScrollPane scrollPane = new JScrollPane(listUrunler);
        scrollPane.setBounds(300, 30, 260, 200);
        add(scrollPane);

        
        listeyiYenile();

        
        btnIptal.addActionListener(e -> dispose());

        btnKaydet.addActionListener(e -> {
            String barkod = txtBarkod.getText().trim();
            String ad = txtAd.getText().trim();

            if (barkod.isEmpty() || ad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }
            if (!barkod.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Barkod sadece rakam olmalı!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }
            if (urunIslemleri.barkodVarMi(barkod)) {
                JOptionPane.showMessageDialog(this, "Bu barkod zaten kayıtlı!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }
            
            if (urunIslemleri.urunEkle(barkod, ad)) {
                JOptionPane.showMessageDialog(this, "Ürün eklendi.");
             
                
                txtBarkod.setText("");
                txtAd.setText("");
                listeyiYenile(); 
                
            } else {
                JOptionPane.showMessageDialog(this, "Hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    // Listeyi dosyadan çekip güncelleyen metod
    private void listeyiYenile() {
        listModel.clear();
        List<String> urunler = urunIslemleri.getUrunListesi();
        for (String u : urunler) {
        	
            listModel.addElement(u);
        }
    }
}