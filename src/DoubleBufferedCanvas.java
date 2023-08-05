import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class DoubleBufferedCanvas extends JPanel {

    // TODO: move MOUSE and KEYBOARD classes from IO inside DBC as adapters (like in GOL)
    public IO.Mouse      mouse;
    public BufferedImage buffer;
    public Layer[]       layers;

    public DoubleBufferedCanvas(int width, int height, IO.Mouse mouse) {
        this.mouse  = mouse;
        this.layers = new Layer[0];
        addMouseWheelListener(mouse);
        addMouseListener(mouse);
        // addKeyListener(Global.KEYBOARD);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
        setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) buffer.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 130);
        updateChildren(mouse);
        drawChildren(g2d);
        g.drawImage(buffer, 0, 0, this);
    }

    public void updateChildren(IO.Mouse mouse) {
        for (int i = layers.length - 1; i >= 0; i--) {
            layers[i].update(mouse);
        }
    }

    public void drawChildren(Graphics2D g2d) {
        for (Layer l : layers) {
            l.draw(g2d);
        }
    }

    public void addLayer(Layer newLayer) {
        Layer[] temp = layers;
        layers                    = new Layer[layers.length + 1];
        layers[layers.length - 1] = newLayer;
        System.arraycopy(temp, 0, layers, 0, temp.length);
        Arrays.sort(layers, new LayerPriorityComparator());
    }

    public Layer getLayer(String searchName) {
        for (Layer l : layers) {
            if (l.name.equals(searchName.toUpperCase())) return l;
        }
        return null;
    }

    // public void updateScrollAt(int x, int y, int wheelRotation) {
    //     int flags = wheelRotation < 0 ? Element.Flags.MWHEELUP : Element.Flags.MWHEELDN;
    //     UILayer[] layers = Main.window.getLayers();
    //     for (int i = layers.length - 1; i >= 0; i--) {
    //         if (layers[i].update(flags)) return;
    //     }
    // }

}
