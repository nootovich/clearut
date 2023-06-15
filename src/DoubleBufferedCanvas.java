import java.awt.*;

public class DoubleBufferedCanvas extends Canvas {

    private Point mousePos = new Point(getWidth() >> 1, getHeight() >> 1);

    public DoubleBufferedCanvas(int width, int height) {
        addMouseWheelListener(Global.MOUSE);
        addMouseListener(Global.MOUSE);
        addKeyListener(Global.KEYBOARD);
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
        setVisible(true);
    }

    @Override
    public void update(Graphics g) {
        Graphics2D g2d = (Graphics2D) Global.IMAGE.getGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 130);

        updateChildren();
        drawChildren(g2d);

        g.drawImage(Global.IMAGE, 0, 0, this);
        Global.FRAMECOUNT++;
    }

    public void updateChildren() {
        UILayer[] layers = Global.WINDOW.getLayers();
        for (int i = layers.length - 1; i >= 0; i--) {
            if (layers[i].update(Element.Flags.NONE)) return;
        }
    }

    public void drawChildren(Graphics2D g2d) {
        UILayer[] layers = Global.WINDOW.getLayers();
        for (UILayer layer : layers) {
            layer.draw(g2d);
        }
    }

    public void updateScrollAt(int x, int y, int wheelRotation) {
        int flags = wheelRotation < 0 ? Element.Flags.MWHEELUP : Element.Flags.MWHEELDN;
        UILayer[] layers = Global.WINDOW.getLayers();
        for (int i = layers.length - 1; i >= 0; i--) {
            if (layers[i].update(flags)) return;
        }
    }

    public Point getMousePos() {
        Point newMousePos = getMousePosition();
        if (newMousePos != null) mousePos = newMousePos;
        return mousePos;
    }

    public int getMouseX() {
        return getMousePos().x;
    }

    public int getMouseY() {
        return getMousePos().y;
    }
}
