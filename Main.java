import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        
        UserManager userManager = new UserManager();

        
        SwingUtilities.invokeLater(() -> {
            new LoginFrame(userManager).setVisible(true);

        });

    }
    
}