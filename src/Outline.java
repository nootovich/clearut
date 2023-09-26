import java.awt.*;

public class Outline extends Member {

    private static final boolean DEBUG = false;

    public boolean visible = true;
    public boolean hovered, active;
    public int   thickness = 1;
    public int[] colors;


    Outline(int thickness, int color) {
        this.colors    = new int[]{color, color, color};
        this.thickness = thickness;
    }

    Outline(int thickness, int idleColor, int hoveredColor, int activeColor) {
        this.colors    = new int[]{idleColor, hoveredColor, activeColor};
        this.thickness = thickness;
    }

    @Override
    public void update(Mouse mouse) {
        Object parentClass = parent.getClass();
        if (!parentClass.equals(Rect.class) && !parentClass.equals(RoundRect.class)) {
            System.out.println("Not implemented parent class for Outline");
            System.exit(1);
        }
        Rect p = (Rect) parent;
        hovered = p.hovered;
        active  = p.active;
        z       = p.z+1;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Object parentClass = parent.getClass();
        if (!parentClass.equals(Rect.class) && !parentClass.equals(RoundRect.class)) {
            System.out.println("Not implemented parent class for Outline");
            System.exit(1);
        }
        Rect p = (Rect) parent;
        int  x = (int) (p.x+thickness/4.f);
        int  y = (int) (p.y+thickness/4.f);
        int  w = (int) (p.w-thickness/2.f);
        int  h = (int) (p.h-thickness/2.f);
        if (DEBUG) System.out.printf("\t\tdraw outline of '%s': x:%d, y:%d, z:%d, w:%d, h:%d, t:%d %s%n",
                                     p.getName(), x, y, p.z+1, w, h, thickness, visible ? " visible" : "!visible"); // $DEBUG
        if (!visible) return;
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(new Color(colors[active ? 2 : hovered ? 1 : 0]));
        if (p.getClass().equals(Rect.class)) g2d.drawRect(x, y, w, h);
        else if (p.getClass().equals(RoundRect.class)) {
            RoundRect o = (RoundRect) parent;
            g2d.drawRoundRect(x, y, w, h, o.rounding, o.rounding);
        }
    }

}
