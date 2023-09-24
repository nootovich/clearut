import java.awt.*;

public class Sprite extends Member {

    private static final boolean DEBUG = false;

    public int x, y, w, h, extra = 0;
    public int[]   colors  = new int[3];
    public boolean visible = true, interactive = false, hovered = false, active = false, inherits = false;
    public String     action = "";
    public SpriteType type   = SpriteType.RECTANGLE;

    public Sprite(int x, int y, int w, int h, int z) {
        this.x = x; this.y = y; this.w = w; this.h = h; this.z = z;
    }

    public Sprite(int x, int y, int w, int h, int z, int color) {
        this(x, y, w, h, z);
        colors = new int[]{color, color, color};
    }

    public Sprite(int x, int y, int w, int h, int z, String name) {
        this(x, y, w, h, z);
        this.name = name.toUpperCase();
    }

    public Sprite(int x, int y, int w, int h, int z, int color, String name) {
        this(x, y, w, h, z, color);
        this.name = name.toUpperCase();
    }

    public Sprite(int x, int y, int w, int h, int z, String name, String action) {
        this(x, y, w, h, z, name);
        this.action = action;
    }

    public Sprite(int x, int y, int w, int h, int z, int color, String name, String action) {
        this(x, y, w, h, z, color, name);
        this.action = action;
    }

    @Override
    public void update(IO.Mouse mouse) {
        updateHigherChildren(mouse);
        if (inherits) {
            // TODO: refactor by adding a "Interactive" interface
            Sprite p = (Sprite) parent;
            hovered = p.hovered;
            active  = p.active;
        } else {
            if (active && mouse.isLMBFallingEdge()) Global.ACTIONS.invoke(action); // TODO: refactor
            hovered = mouse.x > x && mouse.x < x+w && mouse.y > y && mouse.y < y+h;
            active  = hovered && mouse.LMB;
        }
        updateLowerChildren(mouse);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (DEBUG) System.out.printf("\t\tdraw sprite '%s': x:%d, y:%d, z:%d, w:%d, h:%d, : %s%n",
            name, x, y, z, w, h, visible ? " visible" : "!visible"); // $DEBUG
        drawLowerChildren(g2d);
        if (visible) {
            g2d.setColor(new Color(getColorBasedOnState()));
            switch (type) {
                case RECTANGLE -> g2d.fillRect(x, y, w, h);
                case ROUNDED_RECTANGLE -> g2d.fillRoundRect(x, y, w, h, extra, extra);
            }
        }
        drawHigherChildren(g2d);
    }

    public void setColor(int color) {
        setColors(color, color, color);
    }

    public void setColors(int idleC, int hoveredC, int activeC) {
        colors[0] = idleC; colors[1] = hoveredC; colors[2] = activeC;
    }

    private int getColorBasedOnState() {
        if (active) return colors[2];
        if (hovered) return colors[1];
        return colors[0];
    }

    public int getCenterX() {
        return x+w/2;
    }

    public int getCenterY() {
        return y+h/2;
    }

    public enum SpriteType {
        RECTANGLE, ROUNDED_RECTANGLE
    }
}
