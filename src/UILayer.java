import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class UILayer {

    private final String             name;
    private final int                priority;
    private final ArrayList<Element> elements = new ArrayList<>();

    public UILayer(String name, int priority) {
        this.name     = name.toUpperCase();
        this.priority = priority;
    }

    public boolean update() {
        if (Global.LOG)
            System.out.println("update layer " + getName());

        for (int i = elements.size() - 1; i >= 0; i--)
            if (elements.get(i).update())
                return true;
        return false;
    }

    public void draw(Graphics2D g) {
        if (Global.LOG)
            System.out.println("draw layer " + priority + " : " + name);

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
