import java.awt.*;

public class Menu extends Element {

    private boolean minimized = false;

    public Menu(int x, int y, int w, int h, int z, String name) {
        super(x, y, w, h, z, name);
    }

	@Override
    public boolean update() {
        if (Global.LOG > 1) {
            System.out.println("update menu " + getName());
		}
		
        return updateChildren();
    }

    // public void addButton(int x, int y, int w, int h, int priority, String name, String text) {
    //     Button b = new Button(this.x + x, this.y + y, w, h, layer.getName(), name, priority, text);
    // }

    // public Point getPos() {
    //     return new Point(x, y);
    // }

    // public void setPos(Point pos) {
    //     int changeX = pos.x - this.x;
    //     int changeY = pos.y - this.y;
    //     this.x = pos.x;
    //     this.y = pos.y;
    //     for (Element element : layer.getElements()) {
    //         element.setX(element.getX() + changeX);
    //         element.setY(element.getY() + changeY);
    //     }
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

    public boolean isMinimized() {
        return minimized;
    }

    public void setMinimized(boolean bool) {
        this.minimized = bool;
    }

}
