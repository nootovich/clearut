// import javax.imageio.ImageIO;
// import java.awt.*;
// import java.io.File;

// public class Picture implements Element {

//     private int     x;
//     private int     y;
//     private int     w;
//     private int     h;
//     private int     priority;
//     private boolean visible = true;
//     private String  imageName;
//     private Image   image;

//     public Picture(int x, int y, int w, int h, int priority, String imageName) {
//         this.x         = x;
//         this.y         = y;
//         this.w         = w;
//         this.h         = h;
//         this.priority  = priority;
//         this.imageName = imageName;
//         try {
//             this.image = ImageIO.read(new File(Global.IMAGE_FOLDER.getAbsolutePath() + "\\" + imageName));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }


//     public boolean update() {
//         return false;
//     }

//     public void draw(Graphics2D g) {
//         g.drawImage(image, x, y, w, h, null);
//     }

//     public int getX() {
//         return x;
//     }

//     public void setX(int x) {
//         this.x = x;
//     }

//     public int getY() {
//         return y;
//     }

//     public void setY(int y) {
//         this.y = y;
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
//         this.x = pos.x;
//         this.y = pos.y;
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
//         return priority;
//     }

//     public void setPriority(int priority) {
//         this.priority = priority;
//     }

//     public boolean isVisible() {
//         return visible;
//     }

//     public void setVisibility(boolean bool) {
//         visible = bool;
//     }

//     public String getImageName() {
//         return imageName;
//     }
//     public Image getImage() {
//         return image;
//     }

//     public void setImage(Image image) throws Exception {
//         throw new Exception("TODO: not implemented");
//     }

//     public Color getColor() {
//         return null;
//     }

//     public void setColor(Color color) {

//     }
// }
