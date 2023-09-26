import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final boolean DEBUG = false;

    private static final int FPS = 60, plannedFrameTime = (int) (1000.f/FPS);

    public static IO.Mouse    mouse;
    public static IO.Keyboard keyboard;
    public static Window      window;
    public static State       state;


    public static void main(String[] args) throws InterruptedException {
        mouse    = new IO.Mouse();
        keyboard = new IO.Keyboard();
        window   = new Window(800, 400, 300, 200, mouse, keyboard);
        changeState(State.MAIN);
        long prevFrameTime = 0, curFrameTime = 0, spentFrameTime = 0;
        while (true) {
            curFrameTime   = System.currentTimeMillis();
            spentFrameTime = curFrameTime-prevFrameTime;
            prevFrameTime  = curFrameTime;
            if (spentFrameTime > 250) spentFrameTime = 250;
            if (DEBUG) System.out.printf("frametime: %dms%n", spentFrameTime); // $DEBUG
            mouse.update(window);
            window.repaint();
            int sleepTime = (int) (plannedFrameTime*2-spentFrameTime);
            if (sleepTime < 0) sleepTime = 0;
            if (sleepTime > plannedFrameTime) sleepTime = plannedFrameTime;
            Thread.sleep(sleepTime);
        }
    }

    public static void changeState(State newState) {
        if (state == State.NOTE)
            if (newState != State.CANCEL_NOTE) Notes.saveOpenedNote();
            else newState = State.NOTES;
        state = newState;
        reinit();
    }

    public static void reinit() {
        window.DBC.layers = new Layer[0];
        switch (state) {
            case MAIN -> initMainLayout();
            case NOTES -> initNotesLayout();
            case NOTE -> initNoteLayout();
            case CALENDAR -> initCalendarLayout();
            default -> System.out.println("ERROR: can't change state to "+state);
        }
    }

    public static void resize() {
        if (state == State.NOTE) {
            int savedNoteId = Notes.getOpenedNote().id;
            if (savedNoteId != -1) {
                reinit();
                Actions.openNote(savedNoteId);
                return;
            }
        }
        reinit();
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
        Rect  bg = new Rect(0, 0, width, height, 0);
        bg.setColor(priColors[1]);
        bg.setName("BG");
        BG.addChild(bg);

        // side panel
        Layer UI_side = window.addLayer("UI_SIDE", 1);
        Rect  sideBG  = new Rect(0, 0, bs+offs*2, height, 0);
        sideBG.setColor(priColors[2]);
        sideBG.setName("SIDE_BG");
        UI_side.addChild(sideBG);

        // notes button
        RoundRect notesButton = new RoundRect(offs, offs, bs, bs, 1, 8);
        notesButton.setColors(secColors[4], secColors[5], secColors[6]);
        notesButton.setName("NOTES_BUTTON");
        notesButton.action = "openNotes";
        Text notesButtonText = new Text((int) notesButton.getCenterX(), (int) notesButton.getCenterY(), bs, bs, 5, 12, "NOTES", 0xFFFFFF);
        notesButton.addChild(notesButtonText);
        UI_side.addChild(notesButton);

        // calendar button
        RoundRect calButton = new RoundRect(offs, offs*2+bs, bs, bs, 1, 8);
        calButton.setColors(secColors[4], secColors[5], secColors[6]);
        calButton.setName("CALENDAR_BUTTON");
        calButton.action = "openCalendar";
        Text calButtonText = new Text((int) calButton.getCenterX(), (int) calButton.getCenterY(), bs, bs, 5, 12, "TASKS", 0xFFFFFF);
        calButton.addChild(calButtonText);
        UI_side.addChild(calButton);

        // the other buttons (temporary)
        for (int i = 2; i < 6; i++) {
            int       by     = (int) (offset+(buttonSize+offset)*i);
            RoundRect button = new RoundRect(bx, by, bs, bs, 1, 8);
            button.setColors(priColors[4], priColors[5], priColors[6]);
            button.setName("BUTTON_"+i);
            button.action = "button"+i;
            // button.addChild(new Outline(4, 2, priColors[5], priColors[6], priColors[7]));
            UI_side.addChild(button);
        }

        // profile panel
        Layer UI_profile = window.addLayer("UI_PROFILE", 2);
        Rect  profileBG  = new Rect(bs+offs*2, 0, width-sideBG.w, bs+offs*2, 0);
        profileBG.setColor(priColors[3]);
        profileBG.setName("PROFILE_BG");
        UI_profile.addChild(profileBG);

        // profile pic
        Picture profilePic = new Picture(width-bs-offs, offs, bs, bs, 1, Global.IMAGE_FOLDER+"lizard.jpg", "PROFILE_PIC");
        profilePic.type = Picture.PictureType.CIRCLE;
        UI_profile.addChild(profilePic);
    }

    private static void initMainLayout() {
        initBaseLayout(Colors.blue2(), Colors.yellow2());
    }

    private static void initNotesLayout() {
        int[] yellow = Colors.yellow2();
        initBaseLayout(Colors.blue2(), yellow);
        Rect  sideBG    = (Rect) window.getLayer("UI_SIDE").getChild("SIDE_BG");
        Rect  profileBG = (Rect) window.getLayer("UI_PROFILE").getChild("PROFILE_BG");
        Layer UI_notes  = window.addLayer("UI_NOTES", 3);

        // TODO: remove hardcoded
        int   horizNotes = 5;
        int   vertNotes  = 2;
        float x          = sideBG.w;
        float y          = profileBG.h;
        float freeW      = window.DBC.getWidth()-x;
        float freeH      = window.DBC.getHeight()-y;
        float spacing    = freeW/32.f;
        float nw         = (freeW-spacing*(horizNotes+1.f))/horizNotes;
        float nh         = (freeH-spacing*(vertNotes+1.f))/vertNotes;

        for (int i = 0; i < vertNotes; i++) {
            for (int j = 0; j < horizNotes; j++) {

                int   id = i*horizNotes+j;
                float nx = x+spacing*(j+1.f)+nw*j;
                float ny = y+spacing*(i+1.f)+nh*i;

                RoundRect note = new RoundRect(nx, ny, nw, nh, 3, 24);
                note.setColors(yellow[5], yellow[6], yellow[7]);
                note.setName("NOTE"+id);
                note.action = "openNote:int:"+id;
                UI_notes.addChild(note);

                if (Notes.noteExists(id)) {
                    int      pad          = 5;
                    String[] noteContents = Notes.loadNote(id);
                    Text     noteTitle    = new Text((int) (nx+pad), (int) (ny+pad), (int) (nw-pad*2), pad*2, 4, 18, noteContents[0]);
                    Text     noteText     = new Text((int) (nx+pad), (int) (ny+pad*6), (int) (nw-pad*2), (int) (nh-pad*2), 4, 12, noteContents[1]);
                    noteTitle.alignment = Text.Alignment.LEFT;
                    noteText.alignment  = Text.Alignment.LEFT;
                    note.addChild(noteTitle);
                    note.addChild(noteText);
                    continue;
                }

                // TODO: remove hardcoded values
                float size      = 25.f;
                float thickness = 3.f;
                float ncx       = nx+nw/2.f;
                float ncy       = ny+nh/2.f;
                Rect  s1        = new Rect(ncx-thickness, ncy-size, thickness/2.f, size/2.f, 4);
                Rect  s2        = new Rect(ncx-size, ncy-thickness, size/2.f, thickness/2.f, 4);
                s1.setColors(yellow[3], yellow[9], yellow[5]);
                s2.setColors(yellow[3], yellow[9], yellow[5]);
                // s1.inherits = true;
                // s2.inherits = true;
                note.addChild(s1);
                note.addChild(s2);
            }
        }
    }

    private static void initNoteLayout() {
        int[] yellow = Colors.yellow2();
        initBaseLayout(Colors.blue2(), yellow);
        Rect  sideBG    = (Rect) window.getLayer("UI_SIDE").getChild("SIDE_BG");
        Rect  profileBG = (Rect) window.getLayer("UI_PROFILE").getChild("PROFILE_BG");
        Layer UI_note   = window.addLayer("UI_NOTE", 3);

        int   padding = 15;
        float x       = sideBG.w+padding;
        float y       = profileBG.h+padding;
        float w       = window.DBC.getWidth()-sideBG.w-padding*2.f;
        float h       = window.DBC.getHeight()-profileBG.h-padding*2.f;

        RoundRect noteBG = new RoundRect(x, y, w, h, 4, padding);
        noteBG.setColor(yellow[5]);
        noteBG.setName("NOTE_BG");
        RoundRect noteTitleSeparation = new RoundRect(x+padding, y+Note.titleSize+padding*1.7f, w-padding*2.f, 2.f, 5, (int) (w/4.f));
        noteTitleSeparation.setColor(0x424200);
        noteBG.addChild(noteTitleSeparation);
        UI_note.addChild(noteBG);

        // TODO: add a way to set the value of z based on what you want to do with it
        Note note = new Note((int) (x+padding), (int) (y+padding), (int) (w-padding*2.f), (int) (h-padding*2.f), 5, 20, "NOTE", 0);
        note.alignment  = Text.Alignment.LEFT;
        note.scrollable = true;
        UI_note.addChild(note);

        RoundRect cancelButton = new RoundRect(x+w-padding*8.f, y+padding, padding*7.f, padding*2.f, 6, padding);
        cancelButton.setColor(0xff6969);
        cancelButton.setName("CANCEL");
        cancelButton.action = "cancelNote";
        Text cancelText = new Text((int) cancelButton.getCenterX(), (int) (cancelButton.getCenterY()-3), (int) cancelButton.w, (int) cancelButton.h, 7, 20, "CANCEL", 0xffffff);
        cancelButton.addChild(cancelText);
        note.addChild(cancelButton);
    }

    private static void initCalendarLayout() {
        int[] blue = Colors.blue2();
        initBaseLayout(blue, Colors.yellow2());
        Rect sideBG    = (Rect) window.getLayer("UI_SIDE").getChild("SIDE_BG");
        Rect profileBG = (Rect) window.getLayer("UI_PROFILE").getChild("PROFILE_BG");

        Dimension   usable     = window.getUsableSpace();
        final int   taskCount  = Calendar.TASK_COUNT;
        final float startX     = sideBG.w;
        final float startY     = profileBG.h;
        final float usableW    = usable.width-startX;
        final float usableH    = usable.height-startY;
        final float tileW      = usableW/14.f;
        final float tileH      = usableH/(taskCount+2.f);
        final float pad        = 0.f; // TODO: remove hardcoded
        final float dayTaskW   = tileW-pad*2.f;
        final float dayTaskH   = tileH-pad*2.f;
        final float lowerShelf = startY+tileH*(taskCount+1.f)+tileH/2.f;

        Layer     UI_calendar = window.addLayer("UI_CALENDAR", 3);
        LocalDate now         = LocalDate.now().plusWeeks(Calendar.weekOffset);
        int       dow         = now.getDayOfWeek().getValue()-1;
        boolean   curWeek     = Calendar.weekOffset == 0;

        // current day bg
        Rect curDayBg = new Rect(startX+usableW/2.f+tileW*dow+pad, startY+pad, dayTaskW, usableH-pad*2.f, 2);
        curDayBg.setColor(blue[2]);
        if (curWeek) UI_calendar.addChild(curDayBg);

        // past week bg
        Rect pastWeekBg = new Rect(startX+usableW/2.f-tileW*2.f, startY, tileW*2.f, usableH, 2);
        pastWeekBg.setColor(blue[0]);
        UI_calendar.addChild(pastWeekBg);

        // horizontal lines
        for (int i = 0; i < taskCount+1; i++) {
            Rect horizLine = new Rect(startX-1.f, startY+tileH*i+tileH, usableW, 2.f, 5);
            horizLine.setColor(0);
            UI_calendar.addChild(horizLine);
        }

        // go through shown days
        for (int i = -2; i < 7; i++) {
            // vertical lines
            Rect vertLine = new Rect(startX+usableW/2.f+tileW*i, startY-1.f, 2.f, usableH, 5);
            vertLine.setColor(0);
            UI_calendar.addChild(vertLine);

            // day date
            LocalDate day       = now.plusDays(i-dow);
            String    dateHuman = day.format(DateTimeFormatter.ofPattern("dd/MM"));
            float     dayX      = startX+usableW/2.f+tileW*i+tileW/2.f;
            float     dayY      = startY+tileH/2.f;
            int       dayCol    = i < 0 ? 0xAA6666 : i > 4 ? 0xFF8888 : i == dow ? Colors.white : Colors.lgray;
            int       textSize  = (curWeek && i == dow) ? 12 : 9;
            UI_calendar.addChild(new Text((int) dayX, (int) dayY, (int) tileW, (int) tileH, 3, textSize, dateHuman, dayCol));

            // task sprites for a day
            if (curWeek && i > dow) continue;
            String date = day.format(DateTimeFormatter.ofPattern("ddMMyy"));
            for (int j = 0; j < taskCount; j++) {
                float     dayTaskX = startX+usableW/2.f+tileW*i+pad;
                float     dayTaskY = startY+tileH*(j+1.f)+pad;
                RoundRect dayTask  = new RoundRect(dayTaskX, dayTaskY, dayTaskW, dayTaskH, 3, (curWeek && i == dow) ? 1 : 0);
                dayTask.setName("TASK"+date+Calendar.getTaskId(j));
                dayTask.action = "toggleTask:LocalDate|int:"+date+":"+j;
                UI_calendar.addChild(dayTask);
            }

            // load day info from file
            if (IO.fileExists("tasks\\"+date+".txt")) {
                String[] lines = IO.loadFile("tasks\\"+date+".txt").split("\n");
                assert lines.length == taskCount;
                for (int j = 0; j < taskCount; j++) {
                    int taskId  = Integer.parseInt(lines[j].substring(0, 4));
                    int taskVal = Integer.parseInt(lines[j].substring(5));
                    Calendar.getDayTask(day, taskId, UI_calendar).rounding += 2*taskVal;
                }
            }

            // total score for a day
            UI_calendar.addChild(new Text((int) dayX, (int) lowerShelf, (int) tileW, (int) tileH, 4, 14, "0", "total"+date, dayCol));

            // updates
            Calendar.updateDayTotal(day, UI_calendar);
            Calendar.updateDayColor(day, UI_calendar);
        }

        // titles
        int leftSide = (int) (startX+usableW/4.f-tileW);
        UI_calendar.addChild(new Text(leftSide, (int) (startY+tileH*.4f), (int) (usableW/2.f), (int) tileH, 3, 34, "Задания", Colors.white));
        UI_calendar.addChild(new Text(leftSide, (int) lowerShelf, (int) (usableW/2.f), (int) tileH, 3, 30, "Итого:", Colors.white));

        // tasks
        for (int i = 0; i < taskCount; i++) {
            Text task = new Text(leftSide, (int) (startY+tileH*(i+1.5f)), (int) (usableW/2.f), (int) tileH, 3, 20, Calendar.TASK_LIST[i], Colors.white);
            UI_calendar.addChild(task);
        }

        // total score for a week
        Text weekTotal = new Text((int) (leftSide+tileW*2.f), (int) lowerShelf, (int) (usableW/2.f), (int) tileH, 3, 30, "0", "weekTotal", Colors.white);
        UI_calendar.addChild(weekTotal);
        Calendar.updateWeekTotal(now, UI_calendar);

        // left button
        RoundRect left = new RoundRect(startX+pad, startY+pad, dayTaskW-pad, dayTaskH-pad, 4, 12);
        left.setColors(blue[4], blue[5], blue[6]);
        left.setName("CALENDAR_LEFT");
        left.action = "calendarLeft";
        UI_calendar.addChild(left);

        // right button
        RoundRect right = new RoundRect(startX+usableW/2.f-tileW*3.f+pad, startY+pad, dayTaskW-pad, dayTaskH-pad, 4, 12);
        right.setColors(blue[4], blue[5], blue[6]);
        right.setName("CALENDAR_RIGHT");
        right.action = "calendarRight";
        UI_calendar.addChild(right);
    }

    // private static Menu initSpawnMenu() {
    //     int[] y        = Colors.yellow2();
    //     Menu  testMenu = new Menu(150, 200, 2, "SPAWN_MENU", y[3], y[5], y[8]);
    //     String[][] menuLayout = new String[][]{
    //             {"Dump info to console", "dumpInfoToConsole"},
    //             {"Edit element", "editElement"}};
    //     for (int i = 0; i < menuLayout.length; i++) testMenu.addButton(menuLayout[i][0], "button" + menuLayout[i][1], menuLayout[i][1]);
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
        MAIN, NOTES, NOTE, CANCEL_NOTE, CALENDAR
    }

}
