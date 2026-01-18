package foodstore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class YoneticiPanelFrame extends JFrame {

    private JPanel contentPane;
    private final UserFileRepository repo;
    private final String kullaniciAdi;

    public YoneticiPanelFrame(UserFileRepository repo, String kullaniciAdi) {
        this.repo = repo;
        this.kullaniciAdi = kullaniciAdi;

        setTitle("Yönetici Paneli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 420);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        
        JLabel title = new JLabel("Yönetici Paneli - Hoş geldin: " + this.kullaniciAdi, SwingConstants.CENTER);
        title.setBounds(15, 15, 470, 20);
        title.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(title);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(15, 45, 470, 332);
        buttonPanel.setLayout(null);
        contentPane.add(buttonPanel);

        JButton btnStokGiris = new JButton("Stok Giriş");
        btnStokGiris.setBounds(0, 0, 229, 102);
        JButton btnStokCikis = new JButton("Stok Çıkış");
        btnStokCikis.setBounds(241, 0, 229, 102);
        JButton btnUrunler = new JButton("Stoklar");
        btnUrunler.setBounds(0, 114, 229, 102);
        JButton btnYeniUrun = new JButton("Yeni Ürün");
        btnYeniUrun.setBounds(241, 114, 229, 102);
        JButton btnRaporlar = new JButton("Raporlar");
        btnRaporlar.setBounds(0, 228, 229, 102);
        JButton btnPersonelEkle = new JButton("Personel İşlemleri");
        btnPersonelEkle.setBounds(241, 228, 229, 102);

        Font f = new Font("Tahoma", Font.BOLD, 14);
        btnStokGiris.setFont(f); btnStokCikis.setFont(f);
        btnUrunler.setFont(f); btnYeniUrun.setFont(f);
        btnRaporlar.setFont(f); btnPersonelEkle.setFont(f);

        buttonPanel.add(btnStokGiris); buttonPanel.add(btnStokCikis);
        buttonPanel.add(btnUrunler); buttonPanel.add(btnYeniUrun);
        buttonPanel.add(btnRaporlar); buttonPanel.add(btnPersonelEkle);

        // RAPOR için kullanıcı adını gönderiyorum
        btnStokGiris.addActionListener(e -> new StokGirisWindow(this, kullaniciAdi).setVisible(true));
        btnStokCikis.addActionListener(e -> new StokCikisWindow(this, kullaniciAdi).setVisible(true));
        
        
        btnUrunler.addActionListener(e -> new StokListeWindow(this).setVisible(true));
        btnYeniUrun.addActionListener(e -> new YeniUrunWindow(this).setVisible(true));
        btnRaporlar.addActionListener(e -> new RaporlarWindow(this).setVisible(true));
        btnPersonelEkle.addActionListener(e -> new PersonelRegisterWindow(this, repo).setVisible(true));
    }
}