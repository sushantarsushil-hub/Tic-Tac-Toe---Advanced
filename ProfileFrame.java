import javax.swing.*;
import java.awt.*;


public class ProfileFrame extends JFrame {

    private UserManager userManager;

    public ProfileFrame(UserManager userManager) {
        this.userManager = userManager;

        User user = userManager.getCurrentUser();

        UIStyle.ThemePalette palette = UIStyle.getPalette(user.getSelectedTheme());

        setTitle("Tic Tac Toe - Profile & Inventory");

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

        card.setBounds(25, 20, 410, 600);

        mainPanel.add(card);

        
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                UIStyle.drawAvatar((Graphics2D) g, user.getSelectedAvatar(), 0, 0, getWidth());
            }

        };

        avatarPanel.setBounds(155, 15, 100, 100);

        avatarPanel.setOpaque(false);

        card.add(avatarPanel);

        
        JLabel usernameLabel = new JLabel(user.username, SwingConstants.CENTER);

        usernameLabel.setFont(new Font("Outfit", Font.BOLD, 22));

        usernameLabel.setForeground(palette.textMain);

        usernameLabel.setBounds(20, 120, 370, 30);

        card.add(usernameLabel);

        
        JPanel statsPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                UIStyle.enableAntiAliasing(g2d);
                g2d.setColor(new Color(255, 255, 255, 12));

                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2d.setColor(new Color(255, 255, 255, 25));

                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            }

        };
        statsPanel.setOpaque(false);

        statsPanel.setLayout(new GridLayout(2, 3, 5, 5));

        statsPanel.setBounds(20, 160, 370, 75);

        card.add(statsPanel);

        
        statsPanel.add(createStatCell("Wins", String.valueOf(user.wins), palette));

        statsPanel.add(createStatCell("Losses", String.valueOf(user.losses), palette));

        statsPanel.add(createStatCell("Draws", String.valueOf(user.draws), palette));

        statsPanel.add(createStatCell("Total", String.valueOf(user.getTotalGames()), palette));

        statsPanel.add(createStatCell("Coins", "💰 " + user.coins, palette));

        statsPanel.add(createStatCell("Score", "🏅 " + user.getScore(), palette));

        
        JLabel badgeTitle = new JLabel("🏆 UNLOCKED BADGES");
        badgeTitle.setFont(UIStyle.FONT_LABEL);
        badgeTitle.setForeground(Color.YELLOW);
        badgeTitle.setBounds(30, 250, 350, 20);
        card.add(badgeTitle);

        StringBuilder badgeText = new StringBuilder("<html><div style='font-family:Outfit; font-size:11px; color:#E2E8F0;'>");
        if (user.badges.isEmpty()) {
            badgeText.append("No badges yet. Play games to earn trophies!");
        } else {
            for (int i = 0; i < user.badges.size(); i++) {
                badgeText.append("✨ ").append(user.badges.get(i));
                if (i < user.badges.size() - 1) {
                    badgeText.append(" &nbsp;•&nbsp; ");
                }
            }
        }

        badgeText.append("</div></html>");

        JLabel badgeList = new JLabel(badgeText.toString());

        badgeList.setBounds(30, 275, 350, 40);

        badgeList.setVerticalAlignment(SwingConstants.TOP);
        card.add(badgeList);

        
        JLabel equipTitle = new JLabel("⚔️ EQUIP INVENTORY");

        equipTitle.setFont(UIStyle.FONT_LABEL);

        equipTitle.setForeground(Color.YELLOW);

        equipTitle.setBounds(30, 325, 350, 20);

        card.add(equipTitle);

        
        java.util.ArrayList<String> ownedAvatars = new java.util.ArrayList<>();

        java.util.ArrayList<String> ownedThemes = new java.util.ArrayList<>();

        java.util.ArrayList<String> ownedIcons = new java.util.ArrayList<>();

        for (String item : user.ownedItems) {

            if (item.contains("Avatar") || item.equals("Default")) {
                ownedAvatars.add(item);
            }
            if (item.contains("Theme") || item.equals("Classic")) {
                ownedThemes.add(item);
            }
            if (item.contains("Icon") || item.equals("Classic")) {
                ownedIcons.add(item);
            }

        }

        
        int labelY = 355;
        int comboY = 378;

        
        JLabel avatarLabel = new JLabel("EQUIP AVATAR");

        avatarLabel.setFont(new Font("Outfit", Font.BOLD, 11));

        avatarLabel.setForeground(palette.textSecondary);

        avatarLabel.setBounds(30, labelY, 160, 18);

        card.add(avatarLabel);

        JComboBox<String> avatarCombo = new JComboBox<>(ownedAvatars.toArray(new String[0]));

        avatarCombo.setSelectedItem(user.getSelectedAvatar());

        styleComboBox(avatarCombo, palette);
        avatarCombo.setBounds(30, comboY, 160, 35);

        card.add(avatarCombo);

        avatarCombo.addActionListener(e -> {
            user.selectedAvatar = (String) avatarCombo.getSelectedItem();
            userManager.saveUsers();
            repaint();
        });

        
        JLabel themeLabel = new JLabel("BOARD THEME");
        themeLabel.setFont(new Font("Outfit", Font.BOLD, 11));
        themeLabel.setForeground(palette.textSecondary);
        themeLabel.setBounds(220, labelY, 160, 18);
        card.add(themeLabel);

        JComboBox<String> themeCombo = new JComboBox<>(ownedThemes.toArray(new String[0]));
        themeCombo.setSelectedItem(user.getSelectedTheme());
        styleComboBox(themeCombo, palette);
        themeCombo.setBounds(220, comboY, 160, 35);
        card.add(themeCombo);

        themeCombo.addActionListener(e -> {
            user.selectedTheme = (String) themeCombo.getSelectedItem();
            userManager.saveUsers();
            
            UIStyle.ThemePalette newPal = UIStyle.getPalette(user.getSelectedTheme());
            mainPanel.setPalette(newPal);
            repaint();

        });

        
        JLabel iconLabel = new JLabel("EQUIP GAME MARK (PLAYER 1)");

        iconLabel.setFont(new Font("Outfit", Font.BOLD, 11));

        iconLabel.setForeground(palette.textSecondary);

        iconLabel.setBounds(30, labelY + 65, 350, 18);

        card.add(iconLabel);

        JComboBox<String> iconCombo = new JComboBox<>(ownedIcons.toArray(new String[0]));

        iconCombo.setSelectedItem(user.getSelectedIcon());

        styleComboBox(iconCombo, palette);

        iconCombo.setBounds(30, comboY + 65, 350, 35);

        card.add(iconCombo);

        iconCombo.addActionListener(e -> {
            user.selectedIcon = (String) iconCombo.getSelectedItem();
            userManager.saveUsers();
            repaint();

        });

        
        ModernButton backBtn = new ModernButton("Back to Menu", palette);

        backBtn.setBounds(105, 535, 200, 40);

        card.add(backBtn);

        backBtn.addActionListener(e -> {
            new MainMenuFrame(userManager).setVisible(true);
            dispose();
        });

    }

    private JPanel createStatCell(String name, String value, UIStyle.ThemePalette palette) {
        JPanel cell = new JPanel(new GridLayout(2, 1));
        cell.setOpaque(false);

        JLabel valLbl = new JLabel(value, SwingConstants.CENTER);
        valLbl.setFont(new Font("Outfit", Font.BOLD, 16));
        valLbl.setForeground(Color.WHITE);

        JLabel nameLbl = new JLabel(name.toUpperCase(), SwingConstants.CENTER);
        nameLbl.setFont(new Font("Outfit", Font.PLAIN, 10));
        nameLbl.setForeground(palette.textSecondary);

        cell.add(valLbl);
        cell.add(nameLbl);
        return cell;
    }

    private void styleComboBox(JComboBox<String> cb, UIStyle.ThemePalette palette) {
        cb.setFont(new Font("Outfit", Font.BOLD, 13));

        cb.setBackground(new Color(255, 255, 255, 35));

        cb.setForeground(Color.WHITE);

        cb.setFocusable(false);

        cb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, val, index, isSelected, cellHasFocus);
                lbl.setFont(new Font("Outfit", Font.BOLD, 13));
                lbl.setOpaque(true);
                if (isSelected) {
                    lbl.setBackground(palette.btnBg);
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(new Color(30, 30, 50));
                    lbl.setForeground(Color.WHITE);
                }
                lbl.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                return lbl;
            }
        });
    }
}