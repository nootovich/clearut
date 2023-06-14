import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Actions {


    public void invoke(String action) {
        if (action.equals("")) return;

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


    private void printf(String template, Object... args) {
        System.out.printf(template + "%n", (Object[]) args);
    }


}
