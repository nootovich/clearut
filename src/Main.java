public class Main {

    public static void main(String[] args) throws InterruptedException {
        Global.WINDOW = new Window(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
		layer("BG").addElement(new Sprite(0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, 0, Global.COLORS[0], "bg"));
		
        initSpawnMenuTesting();

        //noinspection InfiniteLoopStatement
        while (true) {

            Global.MOUSE.update();
            Global.CANVAS.repaint();
            Thread.sleep(500);

			if (Global.LOG > 1) {
                System.out.printf("\t\tmouse - x:%d y:%d%n", Global.MOUSE.getX(), Global.MOUSE.getY());
            } // $DEBUG
        }
    }

//     private static void initSaveTesting() {
//         initGame();
//         IO.saveInfo();
//         System.exit(0);
//     }

    private static void initSpawnMenuTesting() {
        Menu testMenu = new Menu(50, 50, 200, 120, 2, "SPAWN_MENU");
		layer("UI").addElement(testMenu); // TODO: continue from here (this function seemingly does nothing)
		
        String[][] buttonDefinitions = {{"DumpInfoToConsole","Dump info to Console"}, {"2","2"},{"3","3"}};
        for (int i = 0; i < buttonDefinitions.length; i++) {
			Sprite button = new Sprite(50, 50 + i * 30, 200, 25, 2, Global.COLORS[3], "sprite" + i);
			Text   text	  = new Text(150, 65 + i * 30, 12, 4, buttonDefinitions[i][1], Global.COLOR_VANILLA);
			
			button.setAction(buttonDefinitions[i][0]);
			button.addChild(text);

			testMenu.addChild(button);
		} 
    }

    private static void initTesting() {
        Sprite sample_sprite  = new Sprite(50, 50, 200, 100, 1, Global.COLORS[4], "sprite1");
        Sprite sample_sprite2 = new Sprite(450, 50, 100, 200, 2, Global.COLORS[5], "button_sprite", "button1");
		Text   sample_text	  = new Text(500, 150, 11, 4, "Sample text", Global.COLOR_RED_MUNSELL);

		sample_sprite.setHoveredColor(Global.COLOR_VANILLA);
		sample_sprite.setActiveColor(Global.COLOR_MELON);
		
		sample_sprite2.addChild(sample_text);
        sample_sprite2.setHoveredColor(Global.COLORS[6]);
        sample_sprite2.setActiveColor(Global.COLORS[9]);
		
        layer("UI").addElement(sample_sprite);
        layer("UI").addElement(sample_sprite2);
    }

//   private static void initGame() {
//       int   GW          = Global.WINDOW_WIDTH;
//       int   GH          = Global.WINDOW_HEIGHT;
//       float offset      = GH / 28.0f;
//       float button_size = GH / 8.0f;
//       Sprite sideBG = new Sprite(0,
//                                  0,
//                                  (int) (button_size + offset * 2),
//                                  GH,
//                                  0,
//                                  Global.COLORS[2]);
//       layer("UISIDE").addElement(sideBG);

//       for (int i = 0; i < 6; i++) {
//           new Button((int) offset,
//                      (int) (offset + (button_size + offset) * i),
//                      (int) button_size,
//                      (int) button_size,
//                      "UISIDE",
//                      "button" + i,
//                      i + 1,
//                      "BUTTON" + i); // TODO: add a proper way to create buttons
//       }

//       Sprite profileBG = new Sprite((int) (button_size + offset * 2),
//                                     0,
//                                     GW,
//                                     (int) (button_size + offset * 2),
//                                     0,
//                                     Global.COLORS[1]);
//       layer("PROFILE").addElement(profileBG);

//       InteractivePicture profilePic = new InteractivePicture((int) (GW - button_size - offset),
//                                                              (int) offset,
//                                                              (int) (button_size),
//                                                              (int) (button_size),
//                                                              1,
//                                                              "ipolitta.jpg");
//       layer("PROFILE").addElement(profilePic);

//       Menu test_menu = new Menu(Global.WINDOW_WIDTH >> 1,
//                                 Global.WINDOW_HEIGHT >> 1,
//                                 Global.STROKE_WIDTH >> 2,
//                                 Global.WINDOW_HEIGHT >> 2,
//                                 69,
//                                 "test");
//       test_menu.addSprite(0, 0, 200, 200, 0, Global.COLORS[3]);
//       test_menu.addInteractiveSprite(20, 100, 50, 25, 1, Global.COLORS[6]);
//       test_menu.addButton(130, 100, 50, 25, 2, "s", "S");
//   }

    private static UILayer layer(String name) {
        // TODO: where this thing should be?
        // MEGA TODO: prob rework or remove this. Good idea tho
		// GIGA TODO: this thing is amazing. DO NOT TOUCHA MA SPAGET
        UILayer layer = Global.WINDOW.getLayer(name);
        if (layer != null)
            return layer;
        Global.WINDOW.addLayerToTop(name);
        return Global.WINDOW.getLayer(name);
    }
}
