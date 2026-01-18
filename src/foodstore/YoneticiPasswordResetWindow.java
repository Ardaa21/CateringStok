package foodstore;
import javax.swing.*;

public class YoneticiPasswordResetWindow extends JDialog {

    private final UserFileRepository repo;

    public YoneticiPasswordResetWindow(JFrame owner, UserFileRepository repo) {
        super(owner, "Yönetici Şifre Yenileme", true);
        this.repo = repo;

        setSize(480, 280);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(null);

        JLabel lblUser = new JLabel(" Kullanıcı Adı:");
        lblUser.setBounds(10, 7, 140, 25);
        getContentPane().add(lblUser);

        JTextField txtKullanici = new JTextField();
        txtKullanici.setBounds(162, 6, 290, 25);
        getContentPane().add(txtKullanici);

        JButton btnSoruyuGetir = new JButton("Soruyu Getir");
        btnSoruyuGetir.setBounds(162, 35, 140, 25);
        getContentPane().add(btnSoruyuGetir);

        JLabel lblSoru = new JLabel(" Güvenlik Sorusu:");
        lblSoru.setBounds(10, 73, 140, 25);
        getContentPane().add(lblSoru);

        JTextField txtSoru = new JTextField();
        txtSoru.setBounds(162, 72, 290, 25);
        txtSoru.setEditable(false);
        getContentPane().add(txtSoru);

        JLabel lblCevap = new JLabel(" Güvenlik Cevabı:");
        lblCevap.setBounds(10, 110, 140, 25);
        getContentPane().add(lblCevap);

        JTextField txtCevap = new JTextField();
        txtCevap.setBounds(162, 111, 290, 25);
        getContentPane().add(txtCevap);

        JLabel lblYeni = new JLabel(" Yeni Şifre:");
        lblYeni.setBounds(10, 147, 140, 25);
        getContentPane().add(lblYeni);

        JPasswordField txtYeniSifre = new JPasswordField();
        txtYeniSifre.setBounds(162, 148, 290, 25);
        getContentPane().add(txtYeniSifre);

        JLabel lblYeni2 = new JLabel(" Yeni Şifre (Tekrar):");
        lblYeni2.setBounds(10, 184, 140, 25);
        getContentPane().add(lblYeni2);

        JPasswordField txtYeniSifre2 = new JPasswordField();
        txtYeniSifre2.setBounds(162, 185, 290, 25);
        getContentPane().add(txtYeniSifre2);

        JButton btnGuncelle = new JButton("Şifreyi Güncelle");
        btnGuncelle.setBounds(162, 222, 290, 25);
        getContentPane().add(btnGuncelle);
        
        JButton btnIptal = new JButton("İptal");
        btnIptal.setBounds(10, 222, 140, 25);
        getContentPane().add(btnIptal);
        btnIptal.addActionListener(e -> dispose());
        
        
        // 1) Kullanıcı adına göre güvenlik sorusunu getir
        btnSoruyuGetir.addActionListener(e -> {
            String username = txtKullanici.getText().trim();
            if (username.isEmpty()) return;
            
            String soru = repo.getManagerSecurityQuestion(username);
            
            if (soru == null) {
                JOptionPane.showMessageDialog(this, "Kullanıcı bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                txtSoru.setText("");
            } else {
                txtSoru.setText(soru);
            }
        });
        
        
        // 2) Cevap doğruysa şifreyi güncelle
        btnGuncelle.addActionListener(e -> {
            String username = txtKullanici.getText().trim();
            String answer = txtCevap.getText().trim();
            String pass1 = new String(txtYeniSifre.getPassword()).trim();
            String pass2 = new String(txtYeniSifre2.getPassword()).trim();

            if (username.isEmpty() || answer.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(this, "Şifreler uyuşmuyor!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!repo.validateManagerSecurityAnswer(username, answer)) {
                JOptionPane.showMessageDialog(this, "Güvenlik cevabı hatalı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (repo.updateManagerPassword(username, pass1)) {
                JOptionPane.showMessageDialog(this, "Şifre güncellendi.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}