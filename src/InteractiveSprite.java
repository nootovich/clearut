import java.awt.*;

public class InteractiveSprite extends Sprite {

    private boolean highlighted = false;

    public InteractiveSprite(int x, int y, int w, int h, int priority, Color color) {
        super(x, y, w, h, priority, color);
    }

    @Override
    public boolean update() {
        if (Global.LOG) System.out.printf(
                "\t\tupdate sprite %d : %s%s%s",
                super.getPriority(),
                super.isVisible() ? " visible" : "!visible",
                super.isOutline() ? " : outline" : "",
                highlighted ? " : highlighted" : ""); // $DEBUG

        int mx = Global.MOUSE.getX(); // TODO: reuse from Button.update()
        int my = Global.MOUSE.getY();
        int sx = getX();
        int sy = getY();
        int sw = getWidth();
        int sh = getHeight();
        highlighted = (mx >= sx && mx <= sx + sw && my >= sy && my <= sy + sh);

        if (Global.LOG && highlighted) System.out.println("\t\t\tmouse is currently inside this sprite"); // $DEBUG

        return highlighted;
    }

    @Override
    public void draw(Graphics2D g) {
        // TODO: move after visibility check after removing debug
        int     priority = super.getPriority();
        boolean visible  = super.isVisible();
        boolean outline  = super.isOutline();

        if (Global.LOG) System.out.printf(
                "\t\tdraw sprite %d : %s%s%s%n",
                priority,
                visible ? " visible" : "!visible",
                outline ? " : outline" : "",
                highlighted ? " : " + "highlighted" : ""); // $DEBUG

        if (!visible) return;

        int     x        = super.getX();
        int     y        = super.getY();
        int     w        = super.getWidth();
        int     h        = super.getHeight();
        int     s        = Global.STROKE_WIDTH;
        Color   color    = super.getColor();

        // TODO: make this function use enumeration to identify the type of sprite and draw it appropriately
        // TODO: *everything after this line*

        if (highlighted) {
            if (Global.LOG) System.out.println("\t\t\t\thighlighted"); // $DEBUG
            g.setColor(outline ? Global.COLOR_RED_MUNSELL : Global.COLOR_MELON);
            highlighted = false;
        } else {
            if (Global.LOG) System.out.println("\t\t\t\t!highlighted"); // $DEBUG
            g.setColor(color);
        }

        if (outline) {
            if (Global.LOG) System.out.println("\t\t\toutline"); // $DEBUG
            g.setStroke(new BasicStroke(s));
            g.drawRect(x + s / 2, y + s / 2, w - s, h - s);
        } else {
            if (Global.LOG) System.out.println("\t\t\tsolid"); // $DEBUG
            g.fillRect(x, y, w, h);
        }
    }
}
