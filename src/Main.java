public class Main {

    private static final boolean DEBUG         = false;
    private static final int     WINDOW_WIDTH  = 800;
    private static final int     WINDOW_HEIGHT = 400;
    private static final int     FPS           = 60;

    private static final int plannedFrameTime = (int) (1000.f / FPS);

    public static Window window;
    public static Menu   spawnMenu;
    public static Group  editingMenu;

    public static void main(String[] args) throws InterruptedException {
        window = createWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setBG(Colors.blue2(0));

        initGame();
        spawnMenu   = initSpawnMenu();
        editingMenu = initEditingMenu();

        long prevFrameTime  = 0;
        long curFrameTime   = 0;
        long spentFrameTime = 0;
        while (true) {

            curFrameTime   = System.currentTimeMillis();
            spentFrameTime = curFrameTime - prevFrameTime;
            prevFrameTime  = curFrameTime;
            if (spentFrameTime > 250) spentFrameTime = 250;

            if (DEBUG) {
                System.out.printf("frametime: %dms%n", spentFrameTime);
            } // $DEBUG

            Global.MOUSE.update();
            Global.CANVAS.repaint();

            if (Global.MOUSE.isRMBFallingEdge() && spawnMenu.isMinimized()) {
                spawnMenu.open(Global.MOUSE.getX(), Global.MOUSE.getY());
            } else if (Global.MOUSE.isRMBRisingEdge() && !spawnMenu.isMinimized()) {
                spawnMenu.close();
            }

            int sleepTime = (int) (plannedFrameTime * 2 - spentFrameTime);
            if (sleepTime < 0) sleepTime = 0;
            if (sleepTime > plannedFrameTime) sleepTime = plannedFrameTime;

            Thread.sleep(sleepTime);

            if (DEBUG) {
                System.out.printf("\t\tmouse - x:%d y:%d%n", Global.MOUSE.getX(), Global.MOUSE.getY());
            } // $DEBUG
        }
    }

    private static Menu initSpawnMenu() {
        int[] y        = Colors.yellow2();
        Menu  testMenu = new Menu(150, 200, 2, "SPAWN_MENU", y[3], y[5], y[8]);

        String[][] menuLayout = new String[][]{
                {"Dump info to console", "dumpInfoToConsole"},
                {"Edit element", "editElement"}};

        for (int i = 0; i < menuLayout.length; i++) {
            testMenu.addButton(menuLayout[i][0], "button" + menuLayout[i][1], menuLayout[i][1]);
        }

        testMenu.close();
        window.layer("UI").addChild(testMenu);
        return testMenu;
    }

    private static Group initEditingMenu() {
        int[] yellow = Colors.yellow2();

        Group editingMenu = new Group("EDITING_MENU");
        editingMenu.setVisibility(false).setInteractive(false);

        Sprite bg = editingMenu.addSprite(0, 0, 200, 200, 3, yellow[4], "EDITING_MENU:BG");
        bg.addOutline(2, yellow[9]);

        int bgw = bg.getWidth();
        int xo  = bgw / 10;
        int yo  = bgw / 10;

        Text title = editingMenu.addText(0, yo, bgw, yo, 12, bg.getZ() + 1, "Editing: null", 0);
        title.setOffsetX(xo).setAlignment(Text.Alignment.LEFT).setName("EDITING_MENU:TITLE");
        Text type = editingMenu.addText(0, yo * 2, bgw, yo, 12, bg.getZ() + 1, "Type: null", 0);
        type.setOffsetX(xo).setAlignment(Text.Alignment.LEFT).setName("EDITING_MENU:TYPE");

        editingMenu.addSlider(xo, yo * 3, bgw - xo * 2, yo, "EDITING_MENU:COL_R", 0x00, 0xff, 0x00, 0xff0000);
        editingMenu.addSlider(xo, yo * 4, bgw - xo * 2, yo, "EDITING_MENU:COL_G", 0x00, 0xff, 0x7f, 0x00ff00);
        editingMenu.addSlider(xo, yo * 5, bgw - xo * 2, yo, "EDITING_MENU:COL_B", 0x00, 0xff, 0xff, 0x0000ff);

        window.layer("UI").addChild(editingMenu);
        return editingMenu;
    }

    private static void initGame() {
        float offset      = WINDOW_HEIGHT / 28.0f;
        float button_size = WINDOW_HEIGHT / 8.0f;
        int   offs        = (int) offset;
        int   bs          = (int) button_size;
        int[] blue        = Colors.blue2();
        int[] yellow      = Colors.yellow2();

        window.layer("UISIDE").addSprite(0, 0, bs + offs * 2, WINDOW_HEIGHT, 0, blue[2], "side_bg").setInteractive(false);

        Sprite notesButton = window.layer("UISIDE").addSprite(offs, offs, bs, bs, 1, "notesButton", "openNotes");
        notesButton.setColors(yellow[3], yellow[5], yellow[1]).setType(Sprite.SpriteType.ROUNDED).setAdditional(12);
        notesButton.addOutline(4, yellow[8]);
        notesButton.addText(notesButton.getCenterX(), notesButton.getCenterY(), bs, bs, 12, 5, "NOTES", 0xffffff);

        for (int i = 1; i < 6; i++) {
            int    bx     = (int) offset;
            int    by     = (int) (offset + (button_size + offset) * i);
            Sprite button = new Sprite(bx, by, bs, bs, 1);
            button.setColors(blue[4], blue[5], blue[6]);
            button.setType(Sprite.SpriteType.ROUNDED);
            button.setAdditional(10);
            button.setAction("button" + i);
            button.setName("button" + i);
            Outline button_outline = new Outline(button, 2, blue[7]);
            window.layer("UISIDE").addChild(button);
        }

        int    pxh       = (int) (button_size + offset * 2);
        Sprite profileBG = new Sprite(pxh, 0, WINDOW_WIDTH, pxh, 0, blue[1]);
        profileBG.setName("profile_bg");
        profileBG.setInteractive(false);
        window.layer("UIPROFILE").addChild(profileBG);

        int     px         = (int) (WINDOW_WIDTH - button_size - offset);
        int     py         = (int) offset;
        int     ps         = (int) (button_size);
        Picture profilePic = new Picture(px, py, ps, ps, 1, Global.IMAGE_FOLDER + "lizard.jpg");
        profilePic.setName("profilePic");
        window.layer("UIPROFILE").addChild(profilePic);

    }

    private static Window createWindow(int w, int h) {
        return new Window(w, h);
    }

}
