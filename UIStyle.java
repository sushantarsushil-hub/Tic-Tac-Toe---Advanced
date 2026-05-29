import java.awt.*;
import java.awt.geom.*;


public class UIStyle {

    
    public static final Font FONT_HEADER = new Font("Outfit", Font.BOLD, 30);

    public static final Font FONT_TITLE = new Font("Outfit", Font.BOLD, 22);

    public static final Font FONT_BODY = new Font("Outfit", Font.PLAIN, 15);

    public static final Font FONT_LABEL = new Font("Outfit", Font.BOLD, 14);

    public static class ThemePalette {
        public String name;
        public Color bgStart;
        public Color bgEnd;
        public Color cardBg;
        public Color textMain;
        public Color textSecondary;
        public Color border;
        public Color btnBg;
        public Color btnHoverBg;
        public Color btnText;
        public Color p1Mark;
        public Color p2Mark;
        
        public ThemePalette(String name, Color bgStart, Color bgEnd, Color cardBg, Color textMain, Color textSecondary,
                            Color border, Color btnBg, Color btnHoverBg, Color btnText, Color p1Mark, Color p2Mark) {
            this.name = name;
            this.bgStart = bgStart;
            this.bgEnd = bgEnd;
            this.cardBg = cardBg;
            this.textMain = textMain;
            this.textSecondary = textSecondary;
            this.border = border;
            this.btnBg = btnBg;
            this.btnHoverBg = btnHoverBg;
            this.btnText = btnText;
            this.p1Mark = p1Mark;
            this.p2Mark = p2Mark;
        }
    }

    
    public static final ThemePalette CLASSIC = new ThemePalette(
        "Classic",
        new Color(43, 45, 66),      
        new Color(26, 27, 38),      
        new Color(255, 255, 255, 20), 
        Color.WHITE,
        new Color(200, 200, 220),
        new Color(255, 255, 255, 40),
        new Color(79, 70, 229),      
        new Color(99, 102, 241),     
        Color.WHITE,
        new Color(239, 68, 68),      
        new Color(59, 130, 246)      
    );

    public static final ThemePalette DARK = new ThemePalette(
        "Dark Theme",
        new Color(15, 12, 30),      
        new Color(4, 3, 10),
        new Color(20, 15, 45, 160), 
        Color.WHITE,
        new Color(170, 150, 220),
        new Color(168, 85, 247, 80),  
        new Color(147, 51, 234),     
        new Color(168, 85, 247),
        Color.WHITE,
        new Color(236, 72, 153),     
        new Color(34, 211, 238)      
    );

    public static final ThemePalette OCEAN = new ThemePalette(
        "Ocean Theme",
        new Color(12, 74, 96),       
        new Color(6, 28, 40),
        new Color(10, 60, 80, 140),  
        new Color(224, 242, 254),
        new Color(186, 230, 253),
        new Color(20, 184, 166, 80), 
        new Color(13, 148, 136),     
        new Color(20, 184, 166),
        Color.WHITE,
        new Color(245, 158, 11),     
        new Color(20, 184, 166)      
    );

