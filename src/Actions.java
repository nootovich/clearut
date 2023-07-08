import java.lang.reflect.InvocationTargetException;

public class Actions {


    public void invoke(String action) {
        if (action.equals("")) return;

        Menu menu = (Menu) Global.findElement("SPAWN_MENU");
        if (menu != null) menu.close();

        try {
            String[] lines = action.split(":");
            if (lines.length > 1) switch (lines[1]) {
                case "int" -> invokeWithInt(lines[0], Integer.parseInt(lines[2]));
                case "String" -> invokeWithString(lines[0], lines[2]);
                default -> throw new IllegalStateException("Unexpected value: " + lines[1]);
            }
            else this.getClass().getMethod(action).invoke(this);

        } catch (NoSuchMethodException e) {
            printf("There is no action named \"%s\"", action);
        } catch (InvocationTargetException e) {
            printf("An error occurred inside the method: \"%s\"", action);
        } catch (IllegalAccessException e) {
            printf("Illegal access \"%s\"", action);
        }
    }

    private void invokeWithInt(String method, int value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getMethod(method, int.class).invoke(this, value);
    }

    private void invokeWithString(String method, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getMethod(method, String.class).invoke(this, value);
    }

    public void dumpInfoToConsole() {
        for (UILayer layer : Main.window.getLayers()) {
            Element[] descendants = layer.getChildren();
            printf("%nLayer: %s (z: %d, children: %d)", layer.getName(), layer.getZ(), descendants.length);
            for (Element e : descendants) {
                dumpChildInfoToConsole(e, 0);
            }
        }
    }

    private void dumpChildInfoToConsole(Element e, int depth) {
        Element[] descendants = e.getChildren();

        if (e.getClass().equals(Text.class) || e.getClass().equals(Note.class)) {
            Text ea = (Text) e;
            printf("\t".repeat(depth) + "•\t" +
                           "%s %s: (x: %d, y: %d, w: %d, h: %d, ox: %d, oy: %d, z: %d, " +
                           "action: %s, state: %s%s%s, children: %d)",
                   e.getClass(),
                   e.getName(),
                   e.getX(),
                   e.getY(),
                   e.getWidth(),
                   e.getHeight(),
                   ea.getOffsetX(),
                   ea.getOffsetY(),
                   e.getZ(),
                   e.getAction(),
                   e.isVisible() ? "v" : "",
                   e.isHovered() ? "h" : "",
                   e.isActive() ? "a" : "",
                   descendants.length);

            String[] lines = ea.getLines();
            for (String line : lines) {
                printf("\t".repeat(depth + 1) + "Text: ► |%s|", line);
            }

            String[] wrappedLines = ea.getWrappedLines();
            for (String wrappedLine : wrappedLines) {
                printf("\t".repeat(depth + 1) + "Wrapped: ► |%s|", wrappedLine.replace('\r', '¶'));
            }
        } else {
            printf("\t".repeat(depth) + "•\t" +
                           "%s %s: (x: %d, y: %d, w: %d, h: %d, z: %d, " +
                           "action: %s, state: %s%s%s, children: %d)",
                   e.getClass(),
                   e.getName(),
                   e.getX(),
                   e.getY(),
                   e.getWidth(),
                   e.getHeight(),
                   e.getZ(),
                   e.getAction(),
                   e.isVisible() ? "v" : "",
                   e.isHovered() ? "h" : "",
                   e.isActive() ? "a" : "",
                   descendants.length);
        }

        for (Element el : descendants) {
            dumpChildInfoToConsole(el, depth + 1);
        }
    }

    public void editElement() {
        int mx = Main.spawnMenu.getX();
        int my = Main.spawnMenu.getY();

        int     offset = 50;
        boolean found  = false;

        UILayer[] layers = Main.window.getLayers();
        for (int i = layers.length - 1; i >= 0 && !found; i--) {

            Element[] descendants = layers[i].getChildren();
            for (int j = descendants.length - 1; j >= 0 && !found; j--) {

                Element d = descendants[j];
                System.out.printf("%d %s %d %s%n", layers[i].getZ(), layers[i].getName(), d.getZ(), d.getName());
                System.out.printf("mx:%d my:%d x:%d y:%d xw:%d yh:%d%n", mx, my, d.getX(), d.getY(),
                                  d.getX() + d.getWidth(), d.getY() + d.getHeight());
                System.out.printf("%b %b %b %b%n%n", d.getX() < mx, d.getX() + d.getWidth() > mx, d.getY() < my,
                                  d.getY() + d.getHeight() > my);
                if (d.getX() < mx && d.getX() + d.getWidth() > mx && d.getY() < my && d.getY() + d.getHeight() > my) {
                    Group em = Main.editingMenu;

                    if (mx > Global.CANVAS.getWidth() / 2) mx -= em.getChild("EDITING_MENU:BG").getWidth() + offset;
                    else mx += offset;
                    if (my > Global.CANVAS.getHeight() / 2) my -= em.getChild("EDITING_MENU:BG").getHeight() + offset;
                    else my += offset;
                    em.setX(mx).setY(my).setVisibility(true).setInteractive(true);

                    Text title = (Text) em.getChild("EDITING_MENU:TITLE");
                    title.setText("Editing: " + d.getName());

                    Text type = (Text) em.getChild("EDITING_MENU:TYPE");
                    type.setText("Type: " + d.getClass().getName());

                    found = true;
                }
            }
        }
    }

    public void openNotes() {
        Notes.openList();
    }

    public void openNote(int index) {
        Notes.openNote(index);
    }

    public void button1() {
        System.out.println("Hello world!");
    }

    public void button2() {
        Global.layer("UIMAIN").removeChild("notesGroup");
    }

    public void button3() {
        System.out.printf("abobus%n");
    }

    public void button4() {
        System.out.printf("4%n");
    }

    public void button5() {
        System.out.printf("5%n");
    }

    private void printf(String template, Object... args) {
        System.out.printf(template + "%n", (Object[]) args);
    }


}
