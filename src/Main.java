public class Main {

    public static void main(String[] args) throws InterruptedException {
	    Global.WINDOW = new Window(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        layer("BG").addChild(new Sprite(0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, 0, Global.COLORS[0], "bg"));
		layer("UIMAIN");
		
        initGame();

        //noinspection InfiniteLoopStatement
        while (true) {

            Global.MOUSE.update();
            Global.CANVAS.repaint();
            Thread.sleep(50);

            if (Global.LOG > 1) {
                System.out.printf("\t\tmouse - x:%d y:%d%n", Global.MOUSE.getX(), Global.MOUSE.getY());
            } // $DEBUG
        }
    }

    private static void initSaveTesting() {
        initGame();
        IO.saveInfo();
        System.exit(0);
    }

    private static void initSpawnMenuTesting() {
        Menu testMenu = new Menu(50, 50, 200, 70, 2, "SPAWN_MENU");
        layer("UI").addChild(testMenu);

        int        buttonW           = 250;
        int        buttonH           = 75;
        String[][] buttonDefinitions = {{"dumpInfoToConsole", "Dump info to Console"}, {"2", "2"}, {"3", "3"}};

        for (int i = 0; i < buttonDefinitions.length; i++) {
            Sprite button = new Sprite(100, 50 + i * (buttonH + 5), buttonW, buttonH, 2);
            button.setColors(Global.COLORS[4], Global.COLORS[5], Global.COLORS[6]);
            button.setType(Sprite.SpriteType.ROUNDED);
            button.setAction(buttonDefinitions[i][0]);
            button.setName("sprite" + i);

            Outline outline = new Outline(button, 2);
            outline.setColors(Global.COLORS[2], Global.COLORS[1], Global.COLORS[4]);
            outline.setParent(button);

            Text text = new Text(100 + (buttonW >> 1), 50 + (buttonH >> 1) + i * (buttonH + 5), 12, 4);
            text.setText(buttonDefinitions[i][1]);
            text.setColor(Global.COLOR_VANILLA);
            text.setParent(button);

            testMenu.addChild(button);
//            layer("UI").addElement(button);
        }
    }

    private static void initTesting() {
        Sprite sample_sprite  = new Sprite(50, 50, 200, 100, 1, Global.COLORS[4], "sprite1");
        Sprite sample_sprite2 = new Sprite(450, 50, 100, 200, 2, Global.COLORS[5], "button_sprite", "button1");
        Text   sample_text    = new Text(500, 150, 11, 4, "Sample text", Global.COLOR_RED_MUNSELL);

        sample_sprite.setHoveredColor(Global.COLOR_VANILLA);
        sample_sprite.setActiveColor(Global.COLOR_MELON);

        sample_sprite2.addChild(sample_text);
        sample_sprite2.setHoveredColor(Global.COLORS[6]);
        sample_sprite2.setActiveColor(Global.COLORS[9]);

        layer("UI").addChild(sample_sprite);
        layer("UI").addChild(sample_sprite2);
    }

    private static void initGame() {
        int   GW          = Global.WINDOW_WIDTH;
        int   GH          = Global.WINDOW_HEIGHT;
        float offset      = GH / 28.0f;
        float button_size = GH / 8.0f;

        Sprite sideBG = new Sprite(0, 0, (int) (button_size + offset * 2), GH, 0, Global.COLORS[2]);
        sideBG.setName("sideBG");
		layer("UISIDE").addChild(sideBG);

		Sprite notesButton = new Sprite((int) offset, (int) offset, (int) button_size, (int) button_size, 1);
		Outline notesButtonOutline = new Outline(notesButton, 4, Global.getColor(0xAAAA69));
		Text notesButtonText = new Text((int) (offset + (button_size / 2.0f)), (int) (offset + (button_size / 2.0f)), 10, 3, "NOTES", Global.getColor(0xFFFFFF));
		notesButton.addChild(notesButtonText);
		notesButton.setColors(Global.getColor(0xBFCF42), Global.getColor(0xC7D742), Global.getColor(0xCFDF69));
		notesButton.setType(Sprite.SpriteType.ROUNDED);
		notesButton.setAdditional(12);
		notesButton.setAction("openNotes");
		notesButton.setName("notesButton");
		layer("UISIDE").addChild(notesButton);
		
        for (int i = 1; i < 6; i++) {
            int    bx     = (int) offset;
            int    by     = (int) (offset + (button_size + offset) * i);
            int    bs     = (int) button_size;
            Sprite button = new Sprite(bx, by, bs, bs, 1);
            button.setColors(Global.COLORS[4], Global.COLORS[5], Global.COLORS[6]);
			button.setType(Sprite.SpriteType.ROUNDED);
			button.setAdditional(10);
            button.setAction("button" + i);
            button.setName("button" + i);
			Outline button_outline = new Outline(button, 2, Global.COLORS[7]);			
            layer("UISIDE").addChild(button);
        }

        int    pxh       = (int) (button_size + offset * 2);
        Sprite profileBG = new Sprite(pxh, 0, GW, pxh, 0, Global.COLORS[1]);
        profileBG.setName("profileBG");
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
