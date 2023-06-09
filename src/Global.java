import java.awt.*;
import java.io.File;

public class Global {

    public static final int LOG = 0;

    public static final int         SAVEDATA_VERSION = 1; //TODO: rename 'info' to 'data'
    public static final Actions     ACTIONS          = new Actions();
    public static final IO.Mouse    MOUSE            = new IO.Mouse();
    public static final IO.Keyboard KEYBOARD         = new IO.Keyboard();
    public static final String      IMAGE_FOLDER     = "images" + File.separator;
    public static final String      SAVEDATA_FOLDER  = "saves" + File.separator;
    public static final String      NOTES_FOLDER     = "notes" + File.separator;

    public static final Color SPACE_CADET = new Color(0x2B2D42);
    public static final Color DARK_GRAY   = new Color(0x424269);
    public static final Color COOL_GRAY   = new Color(0x8D99AE);
    public static final Color VANILLA     = new Color(0xF6EFA6);
    public static final Color MELON       = new Color(0xFFA69E);
    public static final Color RED_MUNSELL = new Color(0xF21B3F);

    public static DoubleBufferedCanvas CANVAS;
    public static Image                IMAGE;

    public static int    FRAMECOUNT = 0;
    public static String MODE       = "TEST";

    public static void asrt(boolean contition, String message) {
        if (contition) return;
        System.out.printf("Assertion error: %s%n", message);
        System.exit(1);
    }

    public static Element findElement(String name) {
        name = name.toUpperCase();

        UILayer[] layers = Main.window.getLayers();
        for (UILayer layer : layers) {
            Element foundElement = layer.getChild(name);
            if (foundElement != null) return foundElement;
        }

        return null;
    }

    public static UILayer layer(String name) {
        Window window = Main.window;
        UILayer layer = window.getLayer(name);
        if (layer != null) return layer;
        window.addLayerToTop(name);
        return window.getLayer(name);
    }
}
