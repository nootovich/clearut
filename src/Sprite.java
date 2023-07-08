import java.awt.*;

public class Sprite extends Element {

    private SpriteType type   = SpriteType.RECTANGLE;
    private int[]      colors = new int[3];

    public Sprite(int x, int y, int w, int h, int z) {
        super(x, y, w, h, z);
    }

    public Sprite(int x, int y, int w, int h, int z, int color) {
        super(x, y, w, h, z);
        setColors(color, color, color);
    }

    public Sprite(int x, int y, int w, int h, int z, int color, String name) {
        super(x, y, w, h, z, name);
        setColors(color, color, color);
    }

    public Sprite(int x, int y, int w, int h, int z, int color, String name, String action) {
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
        g2d.setColor(new Color(getColorBasedOnState()));
        switch (getType()) {
            case RECTANGLE -> g2d.fillRect(sx, sy, sw, sh);
            case ROUNDED -> g2d.fillRoundRect(sx, sy, sw, sh, add, add);
        }

        // draw children with higher z order next
        for (; i < descendants.length; i++) {
            descendants[i].draw(g2d);
        }
    }

    public SpriteType getType() {
        return type;
    }

    public Sprite setType(SpriteType type) {
        this.type = type;
        return this;
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

    public Sprite setColors(int idleColor, int hoveredColor, int activeColor) {
        colors = new int[]{idleColor, hoveredColor, activeColor};
        return this;
    }

    private int getColorBasedOnState() {
        if (isActive()) return getActiveColor();
        if (isHovered()) return getHoveredColor();
        return getIdleColor();
    }

    public enum SpriteType {
        RECTANGLE, ROUNDED; // TODO: rename to ROUNDED_RECTANGLE when i get home
    }
}
