import java.awt.*;
import java.util.Comparator;

public class Layer extends Member {

    private boolean DEBUG = false;

    public Layer(String name, int z) {
        setName(name);
        this.z = z;
    }

    @Override
    public void update(Mouse mouse) {
        if (DEBUG) System.out.printf("update layer %s%n", getName()); // $DEBUG
        for (int i = children.length-1; i >= 0; i--) children[i].update(mouse);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (DEBUG) System.out.printf("draw layer %d : %s%n", z, getName()); // $DEBUG
        for (Member c: children) c.draw(g2d);
    }
}

class LayerComparator implements Comparator<Layer> {

    @Override
    public int compare(Layer a, Layer b) {
        return Integer.compare(a.z, b.z);
    }
}
