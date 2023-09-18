import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    public Dimension size, diff;
    public DoubleBufferedCanvas DBC;

    public Window(int width, int height, int minWidth, int minHeight, IO.Mouse mouse, IO.Keyboard keyboard) {
        size = new Dimension(width, height);
        DBC  = new DoubleBufferedCanvas(width, height, mouse);
        add(DBC);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        diff = new Dimension(getWidth()-size.width, getHeight()-size.height);
        setMinimumSize(new Dimension(minWidth+diff.width, minHeight+diff.height));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("CLOSED");
                super.windowClosing(e);
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension newSize = e.getComponent().getSize();
                if (size.width == newSize.width-diff.width && size.height == newSize.height-diff.height) return;
                size.width  = newSize.width-diff.width;
                size.height = newSize.height-diff.height;
                DBC.buffer  = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                Main.reinit();
            }
        });
        addKeyListener(keyboard);
    }

    public Layer addLayer(String name, int z) {
        return DBC.addLayer(new Layer(name, z));
    }

    public Layer getLayer(String name) {
        return DBC.getLayer(name);
    }

    public Dimension getUsableSpace() {
        return size;
    }
}
