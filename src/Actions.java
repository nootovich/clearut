import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Actions {

    private static UILayer layer(String name) {
        // TODO: where this thing should be?
        // MEGA TODO: prob rework or remove this. Good idea tho
        // GIGA TODO: this thing is amazing. DO NOT TOUCHA MA SPAGET
        UILayer layer = Global.WINDOW.getLayer(name);
        if (layer != null) return layer;
        Global.WINDOW.addLayerToTop(name);
        return Global.WINDOW.getLayer(name);
    }

    public void invoke(String action) {
        if (action.equals("")) return;

        String[] lines = action.split(":");
        if (lines.length > 1) {
            String function = lines[0];

            switch (lines[1]) {
                case "int" -> {
                    try {
                        this.getClass().getMethod(lines[0], int.class).invoke(this, Integer.parseInt(lines[2]));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        printf("2 There is no action named \"%s\"", action);
                        throw new RuntimeException(e);
                    }

                }
                case "String" -> {
                    try {
                        this.getClass().getMethod(function, String.class).invoke(this, lines[2]);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        printf("3 There is no action named \"%s\"", action);
                        throw new RuntimeException(e);
                    }
                }
            }

        } else try {
            this.getClass().getMethod(action).invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            printf("1 There is no action named \"%s\"", action);
            throw new RuntimeException(e);
        }
    }

    public void dumpInfoToConsole() {
        for (UILayer layer : Global.WINDOW.getLayers()) {
            Element[] descendants = layer.getChildren();
            printf("%nLayer: %s (z: %d, children: %d)", layer.getName(), layer.getZ(), descendants.length);
            for (Element e : descendants) {
                dumpChildInfoToConsole(e, 0);
            }
        }
    }

    private void dumpChildInfoToConsole(Element e, int depth) {
        Element[] descendants = e.getChildren();
        printf("\t".repeat(depth) + "•\t" +
                       "%s %s: (x: %d, y: %d, w: %d, h: %d, z: %d, action: %s, state: %s%s%s, children: %d)",
               e.getClass(), e.getName(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getZ(), e.getAction(),
               e.isVisible() ? "v" : "", e.isHovered() ? "h" : "", e.isActive() ? "a" : "", descendants.length);
        if (e.getClass().equals(Text.class)) {
            Text ea = (Text) e;
            printf("\t".repeat(depth + 1) + "► Value: |%s|", ea.getText());
        }
        for (Element el : descendants) {
            dumpChildInfoToConsole(el, depth + 1);
        }
    }

    public void openNotes() {
        Sprite bg        = (Sprite) findElement("BG");
        Sprite sideBG    = (Sprite) findElement("sideBG");
        Sprite profileBG = (Sprite) findElement("profileBG");

        Element openNote = findElement("openNote");
        if (openNote != null) {
            Text on = (Text) openNote;
            if (on.getText().strip().equals("")) {
                IO.deleteNote(on.getAdditional());
            } else {
                IO.saveNote(on.getAdditional(), on.getText());
            }
            layer("UIMAIN").removeChild("openNote");
        }

        if (bg == null) throw new AssertionError();
        if (sideBG == null) throw new AssertionError();
        if (profileBG == null) throw new AssertionError();

        bg.setIdleColor(Global.COLORS_YELLOW[6]);
        sideBG.setIdleColor(Global.COLORS_YELLOW_BRIGHT[7]);
        profileBG.setIdleColor(Global.COLORS_YELLOW[8]);

        Global.MODE = "NOTES";

        int x = sideBG.getWidth();
        int y = profileBG.getHeight();

        int freeW = Global.CANVAS.getWidth() - x;
        int freeH = Global.CANVAS.getHeight() - y;

        int spacing = freeW >> 5;

        int horizNotes = 5;
        int vertNotes  = 2;

        int nw = (freeW - spacing * (horizNotes + 1)) / horizNotes;
        int nh = (freeH - spacing * (vertNotes + 1)) / vertNotes;

        Group notesGroup = new Group("notesGroup");

        for (int i = 0; i < vertNotes; i++) {
            for (int j = 0; j < horizNotes; j++) {
                int index = (i * horizNotes + j);
                int nx    = x + spacing * (j + 1) + nw * j;
                int ny    = y + spacing * (i + 1) + nh * i;

                Sprite note = new Sprite(nx, ny, nw, nh, 3);
                note.setColors(Global.COLORS_YELLOW_BRIGHT[5], Global.COLORS_YELLOW_BRIGHT[6], Global.COLORS_YELLOW_BRIGHT[7]);
                note.setName("note" + index);
                note.setAction("openNote:int:" + index);

                notesGroup.addChild(note);
                if (IO.noteExists(index)) {
                    Text noteText = new Text(nx + 10, ny + 10, 10, 4, IO.loadNote(index));
                    noteText.setAlignment(Text.Alignment.LEFT);
                    note.addChild(noteText);
                } else {
                    // TODO: introduce copy() or clone() function for Elements
                    int    size      = 25;
                    int    thickness = 3;
                    int    ncx       = nx + nw / 2;
                    int    ncy       = ny + nh / 2;
                    Sprite s1        = new Sprite(ncx - thickness, ncy - size, thickness << 1, size << 1, 4);
                    Sprite s2        = new Sprite(ncx - size, ncy - thickness, size << 1, thickness << 1, 4);
                    s1.setColors(Global.COLORS_YELLOW_BRIGHT[3], Global.COLORS_YELLOW_BRIGHT[9], Global.COLORS_YELLOW_BRIGHT[5]);
                    s2.setColors(Global.COLORS_YELLOW_BRIGHT[3], Global.COLORS_YELLOW_BRIGHT[9], Global.COLORS_YELLOW_BRIGHT[5]);
                    s1.setInheritingInteractions(true);
                    s2.setInheritingInteractions(true);
                    note.addChild(s1);
                    note.addChild(s2);
                }

            }
        }

        layer("UIMAIN").addChild(notesGroup);
    }

    public void openNote(int index) {
        layer("UIMAIN").removeChild("notesGroup");
        String   noteText = "";

        if (IO.noteExists(index)) noteText = IO.loadNote(index);

        Sprite sideBG    = (Sprite) findElement("sideBG");
        Sprite profileBG = (Sprite) findElement("profileBG");

        if (sideBG == null) throw new AssertionError();
        if (profileBG == null) throw new AssertionError();

        int x = sideBG.getWidth();
        int y = profileBG.getHeight();

        Text note = new Text(x + 20, y + 20, 12, 4, noteText, Color.BLACK);
        note.setAlignment(Text.Alignment.LEFT);
        note.setAdditional(index);
        note.setName("openNote");
        layer("UIMAIN").addChild(note);

        Sprite cursor = new Sprite(x + 20, y + 20, 1, 18, 5, Color.BLACK, "cursor");
        note.addChild(cursor);
    }

    public void button1() {
        System.out.println("Hello world!");
    }

    public void button2() {
        layer("UIMAIN").removeChild("notesGroup");
    }

    public void button3() {
        Element button3 = findElement("button3");
        if (button3 == null) return;
        button3.setX(button3.getX() + 100);
    }

    public void button5() {
        Element button3 = findElement("button3");
        if (button3 == null) return;
        button3.setX(button3.getX() - 50);
    }

    private void printf(String template, Object... args) {
        System.out.printf(template + "%n", (Object[]) args);
    }

    public Element findElement(String name) {
        name = name.toUpperCase();

        UILayer[] layers = Global.WINDOW.getLayers();
        for (UILayer layer : layers) {
            Element foundElement = layer.getChild(name);
            if (foundElement != null) return foundElement;
        }

        printf("Element %s was not found!", name);
        return null;
    }
}
