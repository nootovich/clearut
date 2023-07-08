import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Window extends JFrame {

    UILayer[] layers = new UILayer[0];

    public Window(int width, int height) {
        Global.IMAGE  = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Global.CANVAS = new DoubleBufferedCanvas(width, height);
        add(Global.CANVAS);

        Sprite bg = new Sprite(0, 0, width, height, 0, 0, "window_bg");
        bg.setInteractive(false);
        layer("window").addChild(bg);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    public void setBG(int color) {
        Sprite bg = (Sprite) layer("window").getChild("window_bg");
        Global.asrt(bg != null, "Couldn't find background for some reason.");
        bg.setIdleColor(color);
    }

    public UILayer layer(String name) {
        UILayer layer = getLayer(name);
        if (layer != null) return layer;
        addLayerToTop(name);
        return getLayer(name);
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
        int newZ = layers.length == 0 ? 0 : layers[layers.length - 1].getZ() + 1;
        addLayer(new UILayer(name, newZ));
    }

}
