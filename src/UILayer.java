import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class UILayer {

    private final String             name;
    private final ArrayList<Element> elements = new ArrayList<>();
    private       int                priority;

    public UILayer(String name, int priority) {
        this.name     = name.toUpperCase();
        this.priority = priority;
    }

    public boolean update() {
        if (Global.LOG > 2) {
            System.out.println("update layer " + getName());
        }

        for (int i = elements.size() - 1; i >= 0; i--)
            if (elements.get(i).update())
                return true;
        return false;
    }

    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (Global.LOG > 2) {
            System.out.println("draw layer " + getPriority() + " : " + getName());
        }

        for (Element element : elements)
            element.draw(g);
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
