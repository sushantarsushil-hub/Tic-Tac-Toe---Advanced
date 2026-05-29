import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame {
    private UserManager userManager;
    private GameLogic game;
    private AIPlayer ai;
    private boolean vsAI;
    private String difficulty;

    private JButton[] cells = new JButton[9];
    private char currentPlayer = 'X';
    private JLabel statusLabel;
    private JLabel scoreLabel;

    
    private int scoreX = 0, scoreO = 0, scoreDraw = 0;
    
    private UIStyle.ThemePalette palette;

    private User user;

    public GameFrame(UserManager userManager, boolean vsAI, String difficulty) {

        this.userManager = userManager;

        this.vsAI = vsAI;

        this.difficulty = difficulty;

        this.game = new GameLogic();

        this.ai = new AIPlayer('O', 'X');
        
        this.user = userManager.getCurrentUser();

        this.palette = UIStyle.getPalette(user.getSelectedTheme());

        SoundManager.start();

        setTitle("Tic Tac Toe - Game Match");

        setSize(460, 680);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        
        GradientPanel mainPanel = new GradientPanel(palette);

        mainPanel.setLayout(null);

        add(mainPanel);

        
        JPanel topLobby = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                
                UIStyle.drawGlassCard((Graphics2D) g, 0, 0, getWidth(), getHeight(), 20, palette);
            }

        };

        topLobby.setOpaque(false);

        topLobby.setLayout(null);

        topLobby.setBounds(20, 20, 405, 125);

        mainPanel.add(topLobby);

        
        JPanel p1Avatar = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                UIStyle.drawAvatar((Graphics2D) g, user.getSelectedAvatar(), 0, 0, getWidth());
            }

        };

        p1Avatar.setBounds(20, 15, 50, 50);

        p1Avatar.setOpaque(false);

        topLobby.add(p1Avatar);

        JLabel p1Name = new JLabel(user.username, SwingConstants.LEFT);

        p1Name.setFont(new Font("Outfit", Font.BOLD, 14));
        p1Name.setForeground(palette.textMain);

        p1Name.setBounds(20, 70, 90, 20);

        topLobby.add(p1Name);

        JLabel p1Role = new JLabel("PLAYER X", SwingConstants.LEFT);

        p1Role.setFont(new Font("Outfit", Font.PLAIN, 10));
        p1Role.setForeground(palette.textSecondary);

        p1Role.setBounds(20, 88, 90, 15);

        topLobby.add(p1Role);

        
        scoreLabel = new JLabel("0 - 0 - 0", SwingConstants.CENTER);

        scoreLabel.setFont(new Font("Outfit", Font.BOLD, 22));

        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBounds(120, 30, 165, 30);

        topLobby.add(scoreLabel);

        statusLabel = new JLabel("Your Turn!", SwingConstants.CENTER);

        statusLabel.setFont(new Font("Outfit", Font.BOLD, 13));
        statusLabel.setForeground(Color.WHITE);

        statusLabel.setBounds(120, 65, 165, 20);
        topLobby.add(statusLabel);

        
        JPanel p2Avatar = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                String opAvatar = vsAI ? "Robot Avatar" : "Default";
                UIStyle.drawAvatar((Graphics2D) g, opAvatar, 0, 0, getWidth());
            }

        };

        p2Avatar.setBounds(335, 15, 50, 50);

        p2Avatar.setOpaque(false);

        topLobby.add(p2Avatar);

        JLabel p2Name = new JLabel(vsAI ? "Computer" : "Guest P2", SwingConstants.RIGHT);

        p2Name.setFont(new Font("Outfit", Font.BOLD, 14));

        p2Name.setForeground(palette.textMain);

        p2Name.setBounds(295, 70, 90, 20);

        topLobby.add(p2Name);

        JLabel p2Role = new JLabel("PLAYER O", SwingConstants.RIGHT);

        p2Role.setFont(new Font("Outfit", Font.PLAIN, 10));
        p2Role.setForeground(palette.textSecondary);

        p2Role.setBounds(295, 88, 90, 15);

        topLobby.add(p2Role);

    
        JPanel boardPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
            
                UIStyle.drawGlassCard((Graphics2D) g, 0, 0, getWidth(), getHeight(), 24, palette);
            }
        };
        boardPanel.setOpaque(false);

        boardPanel.setLayout(new GridLayout(3, 3, 10, 10));

        boardPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        boardPanel.setBounds(20, 165, 405, 390);


        
        for (int i = 0; i < 9; i++) {

            final int index = i;

            BoardCell cell = new BoardCell(palette, user.getSelectedIcon());

            cell.addActionListener(e -> cellClicked(index));

            cells[i] = cell;
            boardPanel.add(cell);
        }

        mainPanel.add(boardPanel);

        
        JPanel bottomPanel = new JPanel();

        bottomPanel.setOpaque(false);

        bottomPanel.setLayout(null);

        bottomPanel.setBounds(20, 570, 405, 60);

        mainPanel.add(bottomPanel);

        ModernButton restartBtn = new ModernButton("Restart", palette);

        restartBtn.setBounds(15, 5, 175, 42);

        restartBtn.addActionListener(e -> restartGame());

        bottomPanel.add(restartBtn);

        ModernButton menuBtn = new ModernButton("Main Menu", palette);

        menuBtn.setBounds(215, 5, 175, 42);

        menuBtn.addActionListener(e -> {

            new MainMenuFrame(userManager).setVisible(true);
            dispose();

        });

        bottomPanel.add(menuBtn);

        updateScoreDisplay();

        updateStatusText();

    }

    private void cellClicked(int index) {

        if (game.getCell(index) != ' ') return;

        makeMove(index, currentPlayer);

        if (checkGameOver()) return;

    
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        updateStatusText();

        
        if (vsAI && currentPlayer == 'O') {

            Timer timer = new Timer(450, e -> {

                int aiMove = ai.getMove(game, difficulty);

                makeMove(aiMove, 'O');

                if (!checkGameOver()) {

                    currentPlayer = 'X';

                    updateStatusText();

                }

            });

            timer.setRepeats(false);

            timer.start();

        }

    }

    private void makeMove(int index, char player) {
        game.makeMove(index, player);
        BoardCell cell = (BoardCell) cells[index];
        cell.setPlayerMark(player);
        cell.triggerPopAnimation();
    }

    private boolean checkGameOver() {
        char winner = game.checkWinner();
        if (winner != ' ') {

            highlightWinningLine();
            SoundManager.win();

            updateStats(winner);

            if (winner == 'X') scoreX++; else scoreO++;

            updateScoreDisplay();
            
            showResult("Player " + (winner == 'X' ? user.username : (vsAI ? "Computer" : "Player O")) + " Wins! 🎉");
            disableBoard();

            return true;

        }

        if (game.isBoardFull()) {

            SoundManager.draw();
            scoreDraw++;
            user.draws++;
            user.coins += 5;
            userManager.saveUsers();
            
            updateScoreDisplay();

            showResult("It's a Draw! 🤝");
            return true;

        }

        return false;
    }

    private void updateStats(char winner) {

        if (vsAI) {
            if (winner == 'X') {
                user.wins++;
                user.coins += 15;
            } else {
                user.losses++;
            }
        } else {
            if (winner == 'X') {
                user.wins++;
                user.coins += 10;
            } else {
                user.losses++;
            }

        }

        userManager.saveUsers();
        AchievementManager.checkAchievements(user, userManager);

    }

    private void highlightWinningLine() {

        int[] line = game.getWinningLine();
        if (line != null) {
            for (int i : line) {
                ((BoardCell) cells[i]).setWinning(true);
            }
        }
    }

    private void showResult(String message) {

        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private void disableBoard() {
        for (JButton btn : cells) {
            btn.setEnabled(false);
        }

    }

    private void restartGame() {

        game.reset();
        currentPlayer = 'X';
        updateStatusText();
        for (JButton btn : cells) {
            ((BoardCell) btn).reset();

        }

    }

    private void updateScoreDisplay() {

        scoreLabel.setText(String.format("%d - %d - %d", scoreX, scoreDraw, scoreO));

    }

    private void updateStatusText() {
        if (currentPlayer == 'X') {
            statusLabel.setText("Your Turn (X)");
        } else {
            statusLabel.setText(vsAI ? "Computer's Turn (O)" : "Player 2's Turn (O)");

        }
    }

}


