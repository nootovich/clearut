import java.lang.reflect.InvocationTargetException;

public class Actions {

    public void invoke(String action) {
        if (action.equals("")) return;
        try {
            this.getClass().getMethod(action).invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.printf("The is no action named \"%s\"%n", action);
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
        for (Element el : descendants) {
            dumpChildInfoToConsole(el, depth + 1);
        }
    }

    public void button0() {
        System.out.println("button0 was pressed!");
    }

    public void button1() {
        System.out.println("Hello world!");
    }

    public void button3() {
        Element button3 = findElement("button3");
        if (button3 == null) return;
        button3.setX(button3.getX() + 100);
        System.out.println("button3 move right");
    }

    public void button5() {
        Element button3 = findElement("button3");
        if (button3 == null) return;
        button3.setX(button3.getX() - 50);
        System.out.println("button3 move left");
    }

    private void printf(String template, Object... args) {
        System.out.printf(template + "%n", (Object[]) args);
    }

    private Element findElement(String name) {
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
