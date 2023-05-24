import java.awt.*;

public class Sprite extends Element {

    // private boolean outline = false; // TODO: rework Sprite types
    private Color[] colors = new Color[3]; // TODO: add colors for highlighting and activating

    public Sprite(int x, int y, int w, int h, int z, Color color) {
        super(x, y, w, h, z);
        setColors(color, color, color);
    }

    public Sprite(int x, int y, int w, int h, int z, Color color, String name) {
        super(x, y, w, h, z, name);
        setColors(color, color, color);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (Global.LOG > 2) {
            System.out.printf(
                    "\t\tdraw sprite: x:%d, y:%d, z:%d, w:%d, h:%d, : %s%n",
                    getX(), getY(), getZ(), getWidth(), getHeight(),
                    isVisible() ? " visible" : "!visible"); // $DEBUG
        }

        if (!isVisible()) return;

        // TODO: make this function use enumeration to identify the type of sprite and draw it appropriately
        // TODO: *everything after this line*


        // TODO: don't forget to rework this part. Color should be set based on current state of the Sprite
        if (isActive()) {
            g2d.setColor(getActiveColor());
        } else if (isHovered()) {
            g2d.setColor(getHoveredColor());
        } else {
            g2d.setColor(getIdleColor());
        }


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
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
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

    public Color[] getColors() {
        return colors;
    }

    public Color getIdleColor() {
        return colors[0];
    }

    public void setIdleColor(Color color) {
        colors[0] = color;
    }

    public Color getHoveredColor() {
        return colors[1];
    }

    public void setHoveredColor(Color color) {
        colors[1] = color;
    }

    public Color getActiveColor() {
        return colors[2];
    }

    public void setActiveColor(Color color) {
        colors[2] = color;
    }

    public void setColors(Color idleColor, Color hoveredColor, Color activeColor) {
        colors = new Color[]{idleColor, hoveredColor, activeColor};
    }

//    public boolean isOutline() {
//        return outline;
//    }
//
//    public void setOutline(boolean bool) {
//        outline = bool;
//    }
}
