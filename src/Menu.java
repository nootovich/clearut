// import java.awt.*;

// public class Menu {


//     private final String  name;
//     private final UILayer layer;
//     private       int     x;
//     private       int     y;
//     private       int     w;
//     private       int     h;
//     private       boolean minimized = false;

//     public Menu(int x, int y, int w, int h, int priority, String name) {
//         this.x     = x;
//         this.y     = y;
//         this.w     = w;
//         this.h     = h;
//         this.name  = name;
//         this.layer = new UILayer(name + "_LAYER", priority);
//         Global.WINDOW.addLayer(this.layer);
//     }

//     public boolean update() {
//         if (Global.LOG)
//             System.out.println("update menu " + getName());

//         return layer.update();
//     }

//     public void addSprite(int x, int y, int w, int h, int priority, Color color) {
//         layer.addElement(new Sprite(this.x + x, this.y + y, w, h, priority, color));
//     }

//     public void addInteractiveSprite(int x, int y, int w, int h, int priority, Color color) {
//         layer.addElement(new InteractiveSprite(this.x + x, this.y + y, w, h, priority, color));
//     }

//     public void addButton(int x, int y, int w, int h, int priority, String name, String text) {
//         Button b = new Button(this.x + x, this.y + y, w, h, layer.getName(), name, priority, text);
//     }

//     // TODO: make sure every child element moves into an appropriate position after changing position/size of menu
//     public int getX() {
//         return x;
//     }

//     public void setX(int x) {
//         int change = x - this.x;
//         this.x = x;
//         for (Element element : layer.getElements()) {
//             element.setX(element.getX() + change);
//         }
//     }

//     public int getY() {
//         return y;
//     }

//     public void setY(int y) {
//         int change = y - this.y;
//         this.y = y;
//         for (Element element : layer.getElements()) {
//             element.setY(element.getY() + change);
//         }
//     }

//     public int getWidth() {
//         return w;
//     }

//     public void setWidth(int w) {
//         this.w = w;
//     }

//     public int getHeight() {
//         return h;
//     }

//     public void setHeight(int h) {
//         this.h = h;
//     }

//     public Point getPos() {
//         return new Point(x, y);
//     }

//     public void setPos(Point pos) {
//         int changeX = pos.x - this.x;
//         int changeY = pos.y - this.y;
//         this.x = pos.x;
//         this.y = pos.y;
//         for (Element element : layer.getElements()) {
//             element.setX(element.getX() + changeX);
//             element.setY(element.getY() + changeY);
//         }
//     }

//     public Point getSize() {
//         return new Point(w, h);
//     }

//     public void setSize(Point size) {
//         this.w = size.x;
//         this.h = size.y;
//     }

//     public Point getCenter() {
//         return new Point(x + (w >> 1), y + (h >> 1));
//     }

//     public int getPriority() {
//         return layer.getPriority();
//     }

//     public void setPriority(int priority) {
//         this.layer.setPriority(priority);
//     }

//     public String getName() {
//         return name;
//     }

//     public boolean isMinimized() {
//         return minimized;
//     }

//     public void setMinimized(boolean bool) {
//         this.minimized = bool;
//     }

// }
