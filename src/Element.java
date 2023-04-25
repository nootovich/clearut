import java.awt.*;
import java.util.Comparator;

public interface Element {

    boolean update();

    void draw(Graphics2D g);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getWidth();

    void setWidth(int w);

    int getHeight();

    void setHeight(int h);

    Point getPos();

    void setPos(Point pos);

    Point getSize();

    void setSize(Point size);

    int getPriority();

    void setPriority(int priority);

    boolean isVisible();

    void setVisibility(boolean bool);

    Color getColor();

    void setColor(Color color);
}

class ElementPriorityComparator implements Comparator<Element> {
    @Override
    public int compare(Element a, Element b) {
        return Integer.compare(a.getPriority(), b.getPriority());
    }
}