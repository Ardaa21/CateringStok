package foodstore;

import javax.swing.SwingUtilities;

public class Main {
	
    public static void main(String[] args) {
        
        UserFileRepository repo = new UserFileRepository("kullanicilar.txt");
        
       
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame(repo);
            frame.setVisible(true);
        });
    }

}
