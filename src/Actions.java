import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Actions {

    public void invoke(String action) {
        if (action.equals("")) return;
        // Menu menu = (Menu) Global.findElement("SPAWN_MENU");
        // if (menu != null) menu.close();
        try {
            String[] lines = action.split(":");
            if (lines.length > 1) switch (lines[1]) {
                case "int" -> invokeWithInt(lines[0], Integer.parseInt(lines[2]));
                case "String" -> invokeWithString(lines[0], lines[2]);
                case "LocalDate|int" -> invokeWithLocalDateAndInt(lines[0], lines[2], lines[3]);
                default -> throw new IllegalStateException("Unexpected value: "+lines[1]);
            }
            else this.getClass().getMethod(action).invoke(this);
        } catch (NoSuchMethodException e) {
            System.out.printf("There is no action named \"%s\"%n", action);
        } catch (InvocationTargetException e) {
            System.out.printf("An error occurred inside the method: \"%s\"%n", action);
            System.out.printf("%s%n%s%n", e.getCause(), e.getTargetException());
        } catch (IllegalAccessException e) {
            System.out.printf("Illegal access \"%s\"%n", action);
        }
    }

    private void invokeWithInt(String method, int value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getMethod(method, int.class).invoke(this, value);
    }

    private void invokeWithString(String method, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getMethod(method, String.class).invoke(this, value);
    }

    private void invokeWithLocalDateAndInt(String method, String dayStr, String taskIdStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getMethod(method, LocalDate.class, int.class).invoke(this, LocalDate.parse(dayStr, DateTimeFormatter.ofPattern("ddMMyy")), Integer.parseInt(taskIdStr));
    }

    public void toggleTask(LocalDate day, int taskId) {
//        try {
//            URL               url = new URL("http://example.com/");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setDoOutput(true);
//            con.setRequestProperty("Content-Type", "application/json");
//            int status = con.getResponseCode();
//            BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//            String       inputLine;
//            StringBuffer content = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//            con.disconnect();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        Layer parent = Main.window.getLayer("UI_CALENDAR");

        // toggle clicked task
        Sprite dayTask = Calendar.getDayTask(day, taskId, parent);
        dayTask.extra = (dayTask.extra+2)%4;

        // save day info
        StringBuilder content = new StringBuilder();
        Sprite[]      tasks   = Calendar.getDayTasks(day, parent);
        for (int i = 0; i < Calendar.TASK_COUNT; i++) {
            char active = (tasks[i].extra&2) == 2 ? '1' : '0';
            content.append(Calendar.getTaskId(i)).append(':').append(active).append('\n');
        }
        IO.saveFile("tasks\\"+Calendar.getDayStr(day)+".txt", content.toString());

        // updates :)
        Calendar.updateDayTotal(day, parent);
        Calendar.updateDayColor(day, parent);
        Calendar.updateWeekTotal(day, parent);
    }

    public void calendarLeft() {
        Calendar.weekOffset--;
        Main.changeState(Main.State.CALENDAR);
    }

    public void calendarRight() {
        Calendar.weekOffset++;
        Main.changeState(Main.State.CALENDAR);
    }

    // public void dumpInfoToConsole() {
    //     for (UILayer layer : Main.window.layers) {
    //         System.out.printf("%nLayer: %s (z: %d, children: %d)", layer.name, layer.z, layer.children.length);
    //         // for (Element e : layer.children) {
    //         //     dumpChildInfoToConsole(e, 0);
    //         // }
    //     }
    // }

    // private void dumpChildInfoToConsole(Element e, int depth) {
    //     Element[] descendants = e.getChildren();

    //     if (e.getClass().equals(Text.class) || e.getClass().equals(Note.class)) {
    //         Text ea = (Text) e;
    //         System.out.printf("\t".repeat(depth) + "•\t" +
    //                        "%s %s: (x: %d, y: %d, w: %d, h: %d, ox: %d, oy: %d, z: %d, " +
    //                        "action: %s, state: %s%s%s, children: %d)",
    //                e.getClass(),
    //                e.getName(),
    //                e.getX(),
    //                e.getY(),
    //                e.getWidth(),
    //                e.getHeight(),
    //                ea.getOffsetX(),
    //                ea.getOffsetY(),
    //                e.getZ(),
    //                e.getAction(),
    //                e.isVisible() ? "v" : "",
    //                e.isHovered() ? "h" : "",
    //                e.isActive() ? "a" : "",
    //                descendants.length);

    //         String[] lines = ea.getLines();
    //         for (String line : lines) {
    //             System.out.printf("\t".repeat(depth + 1) + "Text: ► |%s|", line);
    //         }

    //         String[] wrappedLines = ea.getWrappedLines();
    //         for (String wrappedLine : wrappedLines) {
    //             System.out.printf("\t".repeat(depth + 1) + "Wrapped: ► |%s|", wrappedLine.replace('\r', '¶'));
    //         }
    //     } else {
    //         System.out.printf("\t".repeat(depth) + "•\t" +
    //                        "%s %s: (x: %d, y: %d, w: %d, h: %d, z: %d, " +
    //                        "action: %s, state: %s%s%s, children: %d)",
    //                e.getClass(),
    //                e.getName(),
    //                e.getX(),
    //                e.getY(),
    //                e.getWidth(),
    //                e.getHeight(),
    //                e.getZ(),
    //                e.getAction(),
    //                e.isVisible() ? "v" : "",
    //                e.isHovered() ? "h" : "",
    //                e.isActive() ? "a" : "",
    //                descendants.length);
    //     }

    //     for (Element el : descendants) {
    //         dumpChildInfoToConsole(el, depth + 1);
    //     }
    // }

    // public void editElement() {
    //     int mx = Main.spawnMenu.getX();
    //     int my = Main.spawnMenu.getY();

    //     int     offset = 50;
    //     boolean found  = false;

    //     UILayer[] layers = Main.window.getLayers();
    //     for (int i = layers.length - 1; i >= 0 && !found; i--) {

    //         Element[] descendants = layers[i].getChildren();
    //         for (int j = descendants.length - 1; j >= 0 && !found; j--) {

    //             Element d = descendants[j];
    //             System.out.printf("%d %s %d %s%n", layers[i].getZ(), layers[i].getName(), d.getZ(), d.getName());
    //             System.out.printf("mx:%d my:%d x:%d y:%d xw:%d yh:%d%n", mx, my, d.getX(), d.getY(),
    //                               d.getX() + d.getWidth(), d.getY() + d.getHeight());
    //             System.out.printf("%b %b %b %b%n%n", d.getX() < mx, d.getX() + d.getWidth() > mx, d.getY() < my,
    //                               d.getY() + d.getHeight() > my);
    //             if (d.getX() < mx && d.getX() + d.getWidth() > mx && d.getY() < my && d.getY() + d.getHeight() > my) {
    //                 Group em = Main.editingMenu;

    //                 if (mx > Global.CANVAS.getWidth() / 2) mx -= em.getChild("EDITING_MENU:BG").getWidth() + offset;
    //                 else mx += offset;
    //                 if (my > Global.CANVAS.getHeight() / 2) my -= em.getChild("EDITING_MENU:BG").getHeight() + offset;
    //                 else my += offset;
    //                 em.setX(mx).setY(my).setVisibility(true).setInteractive(true);

    //                 Text title = (Text) em.getChild("EDITING_MENU:TITLE");
    //                 title.setText("Editing: " + d.getName());

    //                 Text type = (Text) em.getChild("EDITING_MENU:TYPE");
    //                 type.setText("Type: " + d.getClass().getName());

    //                 found = true;
    //             }
    //         }
    //     }
    // }

    public void openNotes() {
        Main.changeState(Main.State.NOTES);
    }

    public static void openNote(int id) {
        if (Main.state != Main.State.NOTE) Main.changeState(Main.State.NOTE);
        Note note = Notes.getOpenedNote();
        note.title           = new Text(note.x, note.y, note.w, 40, note.z+1, Note.titleSize, "", "NOTE_TITLE");
        note.title.alignment = Text.Alignment.LEFT;
        note.text            = "";
        note.id              = id;
        if (Notes.noteExists(id)) {
            String[] contents = Notes.loadNote(id);
            note.title.text = contents[0];
            note.text       = contents[1];
        }
    }

    public void cancelNote() {
        Main.changeState(Main.State.CANCEL_NOTE);
    }

    public static void openCalendar() {
        Main.changeState(Main.State.CALENDAR);
    }

    public void button1() {
        System.out.println("Hello world!");
    }

    // public void button2() {
    //     Global.layer("UIMAIN").removeChild("notesGroup");
    // }

    public void button3() {
        System.out.println("abobus");
    }

    public void button4() {
        System.out.println("4");
    }

    public void button5() {
        System.out.println("5");
    }

}
