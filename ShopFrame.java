import javax.swing.*;
import java.awt.*;


public class ShopFrame extends JFrame {
    private UserManager userManager;
    private JLabel coinLabel;

    private String[][] items = {
        {"Dark Theme", "50"},
        {"Ocean Theme", "70"},
        {"Star Icon", "40"},
        {"Heart Icon", "40"},
        {"Robot Avatar", "100"},
        {"Ninja Avatar", "120"}
    };

    public ShopFrame(UserManager userManager) {
        this.userManager = userManager;
        User user = userManager.getCurrentUser();
        UIStyle.ThemePalette palette = UIStyle.getPalette(user.getSelectedTheme());

        setTitle("Tic Tac Toe - Shop");

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
        card.setBounds(25, 25, 410, 590);
        mainPanel.add(card);

        
        JLabel title = new JLabel("🛒 COIN SHOP", SwingConstants.CENTER);

        title.setFont(new Font("Outfit", Font.BOLD, 26));
        title.setForeground(palette.textMain);

        title.setBounds(20, 20, 370, 35);
        card.add(title);

        coinLabel = new JLabel("💰 Coins: " + user.coins, SwingConstants.CENTER);
        coinLabel.setFont(new Font("Outfit", Font.BOLD, 18));
        coinLabel.setForeground(Color.YELLOW);
        coinLabel.setBounds(20, 55, 370, 25);
        card.add(coinLabel);

        
        int y = 95;
        for (String[] item : items) {
            String name = item[0];
            int price = Integer.parseInt(item[1]);

            
            JPanel itemRow = new JPanel() {

                @Override
                protected void paintComponent(Graphics g) {

                    super.paintComponent(g);
                    
                    Graphics2D g2d = (Graphics2D) g;

                    UIStyle.enableAntiAliasing(g2d);

                    g2d.setColor(new Color(255, 255, 255, 12));

                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                    g2d.setColor(new Color(255, 255, 255, 25));

                    g2d.setStroke(new BasicStroke(1.0f));

                    g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                }

            };

            itemRow.setOpaque(false);
            itemRow.setLayout(null);

            itemRow.setBounds(20, y, 370, 60);

            card.add(itemRow);

            
            JPanel previewPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    UIStyle.enableAntiAliasing(g2d);
                    int s = getWidth();

                    if (name.contains("Theme")) {
                        
                        UIStyle.ThemePalette p = UIStyle.getPalette(name);
                        GradientPaint gp = new GradientPaint(0, 0, p.bgStart, s, s, p.bgEnd);
                        g2d.setPaint(gp);

                        g2d.fillOval(2, 2, s - 4, s - 4);
                        g2d.setColor(Color.WHITE);

                        g2d.setStroke(new BasicStroke(1.5f));
                        g2d.drawOval(2, 2, s - 4, s - 4);

                    } else if (name.contains("Icon")) {
                    
                        String iconName = name;
                        UIStyle.drawMark(g2d, iconName, 'X', 0, 0, s, false, palette);
                    } else if (name.contains("Avatar")) {
                    
                        UIStyle.drawAvatar(g2d, name, 0, 0, s);
                    }

                }

            };

            previewPanel.setBounds(15, 10, 40, 40);
            previewPanel.setOpaque(false);
            itemRow.add(previewPanel);

            
            JLabel nameLabel = new JLabel(name);

            nameLabel.setFont(new Font("Outfit", Font.BOLD, 15));

            nameLabel.setForeground(palette.textMain);
            nameLabel.setBounds(70, 10, 180, 20);

            itemRow.add(nameLabel);

            JLabel priceLabel = new JLabel("Price: " + price + " coins");
            priceLabel.setFont(new Font("Outfit", Font.PLAIN, 12));
            priceLabel.setForeground(palette.textSecondary);
            priceLabel.setBounds(70, 30, 180, 20);
            itemRow.add(priceLabel);

            
            boolean isOwned = user.ownedItems.contains(name);
            ModernButton buyBtn = new ModernButton(isOwned ? "Owned" : "Buy", palette);
            buyBtn.setBounds(255, 12, 100, 36);
            if (isOwned) {
                buyBtn.setEnabled(false);
            }
            itemRow.add(buyBtn);

            buyBtn.addActionListener(e -> {
                if (user.coins >= price) {
                    user.coins -= price;
                    user.ownedItems.add(name);
                    
                    if (name.contains("Theme")) {
                        user.selectedTheme = name;
                    } else if (name.contains("Avatar")) {
                        user.selectedAvatar = name;
                    } else if (name.contains("Icon")) {
                        user.selectedIcon = name;
                    }
                    userManager.saveUsers();
                    coinLabel.setText("💰 Coins: " + user.coins);
                    buyBtn.setText("Owned");
                    buyBtn.setEnabled(false);
                    repaint(); // Update preview lists and palettes!
                    JOptionPane.showMessageDialog(this, "Success! Purchased and equipped " + name + "!");
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough coins!");
                }
            });

            y += 68;
        }

        
        ModernButton backBtn = new ModernButton("Back to Menu", palette);
        backBtn.setBounds(105, y + 10, 200, 40);
        card.add(backBtn);

        backBtn.addActionListener(e -> {
            new MainMenuFrame(userManager).setVisible(true);
            dispose();
        });
    }
}