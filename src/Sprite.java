import java.awt.*;
import static java.lang.String.format;

public class Sprite extends Element {
	
    private boolean outline = false; // TODO: rework Sprite types
    private Color   color; // TODO: add colors for highlighting and activating

    public Sprite(int x, int y, int w, int h, int priority, Color color) {
        super(x, y, priority, w, h);
        this.color = color;
    }

    public boolean update() {
        // if (Global.LOG) System.out.printf(
        //         "\t\tupdate sprite %d : %s%s%n",
        //         getPriority(),
        //         isVisible() ? " visible" : "!visible",
        //         isOutline() ? " : outline" : "");// $DEBUG
        return false;
    }

	@Override
    public void draw(Graphics2D g) {
		System.out.println(format(
			"drawing sprite: %dx%d %dx%d", getX(), getY(), getWidth(), getHeight()
		)); // TODO: figure out why dis no work :(
		
        // if (Global.LOG) System.out.printf(
        //         "\t\tdraw sprite %d : %s%s%n",
        //         getPriority(),
        //         isVisible() ? " visible" : "!visible",
        //         isOutline() ? " : outline" : ""); // $DEBUG

        if (!isVisible()) return;

        // TODO: make this function use enumeration to identify the type of sprite and draw it appropriately
        // TODO: *everything after this line*

        g.setColor(color);
        // if (outline) {
        //     if (Global.LOG) System.out.println("\t\t\toutline"); // $DEBUG
        //     int s = Global.STROKE_WIDTH;
        //     g.setStroke(new BasicStroke(s));
        //     g.drawRect(x + s / 2, y + s / 2, w - s, h - s);
        // } else {
        //     if (Global.LOG) System.out.println("\t\t\tsolid"); // $DEBUG
        //     g.fillRect(x, y, w, h);
        // }

		// TEMPORARY
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		// END OF TEMPORARY
    }

    // public Point getPos() {
    //     return new Point(x, y);
    // }

    // public void setPos(Point pos) {
    //     this.x = pos.x;
    //     this.y = pos.y;
    // }

    // public Point getSize() {
    //     return new Point(w, h);
    // }

    // public void setSize(Point size) {
    //     this.w = size.x;
    //     this.h = size.y;
    // }

    // public Point getCenter() {
    //     return new Point(x + (w >> 1), y + (h >> 1));
    // }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isOutline() {
        return outline;
    }

    public void setOutline(boolean bool) {
        outline = bool;
    }
}
