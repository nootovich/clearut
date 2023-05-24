import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class UILayer {

    private final String             name;
    private final ArrayList<Element> elements = new ArrayList<>(); // TODO: turn into a regular array
	private final ArrayList<Button>  buttons  = new ArrayList<>(); // TODO: this is a temporary solution
    private       int                z;

    public UILayer(String name, int z) {
        this.name     = name.toUpperCase();
        this.z = z;
    }

    public boolean update() {
        if (Global.LOG > 2) {
            System.out.println("update layer " + getName());
        } // $DEBUG

		// TODO: this is temporary
        for (int i = buttons.size() - 1; i >= 0; i--) {
            if (buttons.get(i).update()) {
                return true;
			}
		}
		
        for (int i = elements.size() - 1; i >= 0; i--) {
            if (elements.get(i).update()) {
                return true;
			}
		}
		
        return false;
    }

    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (Global.LOG > 2) {
            System.out.println("draw layer " + getZ() + " : " + getName());
        } // $DEBUG

		// TODO: this is temporary
        for (Button button : buttons) {
            button.draw(g);
		}
		
        for (Element element : elements) {
            element.draw(g);
		}
    }

    public String getName() {
        return name;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Element[] getElements() {
        return elements.toArray(new Element[0]);
    }

    public void addElement(Element element) {
        elements.add(element);
        elements.sort(new ElementPriorityComparator());
    }
	
    public void addButton(Button button) {
        buttons.add(button);
        // elements.sort(new ElementPriorityComparator());
    }
}

class LayerPriorityComparator implements Comparator<UILayer> {
    @Override
    public int compare(UILayer a, UILayer b) {
        return Integer.compare(a.getZ(), b.getZ());
    }
}
