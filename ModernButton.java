import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;


public class ModernButton extends JButton {

    private boolean isHovered = false;
    private boolean isPressed = false;
    private Color customBgColor;
    private Color customHoverBgColor;
    private Color customTextColor = Color.WHITE;

    public ModernButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFont(UIStyle.FONT_LABEL);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                SoundManager.click(); 
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    public ModernButton(String text, UIStyle.ThemePalette palette) {
        this(text);

        this.customBgColor = palette.btnBg;

        this.customHoverBgColor = palette.btnHoverBg;

        this.customTextColor = palette.btnText;

    }

    public void applyPalette(UIStyle.ThemePalette palette) {
        this.customBgColor = palette.btnBg;

        this.customHoverBgColor = palette.btnHoverBg;

        this.customTextColor = palette.btnText;

        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        UIStyle.enableAntiAliasing(g2d);

        int w = getWidth();
        int h = getHeight();

        
        int yOffset = isPressed ? 2 : 0;

        int heightReduction = isPressed ? 2 : 0;

        Color bg = customBgColor != null ? customBgColor : new Color(79, 70, 229);

        Color hover = customHoverBgColor != null ? customHoverBgColor : new Color(99, 102, 241);
        
        Color textC = customTextColor != null ? customTextColor : Color.WHITE;

        if (!isEnabled()) {

            bg = new Color(100, 100, 120, 100);

            hover = bg;

            textC = new Color(200, 200, 200, 120);
        }

        
        GradientPaint gp;

        if (isHovered && isEnabled()) {
            gp = new GradientPaint(0, yOffset, hover.brighter(), 0, yOffset + h - heightReduction, hover);
        } else {
            gp = new GradientPaint(0, yOffset, bg.brighter(), 0, yOffset + h - heightReduction, bg);
        }
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, yOffset, w, h - heightReduction, 12, 12);

        
        if (isHovered && isEnabled()) {

            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.setStroke(new BasicStroke(1.5f));

            g2d.drawRoundRect(0, yOffset, w - 1, h - 1 - heightReduction, 12, 12);

        }

        
        g2d.setColor(textC);

        FontMetrics fm = g2d.getFontMetrics(getFont());
        Rectangle2D r = fm.getStringBounds(getText(), g2d);

        int tx = (int) (w - r.getWidth()) / 2;
        int ty = (int) (h - r.getHeight()) / 2 + fm.getAscent() + yOffset - heightReduction/2;
        g2d.setFont(getFont());

        g2d.drawString(getText(), tx, ty);

    }
    
}
