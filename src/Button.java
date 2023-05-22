import java.awt.*;
import java.util.ArrayList;

// TODO: think of a better way to construct buttons
public class Button implements Element {

    private final ArrayList<Element> elements = new ArrayList<>();
    private       String             name;
    private       int                x;
    private       int                y;
    private       int                w;
    private       int                h;
    private       int                priority;
    private       boolean            visible  = true;

    public Button(int x, int y, int w, int h, String layerName, String name, int priority) {
        Sprite body    = new InteractiveSprite(x, y, w, h, 0, Global.COLORS[5]);
        Sprite outline = new InteractiveSprite(x, y, w, h, 1, Global.COLORS[6]);
        // TODO: make Sprite have different types (prob using enum but maybe different classes)
        outline.setOutline(true);
        addElement(body);
        addElement(outline);
        this.x        = x;
        this.y        = y;
        this.w        = w;
        this.h        = h;
        this.name     = name;
        this.priority = priority;
        Global.WINDOW.getLayer(layerName).addElement(this);
    }

    public Button(int x, int y, int w, int h, String layerName, String name, int priority, String textContent) { //
        Sprite body    = new InteractiveSprite(x, y, w, h, 0, Global.COLORS[5]);
        Sprite outline = new InteractiveSprite(x, y, w, h, 1, Global.COLORS[6]);
        Text   text    = new Text(body.getCenter().x, body.getCenter().y, 10, 5, textContent, Global.COLORS[9]);
        // TODO: make Sprite have different types (prob using enum but maybe different classes)
        outline.setOutline(true);
        addElement(body);
        addElement(outline);
        addElement(text);
        this.x        = x;
        this.y        = y;
        this.w        = w;
        this.h        = h;
        this.name     = name;
        this.priority = priority;
        Global.WINDOW.getLayer(layerName).addElement(this);
    }

    public boolean update() {
        if (Global.LOG)
            System.out.println("\tupdate button " + name);

        int     mx             = Global.MOUSE.getX();
        int     my             = Global.MOUSE.getY();
        int     sx             = getX();
        int     sy             = getY();
        int     sw             = getWidth();
        int     sh             = getHeight();
        boolean underMouse = (mx >= sx && mx <= sx + sw && my >= sy && my <= sy + sh);

        if (underMouse && !Global.MOUSE.getLMBUsed() && Global.MOUSE.getLMB()) {
            if (Global.LOG)
                System.out.println("\t\tmouse clicked at " + name);

            Global.MOUSE.setLMBUsed(true);
            return true;
        }

        Element[] elmnts      = getElements();
        boolean   interaction = false;

        for (int i = elmnts.length - 1; i >= 0; i--)
             interaction |= elmnts[i].update();

        return interaction;
    }

    public void draw(Graphics2D g) {
        if (Global.LOG)
            System.out.println("\tdraw button " + priority + " : " + name);

        for (Element element : elements)
            element.draw(g);
    }

    public Element[] getElements() {
        return elements.toArray(new Element[0]);
    }

    public void addElement(Element element) {
        elements.add(element);
        elements.sort(new ElementPriorityComparator());
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
        return null;
    }

    public void setColor(Color color) {
    }

    public Point getCenter() {
        return new Point(x + (w >> 1), y + (h >> 1));
    }

}
