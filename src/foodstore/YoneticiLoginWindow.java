package foodstore;

import javax.swing.*;

public class YoneticiLoginWindow extends JDialog {

    private final UserFileRepository repo;

    public YoneticiLoginWindow(JFrame owner, UserFileRepository repo) {
        super(owner, "Yönetici Girişi", true);
        this.repo = repo;

        setSize(360, 223);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(null);

        JLabel lblUser = new JLabel(" Kullanıcı Adı:");
        lblUser.setBounds(10, 20, 140, 30);
        getContentPane().add(lblUser);

        JTextField txtKullanici = new JTextField();
        txtKullanici.setBounds(160, 20, 170, 30);
        getContentPane().add(txtKullanici);

        JLabel lblPass = new JLabel(" Şifre:");
        lblPass.setBounds(10, 60, 140, 30);
        getContentPane().add(lblPass);

        JPasswordField txtSifre = new JPasswordField();
        txtSifre.setBounds(160, 60, 170, 30);
        getContentPane().add(txtSifre);

        JButton btnKayitOl = new JButton("Kayıt Ol");
        btnKayitOl.setBounds(20, 110, 150, 35);
        getContentPane().add(btnKayitOl);

        JButton btnGiris = new JButton("Giriş Yap");
        btnGiris.setBounds(180, 110, 150, 35);
        getContentPane().add(btnGiris);

        JButton btnSifremiUnuttum = new JButton("Şifremi Unuttum");
        btnSifremiUnuttum.setBounds(20, 154, 310, 35);
        getContentPane().add(btnSifremiUnuttum);

        btnGiris.addActionListener(e -> {
            String username = txtKullanici.getText().trim();
            String password = new String(txtSifre.getPassword()).trim();
            

            if (repo.validateCredentials(username, password, UserType.YONETICI)) {
                dispose();
                owner.dispose();
                
                // Yönetici Panel ekranına kullanıcı adıyla yönlendirme
                YoneticiPanelFrame panel = new YoneticiPanelFrame(repo, username);
                panel.setLocationRelativeTo(null);
                panel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı Kullanıcı Adı veya Şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnKayitOl.addActionListener(e -> new YoneticiRegisterWindow(owner, repo).setVisible(true));
        btnSifremiUnuttum.addActionListener(e -> new YoneticiPasswordResetWindow(owner, repo).setVisible(true));
    }
}