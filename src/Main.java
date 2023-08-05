import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final boolean DEBUG = false;

    private static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 400, FPS = 60;
    private static final int plannedFrameTime = (int) (1000.f/FPS);

    public static IO.Mouse mouse;
    public static Window   window;
    public static State    state;

    public static void main(String[] args) throws InterruptedException {
        mouse = new IO.Mouse();
        window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, mouse);
        changeState(State.MAIN);
        // spawnMenu   = initSpawnMenu();
        // editingMenu = initEditingMenu();
        long prevFrameTime = 0, curFrameTime = 0, spentFrameTime = 0;
        while (true) {
            curFrameTime = System.currentTimeMillis();
            spentFrameTime = curFrameTime-prevFrameTime;
            prevFrameTime = curFrameTime;
            if (spentFrameTime > 250) spentFrameTime = 250;
            if (DEBUG) System.out.printf("frametime: %dms%n", spentFrameTime); // $DEBUG
            mouse.update(window);
            window.repaint();
            // if (Global.MOUSE.isRMBFallingEdge() && spawnMenu.isMinimized()) {
            //     spawnMenu.open(Global.MOUSE.getX(), Global.MOUSE.getY());
            // } else if (Global.MOUSE.isRMBRisingEdge() && !spawnMenu.isMinimized()) {
            //     spawnMenu.close();
            // }
            int sleepTime = (int) (plannedFrameTime*2-spentFrameTime);
            if (sleepTime < 0) sleepTime = 0;
            if (sleepTime > plannedFrameTime) sleepTime = plannedFrameTime;
            Thread.sleep(sleepTime);
        }
    }

    public static void changeState(State newState) {
        state = newState;
        window.DBC.layers = new Layer[0];
        System.gc();
        if (state == State.MAIN) {
            System.out.println("main");
            initMainState();
        } else if (state == State.CALENDAR) {
            System.out.println("change to cal");
            initCalendarState();
        }
    }

    private static void initMainState() {
        float offset      = WINDOW_HEIGHT/28.0f;
        float button_size = WINDOW_HEIGHT/8.0f;
        int   offs        = (int) offset;
        int   bs          = (int) button_size;
        int[] blue        = Colors.blue2();
        int[] yellow      = Colors.yellow2();

        window.addLayer("BG", 0);

        Sprite bg = new Sprite(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 0, blue[3], "bg");
        window.getLayer("BG").addChild(bg);

        window.addLayer("UI_SIDE", 1);

        Sprite sideBG = new Sprite(0, 0, bs+offs*2, WINDOW_HEIGHT, 0, blue[2], "SIDE_BG");
        window.getLayer("UI_SIDE").addChild(sideBG);

        Sprite notesButton = new Sprite(offs, offs, bs, bs, 1, "NOTES_BUTTON", "openNotes");
        notesButton.type = Sprite.SpriteType.ROUNDED_RECTANGLE;
        notesButton.extra = 8;
        notesButton.setColors(yellow[3], yellow[5], yellow[1]);
        window.getLayer("UI_SIDE").addChild(notesButton);

        Text notesButtonText = new Text(notesButton.getCenterX(), notesButton.getCenterY(), bs, bs, 12, 5, "NOTES", 0xFFFFFF);
        notesButton.addChild(notesButtonText);

        Sprite calButton = new Sprite(offs, offs*2+bs, bs, bs, 1, "CALENDAR_BUTTON", "openCalendar");
        calButton.type = Sprite.SpriteType.ROUNDED_RECTANGLE;
        calButton.extra = 8;
        calButton.setColors(yellow[3], yellow[5], yellow[1]);
        window.getLayer("UI_SIDE").addChild(calButton);

        Text calButtonText = new Text(calButton.getCenterX(), calButton.getCenterY(), bs, bs, 7, 5, "CALENDAR", 0xFFFFFF);
        calButton.addChild(calButtonText);

        for (int i = 2; i < 6; i++) {
            int    bx     = (int) offset;
            int    by     = (int) (offset+(button_size+offset)*i);
            Sprite button = new Sprite(bx, by, bs, bs, 1);
            button.name = "BUTTON_"+i;
            button.action = "button"+i;
            button.type = Sprite.SpriteType.ROUNDED_RECTANGLE;
            button.extra = 8;
            button.setColors(blue[4], blue[5], blue[6]);

            Outline buttonOutline = new Outline(4, 2, blue[5], blue[6], blue[7]);
            button.addChild(buttonOutline);

            window.getLayer("UI_SIDE").addChild(button);
        }

        window.addLayer("UI_PROFILE", 2);

        int    pxh       = (int) (button_size+offset*2);
        Sprite profileBG = new Sprite(pxh, 0, WINDOW_WIDTH, pxh, 0, blue[1], "PROFILE_BG");
        window.getLayer("UI_PROFILE").addChild(profileBG);

        int     px         = (int) (WINDOW_WIDTH-button_size-offset);
        int     py         = (int) offset;
        int     ps         = (int) (button_size);
        Picture profilePic = new Picture(px, py, ps, ps, 1, Global.IMAGE_FOLDER+"lizard.jpg", "PROFILE_PIC");
        window.getLayer("UI_PROFILE").addChild(profilePic);
    }

    private static void initCalendarState() {
        float offset      = WINDOW_HEIGHT/28.0f;
        float button_size = WINDOW_HEIGHT/8.0f;
        int   offs        = (int) offset;
        int   bs          = (int) button_size;
        int[] green       = Colors.green();
        int[] blue        = Colors.blue2();

        window.addLayer("BG", 0);

        Sprite bg = new Sprite(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 0, green[1], "bg");
        window.getLayer("BG").addChild(bg);

        window.addLayer("UI_SIDE", 1);

        Sprite sideBG = new Sprite(0, 0, bs+offs*2, WINDOW_HEIGHT, 0, green[2], "SIDE_BG");
        window.getLayer("UI_SIDE").addChild(sideBG);

        Sprite notesButton = new Sprite(offs, offs, bs, bs, 1, "NOTES_BUTTON", "openNotes");
        notesButton.type = Sprite.SpriteType.ROUNDED_RECTANGLE;
        notesButton.extra = 8;
        notesButton.setColors(blue[4], blue[5], blue[6]);
        window.getLayer("UI_SIDE").addChild(notesButton);

        Text notesButtonText = new Text(notesButton.getCenterX(), notesButton.getCenterY(), bs, bs, 12, 5, "NOTES", 0xFFFFFF);
        notesButton.addChild(notesButtonText);

        Sprite calButton = new Sprite(offs, offs*2+bs, bs, bs, 1, "CALENDAR_BUTTON", "openCalendar");
        calButton.type = Sprite.SpriteType.ROUNDED_RECTANGLE;
        calButton.extra = 8;
        calButton.setColors(blue[4], blue[5], blue[6]);
        window.getLayer("UI_SIDE").addChild(calButton);

        Text calButtonText = new Text(calButton.getCenterX(), calButton.getCenterY(), bs, bs, 7, 5, "CALENDAR", 0xFFFFFF);
        calButton.addChild(calButtonText);

        for (int i = 2; i < 6; i++) {
            int    bx     = (int) offset;
            int    by     = (int) (offset+(button_size+offset)*i);
            Sprite button = new Sprite(bx, by, bs, bs, 1);
            button.name = "BUTTON_"+i;
            button.action = "button"+i;
            button.type = Sprite.SpriteType.ROUNDED_RECTANGLE;
            button.extra = 8;
            button.setColors(green[4], green[5], green[6]);
            window.getLayer("UI_SIDE").addChild(button);
        }

        window.addLayer("UI_PROFILE", 2);

        int    pxh       = (int) (button_size+offset*2);
        Sprite profileBG = new Sprite(pxh, 0, WINDOW_WIDTH, pxh, 0, green[3], "PROFILE_BG");
        window.getLayer("UI_PROFILE").addChild(profileBG);

        int     px         = (int) (WINDOW_WIDTH-button_size-offset);
        int     py         = (int) offset;
        int     ps         = (int) (button_size);
        Picture profilePic = new Picture(px, py, ps, ps, 1, Global.IMAGE_FOLDER+"lizard.jpg", "PROFILE_PIC");
        window.getLayer("UI_PROFILE").addChild(profilePic);

        window.addLayer("UI_CALENDAR", 3);
        String[] tasks = new String[]{"Подъём 08:00", "Зарядка", "Правильное питание",
            "Тренировка", "Иностранный язык", "Отбой 00:00"};
        int ox = sideBG.w;
        int oy = profileBG.h;
        int tw = (WINDOW_WIDTH-ox);
        int th = (WINDOW_HEIGHT-oy);
        int pw = tw/14;
        int ph = th/(tasks.length+2);
        for (int i = 0; i < tasks.length+1; i++) {
            Sprite line = new Sprite(ox, oy+ph*i+ph, tw, 1, 3, 0);
            window.getLayer("UI_CALENDAR").addChild(line);
        }
        for (int i = 0; i < 7; i++) {
            Sprite line = new Sprite(ox+tw/2+pw*i, oy, 1, th, 3, 0);
            window.getLayer("UI_CALENDAR").addChild(line);
        }

        LocalDate now = LocalDate.now().plusDays(-2);
        int    dayOfWeek = now.getDayOfWeek().getValue()-1;
        String dayVal    = now.format(DateTimeFormatter.ofPattern("dd/MM"));
        Sprite dayBG = new Sprite(ox+tw/2+pw*dayOfWeek, oy, pw, th, 2, green[2]);
        // TODO: implement "Child" (Member) sorting (comparison)
        window.getLayer("UI_CALENDAR").addChild(dayBG);
        Text   day       = new Text(ox+tw/2+pw*dayOfWeek+pw/2, oy+ph/2, pw, ph, 12, 3, dayVal, Colors.white);
        window.getLayer("UI_CALENDAR").addChild(day);
//        for (int i = 0; i < dayOfWeek; i++) {
//            dayVal = now.minusDays(dayOfWeek-i).format(DateTimeFormatter.ofPattern("dd/MM"));
//            day = new Text(ox+tw/2+pw*i+pw/2, oy+ph/2, pw, ph, 12, 3, dayVal, blue[9]);
//            window.getLayer("UI_CALENDAR").addChild(day);
//        }
        for (int i = 0; i < 7; i++) {
            if (i == dayOfWeek) continue;
            dayVal = now.plusDays(i-dayOfWeek).format(DateTimeFormatter.ofPattern("dd/MM"));
            day = new Text(ox+tw/2+pw*i+pw/2, oy+ph/2, pw, ph, 9, 3, dayVal, Colors.lgray);
            window.getLayer("UI_CALENDAR").addChild(day);
        }

        for (int i = 0; i < tasks.length; i++) {
            Text taskName = new Text(ox+tw/4, oy+ph*(i+1)+ph/2, tw/2, ph, 20, 3, tasks[i], Colors.white);
            window.getLayer("UI_CALENDAR").addChild(taskName);
            for (int j = 0; j < 7; j++) {
                Sprite box = new Sprite(ox+tw/2+pw*j+pw/4, oy+ph*(i+1)+ph/4, pw/2, ph/2, 4);
                box.setColors(blue[3], blue[4], blue[5]);
                window.getLayer("UI_CALENDAR").addChild(box);
            }
        }
        Text title = new Text(ox+tw/4, (int) (oy+ph*0.4f), tw/2, ph, 34, 3, "Задания", Colors.white);
        window.getLayer("UI_CALENDAR").addChild(title);
        Text total = new Text(ox+tw/4, oy+ph*(tasks.length+1)+ph/2, tw/2, ph, 30, 3, "Итого:", Colors.white);
        window.getLayer("UI_CALENDAR").addChild(total);
    }

    // private static Menu initSpawnMenu() {
    //     int[] y        = Colors.yellow2();
    //     Menu  testMenu = new Menu(150, 200, 2, "SPAWN_MENU", y[3], y[5], y[8]);

    //     String[][] menuLayout = new String[][]{
    //             {"Dump info to console", "dumpInfoToConsole"},
    //             {"Edit element", "editElement"}};

    //     for (int i = 0; i < menuLayout.length; i++) {
    //         testMenu.addButton(menuLayout[i][0], "button" + menuLayout[i][1], menuLayout[i][1]);
    //     }

    //     testMenu.close();
    //     window.layer("UI").addChild(testMenu);
    //     return testMenu;
    // }

    // private static Group initEditingMenu() {
    //     int[] yellow = Colors.yellow2();

    //     Group editingMenu = new Group("EDITING_MENU");
    //     editingMenu.setVisibility(false).setInteractive(false);

    //     Sprite bg = editingMenu.addSprite(0, 0, 200, 200, 3, yellow[4], "EDITING_MENU:BG");
    //     bg.addOutline(2, yellow[9]);

    //     int bgw = bg.getWidth();
    //     int xo  = bgw / 10;
    //     int yo  = bgw / 10;

    //     Text title = editingMenu.addText(0, yo, bgw, yo, 12, bg.getZ() + 1, "Editing: null", 0);
    //     title.setOffsetX(xo).setAlignment(Text.Alignment.LEFT).setName("EDITING_MENU:TITLE");
    //     Text type = editingMenu.addText(0, yo * 2, bgw, yo, 12, bg.getZ() + 1, "Type: null", 0);
    //     type.setOffsetX(xo).setAlignment(Text.Alignment.LEFT).setName("EDITING_MENU:TYPE");

    //     editingMenu.addSlider(xo, yo * 3, bgw - xo * 2, yo, "EDITING_MENU:COL_R", 0x00, 0xff, 0x00, 0xff0000);
    //     editingMenu.addSlider(xo, yo * 4, bgw - xo * 2, yo, "EDITING_MENU:COL_G", 0x00, 0xff, 0x7f, 0x00ff00);
    //     editingMenu.addSlider(xo, yo * 5, bgw - xo * 2, yo, "EDITING_MENU:COL_B", 0x00, 0xff, 0xff, 0x0000ff);

    //     window.layer("UI").addChild(editingMenu);
    //     return editingMenu;
    // }

    public enum State {
        MAIN, NOTES, CALENDAR;
    }

}
