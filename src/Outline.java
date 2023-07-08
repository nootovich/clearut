import java.awt.*;

public class Outline extends Element {

    private int   thickness = 1;
    private int[] colors    = new int[3];

    Outline(Element parent, int thickness) {
        this.thickness = thickness;
        setZ(parent.getZ() + 1);
        setParent(parent);
    }

    Outline(Element parent, int thickness, int color) {
        this(parent, thickness);
        setColors(color, color, color);
    }

    Outline(Element parent, int thickness, int idle_color, int hovered_color, int active_color) {
        this(parent, thickness);
        setColors(idle_color, hovered_color, active_color);
    }

    @Override
    public boolean update(int flags) {
        setHovered(getParent().isHovered());
        setActive(getParent().isActive());
        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Element parent = getParent();
        int     x      = parent.getX() + (thickness >> 2);
        int     y      = parent.getY() + (thickness >> 2);
        int     w      = parent.getWidth() - (thickness >> 1);
        int     h      = parent.getHeight() - (thickness >> 1);

        if (Global.LOG > 2) {
            System.out.printf(
                    "\t\tdraw outline of '%s': x:%d, y:%d, z:%d, w:%d, h:%d, : %s%n",
                    parent.getName(), x, y, getZ(), w, h,
                    isVisible() ? " visible" : "!visible"); // $DEBUG
        }

        if (!isVisible()) return;

        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(new Color(getColorBasedOnState()));

        if (parent.getClass() == Sprite.class) {
            Sprite p          = (Sprite) parent;
            int    additional = p.getAdditional() - 4;

            switch (p.getType()) {
                case RECTANGLE -> g2d.drawRect(x, y, w, h);
                case ROUNDED -> g2d.drawRoundRect(x, y, w, h, additional, additional);
            }

        } else {
            g2d.drawRect(x + (thickness >> 1), y + (thickness >> 1), w - thickness, h - thickness);
        }
    }

    public int[] getColors() {
        return colors;
    }

    public int getIdleColor() {
        return colors[0];
    }

    public void setIdleColor(int color) {
        colors[0] = color;
    }

    public int getHoveredColor() {
        return colors[1];
    }

    public void setHoveredColor(int color) {
        colors[1] = color;
    }

    public int getActiveColor() {
        return colors[2];
    }

    public void setActiveColor(int color) {
        colors[2] = color;
    }

    public void setColors(int idleColor, int hoveredColor, int activeColor) {
        colors = new int[]{idleColor, hoveredColor, activeColor};
    }

    private int getColorBasedOnState() {
        if (isActive()) return getActiveColor();
        if (isHovered()) return getHoveredColor();
        return getIdleColor();
    }

}
