import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class UILayer {

    private final String             name;
    private final int                priority;
    private final ArrayList<Element> elements = new ArrayList<>(); // TODO: rename to elements

    public UILayer(String name, int priority) {
        this.name     = name.toUpperCase();
        this.priority = priority;
    }

    public void draw(Graphics2D g) {
        for (Element element : elements)
            element.draw(g);
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public Element[] getElements() {
        return elements.toArray(new Element[0]);
    }

    public void addElement(Element element) {
        elements.add(element);
        elements.sort(new ElementPriorityComparator());
    }
}

class LayerPriorityComparator implements Comparator<UILayer> {
    @Override
    public int compare(UILayer a, UILayer b) {
        return Integer.compare(a.getPriority(), b.getPriority());
    }
}
