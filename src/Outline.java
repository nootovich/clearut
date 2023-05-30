import java.awt.*;

public class Outline extends Element {

    private int     thickness = 1;
    private Color[] colors    = new Color[3];

    Outline(Element parent, int thickness) {
        this.thickness = thickness;
        setZ(parent.getZ() + 1);
        setParent(parent);
    }

    Outline(Element parent, int thickness, Color color) {
        this(parent, thickness);
        setColors(color, color, color);
    }

    Outline(Element parent, int thickness, Color idle_color, Color hovered_color, Color active_color) {
        this(parent, thickness);
        setColors(idle_color, hovered_color, active_color);
    }

    @Override
    public boolean update() {
        setHovered(getParent().isHovered());
        setActive(getParent().isActive());
        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Element parent = getParent();
        int     x      = parent.getX() + (thickness >> 1);
        int     y      = parent.getY() + (thickness >> 1);
        int     w      = parent.getWidth() - thickness;
        int     h      = parent.getHeight() - thickness;

        if (Global.LOG > 2) {
            System.out.printf(
                    "\t\tdraw outline of '%s': x:%d, y:%d, z:%d, w:%d, h:%d, : %s%n",
                    parent.getName(), x, y, getZ(), w, h,
                    isVisible() ? " visible" : "!visible"); // $DEBUG
        }

        if (!isVisible()) return;

        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(getColorBasedOnState());

        if (parent.getClass() == Sprite.class) {
            Sprite p          = (Sprite) parent;
            int    additional = p.getAdditional();

            switch (p.getType()) {
                case RECTANGLE -> g2d.drawRect(x, y, w, h);
                case ROUNDED -> g2d.drawRoundRect(x, y, w, h, additional, additional);
            }

        } else {
            g2d.drawRect(x + (thickness >> 1), y + (thickness >> 1), w - thickness, h - thickness);
        }
    }

    public Color[] getColors() {
        return colors;
    }

    public Color getIdleColor() {
        return colors[0];
    }

    public void setIdleColor(Color color) {
        colors[0] = color;
    }

    public Color getHoveredColor() {
        return colors[1];
    }

    public void setHoveredColor(Color color) {
        colors[1] = color;
    }

    public Color getActiveColor() {
        return colors[2];
    }

    public void setActiveColor(Color color) {
        colors[2] = color;
    }

    public void setColors(Color idleColor, Color hoveredColor, Color activeColor) {
        colors = new Color[]{idleColor, hoveredColor, activeColor};
    }

    private Color getColorBasedOnState() {
        if (isActive()) return getActiveColor();
        if (isHovered()) return getHoveredColor();
        return getIdleColor();
    }

}
