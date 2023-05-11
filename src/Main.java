public class Main {

    public static void main(String[] args) throws InterruptedException {
        Global.WINDOW = new Window(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, Global.COLORS[0]);

        initGame();

        //noinspection InfiniteLoopStatement
        while (true) {

            Global.MOUSE.update();
            Global.CANVAS.repaint();
            Thread.sleep(20);

            if (Global.LOG)
                System.out.println("Mouse - " + Global.MOUSE.getX() + ":" + Global.MOUSE.getY() + "\n" + "\n");
        }
    }

    private static void initTesting() {
        layer("UI");
        layer("a").addElement(new Text(130, 80, 50, 2, "Test", Global.COLORS[3]));
        Button a          = new Button(0, 0, Global.WINDOW_WIDTH, 120, "a", "b", 2);
        Button testButton = new Button(100, 100, 200, 100, "UI", "mainButtonOfExistence", 1, "Sample text");
    }

    private static void initGame() {
        int    GW          = Global.WINDOW_WIDTH;
        int    GH          = Global.WINDOW_HEIGHT;
        float  offset      = GH / 28.0f;
        float  button_size = GH / 8.0f;
        Sprite sideBG      = new Sprite(0, 0, (int) (button_size + offset * 2), GH, 0, Global.COLORS[2]);
        layer("UISIDE").addElement(sideBG);

        for (int i = 0; i < 6; i++) {
            new Button((int) offset, (int) (offset + (button_size + offset) * i), (int) button_size, (int) button_size,
                       "UISIDE", "" + i, i + 1, "BUTTON" + i); // TODO: add a proper way to create buttons
        }

        Sprite profileBG = new Sprite((int) (button_size + offset * 2), 0, GW, (int) (button_size + offset * 2), 0, Global.COLORS[1]);
        layer("PROFILE").addElement(profileBG);

        InteractivePicture profilePic = new InteractivePicture((int) (GW - button_size - offset), (int) offset,
                                                     (int) (button_size),
                                          (int) (button_size), 1, "ipolitta.jpg");
        layer("PROFILE").addElement(profilePic);
    }

    private static UILayer layer(String name) { // TODO: where this thing should be?
        UILayer layer = Global.WINDOW.getLayer(name);
        if (layer != null)
            return layer;
        Global.WINDOW.addLayerToTop(name);
        return Global.WINDOW.getLayer(name);
    }
}
