import java.awt.*;

public class Text implements Element {

    private int     x;
    private int     y;
    // TODO: Prob make width and height actually matter
    private int     w;
    private int     h;
    private int     priority;
    private int     textSize;
    private boolean visible = true;
    private String  text;
    private Color   color;

    public Text(int x, int y, int size, int priority, String text, Color color) {
        this.x        = x;
        this.y        = y;
        this.textSize = size;
        this.priority = priority;
        this.text     = text;
        this.color    = color;
    }

    public boolean update() {
        return false;
    }

    public void draw(Graphics2D g) {
        // TODO: make this function actually use the size of the text
        // TODO: add text centering

        g.setColor(color);
        g.drawString(text, x, y);
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isVisible() {
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

}
