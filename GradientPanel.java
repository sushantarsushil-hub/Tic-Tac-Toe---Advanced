import javax.swing.*;
import java.awt.*;


public class GradientPanel extends JPanel {
    private float cycle = 0f; 
    private UIStyle.ThemePalette palette;

    public GradientPanel() {
        this(UIStyle.CLASSIC);
    }

    public GradientPanel(UIStyle.ThemePalette palette) {
        this.palette = palette;
        
        Timer timer = new Timer(40, e -> {

            cycle += 0.004f;
            if (cycle > 2.0f) cycle = 0f;

            repaint();
        });

        timer.start();

    }

    public void setPalette(UIStyle.ThemePalette palette) {

        this.palette = palette;
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        UIStyle.enableAntiAliasing(g2);

        int w = getWidth();
        int h = getHeight();

        
        float colorShift = (float) (Math.sin(cycle * Math.PI) + 1.0) / 2.0f; 

        Color c1 = interpolateColor(palette.bgStart, palette.bgEnd, colorShift);

        Color c2 = interpolateColor(palette.bgEnd, palette.bgStart, colorShift);

        
        double angle = cycle * Math.PI;
        int x1 = (int) (w / 2.0 + Math.cos(angle) * (w / 2.0));

        int y1 = (int) (h / 2.0 + Math.sin(angle) * (h / 2.0));

        int x2 = (int) (w / 2.0 - Math.cos(angle) * (w / 2.0));

        int y2 = (int) (h / 2.0 - Math.sin(angle) * (h / 2.0));

        GradientPaint gp = new GradientPaint(x1, y1, c1, x2, y2, c2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        
        RadialGradientPaint rgp = new RadialGradientPaint(

            new Point(w / 2, 0),
            Math.max(w, h),

            new float[]{0.0f, 1.0f},

            new Color[]{new Color(255, 255, 255, 30), new Color(0, 0, 0, 0)}

        );

        g2.setPaint(rgp);
        g2.fillRect(0, 0, w, h);

    }

    private Color interpolateColor(Color c1, Color c2, float ratio) {
        int r = (int) (c1.getRed()   + ratio * (c2.getRed()   - c1.getRed()));

        int g = (int) (c1.getGreen() + ratio * (c2.getGreen() - c1.getGreen()));

        int b = (int) (c1.getBlue()  + ratio * (c2.getBlue()  - c1.getBlue()));

        int a = (int) (c1.getAlpha() + ratio * (c2.getAlpha() - c1.getAlpha()));

        return new Color(r, g, b, a);

    }
    
}