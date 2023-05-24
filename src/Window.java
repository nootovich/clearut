import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends JFrame {

    ArrayList<UILayer> layers = new ArrayList<>(); // TODO: turn into a regular array

    public Window(int width, int height) {
        Global.IMAGE = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Global.CANVAS = new DoubleBufferedCanvas(width, height);
        add(Global.CANVAS);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    public UILayer getLayer(String name) {
        for (UILayer layer : layers) {
            if (layer.getName().equals(name.toUpperCase())) {
                return layer;
			}
		}
        return null;
    }

    public UILayer[] getLayers() {
        return layers.toArray(new UILayer[0]);
    }

    public void addLayer(UILayer layer) {
        layers.add(layer);
        layers.sort(new LayerPriorityComparator());
    }

    public void addLayer(String name, int z) {
        layers.add(new UILayer(name, z));
        layers.sort(new LayerPriorityComparator());
    }

    public void addLayerToTop(String name) {
		if (layers.size() == 0) {
			layers.add(new UILayer(name, 0));
			return;
		}
		
        int maxZ = layers.get(layers.size() - 1).getZ();
        layers.add(new UILayer(name, maxZ + 1));
        layers.sort(new LayerPriorityComparator());
    }

}
