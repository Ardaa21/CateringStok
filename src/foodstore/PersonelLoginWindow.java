package foodstore;

import javax.swing.*;

public class PersonelLoginWindow extends JDialog {

    private final UserFileRepository repo;

    public PersonelLoginWindow(JFrame owner, UserFileRepository repo) {
        super(owner, "Personel Girişi", true);
        this.repo = repo;

        setSize(360, 174);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(null);

        JLabel lblUser = new JLabel(" Kullanıcı Adı:");
        lblUser.setBounds(0, 0, 177, 44);
        add(lblUser);

        JTextField txtKullanici = new JTextField();
        txtKullanici.setBounds(182, 0, 177, 44);
        add(txtKullanici);

        JLabel lblPass = new JLabel(" Şifre:");
        lblPass.setBounds(0, 49, 177, 44);
        add(lblPass);

        JPasswordField txtSifre = new JPasswordField();
        txtSifre.setBounds(182, 49, 177, 44);
        add(txtSifre);

        JButton btnSifremiUnuttum = new JButton("Şifremi Unuttum");
        btnSifremiUnuttum.setBounds(0, 98, 177, 44);
        add(btnSifremiUnuttum);

        
        btnSifremiUnuttum.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Lütfen yönetici ile iletişime geçiniz.")
        );

        JButton btnGiris = new JButton("Giriş Yap");
        btnGiris.setBounds(182, 98, 177, 44);
        add(btnGiris);

        btnGiris.addActionListener(e -> {
            String username = txtKullanici.getText().trim();
            String password = new String(txtSifre.getPassword()).trim();

            if (repo.validateCredentials(username, password, UserType.PERSONEL)) {
                dispose();
                owner.dispose();
                
                // Kullanıcı adı Personel paneline aktarılıyor
                PersonelPanelFrame panel = new PersonelPanelFrame(username);
                panel.setLocationRelativeTo(null);
                panel.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı Kullanıcı Adı veya Şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}