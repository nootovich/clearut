import java.awt.*;

public class Rect extends Member {

    private static final boolean DEBUG = false;

    public boolean visible = true;
    public boolean hovered, active;
    public float x, y, w, h;
    public int[]  colors = new int[3];
    public String action = "";

    Rect(float x, float y, float w, float h, int z) {
        this.x = x; this.y = y; this.w = w; this.h = h; this.z = z;
    }

    @Override
    public void update(Mouse mouse) {
        updateHigherChildren(mouse);
        // TODO: refactor by adding a "Interactive" interface?
        // if (inherits) {
        //     Sprite p = (Sprite) parent;
        //     hovered = p.hovered;
        //     active  = p.active;
        // } else {
        if (active && mouse.isLMBFallingEdge()) Global.ACTIONS.invoke(action); // TODO: refactor
        hovered = mouse.x > x && mouse.x < x+w && mouse.y > y && mouse.y < y+h;
        active  = hovered && mouse.LMB;
        // }
        updateLowerChildren(mouse);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (DEBUG) System.out.printf("\t\tdraw sprite '%s': x:%d, y:%d, z:%d, w:%d, h:%d, : %s%n",
                                     getName(), x, y, z, w, h, visible ? " visible" : "!visible"); // $DEBUG
        drawLowerChildren(g2d);
        if (visible) {
            g2d.setColor(new Color(getColorBasedOnState()));
            g2d.fillRect((int) x, (int) y, (int) w, (int) h);
        }
        drawHigherChildren(g2d);
    }

    public void setColor(int color) {
        setColors(color, color, color);
    }


    public void setColors(int idleC, int hoveredC, int activeC) {
        colors = new int[]{idleC, hoveredC, activeC};
    }

    public int getColorBasedOnState() {
        return colors[active ? 2 : hovered ? 1 : 0];
    }

    public float getCenterX() {
        return x+w/2.f;
    }

    public float getCenterY() {
        return y+h/2.f;
    }
}
