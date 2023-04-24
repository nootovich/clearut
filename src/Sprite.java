import java.awt.*;

public class Sprite implements Element {

    private int     x;
    private int     y;
    private int     w;
    private int     h;
    private int     priority;
    private boolean visible     = true;
    private boolean highlighted = false;
    private boolean outline     = false; // TODO: rework Sprite types
    private Color   color; // TODO: add colors for highlighting and activating

    public Sprite(int x, int y, int w, int h, int priority, Color color) {
        this.x        = x;
        this.y        = y;
        this.w        = w;
        this.h        = h;
        this.priority = priority;
        this.color    = color;
    }

    public boolean update() {
        if (Global.LOG)
            System.out.println("\t\tupdate sprite " + priority
                                       + (outline ? " : outline" : "")
                                       + (highlighted ? " : " + "highlighted" : ""));

        int mx = Global.MOUSE.getX(); // TODO: reuse from Button.update()
        int my = Global.MOUSE.getY();
        int sx = getX();
        int sy = getY();
        int sw = getWidth();
        int sh = getHeight();
        highlighted = (mx >= sx && mx <= sx + sw && my >= sy && my <= sy + sh);

        if (Global.LOG && highlighted)
            System.out.println("\t\t\tinside ! " + priority);

        return highlighted;
    }

    public void draw(Graphics2D g) {
        if (Global.LOG)
            System.out.println("\t\tdraw sprite " + priority + " : "
                                       + (visible ? " vis" : "!vis")
                                       + (outline ? " " + ": outline" : "")
                                       + (highlighted ? " : " + "highlighted" : ""));

        if (!visible)
            return;

        if (Global.LOG)
            System.out.println("\t\t\tvisible");

        // TODO: make this function use enumeration to identify the type of sprite and draw it appropriately
        if (outline) {
            if (Global.LOG)
                System.out.println("\t\t\toutline");

            int s = Global.STROKE_WIDTH;
            g.setStroke(new BasicStroke(s));

            if (highlighted) {
                if (Global.LOG)
                    System.out.println("\t\t\t\thighlighted");

                g.setColor(Global.COLOR_RED_MUNSELL);
                highlighted = false;
            } else {
                if (Global.LOG)
                    System.out.println("\t\t\t\t!highlighted");

                g.setColor(color);
            }
            g.drawRect(x + s / 2, y + s / 2, w - s, h - s);
        } else {
            if (Global.LOG)
                System.out.println("\t\t\tsolid");

            if (highlighted) {
                if (Global.LOG)
                    System.out.println("\t\t\t\thighlighted");

                g.setColor(Global.COLOR_MELON);
                highlighted = false;
            } else {
                if (Global.LOG)
                    System.out.println("\t\t\t\t!highlighted");

                g.setColor(color);
            }

            g.fillRect(x, y, w, h);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    public Point getPos() {
        return new Point(x, y);
    }

    public void setPos(Point pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public Point getSize() {
        return new Point(w, h);
    }

    public void setSize(Point size) {
        this.w = size.x;
        this.h = size.y;
    }

    public Point getCenter() {
        return new Point(x + (w >> 1), y + (h >> 1));
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean getVisibility() {
        return visible;
    }

    public void setVisibility(boolean bool) {
        visible = bool;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean getOutline() {
        return outline;
    }

    public void setOutline(boolean bool) {
        outline = bool;
    }
}
