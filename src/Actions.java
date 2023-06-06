import java.awt.*;
import java.lang.reflect.InvocationTargetException;

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
        try {
            this.getClass().getMethod(action).invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            printf("The is no action named \"%s\"", action);
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
        if(e.getClass().equals(Text.class)) {
            Text ea = (Text) e;
            printf("\t".repeat(depth+1) + "► Value: %s", ea.getText());
        }
        for (Element el : descendants) {
            dumpChildInfoToConsole(el, depth + 1);
        }
    }

    public void openNotes() {
        Sprite bg        = (Sprite) findElement("BG");
        Sprite sideBG    = (Sprite) findElement("sideBG");
        Sprite profileBG = (Sprite) findElement("profileBG");

        assert bg != null;
        assert sideBG != null;
        assert profileBG != null;

        bg.setIdleColor(Global.COLORS_YELLOW[6]);
        sideBG.setIdleColor(Global.COLORS_YELLOW_BRIGHT[7]);
        profileBG.setIdleColor(Global.COLORS_YELLOW[8]);

        Global.MODE = "NOTES";

        int x = sideBG.getWidth();
        int y = profileBG.getHeight();
        Text input = new Text(x, y, 12, 4, "", Color.BLACK);
        input.setAlignment(Text.Alignment.LEFT);
        input.setName("notes0");
        layer("UIMAIN").addChild(input);

//        int w = Global.WINDOW_WIDTH - x;
//        int h = Global.WINDOW_HEIGHT - y;

//        Sprite notesBG = new Sprite(x, y, w, h, 0, Global.COLORS_YELLOW[6], "notesBG");
//        layer("UIMAIN").addChild(notesBG);
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
