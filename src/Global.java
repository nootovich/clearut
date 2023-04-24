import java.awt.*;

public class Global {
    public static final int                  WINDOW_WIDTH      = 800;
    public static final int                  WINDOW_HEIGHT     = 400;
    public static final int                  STROKE_WIDTH      = 2;
    public static final int                  INFO_VERSION      = 0;
    public static final Color                COLOR_SPACE_CADET = new Color(0x2B2D42);
    public static final Color                COLOR_COOL_GRAY   = new Color(0x8D99AE);
    public static final Color                COLOR_VANILLA     = new Color(0xF6EFA6);
    public static final Color                COLOR_MELON       = new Color(0xFFA69E);
    public static final Color                COLOR_RED_MUNSELL = new Color(0xF21B3F);
    public static       int                  FRAMECOUNT        = 0;
    public static       Window               WINDOW;
    public static       DoubleBufferedCanvas CANVAS;
    public static       Image                IMAGE;
    public static       IO.Mouse             MOUSE             = new IO.Mouse();

    public static boolean LOG = false;
}
