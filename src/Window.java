import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends JFrame {

    ArrayList<UILayer> layers = new ArrayList<>();


    public Window(int width, int height, Color backgroundColor) {

        // init buffered image
        Global.IMAGE = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // init canvas
        Global.CANVAS = new DoubleBufferedCanvas(width, height);
        add(Global.CANVAS);

        // init bg
        UILayer bg = new UILayer("BG", 0);
        bg.addElement(new Sprite(0, 0, width, height, 0, backgroundColor));
        addLayer(bg);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    public UILayer getLayer(String name) {
        for (UILayer layer : layers)
            if (layer.getName().equals(name.toUpperCase()))
                return layer;
        return null;
    }

    public UILayer[] getLayers() {
        return layers.toArray(new UILayer[0]);
    }

    public void addLayer(UILayer layer) {
        layers.add(layer);
        layers.sort(new LayerPriorityComparator());
    }

    public void addLayer(String name, int priority) {
        layers.add(new UILayer(name, priority));
        layers.sort(new LayerPriorityComparator());
    }

    public void addLayerToTop(String name) {
        int maxPriority = layers.get(layers.size() - 1).getPriority();
        layers.add(new UILayer(name, maxPriority + 1));
        layers.sort(new LayerPriorityComparator());
    }

}
