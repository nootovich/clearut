import java.awt.*;
import java.io.File;

public class Global {
    public static final int WINDOW_WIDTH  = 600;
    public static final int WINDOW_HEIGHT = 300;
    public static final int STROKE_WIDTH  = 2;
    public static final int INFO_VERSION  = 0; //TODO: rename 'info' to 'data'
    // e3f2fd,bbdefb,90caf9,64b5f6,42a5f5,2196f3,1e88e5,1976d2,1565c0,0d47a1
    // 012a4a,013a63,01497c,014f86,2a6f97,2c7da0,468faf,61a5c2,89c2d9,a9d6e5

    @SuppressWarnings("ConstantConditionalExpression")
    public static final Color[] COLORS =
            false
            ? new Color[]{new Color(0x012a4a), new Color(0x013a63), new Color(0x01497c), new Color(0x014f86),
                          new Color(0x2a6f97), new Color(0x2c7da0), new Color(0x468faf), new Color(0x61a5c2),
                          new Color(0x89c2d9), new Color(0xa9d6e5)}
            : new Color[]{new Color(0x0d47a1), new Color(0x1565c0), new Color(0x1976d2), new Color(0x1e88e5),
                          new Color(0x2196f3), new Color(0x42a5f5), new Color(0x64b5f6), new Color(0x90caf9),
                          new Color(0xbbdefb), new Color(0xe3f2fd)};

    /*public static final Color                COLOR_SPACE_CADET = new Color(0x2B2D42);
    public static final Color                COLOR_DARK_GRAY   = new Color(0x424269);
    public static final Color                COLOR_COOL_GRAY   = new Color(0x8D99AE);
    public static final Color                COLOR_VANILLA     = new Color(0xF6EFA6);
    public static final Color                COLOR_MELON       = new Color(0xFFA69E);
    public static final Color                COLOR_RED_MUNSELL = new Color(0xF21B3F);
    */
    public static int                  FRAMECOUNT = 0;
    public static Window               WINDOW;
    public static DoubleBufferedCanvas CANVAS;
    public static Image                IMAGE;
    public static IO.Mouse             MOUSE      = new IO.Mouse();

    public static File IMAGE_FOLDER = new File("C:\\Users\\noot\\IdeaProjects\\clearut\\images");
    public static int  LOG          = 0;
    public static Menu spawnMenu; // TODO: temporary solution, remove!
}
