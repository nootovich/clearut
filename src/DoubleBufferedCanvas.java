import java.awt.*;

public class DoubleBufferedCanvas extends Canvas {


    public DoubleBufferedCanvas(int width, int height) {
        addMouseListener(Global.MOUSE);
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
        setVisible(true);
    }

    @Override
    public void update(Graphics g) {
        UILayer[] layers = Global.WINDOW.getLayers();

        for (int i = layers.length - 1; i >= 0; i--)
            if (layers[i].update())
                break;

        for (UILayer layer : layers)
            layer.draw((Graphics2D) Global.IMAGE.getGraphics());

        g.drawImage(Global.IMAGE, 0, 0, this);
        Global.FRAMECOUNT++;
    }
}
