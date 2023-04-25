public class Main {

    public static void main(String[] args) throws InterruptedException {
        Global.WINDOW = new Window(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, Global.COLOR_SPACE_CADET);

        // TODO!!!!: make layers store not only elements but also buttons
        // TODO!!!!: and buttons themselves should store their elements
        layer("UI");
        layer("a").addElement(new Text(130, 80, 50, 2, "Test", Global.COLOR_VANILLA));

        Button a          = new Button(0, 0, Global.WINDOW_WIDTH, 120, "a", "b", 2);
        Button testButton = new Button(100, 100, 200, 100, "UI", "mainButtonOfExistence", 1, "Sample text");

        //noinspection InfiniteLoopStatement
        while (true) {

            Global.MOUSE.update();
            Global.CANVAS.repaint();
            Thread.sleep(20);

            if (Global.LOG)
                System.out.println("Mouse - " + Global.MOUSE.getX() + ":" + Global.MOUSE.getY() + "\n" + "\n");
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
