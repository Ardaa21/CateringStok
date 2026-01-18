package foodstore;

import javax.swing.*;
import java.awt.event.*;

public class StokGirisWindow extends JDialog {

    private final UrunDosyaIslemleri urunIslemleri;
    private final StokDosyaIslemleri stokIslemleri;
    private final IslemKayitlariDosyaIslemleri kayitlar;
    private final String kullaniciAdi;

    public StokGirisWindow(JFrame owner, String kullaniciAdi) {
        super(owner, "Stok Giriş", true);
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
        JTextField txtSKT = new JTextField("GG.AA.YYYY");
        txtSKT.setToolTipText("");txtSKT.setBounds(170, 120, 230, 30); getContentPane().add(txtSKT);

        JLabel lblAdet = new JLabel("Adet:"); lblAdet.setBounds(30, 165, 130, 30); getContentPane().add(lblAdet);
        JTextField txtAdet = new JTextField(); txtAdet.setBounds(170, 165, 230, 30); getContentPane().add(txtAdet);

        JButton btnKaydet = new JButton("Stok Girişi Yap"); btnKaydet.setBounds(170, 215, 230, 35); btnKaydet.setEnabled(false); getContentPane().add(btnKaydet);
        JButton btnIptal = new JButton("İptal"); btnIptal.setBounds(30, 215, 130, 35); getContentPane().add(btnIptal); btnIptal.addActionListener(e -> dispose());

        
        // Barkod alanından çıkınca ürün adı otomatik gelir (kayıtlı değilse engeller)
        
        txtBarkod.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String barkod = txtBarkod.getText().trim();
                if (barkod.isEmpty()) {
                    txtAd.setText(""); btnKaydet.setEnabled(false); return;
                }
                
                String urunAdi = urunIslemleri.barkoddanUrunAdiGetir(barkod);
                
                if (urunAdi == null) {
                    txtAd.setText(""); btnKaydet.setEnabled(false);
                    JOptionPane.showMessageDialog(StokGirisWindow.this, "Barkod kayıtlı değil! 'Yeni Ürün' ekleyin.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                	
                    txtAd.setText(urunAdi); btnKaydet.setEnabled(true);
                }
            }
        });

        btnKaydet.addActionListener(e -> {
            String barkod = txtBarkod.getText().trim();
            String urunAdi = txtAd.getText().trim();
            String skt = txtSKT.getText().trim();
            String adetStr = txtAdet.getText().trim();

            if (barkod.isEmpty() || urunAdi.isEmpty() || skt.equals("GG.AA.YYYY") || adetStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun ve geçerli bir tarih girin!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }
            int adet;
            try { adet = Integer.parseInt(adetStr); } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Adet sayı olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }
            if (adet <= 0) {
                JOptionPane.showMessageDialog(this, "Adet > 0 olmalı!", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }

            
          //RAPOR KAYIT
            if (stokIslemleri.stokEkle(barkod, urunAdi, skt, adet)) {
            	
                kayitlar.kayitEkle("GIRIS", kullaniciAdi, barkod, urunAdi, skt, adet);
                
                JOptionPane.showMessageDialog(this, "Stok girişi yapıldı.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Stok eklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    
}