    public static ThemePalette getPalette(String name) {
        if (name == null) return CLASSIC;
        switch (name) {
            case "Dark Theme":  return DARK;
            case "Ocean Theme": return OCEAN;
            default:            return CLASSIC;
        }
    }

    
    public static void enableAntiAliasing(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    }

    
    public static void drawGlassCard(Graphics2D g2d, int x, int y, int width, int height, int arc, ThemePalette palette) {
        enableAntiAliasing(g2d);

        
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillRoundRect(x + 3, y + 4, width - 6, height - 6, arc, arc);

        
        g2d.setColor(palette.cardBg);

        g2d.fillRoundRect(x, y, width, height, arc, arc);

        
        g2d.setColor(palette.border);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, width - 1, height - 1, arc, arc);

    }

    
    public static void drawAvatar(Graphics2D g2d, String avatarName, int x, int y, int size) {
        enableAntiAliasing(g2d);
        int r = size / 2;
        int cx = x + r;
        int cy = y + r;

        if (avatarName == null) avatarName = "Default";

        switch (avatarName) {
            case "Robot Avatar": {
                
                g2d.setPaint(new GradientPaint(x, y, new Color(50, 60, 90), x, y + size, new Color(20, 25, 45)));
                g2d.fillOval(x, y, size, size);

                g2d.setColor(new Color(34, 211, 238, 120));

                g2d.setStroke(new BasicStroke(2.0f));

                g2d.drawOval(x, y, size, size);

                
                g2d.setColor(new Color(245, 158, 11));

                g2d.fillRect(cx - 3, cy - r + 8, 6, 12);

                g2d.setColor(new Color(251, 191, 36));

                g2d.fillOval(cx - 6, cy - r + 3, 12, 12);

                
                int headW = (int) (size * 0.55);
                int headH = (int) (size * 0.45);
                int headX = cx - headW / 2;
                int headY = cy - headH / 2 + 3;
                g2d.setPaint(new GradientPaint(headX, headY, new Color(148, 163, 184), headX, headY + headH, new Color(71, 85, 105)));
                g2d.fillRoundRect(headX, headY, headW, headH, 10, 10);
                g2d.setColor(new Color(30, 41, 59));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(headX, headY, headW, headH, 10, 10);

                
                g2d.setColor(new Color(34, 211, 238));

                g2d.fillOval(cx - 16, cy - 6, 10, 10);

                g2d.fillOval(cx + 6, cy - 6, 10, 10);

                g2d.setColor(Color.WHITE);
                g2d.fillOval(cx - 13, cy - 4, 4, 4);

                g2d.fillOval(cx + 9, cy - 4, 4, 4);

                
                g2d.setColor(new Color(30, 41, 59));
                g2d.fillRect(cx - 12, cy + 8, 24, 6);
                g2d.setColor(new Color(34, 211, 238, 180));
                g2d.drawLine(cx - 8, cy + 11, cx + 8, cy + 11);
                break;
            }
            case "Ninja Avatar": {
                
                g2d.setPaint(new GradientPaint(x, y, new Color(30, 30, 35), x, y + size, new Color(10, 10, 12)));
                g2d.fillOval(x, y, size, size);
                g2d.setColor(new Color(239, 68, 68, 120));
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawOval(x, y, size, size);

                
                g2d.setColor(new Color(220, 38, 38));
                Path2D.Double ribbons = new Path2D.Double();
                ribbons.moveTo(cx - r + 8, cy - 5);
                ribbons.curveTo(cx - r - 4, cy - 12, cx - r - 6, cy + 2, cx - r - 12, cy - 2);
                ribbons.curveTo(cx - r - 6, cy + 8, cx - r - 4, cy, cx - r + 8, cy + 5);
                g2d.fill(ribbons);

            
                g2d.setColor(new Color(254, 215, 170)); 

                g2d.fillOval(cx - 20, cy - 14, 40, 20);

                
                g2d.setColor(new Color(24, 24, 27));

                g2d.fillArc(cx - 22, cy - 28, 44, 24, 180, 180);

                g2d.fillArc(cx - 22, cy - 6, 44, 24, 0, 180);

                
                g2d.setColor(new Color(220, 38, 38));
                g2d.fillRect(cx - 19, cy - 17, 38, 6);

                
                g2d.setColor(new Color(239, 68, 68));

                Path2D.Double leftEye = new Path2D.Double();

                leftEye.moveTo(cx - 14, cy - 7);

                leftEye.lineTo(cx - 4, cy - 9);

                leftEye.lineTo(cx - 6, cy - 5);

                leftEye.closePath();

                g2d.fill(leftEye);

                Path2D.Double rightEye = new Path2D.Double();
                rightEye.moveTo(cx + 14, cy - 7);
                rightEye.lineTo(cx + 4, cy - 9);
                rightEye.lineTo(cx + 6, cy - 5);
                rightEye.closePath();
                g2d.fill(rightEye);
                break;
            }
            default: { 
                

                g2d.setPaint(new GradientPaint(x, y, new Color(99, 102, 241), x, y + size, new Color(49, 46, 129)));
                
                g2d.fillOval(x, y, size, size);

                g2d.setColor(new Color(255, 255, 255, 80));

                g2d.setStroke(new BasicStroke(2.0f));

                g2d.drawOval(x, y, size, size);

                
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillArc(cx - 20, cy + 6, 40, 40, 0, 180);

                
                g2d.fillOval(cx - 11, cy - 15, 22, 22);

                
                g2d.setColor(new Color(253, 224, 71)); 
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.drawArc(cx - 13, cy - 16, 26, 26, 20, 140); 
                
                g2d.fillOval(cx - 15, cy - 8, 5, 10);
                g2d.fillOval(cx + 10, cy - 8, 5, 10);
                break;
            }
        }
    }

    
    public static void drawMark(Graphics2D g2d, String markName, char player, int x, int y, int size, boolean isWinningCell, ThemePalette palette) {
        enableAntiAliasing(g2d);

        int pad = (int) (size * 0.22);
        int w = size - 2 * pad;
        int cx = x + size / 2;
        int cy = y + size / 2;

        if (markName == null) markName = "Classic";

        
        Color c = (player == 'X') ? palette.p1Mark : palette.p2Mark;
        if (isWinningCell) {
            c = new Color(34, 197, 94); 
        }

        
        g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 60));
        g2d.setStroke(new BasicStroke(8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        
        if (player == 'X') {
            if (markName.equals("Star Icon")) {
                
                g2d.setColor(new Color(253, 224, 71, 70)); 
                drawStar(g2d, cx + 2, cy + 2, (int) (w * 0.65), (int) (w * 0.28));
                
                g2d.setColor(isWinningCell ? new Color(34, 197, 94) : new Color(253, 224, 71)); // gold fill
                drawStar(g2d, cx, cy, (int) (w * 0.6), (int) (w * 0.25));
                g2d.setColor(Color.WHITE);
                drawStar(g2d, cx, cy, (int) (w * 0.25), (int) (w * 0.1));
            } else if (markName.equals("Heart Icon")) {
                
                g2d.setColor(new Color(239, 68, 68, 70)); 

                drawHeart(g2d, cx - w/2 + 2, cy - w/2 + 2, w);
            
                
                Color startC = isWinningCell ? new Color(74, 222, 128) : new Color(248, 113, 113);
                Color endC = isWinningCell ? new Color(21, 128, 61) : new Color(185, 28, 28);
                g2d.setPaint(new GradientPaint(x, y, startC, x, y + size, endC));
                drawHeart(g2d, cx - w/2, cy - w/2, w);
            } else {
                
                g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 60));

                g2d.setStroke(new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(x + pad, y + pad, x + size - pad, y + size - pad);

                g2d.drawLine(x + size - pad, y + pad, x + pad, y + size - pad);

                Color startC = isWinningCell ? new Color(74, 222, 128) : c.brighter();
                Color endC = isWinningCell ? new Color(21, 128, 61) : c.darker();

                g2d.setPaint(new GradientPaint(x, y, startC, x + size, y + size, endC));
                g2d.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(x + pad, y + pad, x + size - pad, y + size - pad);
                g2d.drawLine(x + size - pad, y + pad, x + pad, y + size - pad);
            }
        } else {
            
            g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 60));

            g2d.setStroke(new BasicStroke(10f));

            g2d.drawOval(x + pad, y + pad, w, w);

            Color startC = isWinningCell ? new Color(74, 222, 128) : c.brighter();
            Color endC = isWinningCell ? new Color(21, 128, 61) : c.darker();

            g2d.setPaint(new GradientPaint(x, y, startC, x + size, y + size, endC));
            g2d.setStroke(new BasicStroke(6f));
            g2d.drawOval(x + pad, y + pad, w, w);
        }
    }

    private static void drawStar(Graphics2D g2, int cx, int cy, int outerR, int innerR) {
        Path2D.Double path = new Path2D.Double();
        double angle = Math.PI / 5.0;
        for (int i = 0; i < 10; i++) {
            double r = (i % 2 == 0) ? outerR : innerR;
            double a = i * angle - Math.PI / 2.0;
            double px = cx + Math.cos(a) * r;
            double py = cy + Math.sin(a) * r;
            if (i == 0) path.moveTo(px, py);
            else path.lineTo(px, py);
        }
        path.closePath();
        g2.fill(path);
    }

    private static void drawHeart(Graphics2D g2, int x, int y, int size) {
        Path2D.Double path = new Path2D.Double();
        double cx = x + size / 2.0;
        double cy = y + size * 0.35;
        double w = size * 0.45;
        double h = size * 0.65;
        path.moveTo(cx, cy);
        path.curveTo(cx - w, cy - h / 2.0, cx - w, cy + h / 3.0, cx, cy + h);
        path.curveTo(cx + w, cy + h / 3.0, cx + w, cy - h / 2.0, cx, cy);
        path.closePath();
        g2.fill(path);

    }
    
}
