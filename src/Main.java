import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final boolean DEBUG = false;

    private static final int FPS = 60, plannedFrameTime = (int) (1000.f/FPS);

    public static IO.Mouse mouse;
    public static Window   window;
    public static State    state;


    public static void main(String[] args) throws InterruptedException {
        mouse  = new IO.Mouse();
        window = new Window(800, 400, 300, 200, mouse);
        changeState(State.MAIN);
        // spawnMenu   = initSpawnMenu();
        // editingMenu = initEditingMenu();
        long prevFrameTime = 0, curFrameTime = 0, spentFrameTime = 0;
        while (true) {
            curFrameTime   = System.currentTimeMillis();
            spentFrameTime = curFrameTime-prevFrameTime;
            prevFrameTime  = curFrameTime;
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
        reinit();
    }

    public static void reinit() {
        window.DBC.layers = new Layer[0];
        switch (state) {
            case MAIN -> initMainLayout();
            case NOTES -> System.out.println("ERROR: \"NOTES\" state is not implemented");
            case CALENDAR -> initCalendarLayout();
            default -> System.out.println("ERROR: can't change state to "+state);
        }
    }

    private static void initBaseLayout(int[] priColors, int[] secColors) {
        Dimension usable     = window.getUsableSpace();
        int       width      = usable.width;
        int       height     = usable.height;
        float     offset     = height/28.0f; // TODO: remove hardcoded
        float     buttonSize = height/8.0f;
        int       offs       = (int) offset;
        int       bs         = (int) buttonSize;
        int       bx         = (int) offset;

        // bg
        Layer BG = window.addLayer("BG", 0);
        BG.addChild(new Sprite(0, 0, width, height, 0, priColors[1], "BG"));

        // side panel
        Layer  UI_side = window.addLayer("UI_SIDE", 1);
        Sprite sideBG  = new Sprite(0, 0, bs+offs*2, height, 0, priColors[2], "SIDE_BG");
        UI_side.addChild(sideBG);

        // notes button
        Sprite notesButton = new Sprite(offs, offs, bs, bs, 1, "NOTES_BUTTON", "openNotes");
        notesButton.type  = Sprite.SpriteType.ROUNDED_RECTANGLE;
        notesButton.extra = 8;
        notesButton.setColors(secColors[4], secColors[5], secColors[6]);
        Text notesButtonText = new Text(notesButton.getCenterX(), notesButton.getCenterY(), bs, bs, 5, 12, "NOTES", 0xFFFFFF);
        notesButton.addChild(notesButtonText);
        UI_side.addChild(notesButton);

        // calendar button
        Sprite calButton = new Sprite(offs, offs*2+bs, bs, bs, 1, "CALENDAR_BUTTON", "openCalendar");
        calButton.type  = Sprite.SpriteType.ROUNDED_RECTANGLE;
        calButton.extra = 8;
        calButton.setColors(secColors[4], secColors[5], secColors[6]);
        Text calButtonText = new Text(calButton.getCenterX(), calButton.getCenterY(), bs, bs, 5, 8, "CALENDAR", 0xFFFFFF);
        calButton.addChild(calButtonText);
        UI_side.addChild(calButton);

        // the other buttons (temporary)
        for (int i = 2; i < 6; i++) {
            int    by     = (int) (offset+(buttonSize+offset)*i);
            Sprite button = new Sprite(bx, by, bs, bs, 1);
            button.name   = "BUTTON_"+i;
            button.action = "button"+i;
            button.type   = Sprite.SpriteType.ROUNDED_RECTANGLE;
            button.extra  = 8;
            button.setColors(priColors[4], priColors[5], priColors[6]);
            button.addChild(new Outline(4, 2, priColors[5], priColors[6], priColors[7]));
            window.getLayer("UI_SIDE").addChild(button);
        }

        // profile panel
        Layer  UI_profile = window.addLayer("UI_PROFILE", 2);
        Sprite profileBG  = new Sprite(bs+offs*2, 0, width-sideBG.w, bs+offs*2, 0, priColors[3], "PROFILE_BG");
        UI_profile.addChild(profileBG);

        // profile pic
        Picture profilePic = new Picture(width-bs-offs, offs, bs, bs, 1, Global.IMAGE_FOLDER+"lizard.jpg", "PROFILE_PIC");
        profilePic.type = Picture.PictureType.CIRCLE;
        UI_profile.addChild(profilePic);
    }

    private static void initMainLayout() {
        initBaseLayout(Colors.blue2(), Colors.yellow2());
    }

    private static void initCalendarLayout() {
        int[] blue = Colors.blue2();
        initBaseLayout(blue, Colors.yellow2());
        Sprite sideBG    = (Sprite) window.getLayer("UI_SIDE").getChild("SIDE_BG");
        Sprite profileBG = (Sprite) window.getLayer("UI_PROFILE").getChild("PROFILE_BG");

        Dimension usable      = window.getUsableSpace();
        final int taskCount   = Calendar.TASK_COUNT;
        final int offsetX     = sideBG.w;
        final int offsetY     = profileBG.h;
        final int tasksWidth  = (usable.width-offsetX);
        final int tasksHeight = (usable.height-offsetY);
        final int tileWidth   = tasksWidth/7/2;
        final int tileHeight  = tasksHeight/(taskCount+2);
        final int lowerShelf  = offsetY+tileHeight*(taskCount+1)+tileHeight/2;

        Layer     UI_calendar = window.addLayer("UI_CALENDAR", 3);
        LocalDate now         = LocalDate.now().plusWeeks(Calendar.weekOffset);
        int       dow         = now.getDayOfWeek().getValue()-1;
        boolean   curWeek     = Calendar.weekOffset == 0;

        // current day bg
        if (curWeek) UI_calendar.addChild(new Sprite(offsetX+tasksWidth/2+tileWidth*dow, offsetY, tileWidth, tasksHeight, 2, blue[2]));

        // past week bg
        UI_calendar.addChild(new Sprite(offsetX+tasksWidth/2-tileWidth*2, offsetY, tileWidth*2, tasksHeight, 2, blue[0]));

        // horizontal lines
        for (int i = 0; i < taskCount+1; i++) UI_calendar.addChild(new Sprite(offsetX, offsetY+tileHeight*i+tileHeight, tasksWidth, 1, 5, 0));

        // go through shown days
        for (int i = -2; i < 7; i++) {
            // vertical lines
            UI_calendar.addChild(new Sprite(offsetX+tasksWidth/2+tileWidth*i, offsetY, 1, tasksHeight, 5, 0));

            // day date
            LocalDate day       = now.plusDays(i-dow);
            String    dateHuman = day.format(DateTimeFormatter.ofPattern("dd/MM"));
            int       dayX      = offsetX+tasksWidth/2+tileWidth*i+tileWidth/2;
            int       dayY      = offsetY+tileHeight/2;
            int       dayCol    = i < 0 ? 0xAA6666 : i > 4 ? 0xFF8888 : i == dow ? Colors.white : Colors.lgray;
            int       textSize  = (curWeek && i == dow) ? 12 : 9;
            UI_calendar.addChild(new Text(dayX, dayY, tileWidth, tileHeight, 3, textSize, dateHuman, dayCol));

            // task sprites for a day
            if (curWeek && i > dow) continue;
            String date = day.format(DateTimeFormatter.ofPattern("ddMMyy"));
            for (int j = 0; j < taskCount; j++) {
                int    dayTaskX = offsetX+tasksWidth/2+tileWidth*i;
                int    dayTaskY = offsetY+tileHeight*(j+1);
                Sprite dayTask  = new Sprite(dayTaskX, dayTaskY, tileWidth, tileHeight, 3, "task"+date+Calendar.getTaskId(j), "toggleTask:LocalDate|int:"+date+":"+j);
                if (curWeek && i == dow) dayTask.extra++;
                UI_calendar.addChild(dayTask);
            }

            // load day info from file
            if (IO.fileExists("tasks\\"+date+".txt")) {
                String[] lines = IO.loadFile("tasks\\"+date+".txt").split("\n");
                assert lines.length == taskCount;
                for (int j = 0; j < taskCount; j++) {
                    int taskId  = Integer.parseInt(lines[j].substring(0, 4));
                    int taskVal = Integer.parseInt(lines[j].substring(5));
                    Calendar.getDayTask(day, taskId, UI_calendar).extra += 2*taskVal;
                }
            }

            // total score for a day
            UI_calendar.addChild(new Text(dayX, lowerShelf, tileWidth, tileHeight, 4, 14, "0", "total"+date, dayCol));

            // updates
            Calendar.updateDayTotal(day, UI_calendar);
            Calendar.updateDayColor(day, UI_calendar);
        }

        // titles
        int leftSide = offsetX+tasksWidth/4-tileWidth;
        UI_calendar.addChild(new Text(leftSide, (int) (offsetY+tileHeight*.4f), tasksWidth/2, tileHeight, 3, 34, "Задания", Colors.white));
        UI_calendar.addChild(new Text(leftSide, lowerShelf, tasksWidth/2, tileHeight, 3, 30, "Итого:", Colors.white));

        // tasks
        for (int i = 0; i < taskCount; i++) {
            Text task = new Text(leftSide, (int) (offsetY+tileHeight*(i+1.5f)), tasksWidth/2, tileHeight, 3, 20, Calendar.TASK_LIST[i], Colors.white);
            UI_calendar.addChild(task);
        }

        // total score for a week
        Text weekTotal = new Text(leftSide+tileWidth*2, lowerShelf, tasksWidth/2, tileHeight, 3, 30, "0", "weekTotal", Colors.white);
        UI_calendar.addChild(weekTotal);
        Calendar.updateWeekTotal(now, UI_calendar);

        // left button
        Sprite left = new Sprite((int) (offsetX+tileWidth*0.1f), (int) (offsetY+tileHeight*.1f), (int) (tileWidth*.8f), (int) (tileHeight*.8f), 4);
        left.name   = "CALENDAR_LEFT";
        left.action = "calendarLeft";
        left.type   = Sprite.SpriteType.ROUNDED_RECTANGLE;
        left.extra  = 7;
        left.setColors(blue[4], blue[5], blue[6]);
        UI_calendar.addChild(left);

        // right button
        Sprite rght = new Sprite((int) (offsetX+tileWidth*4.2f), (int) (offsetY+tileHeight*.1f), (int) (tileWidth*.8f), (int) (tileHeight*.8f), 4);
        rght.name   = "CALENDAR_RIGHT";
        rght.action = "calendarRight";
        rght.type   = Sprite.SpriteType.ROUNDED_RECTANGLE;
        rght.extra  = 7;
        rght.setColors(blue[4], blue[5], blue[6]);
        UI_calendar.addChild(rght);
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
        MAIN, NOTES, CALENDAR
    }

}
