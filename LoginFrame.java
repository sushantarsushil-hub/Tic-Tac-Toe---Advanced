import javax.swing.*;
import java.awt.*;


public class LoginFrame extends JFrame {

    private UserManager userManager;

    public LoginFrame(UserManager userManager) {

        this.userManager = userManager;

        setTitle("Tic Tac Toe - Login");
        setSize(420, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        
        GradientPanel mainPanel = new GradientPanel(UIStyle.CLASSIC);

        mainPanel.setLayout(null);

        add(mainPanel);

        
        JPanel card = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                UIStyle.drawGlassCard((Graphics2D) g, 0, 0, getWidth(), getHeight(), 24, UIStyle.CLASSIC);
            }

        };

        card.setOpaque(false);
        card.setLayout(null);

        card.setBounds(40, 50, 340, 420);

        mainPanel.add(card);

        
        JLabel title = new JLabel("TIC TAC TOE", SwingConstants.CENTER);

        title.setFont(new Font("Outfit", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        title.setBounds(20, 35, 300, 40);
        card.add(title);

        JLabel subtitle = new JLabel("Enter your credentials to play", SwingConstants.CENTER);
        subtitle.setFont(new Font("Outfit", Font.PLAIN, 12));

        subtitle.setForeground(new Color(200, 200, 220, 180));

        subtitle.setBounds(20, 75, 300, 20);

        card.add(subtitle);

        
        JLabel userLabel = new JLabel("USERNAME");

        userLabel.setFont(UIStyle.FONT_LABEL);

        userLabel.setForeground(UIStyle.CLASSIC.textSecondary);

        userLabel.setBounds(30, 125, 280, 20);
        card.add(userLabel);

        ModernTextField userField = new ModernTextField("Username");

        userField.setGlowColor(UIStyle.CLASSIC.btnBg);
        userField.setBounds(30, 150, 280, 40);

        card.add(userField);

        
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(UIStyle.FONT_LABEL);

        passLabel.setForeground(UIStyle.CLASSIC.textSecondary);

        passLabel.setBounds(30, 205, 280, 20);
        card.add(passLabel);

        ModernPasswordField passField = new ModernPasswordField("••••••••");
        passField.setGlowColor(UIStyle.CLASSIC.btnBg);
        passField.setBounds(30, 230, 280, 40);
        card.add(passField);

        
        ModernButton loginBtn = new ModernButton("Login", UIStyle.CLASSIC);

        loginBtn.setBounds(30, 300, 130, 45);
        card.add(loginBtn);

        ModernButton registerBtn = new ModernButton("Register", UIStyle.CLASSIC);
        
        registerBtn.applyPalette(new UIStyle.ThemePalette(

            "Sub", null, null, null, null, null, null,
            new Color(255, 255, 255, 40), new Color(255, 255, 255, 60),
            Color.WHITE, null, null

        ));

        registerBtn.setBounds(180, 300, 130, 45);

        card.add(registerBtn);

        
        JLabel footer = new JLabel("Antigravity Engine v2.0", SwingConstants.CENTER);

        footer.setFont(new Font("Outfit", Font.PLAIN, 10));

        footer.setForeground(new Color(255, 255, 255, 100));

        footer.setBounds(20, 375, 300, 20);

        card.add(footer);

    
        loginBtn.addActionListener(e -> {

            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!");
                return;
            }

            if (userManager.login(username, password)) {
                SoundManager.start();
                new MainMenuFrame(userManager).setVisible(true);
                dispose();
            } 
            
            else {

                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }

        });

        
        registerBtn.addActionListener(e -> {
            new RegisterFrame(userManager).setVisible(true);
            dispose();

        });

    }
    
}