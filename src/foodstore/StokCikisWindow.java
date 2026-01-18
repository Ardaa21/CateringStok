package foodstore;

import javax.swing.*;
import java.awt.event.*;

public class StokCikisWindow extends JDialog {

    private final UrunDosyaIslemleri urunIslemleri;
    private final StokDosyaIslemleri stokIslemleri;
    private final IslemKayitlariDosyaIslemleri kayitlar;
    private final String kullaniciAdi;

    public StokCikisWindow(JFrame owner, String kullaniciAdi) {
        super(owner, "Stok Çıkış", true);
        this.kullaniciAdi = kullaniciAdi;

        urunIslemleri = new UrunDosyaIslemleri("urunler.txt");
        stokIslemleri = new StokDosyaIslemleri("stoklar.txt");
        kayitlar = new IslemKayitlariDosyaIslemleri("islem_kayitlari.txt");

        setSize(460, 310);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblBarkod = new JLabel("Barkod:"); lblBarkod.setBounds(30, 30, 130, 30); getContentPane().add(lblBarkod);
        JTextField txtBarkod = new JTextField(); txtBarkod.setBounds(170, 30, 230, 30); getContentPane().add(txtBarkod);

        JLabel lblAd = new JLabel("Ürün Adı:"); lblAd.setBounds(30, 75, 130, 30); getContentPane().add(lblAd);
        JTextField txtAd = new JTextField(); txtAd.setBounds(170, 75, 230, 30); txtAd.setEditable(false); getContentPane().add(txtAd);

        JLabel lblSKT = new JLabel("Son Tüketim Tarihi:"); lblSKT.setBounds(30, 120, 130, 30); getContentPane().add(lblSKT);
        JTextField txtSKT = new JTextField("GG.AA.YYYY"); txtSKT.setBounds(170, 120, 230, 30); getContentPane().add(txtSKT);

        JLabel lblAdet = new JLabel("Çıkış Adedi:"); lblAdet.setBounds(30, 165, 130, 30); getContentPane().add(lblAdet);
        JTextField txtAdet = new JTextField(); txtAdet.setBounds(170, 165, 230, 30); getContentPane().add(txtAdet);

        JButton btnCikar = new JButton("Stok Çıkışı Yap"); btnCikar.setBounds(170, 215, 230, 35); btnCikar.setEnabled(false); getContentPane().add(btnCikar);
        
        JButton btnIptal = new JButton("İptal"); btnIptal.setBounds(30, 215, 130, 35); getContentPane().add(btnIptal); btnIptal.addActionListener(e -> dispose());
        
        
        // Barkod doğrulama
        txtBarkod.addFocusListener(new FocusAdapter() {
        	
            @Override
            public void focusLost(FocusEvent e) {
                String barkod = txtBarkod.getText().trim();
                if (barkod.isEmpty()) { txtAd.setText(""); btnCikar.setEnabled(false); return; }
                
                String urunAdi = urunIslemleri.barkoddanUrunAdiGetir(barkod);
                
                if (urunAdi == null) {
                    txtAd.setText(""); btnCikar.setEnabled(false);
                    JOptionPane.showMessageDialog(StokCikisWindow.this, "Bu barkod kayıtlı değil!", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    txtAd.setText(urunAdi); btnCikar.setEnabled(true);
                }
            }
        });

        btnCikar.addActionListener(e -> {
            String barkod = txtBarkod.getText().trim();
            String urunAdi = txtAd.getText().trim();
            String skt = txtSKT.getText().trim();
            String adetStr = txtAdet.getText().trim();

            if (barkod.isEmpty() || urunAdi.isEmpty() || skt.equals("GG.AA.YYY") || adetStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun ve geçerli bir tarih girin!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }
            int adet;
            try { adet = Integer.parseInt(adetStr); } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Adet sayı olmalı!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }
            if (adet <= 0) {
                JOptionPane.showMessageDialog(this, "Adet > 0 olmalı!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }

            
            int sonuc = stokIslemleri.stokCikar(barkod, skt, adet);
            
            
            //RAPOR KAYIT
            if (sonuc == 1) {
            	
                kayitlar.kayitEkle("CIKIS", kullaniciAdi, barkod, urunAdi, skt, adet);
                
                JOptionPane.showMessageDialog(this, "Stok çıkışı yapıldı.");
                dispose();
            } else if (sonuc == 0) {
                JOptionPane.showMessageDialog(this, "Bu barkod + SKT için kayıt yok!", "Hata", JOptionPane.ERROR_MESSAGE);
            } else if (sonuc == -1) {
                JOptionPane.showMessageDialog(this, "Yeterli stok yok!", "Hata", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Dosya hatası!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}