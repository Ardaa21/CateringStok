package foodstore;

import javax.swing.*;

public class YoneticiRegisterWindow extends JDialog {

    private final UserFileRepository repo;

    public YoneticiRegisterWindow(JFrame owner, UserFileRepository repo) {
        super(owner, "Yönetici Kayıt", true);
        this.repo = repo;

        setSize(420, 320);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(null);

        JTextField txtYeniKullanici = new JTextField();
        txtYeniKullanici.setBounds(212, 1, 207, 44);
        JPasswordField txtYeniSifre = new JPasswordField();
        txtYeniSifre.setBounds(212, 50, 207, 44);
        JPasswordField txtYeniSifreTekrar = new JPasswordField();
        txtYeniSifreTekrar.setBounds(212, 99, 207, 44);

        JTextField txtGuvenlikSorusu = new JTextField();
        txtGuvenlikSorusu.setBounds(212, 148, 207, 44);
        JTextField txtGuvenlikCevabi = new JTextField();
        txtGuvenlikCevabi.setBounds(212, 197, 207, 44);

        JButton btnKaydet = new JButton("Kaydı Tamamla");
        btnKaydet.setBounds(212, 246, 207, 44);
        
        JLabel label = new JLabel(" Yeni Kullanıcı Adı:");
        label.setBounds(0, 1, 207, 44);
        getContentPane().add(label);
        getContentPane().add(txtYeniKullanici);

        JLabel label_1 = new JLabel(" Yeni Şifre:");
        label_1.setBounds(0, 50, 207, 44);
        getContentPane().add(label_1);
        getContentPane().add(txtYeniSifre);

        JLabel label_2 = new JLabel(" Şifre (Tekrar):");
        label_2.setBounds(0, 99, 207, 44);
        getContentPane().add(label_2);
        getContentPane().add(txtYeniSifreTekrar);

        JLabel label_3 = new JLabel(" Güvenlik Sorusu:");
        label_3.setBounds(0, 148, 207, 44);
        getContentPane().add(label_3);
        getContentPane().add(txtGuvenlikSorusu);

        JLabel label_4 = new JLabel(" Güvenlik Cevabı:");
        label_4.setBounds(0, 197, 207, 44);
        getContentPane().add(label_4);
        getContentPane().add(txtGuvenlikCevabi);
        
        JButton btnIptal = new JButton("İptal"); btnIptal.setBounds(0, 246, 207, 44); getContentPane().add(btnIptal); btnIptal.addActionListener(e -> dispose());

        getContentPane().add(btnKaydet);

        btnKaydet.addActionListener(e -> {
            String username = txtYeniKullanici.getText().trim();
            String pass1 = new String(txtYeniSifre.getPassword()).trim();
            String pass2 = new String(txtYeniSifreTekrar.getPassword()).trim();
            String question = txtGuvenlikSorusu.getText().trim();
            String answer = txtGuvenlikCevabi.getText().trim();

            if (username.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || question.isEmpty() || answer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(this, "Şifreler uyuşmuyor!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (question.contains(",") || answer.contains(",")) {
                JOptionPane.showMessageDialog(this, "Virgül (,) kullanmayın.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (repo.managerExists(username)) {
                JOptionPane.showMessageDialog(this, "Bu yönetici zaten kayıtlı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (repo.addManager(username, pass1, question, answer)) {
                JOptionPane.showMessageDialog(this, "Yönetici kaydı başarılı!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}