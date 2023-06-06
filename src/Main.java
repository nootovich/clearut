import java.awt.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Global.WINDOW = new Window(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        Sprite bg = new Sprite(0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, 0, Global.COLORS_BLUE_BRIGHT[0], "bg");
        bg.setInteractive(false);
        layer("BG").addChild(bg);

        initGame();

        layer("UIMAIN");

        while (true) {

            Global.MOUSE.update();
            Global.CANVAS.repaint();
            Thread.sleep(50);

            if (Global.LOG > 1) {
                System.out.printf("\t\tmouse - x:%d y:%d%n", Global.MOUSE.getX(), Global.MOUSE.getY());
            } // $DEBUG
        }
    }

    private static void initSpawnMenuTesting() {
        Menu testMenu = new Menu(50, 50, 200, 70, 2, "SPAWN_MENU");
        layer("UI").addChild(testMenu);

        int        buttonW           = 250;
        int        buttonH           = 75;
        String[][] buttonDefinitions = {{"dumpInfoToConsole", "Dump info to Console"}, {"2", "2"}, {"3", "3"}};

        for (int i = 0; i < buttonDefinitions.length; i++) {
            Sprite button = new Sprite(100, 50 + i * (buttonH + 5), buttonW, buttonH, 2);
            button.setColors(Global.COLORS_BLUE_BRIGHT[4], Global.COLORS_BLUE_BRIGHT[5], Global.COLORS_BLUE_BRIGHT[6]);
            button.setType(Sprite.SpriteType.ROUNDED);
            button.setAction(buttonDefinitions[i][0]);
            button.setName("sprite" + i);

            Outline outline = new Outline(button, 2);
            outline.setColors(Global.COLORS_BLUE_BRIGHT[2], Global.COLORS_BLUE_BRIGHT[1], Global.COLORS_BLUE_BRIGHT[4]);
            outline.setParent(button);

            Text text = new Text(100 + (buttonW >> 1), 50 + (buttonH >> 1) + i * (buttonH + 5), 12, 4);
            text.setText(buttonDefinitions[i][1]);
            text.setColor(Global.COLOR_VANILLA);
            text.setParent(button);

            testMenu.addChild(button);
//            layer("UI").addElement(button);
        }
    }

    private static void initGame() {
        int     GW          = Global.WINDOW_WIDTH;
        int     GH          = Global.WINDOW_HEIGHT;
        float   offset      = GH / 28.0f;
        float   button_size = GH / 8.0f;
        int     offs        = (int) offset;
        int     bs          = (int) button_size;
        Color[] blue        = Global.COLORS_BLUE_BRIGHT;
        Color[] yellow      = Global.COLORS_YELLOW_BRIGHT;

        Sprite sideBG = new Sprite(0, 0, bs + offs * 2, GH, 0, blue[2]);
        sideBG.setName("sideBG");
        sideBG.setInteractive(false);
        layer("UISIDE").addChild(sideBG);

        Sprite  notesButton        = new Sprite(offs, offs, bs, bs, 1);
        Outline notesButtonOutline = new Outline(notesButton, 4, yellow[8]);
        int     nbcx               = notesButton.getCenterX();
        int     nbcy               = notesButton.getCenterY();
        Text    notesButtonText    = new Text(nbcx, nbcy, 10, 3, "NOTES", Color.WHITE);
        notesButton.addChild(notesButtonText);
        notesButton.setColors(yellow[3], yellow[5], yellow[1]);
        notesButton.setType(Sprite.SpriteType.ROUNDED);
        notesButton.setAdditional(12);
        notesButton.setAction("openNotes");
        notesButton.setName("notesButton");
        layer("UISIDE").addChild(notesButton);

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
            layer("UISIDE").addChild(button);
        }

        int    pxh       = (int) (button_size + offset * 2);
        Sprite profileBG = new Sprite(pxh, 0, GW, pxh, 0, blue[1]);
        profileBG.setName("profileBG");
        profileBG.setInteractive(false);
        layer("UIPROFILE").addChild(profileBG);

        int     px         = (int) (GW - button_size - offset);
        int     py         = (int) offset;
        int     ps         = (int) (button_size);
        Picture profilePic = new Picture(px, py, ps, ps, 1, Global.IMAGE_FOLDER + "lizard.jpg");
        profilePic.setName("profilePic");
        layer("UIPROFILE").addChild(profilePic);

    }

    private static UILayer layer(String name) {
        // TODO: where this thing should be?
        // MEGA TODO: prob rework or remove this. Good idea tho
        // GIGA TODO: this thing is amazing. DO NOT TOUCHA MA SPAGET
        UILayer layer = Global.WINDOW.getLayer(name);
        if (layer != null) return layer;
        Global.WINDOW.addLayerToTop(name);
        return Global.WINDOW.getLayer(name);
    }

}
