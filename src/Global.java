import java.awt.*;

public class Global {
    public static final int
            WINDOW_WIDTH  = 800,
            WINDOW_HEIGHT = 400,
            STROKE_WIDTH  = 2,
            INFO_VERSION  = 0;
    public static final Color
            COLOR_SPACE_CADET = new Color(0x2B2D42),
            COLOR_COOL_GRAY   = new Color(0x8D99AE),
            COLOR_VANILLA     = new Color(0xF6EFA6),
            COLOR_MELON       = new Color(0xFFA69E),
            COLOR_RED_MUNSELL = new Color(0xF21B3F);
    public static Window               WINDOW;
    public static DoubleBufferedCanvas CANVAS;
    public static Image                IMAGE;
    public static Point                mousePos = new Point(0, 0); // TODO: remove
    public static IO.Mouse             MOUSE    = new IO.Mouse();
}
