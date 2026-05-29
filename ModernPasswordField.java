import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class ModernPasswordField extends JPasswordField {

    private String placeholder;

    private boolean isFocused = false;

    private Color glowColor = new Color(147, 51, 234);

    public ModernPasswordField(String placeholder) {

        super();
        this.placeholder = placeholder;
        init();

    }

    private void init() {

        setOpaque(false);

        setForeground(Color.WHITE);

        setCaretColor(Color.WHITE);

        setFont(UIStyle.FONT_BODY);

        setBorder(new EmptyBorder(6, 12, 6, 12));

        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                isFocused = true;
                repaint();

            }

            @Override
            public void focusLost(FocusEvent e) {

                isFocused = false;
                repaint();

            }

        });

    }

    public void setGlowColor(Color glowColor) {
        this.glowColor = glowColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        UIStyle.enableAntiAliasing(g2d);

        int w = getWidth();

        int h = getHeight();

        
        g2d.setColor(new Color(255, 255, 255, 25));

        g2d.fillRoundRect(0, 0, w, h, 10, 10);

    
        if (isFocused) {

            g2d.setColor(glowColor);
            g2d.setStroke(new BasicStroke(2.0f));

            g2d.drawRoundRect(0, 0, w - 1, h - 1, 10, 10);
            
            
            g2d.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 40));
            g2d.setStroke(new BasicStroke(4.0f));

            g2d.drawRoundRect(0, 0, w - 1, h - 1, 10, 10);

        } else {
            g2d.setColor(new Color(255, 255, 255, 60));
            g2d.setStroke(new BasicStroke(1.2f));
            g2d.drawRoundRect(0, 0, w - 1, h - 1, 10, 10);
        }

        super.paintComponent(g);

        
        if (getPassword().length == 0 && placeholder != null && !isFocused) {

            g2d.setColor(new Color(200, 200, 220, 150));
            g2d.setFont(UIStyle.FONT_BODY);

            FontMetrics fm = g2d.getFontMetrics();
            int py = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(placeholder, 12, py);
        }

    }
    
}
