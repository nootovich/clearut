import java.awt.*;

public class Outline extends Child { // TODO: needs some rework

    private static final boolean DEBUG = false;

    public int     thickness = 1;
    public int[]   colors    = new int[3];
    public boolean visible   = true, hovered = false, active = false;


    Outline(int thickness, int z) {
        this.thickness = thickness; this.z = z;
    }

    Outline(int thickness, int z, int color) {
        this.thickness = thickness; this.z = z;
        setColors(color, color, color);
    }

    Outline(int thickness, int z, int idle_color, int hovered_color, int active_color) {
        this.thickness = thickness; this.z = z;
        setColors(idle_color, hovered_color, active_color);
    }

    @Override
    public void update(IO.Mouse mouse) {
        if (parent.getClass() == Sprite.class) {
            hovered = ((Sprite) parent).hovered;
            active  = ((Sprite) parent).active;
        }
        // TODO: implement "Interactive" class
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (parent.getClass() != Sprite.class) {
            //g2d.drawRect(x + (thickness / 2), y + (thickness / 2), w - thickness, h - thickness);
            return;
        }
        Sprite p = (Sprite) parent;
        int    x = p.x+thickness/4;
        int    y = p.y+thickness/4;
        int    w = p.w-thickness/2;
        int    h = p.h-thickness/2;
        if (DEBUG) System.out.printf("\t\tdraw outline of '%s': x:%d, y:%d, z:%d, w:%d, h:%d, t:%d %s%n",
            p.name, x, y, p.z+1, w, h, thickness, visible ? " visible" : "!visible"); // $DEBUG
        if (!visible) return;
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(new Color(getColorBasedOnState()));
        switch (p.type) {
            case RECTANGLE -> g2d.drawRect(x, y, w, h);
            case ROUNDED_RECTANGLE -> g2d.drawRoundRect(x, y, w, h, p.extra, p.extra);
        }
    }

    public void setColors(int idleColor, int hoveredColor, int activeColor) {
        colors = new int[]{idleColor, hoveredColor, activeColor};
    }

    private int getColorBasedOnState() {
        if (active) return colors[2];
        if (hovered) return colors[1];
        return colors[0];
    }
}
