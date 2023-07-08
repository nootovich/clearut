import java.awt.*;
import java.util.Comparator;

public class UILayer extends Element {

    public UILayer(String name, int z) {
        setName(name);
        setZ(z);
    }

    @Override
    public boolean update(int flags) {
        if (Global.LOG > 2) {
            System.out.println("update layer " + getName());
        } // $DEBUG

        Element[] descendants = getChildren();
        for (int i = descendants.length - 1; i >= 0; i--) {
            if (descendants[i].update(flags)) return true;
        }

        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (Global.LOG > 2) {
            System.out.println("draw layer " + getZ() + " : " + getName());
        } // $DEBUG

        drawChildren(g2d);
    }

}

class LayerPriorityComparator implements Comparator<UILayer> {
    @Override
    public int compare(UILayer a, UILayer b) {
        return Integer.compare(a.getZ(), b.getZ());
    }
}
