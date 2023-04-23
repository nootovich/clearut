import java.awt.*;

public class DoubleBufferedCanvas extends Canvas {


    public DoubleBufferedCanvas(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
        setVisible(true);
    }

    @Override
    public void update(Graphics g) {
        for (UILayer layer : Global.WINDOW.layers)
            layer.draw((Graphics2D) Global.IMAGE.getGraphics());
        g.drawImage(Global.IMAGE, 0, 0, this);
    }
}
