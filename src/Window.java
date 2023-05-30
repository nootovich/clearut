import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Window extends JFrame {

    UILayer[] layers = new UILayer[0];

    public Window(int width, int height) {
        Global.IMAGE  = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
        return layers;
    }

    public void addLayer(UILayer layer) {
        Element[] old_array = getLayers();
        layers                    = new UILayer[layers.length + 1];
        layers[layers.length - 1] = layer;
        System.arraycopy(old_array, 0, layers, 0, old_array.length);

        Arrays.sort(layers, new LayerPriorityComparator());
    }

    public void addLayerToTop(String name) {
        int newZ = layers.length == 0 ? 0 : layers[layers.length - 1].getZ();
        addLayer(new UILayer(name, newZ));
    }

}
