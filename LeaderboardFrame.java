import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;


public class LeaderboardFrame extends JFrame {

    private UserManager userManager;

    public LeaderboardFrame(UserManager userManager) {

        this.userManager = userManager;
        User user = userManager.getCurrentUser();

        UIStyle.ThemePalette palette = UIStyle.getPalette(user.getSelectedTheme());

        setTitle("Tic Tac Toe - Leaderboard");
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

        
        JLabel title = new JLabel("🏅 LEADERBOARD", SwingConstants.CENTER);
        title.setFont(new Font("Outfit", Font.BOLD, 26));
        title.setForeground(palette.textMain);
        title.setBounds(20, 20, 370, 35);
        card.add(title);

        
        ArrayList<User> sorted = new ArrayList<>(userManager.getAllUsers().values());
        sorted.sort(Comparator.comparingInt(User::getScore).reversed());

        
        JLabel rankHeader = new JLabel("Rank");
        rankHeader.setFont(UIStyle.FONT_LABEL);

        rankHeader.setForeground(Color.YELLOW);
        rankHeader.setBounds(40, 75, 50, 20);
        card.add(rankHeader);

        JLabel playerHeader = new JLabel("Player");
        playerHeader.setFont(UIStyle.FONT_LABEL);

        playerHeader.setForeground(Color.YELLOW);
        playerHeader.setBounds(110, 75, 140, 20);

        card.add(playerHeader);

        JLabel statsHeader = new JLabel("Wins / Score");

        statsHeader.setFont(UIStyle.FONT_LABEL);
        statsHeader.setForeground(Color.YELLOW);

        statsHeader.setHorizontalAlignment(SwingConstants.RIGHT);
        statsHeader.setBounds(240, 75, 120, 20);

        card.add(statsHeader);

        
        int y = 105;
        int rank = 1;
        for (User u : sorted) {

            if (rank > 6) break; 

            final int currentRank = rank;
            final User rowUser = u;

            
            JPanel rowPanel = new JPanel() {

                @Override
                protected void paintComponent(Graphics g) {

                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;

                    UIStyle.enableAntiAliasing(g2d);

                    
                    Color stripeC;

                    if (currentRank == 1) {
                        stripeC = new Color(251, 191, 36, 40); 
                    } else if (currentRank == 2) {
                        stripeC = new Color(203, 213, 225, 45); 
                    } else if (currentRank == 3) {
                        stripeC = new Color(251, 146, 60, 35); 
                    } else {
                        stripeC = new Color(255, 255, 255, 12); 
                    }

                    g2d.setColor(stripeC);

                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                    
                    
                    g2d.setColor(new Color(255, 255, 255, 25));
                    g2d.setStroke(new BasicStroke(1.0f));
                    g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                }

            };

            rowPanel.setOpaque(false);
            rowPanel.setLayout(null);
            rowPanel.setBounds(20, y, 370, 52);
            card.add(rowPanel);

            
            JLabel rankLabel = new JLabel(String.valueOf(rank), SwingConstants.CENTER);

            rankLabel.setFont(new Font("Outfit", Font.BOLD, 18));
            
            if (rank == 1) rankLabel.setForeground(new Color(251, 191, 36));

            else if (rank == 2) rankLabel.setForeground(new Color(226, 232, 240));

            else if (rank == 3) rankLabel.setForeground(new Color(249, 115, 22));

            else rankLabel.setForeground(palette.textMain);

            rankLabel.setBounds(10, 11, 30, 30);

            rowPanel.add(rankLabel);

            
            JPanel miniAvatar = new JPanel() {

                @Override
                protected void paintComponent(Graphics g) {

                    super.paintComponent(g);

                    UIStyle.drawAvatar((Graphics2D) g, rowUser.getSelectedAvatar(), 0, 0, getWidth());
                }

            };
            miniAvatar.setBounds(50, 9, 34, 34);
            miniAvatar.setOpaque(false);
            rowPanel.add(miniAvatar);

            
            JLabel nameLabel = new JLabel(rowUser.username);

            nameLabel.setFont(new Font("Outfit", Font.BOLD, 15));
            nameLabel.setForeground(palette.textMain);
            
            if (rowUser.username.equals(user.username)) {

                nameLabel.setForeground(new Color(74, 222, 128));
                nameLabel.setText(rowUser.username + " (You)");

            }

            nameLabel.setBounds(95, 15, 140, 22);

            rowPanel.add(nameLabel);

        
            String scoreString = String.format("%d W / %d Pts", rowUser.wins, rowUser.getScore());
            JLabel scoreLabel = new JLabel(scoreString);

            scoreLabel.setFont(new Font("Outfit", Font.BOLD, 14));

            scoreLabel.setForeground(palette.textSecondary);

            scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            scoreLabel.setBounds(230, 15, 120, 22);

            rowPanel.add(scoreLabel);

            y += 60;
            rank++;

        }

        
        ModernButton backBtn = new ModernButton("Back to Menu", palette);

        backBtn.setBounds(105, 525, 200, 40);

        card.add(backBtn);

        backBtn.addActionListener(e -> {
            new MainMenuFrame(userManager).setVisible(true);
            dispose();

        });

    }
    
}