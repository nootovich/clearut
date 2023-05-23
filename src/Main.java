public class Main {

    public static void main(String[] args) throws InterruptedException {
        Global.WINDOW = new Window(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, Global.COLORS[0]);

        initTesting();

        //noinspection InfiniteLoopStatement
        while (true) {

            Global.MOUSE.update();
            Global.CANVAS.repaint();
            Thread.sleep(2000);

            if (Global.LOG)
                System.out.println("Mouse - " + Global.MOUSE.getX() + ":" + Global.MOUSE.getY() + "\n" + "\n");
        }
    }

//     private static void initSaveTesting() {
//         initGame();
//         IO.saveInfo();
//         System.exit(0);
//     }

//     private static void initSpawnMenuTesting() {
//         Global.spawnMenu = new Menu(0, 0, 0, 0, 1000, "SPAWN_MENU");
//         String[][] buttonDefinitions = {{"DumpInfoToConsole","Dump info to Console"}, {"2","2"},{"3","3"}};
//         // TODO: this function is unfinished
//         //for (int i = 0; i < ) // this is for adding buttons in a loop
// //        Global.spawnMenu.addButton(0, 0, 200, 40, 1001, );
// //        Global.spawnMenu.addButton(0, 20, 100, 20, 1001, "2", "2");
// //        Global.spawnMenu.addButton(0, 40, 100, 20, 1001, "3", "3");
//     }

    private static void initTesting() {
        layer("UI").addElement(new Sprite(0, 0, 200, 100, 1, Global.COLORS[4]));
		// layer("a").addElement(new Text(130, 80, 50, 2, "Test", Global.COLORS[3]));
        // Button a          = new Button(0, 0, Global.WINDOW_WIDTH, 120, "a", "b", 2);
        // Button testButton = new Button(100, 100, 200, 100, "UI", "mainButtonOfExistence", 1, "Sample text");
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
        UILayer layer = Global.WINDOW.getLayer(name);
        if (layer != null)
            return layer;
        Global.WINDOW.addLayerToTop(name);
        return Global.WINDOW.getLayer(name);
    }
}