class BoardCell extends JButton {

    private char playerMark = ' ';
    private boolean isWinning = false;
    private double scale = 0.0;
    private boolean isHovered = false;
    private UIStyle.ThemePalette palette;
    private String equippedIcon;

    public BoardCell(UIStyle.ThemePalette palette, String equippedIcon) {

        this.palette = palette;

        this.equippedIcon = equippedIcon;
        
        setContentAreaFilled(false);

        setBorderPainted(false);

        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                if (playerMark == ' ') {
                    isHovered = true;
                    repaint();

                }

            }

            @Override

            public void mouseExited(MouseEvent e) {

                isHovered = false;

                repaint();

            }

        });

    }

    public void setPlayerMark(char mark) {

        this.playerMark = mark;

        setEnabled(false);

    }

    public void setWinning(boolean winning) {

        this.isWinning = winning;

        repaint();

    }

    public void triggerPopAnimation() {

        scale = 0.0;
        Timer timer = new Timer(15, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                scale += 0.12;
                if (scale >= 1.0) {

                    scale = 1.0;
                    ((Timer) e.getSource()).stop();

                }

                repaint();

            }

        });

        timer.start();

    }

    public void reset() {

        playerMark = ' ';

        isWinning = false;

        scale = 0.0;
        isHovered = false;

        setEnabled(true);

        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        UIStyle.enableAntiAliasing(g2d);
        int w = getWidth();

        int h = getHeight();

        
        Color bgCol;
        if (isWinning) {

            bgCol = new Color(74, 222, 128, 55); 

        } 
        
        else if (isHovered && isEnabled()) {

            bgCol = new Color(255, 255, 255, 20);
        } 
        
        else {

            if (palette.name.equals("Dark Theme")) {
                bgCol = new Color(255, 255, 255, 8);
            } else if (palette.name.equals("Ocean Theme")) {
                bgCol = new Color(255, 255, 255, 12);
            } else {
                bgCol = new Color(255, 255, 255, 22);
            }

        }
        g2d.setColor(bgCol);
        g2d.fillRoundRect(0, 0, w, h, 14, 14);

        
        Color borderC;

        if (isWinning) {

            borderC = new Color(74, 222, 128);
            g2d.setStroke(new BasicStroke(2.5f));

        } else if (isHovered && isEnabled()) {

            borderC = palette.border.brighter();
            g2d.setStroke(new BasicStroke(1.8f));
        } else {
            borderC = palette.border;
            g2d.setStroke(new BasicStroke(1.2f));
        }
        g2d.drawRoundRect(0, 0, w - 1, h - 1, 14, 14);

        
        if (playerMark != ' ') {

            int markSize = (int) (w * scale);
            int mx = (w - markSize) / 2;

            int my = (h - markSize) / 2;
            
            
            String iconName = (playerMark == 'X') ? equippedIcon : "Classic";

            UIStyle.drawMark(g2d, iconName, playerMark, mx, my, markSize, isWinning, palette);

        }
    }
    
}