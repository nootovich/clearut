import java.awt.*;
import java.util.ArrayList;

public class Button { // TODO: maybe make Button implement Element?

    private final Sprite mainSprite, outlineSprite; // TODO: remove
    private final ArrayList<Element> elements = new ArrayList<>();
    private       Text               text;

    public Button(int x, int y, int w, int h, String layerName) {
        this.mainSprite = new Sprite(x, y, w, h, 0, Global.COLOR_COOL_GRAY);
        Global.WINDOW.getLayer(layerName).addElement(mainSprite);

        this.outlineSprite = new Sprite(x, y, w, h, 1, Global.COLOR_VANILLA);
        this.outlineSprite.setVisibility(false);
        this.outlineSprite.setOutline(true);
        Global.WINDOW.getLayer(layerName).addElement(outlineSprite);
    }

    public Button(int x, int y, int w, int h, String layerName, String text) {
        this.mainSprite = new Sprite(x, y, w, h, 0, Global.COLOR_MELON);
        Global.WINDOW.getLayer(layerName).addElement(mainSprite);

        this.outlineSprite = new Sprite(x, y, w, h, 1, Global.COLOR_VANILLA);
        this.outlineSprite.setVisibility(false);
        this.outlineSprite.setOutline(true);
        Global.WINDOW.getLayer(layerName).addElement(outlineSprite);

        Point center = mainSprite.getCenter();
        System.out.println(mainSprite.getX());
        this.text = new Text(center.x, center.y, 16, 5, text, Global.COLOR_RED_MUNSELL);
        Global.WINDOW.getLayer(layerName).addElement(this.text);
    }

    public Element[] getElements() {
        return elements.toArray(new Element[0]);
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    @SuppressWarnings("CommentedOutCode")
    public void update() {
        Point m  = Global.mousePos;
        int   sx = mainSprite.getX(), sy = mainSprite.getY(), sw = mainSprite.getWidth(), sh = mainSprite.getHeight();
        outlineSprite.setVisibility(m.x >= sx && m.x <= sx + sw && m.y >= sy && m.y <= sy + sh);

        // TODO: add mouse buttons logic
//        boolean lmb = GameOfLife.mouseKeys[0], result = false;
//        if (!type) {
//            if (lmb && !mouseLock) result = true;
//        } else {
//            result = lmb;
//        }
//        mouseLock = lmb;
//        return result;
    }
}
