package foodstore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PersonelPanelFrame extends JFrame {

    private JPanel contentPane;
    private final String kullaniciAdi;

    public PersonelPanelFrame(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;

        setTitle("Personel Paneli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 420);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        
        JLabel title = new JLabel("Personel Paneli - Hoş geldin: " + this.kullaniciAdi, SwingConstants.CENTER);
        title.setBounds(15, 15, 470, 22);
        title.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(title);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(15, 47, 470, 330);
        buttonPanel.setLayout(null);
        contentPane.add(buttonPanel);

        JButton btnStokGiris = new JButton("Stok Giriş");
        btnStokGiris.setBounds(0, 0, 470, 85);
        
        JButton btnStokCikis = new JButton("Stok Çıkış");
        btnStokCikis.setBounds(0, 97, 470, 85);
        
        JButton btnUrunler = new JButton("Stoklar");
        btnUrunler.setBounds(0, 194, 470, 85);
        
        JButton btnCıkıs = new JButton("Çıkış");
        btnCıkıs.setBounds(313, 291, 157, 37);
        

        Font f = new Font("Tahoma", Font.BOLD, 14);
        btnStokGiris.setFont(f); btnStokCikis.setFont(f); btnUrunler.setFont(f); btnCıkıs.setFont(f);

        buttonPanel.add(btnStokGiris); buttonPanel.add(btnStokCikis); buttonPanel.add(btnUrunler); buttonPanel.add(btnCıkıs);

        btnStokGiris.addActionListener(e -> new StokGirisWindow(this, kullaniciAdi).setVisible(true));
        btnStokCikis.addActionListener(e -> new StokCikisWindow(this, kullaniciAdi).setVisible(true));
        
        btnUrunler.addActionListener(e -> new StokListeWindow(this).setVisible(true));
        btnCıkıs.addActionListener(e -> dispose());
    }
}