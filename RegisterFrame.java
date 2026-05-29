import javax.swing.*;
import java.awt.*;


public class RegisterFrame extends JFrame {
    private UserManager userManager;

    public RegisterFrame(UserManager userManager) {

        this.userManager = userManager;

        setTitle("Tic Tac Toe - Register");
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

        
        JLabel title = new JLabel("CREATE ACCOUNT", SwingConstants.CENTER);
        title.setFont(new Font("Outfit", Font.BOLD, 26));

        title.setForeground(Color.WHITE);
        title.setBounds(10, 35, 320, 40);

        card.add(title);

        JLabel subtitle = new JLabel("Join us to start competing today!", SwingConstants.CENTER);
        subtitle.setFont(new Font("Outfit", Font.PLAIN, 12));
        subtitle.setForeground(new Color(200, 200, 220, 180));

        subtitle.setBounds(20, 75, 300, 20);
        card.add(subtitle);

        
        JLabel userLabel = new JLabel("CHOOSE USERNAME");
        userLabel.setFont(UIStyle.FONT_LABEL);
        userLabel.setForeground(UIStyle.CLASSIC.textSecondary);
        userLabel.setBounds(30, 125, 280, 20);
        card.add(userLabel);

        ModernTextField userField = new ModernTextField("Desired username");
        userField.setGlowColor(UIStyle.CLASSIC.btnBg);
        userField.setBounds(30, 150, 280, 40);
        card.add(userField);

        
        JLabel passLabel = new JLabel("CHOOSE PASSWORD");

        passLabel.setFont(UIStyle.FONT_LABEL);

        passLabel.setForeground(UIStyle.CLASSIC.textSecondary);

        passLabel.setBounds(30, 205, 280, 20);
        card.add(passLabel);

        ModernPasswordField passField = new ModernPasswordField("••••••••");
        passField.setGlowColor(UIStyle.CLASSIC.btnBg);

        passField.setBounds(30, 230, 280, 40);

        card.add(passField);

        
        ModernButton registerBtn = new ModernButton("Register", UIStyle.CLASSIC);
        registerBtn.setBounds(30, 300, 130, 45);
        card.add(registerBtn);

        ModernButton backBtn = new ModernButton("Back", UIStyle.CLASSIC);
        backBtn.applyPalette(new UIStyle.ThemePalette(

            "Sub", null, null, null, null, null, null,
            new Color(255, 255, 255, 40), new Color(255, 255, 255, 60),
            Color.WHITE, null, null
        ));

        backBtn.setBounds(180, 300, 130, 45);
        card.add(backBtn);

        
        JLabel footer = new JLabel("Antigravity Engine v2.0", SwingConstants.CENTER);
        footer.setFont(new Font("Outfit", Font.PLAIN, 10));
        footer.setForeground(new Color(255, 255, 255, 100));
        footer.setBounds(20, 375, 300, 20);
        card.add(footer);

        
        registerBtn.addActionListener(e -> {

            String username = userField.getText().trim();

            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!");
                return;
            }

            if (userManager.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
                new LoginFrame(userManager).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!");
            }

        });

        
        backBtn.addActionListener(e -> {
            new LoginFrame(userManager).setVisible(true);
            dispose();
        });
    }
}