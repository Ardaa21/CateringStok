package foodstore;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PersonelRegisterWindow extends JDialog {

    private final UserFileRepository repo;
    private DefaultListModel<String> listModel;

    public PersonelRegisterWindow(JFrame owner, UserFileRepository repo) {
        super(owner, "Personel İşlemleri", true);
        this.repo = repo;

        setSize(550, 300);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(null);

        
        JLabel l1 = new JLabel("Kullanıcı Adı:"); 
        l1.setBounds(20, 30, 100, 25); 
        getContentPane().add(l1);

        JTextField txtUser = new JTextField(); 
        txtUser.setBounds(120, 30, 120, 25); 
        getContentPane().add(txtUser);

        JLabel l2 = new JLabel("Şifre:"); 
        l2.setBounds(20, 70, 100, 25); 
        getContentPane().add(l2);

        JPasswordField txtPass = new JPasswordField(); 
        txtPass.setBounds(120, 70, 120, 25); 
        getContentPane().add(txtPass);
        
        JButton btnIptal = new JButton("İptal");
        btnIptal.setBounds(3, 111, 117, 29);
        getContentPane().add(btnIptal);
        btnIptal.addActionListener(e -> dispose());

        JButton btnEkle = new JButton("Ekle"); 
        btnEkle.setBounds(120, 110, 120, 30); 
        getContentPane().add(btnEkle);

        
        JLabel l3 = new JLabel("Personel Listesi:"); 
        l3.setBounds(280, 10, 150, 20); 
        getContentPane().add(l3);
        
        listModel = new DefaultListModel<>();
        JList<String> listPersonel = new JList<>(listModel);
        
        JScrollPane scrollPane = new JScrollPane(listPersonel);
        scrollPane.setBounds(280, 30, 220, 150);
        getContentPane().add(scrollPane);

        JButton btnSil = new JButton("Seçili Personeli Sil");
        btnSil.setBounds(280, 190, 220, 30);
        getContentPane().add(btnSil);

        
        listeyiYenile();
        
        // EKLEME İŞLEMİ
        btnEkle.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword()).trim();

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Boş alan bırakmayınız!");
                return;
            }
            if (repo.personelExists(u)) {
                JOptionPane.showMessageDialog(this, "Bu kullanıcı adı zaten alınmış!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(repo.addPersonel(u, p)) {
                JOptionPane.showMessageDialog(this, "Personel eklendi.");
                txtUser.setText(""); txtPass.setText(""); 
                
                listeyiYenile(); 
            } else {
                JOptionPane.showMessageDialog(this, "Hata oluştu.");
            }
        });

        // SİLME İŞLEMİ
        btnSil.addActionListener(e -> {
            String secilenIsim = listPersonel.getSelectedValue();
            
            if (secilenIsim == null) {
                JOptionPane.showMessageDialog(this, "Lütfen listeden bir personel seçin.");
                return;
            }
            

            
            int onay = JOptionPane.showConfirmDialog(this, 
                    secilenIsim + " adlı personeli silmek istiyor musunuz?", 
                    "Onay", JOptionPane.YES_NO_OPTION);
            
            if (onay == JOptionPane.YES_OPTION) {
                if (repo.deletePersonel(secilenIsim)) {
                    JOptionPane.showMessageDialog(this, "Personel silindi.");
                    
                    listeyiYenile();
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Silme işlemi başarısız!");
                }
            }
        });
    }

    // Listeyi dosyadan çekip ekrana basan metod
    private void listeyiYenile() {
        listModel.clear();
        for (String isim : repo.getPersonelList()) {
        	
            listModel.addElement(isim);
        }
    }
}