import javax.swing.*;
import java.awt.*;


public class MainMenuFrame extends JFrame {
    private UserManager userManager;

    public MainMenuFrame(UserManager userManager) {

        this.userManager = userManager;

        User user = userManager.getCurrentUser();

        UIStyle.ThemePalette palette = UIStyle.getPalette(user.getSelectedTheme());

        setTitle("Tic Tac Toe - Main Menu");

        setSize(460, 680);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        
        GradientPanel mainPanel = new GradientPanel(palette);

        mainPanel.setLayout(null);

        add(mainPanel);

        
        JPanel card = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                UIStyle.drawGlassCard((Graphics2D) g, 0, 0, getWidth(), getHeight(), 24, palette);
            }

        };

        card.setOpaque(false);

        card.setLayout(null);

        card.setBounds(35, 40, 390, 560);

        mainPanel.add(card);

        
        JPanel avatarPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                UIStyle.drawAvatar((Graphics2D) g, user.getSelectedAvatar(), 0, 0, getWidth());
            }

        };

        avatarPanel.setBounds(155, 30, 80, 80);

        avatarPanel.setOpaque(false);

        card.add(avatarPanel);

        
        JLabel welcome = new JLabel("Welcome, " + user.username + "!", SwingConstants.CENTER);

        welcome.setFont(new Font("Outfit", Font.BOLD, 22));

        welcome.setForeground(palette.textMain);

        welcome.setBounds(20, 120, 350, 30);

        card.add(welcome);

        
        JLabel coins = new JLabel("💰 Coins: " + user.coins, SwingConstants.CENTER);

        coins.setFont(new Font("Outfit", Font.BOLD, 16));

        coins.setForeground(Color.YELLOW);

        coins.setBounds(20, 155, 350, 25);

        card.add(coins);

        
        String[] options = {
            "Play vs Player", "Play vs AI", "Shop",
            "Profile", "Leaderboard", "Logout"
        };

        int y = 200;
        for (String option : options) {
            ModernButton btn = new ModernButton(option, palette);
            btn.setBounds(70, y, 250, 45);
            card.add(btn);
            y += 55;

            
            btn.addActionListener(e -> {
                handleClick(option);
            });

        }

    }

    private void handleClick(String option) {
        switch (option) {
            case "Play vs Player":
                new GameFrame(userManager, false, "Easy").setVisible(true);
                dispose();
                break;
            case "Play vs AI":
                
                String difficulty = (String) JOptionPane.showInputDialog(

                    this, "Choose Difficulty:", "AI Mode",
                    JOptionPane.QUESTION_MESSAGE, null,

                    new String[]{"Easy", "Medium", "Hard"}, "Easy");
                if (difficulty != null) {
                    new GameFrame(userManager, true, difficulty).setVisible(true);
                    dispose();
                }

                break;
            case "Shop":
                new ShopFrame(userManager).setVisible(true);
                dispose();

                break;
            case "Profile":
                new ProfileFrame(userManager).setVisible(true);
                dispose();

                break;
            case "Leaderboard":
                new LeaderboardFrame(userManager).setVisible(true);
                dispose();

                break;
            case "Logout":
                new LoginFrame(userManager).setVisible(true);
                dispose();
                break;
        }
    }
    
}