import java.awt.*;

public class Main {

    public static void main(String[] args) {


        Global.WINDOW = new Window(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, Global.COLOR_SPACE_CADET);

        // TODO!!!!: make layers store not only elements but also buttons
        // TODO!!!!: and buttons themselves should store their elements
        layer("UI");
        layer("a").addElement(new Text(130, 80, 50, 2, "Test", Global.COLOR_VANILLA));
        Button a          = new Button(120, 100, 50, 50, "a");
        Button testButton = new Button(100, 100, 200, 100, "UI", "Sample text");

        IO.saveInfo(); // TODO: remove after testing

        //noinspection InfiniteLoopStatement
        while (true) {

            a.update();
            testButton.update(); // TODO: make a general update system
            Global.CANVAS.repaint();

            try {
                Thread.sleep(20);

                { // TODO: remove
                    Point mousePos = Global.CANVAS.getMousePosition();
                    if (mousePos != null) Global.mousePos = mousePos;
                }

            } catch (NullPointerException | InterruptedException ignored) {
                System.out.println("aboba");
            }
        }
    }

    private static UILayer layer(String name) {
        UILayer layer = Global.WINDOW.getLayer(name);
        if (layer != null)
            return layer;
        Global.WINDOW.addLayerToTop(name);
        return Global.WINDOW.getLayer(name);
    }
}
