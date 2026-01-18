package foodstore;

import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private final UserFileRepository repo;

    public LoginFrame(UserFileRepository repo) {
        this.repo = repo;

        setTitle("Giriş Ekranı");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 505);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblBaslik = new JLabel("Lütfen Giriş Yapacağınız Birimi Seçiniz", SwingConstants.CENTER);
        lblBaslik.setBounds(10, 248, 430, 52);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPane.add(lblBaslik);

        JButton btnYonetici = new JButton("YÖNETİCİ GİRİŞİ");
        btnYonetici.setBounds(10, 312, 430, 77);
        btnYonetici.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnYonetici.addActionListener(e -> new YoneticiLoginWindow(this, repo).setVisible(true));
        contentPane.add(btnYonetici);
        

        JButton btnPersonel = new JButton("PERSONEL GİRİŞİ");
        btnPersonel.setBounds(10, 394, 430, 77);
        btnPersonel.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnPersonel.addActionListener(e -> new PersonelLoginWindow(this, repo).setVisible(true));
        contentPane.add(btnPersonel);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(62, 6, 326, 243);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logo.png"));
        lblNewLabel.setIcon(icon);
        contentPane.add(lblNewLabel);
    }
}