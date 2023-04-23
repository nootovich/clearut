import java.awt.*;

public class Sprite implements Element {

    private int x, y, w, h, priority;
    private boolean visible = true, outline = false;
    private Color color;

    public Sprite(int x, int y, int w, int h, int priority, Color color) {
        this.x        = x;
        this.y        = y;
        this.w        = w;
        this.h        = h;
        this.priority = priority;
        this.color    = color;
    }

    public void draw(Graphics2D g) {
        if (!visible) return;

        // TODO: make this function use enumeration to identify the type of sprite and draw it appropriately
        g.setColor(color);
        if (outline) {
            int s = Global.STROKE_WIDTH;
            g.setStroke(new BasicStroke(s));
            g.drawRect(x + s / 2, y + s / 2, w - s, h - s);
        } else {
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
