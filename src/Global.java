import java.awt.*;
import java.io.File;

public class Global {
    public static final int LOG = 0;

    public static final int         WINDOW_WIDTH     = 800;
    public static final int         WINDOW_HEIGHT    = 400;
    public static final int         SAVEDATA_VERSION = 1; //TODO: rename 'info' to 'data'
    public static final Actions     ACTIONS          = new Actions();
    public static final IO.Mouse    MOUSE            = new IO.Mouse();
    public static final IO.Keyboard KEYBOARD         = new IO.Keyboard();
    public static final String      IMAGE_FOLDER     = "images" + File.separator;
    public static final String      SAVEDATA_FOLDER  = "saves" + File.separator;

    @SuppressWarnings("ConstantConditionalExpression")
    public static final Color[] COLORS            =
            false
            ? new Color[]{new Color(0x012a4a), new Color(0x013a63), new Color(0x01497c), new Color(0x014f86),
                          new Color(0x2a6f97), new Color(0x2c7da0), new Color(0x468faf), new Color(0x61a5c2),
                          new Color(0x89c2d9), new Color(0xa9d6e5)}
            : new Color[]{new Color(0x0d47a1), new Color(0x1565c0), new Color(0x1976d2), new Color(0x1e88e5),
                          new Color(0x2196f3), new Color(0x42a5f5), new Color(0x64b5f6), new Color(0x90caf9),
                          new Color(0xbbdefb), new Color(0xe3f2fd)};
    public static final Color   COLOR_SPACE_CADET = new Color(0x2B2D42);
    public static final Color   COLOR_DARK_GRAY   = new Color(0x424269);
    public static final Color   COLOR_COOL_GRAY   = new Color(0x8D99AE);
    public static final Color   COLOR_VANILLA     = new Color(0xF6EFA6);
    public static final Color   COLOR_MELON       = new Color(0xFFA69E);
    public static final Color   COLOR_RED_MUNSELL = new Color(0xF21B3F);

    public static Window               WINDOW;
    public static DoubleBufferedCanvas CANVAS;
    public static Image                IMAGE;

    public static int FRAMECOUNT = 0;

}
