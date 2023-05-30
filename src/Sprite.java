import java.awt.*;

public class Sprite extends Element {

    private int        additional = 0;
    private SpriteType type       = SpriteType.RECTANGLE;
    private Color[]    colors     = new Color[3];

    public Sprite(int x, int y, int w, int h, int z) {
        super(x, y, w, h, z);
    }

    public Sprite(int x, int y, int w, int h, int z, Color color) {
        super(x, y, w, h, z);
        setColors(color, color, color);
    }

    public Sprite(int x, int y, int w, int h, int z, Color color, String name) {
        super(x, y, w, h, z, name);
        setColors(color, color, color);
    }

    public Sprite(int x, int y, int w, int h, int z, Color color, String name, String action) {
        super(x, y, w, h, z, name, action);
        setColors(color, color, color);
    }

    @Override
    public void draw(Graphics2D g2d) {
        int sx  = getX();
        int sy  = getY();
        int sz  = getZ();
        int sw  = getWidth();
        int sh  = getHeight();
        int add = getAdditional();

        if (Global.LOG > 2) {
            System.out.printf(
                    "\t\tdraw sprite '%s': x:%d, y:%d, z:%d, w:%d, h:%d, : %s%n",
                    getName(), sx, sy, sz, sw, sh,
                    isVisible() ? " visible" : "!visible"); // $DEBUG
        }

        if (!isVisible()) return;

        // draw children with lower z order first
        int       i           = 0;
        Element[] descendants = getChildren();
        for (; i < descendants.length; i++) {
            if (descendants[i].getZ() > sz) break;
            descendants[i].draw(g2d);
        }

        // draw sprite
        g2d.setColor(getColorBasedOnState());
        switch (getType()) {
            case RECTANGLE -> g2d.fillRect(sx, sy, sw, sh);
            case ROUNDED -> g2d.fillRoundRect(sx, sy, sw, sh, add, add);
        }

        // draw children with higher z order next
        for (; i < descendants.length; i++) {
            descendants[i].draw(g2d);
        }
    }

    public int getAdditional() {
        return additional;
    }

    public void setAdditional(int additional) {
        this.additional = additional;
    }

    public SpriteType getType() {
        return type;
    }

    public void setType(SpriteType type) {
        this.type = type;
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

    public enum SpriteType {
        RECTANGLE, ROUNDED;
    }
}
