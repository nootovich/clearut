import java.awt.*;

// TODO: think of a better way to construct buttons
public class Button extends Element {

    public Button(int x, int y, int w, int h, int z, String name) {
//        Sprite body    = new InteractiveSprite(x, y, w, h, 0, Global.COLORS[5]);
//        Sprite outline = new InteractiveSprite(x, y, w, h, 1, Global.COLORS[6]);
        // TODO: make Sprite have different types (prob using enum but maybe different classes)
//        outline.setOutline(true);
//        addElement(body);
//        addElement(outline);
//        Global.WINDOW.getLayer(layerName).addElement(this); // TODO: remove
        super(x, y, w, h, z, name);
    }

    public Button(int x, int y, int w, int h, int z, String name, String text) { //
//        Sprite body    = new InteractiveSprite(x, y, w, h, 0, Global.COLORS[5]);
//        Sprite outline = new InteractiveSprite(x, y, w, h, 1, Global.COLORS[6]);
//        Text   text    = new Text(body.getCenter().x, body.getCenter().y, 10, 5, textContent, Global.COLORS[9]);
        // TODO: make Sprite have different types (prob using enum but maybe different classes)
//        outline.setOutline(true);
//        addElement(body);
//        addElement(outline);
//        addElement(text);
//        Global.WINDOW.getLayer(layerName).addElement(this); // TODO: remove
        this(x, y, w, h, z, name);
    }

    @Override
    public boolean update() {
        if (Global.LOG > 1) {
            System.out.println("\tupdate button " + getName());
        }

        // TODO: put everything below into a separate function of Element class
        int     mx         = Global.MOUSE.getX();
        int     my         = Global.MOUSE.getY();
        int     sx         = getX();
        int     sy         = getY();
        int     sw         = getWidth();
        int     sh         = getHeight();
        boolean underMouse = (mx >= sx && mx <= sx + sw && my >= sy && my <= sy + sh);

        if (underMouse && !Global.MOUSE.getLMBUsed() && Global.MOUSE.getLMB()) {
            if (Global.LOG > 1) {
                System.out.println("\t\tmouse clicked at " + getName());
            }

            Global.MOUSE.setLMBUsed(true);
//            try {
//                this.getClass().getMethod(getName()).invoke(this);
//            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
            return true;
        }

        return updateChildren();
    }

    @Override
    public void draw(Graphics2D g2d) {
//        if (Global.LOG)
//            System.out.println("\tdraw button " + priority + " : " + name);

        drawChildren(g2d);
    }

    public void button0() {
        System.out.println("button0 was pressed!");
    }

    public void button1() {
        System.out.println("Hello world!");
    }

    public void button3() {
        setX(getX() + 100);
        System.out.println("button3 move right");
    }

    public void button5() {
        Element[] elements = Global.WINDOW.getLayer("UISIDE").getElements();

        for (Element element : elements) {
            try {
                Button button = (Button) element;
                if (button.getName().equals("button3")) {
                    button.setX(button.getX() - 50);
                    System.out.println("button3 move left");
                }
            } catch (ClassCastException ignored) {
            }
        }
    }

//    public Point getPos() {
//        return new Point(x, y);
//    }
//
//    public void setPos(Point pos) {
//        int changeX = pos.x - this.x;
//        int changeY = pos.y - this.y;
//        this.x = pos.x;
//        this.y = pos.y;
//        for (Element element : getElements()) {
//            element.setX(element.getX() + changeX);
//            element.setY(element.getY() + changeY);
//        }
//    }
//
//    public Point getSize() {
//        return new Point(w, h);
//    }
//
//    public void setSize(Point size) {
//        this.w = size.x;
//        this.h = size.y;
//    }
//
//    public Point getCenter() {
//        return new Point(x + (w >> 1), y + (h >> 1));
//    }

}
