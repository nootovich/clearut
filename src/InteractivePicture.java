// import java.awt.*;

// public class InteractivePicture extends Picture {

//     private boolean highlighted = false;

//     public InteractivePicture(int x, int y, int w, int h, int priority, String imageName) {
//         super(x, y, w, h, priority, imageName);
//     }

//     @Override
//     public boolean update() {
//         if (Global.LOG) System.out.printf(
//                 "\t\tupdate picture %d : %s%s",
//                 super.getPriority(),
//                 super.isVisible() ? " visible" : "!visible",
//                 highlighted ? " : highlighted" : ""); // $DEBUG

//         int mx = Global.MOUSE.getX(); // TODO: reuse from Button.update()
//         int my = Global.MOUSE.getY();
//         int sx = getX();
//         int sy = getY();
//         int sw = getWidth();
//         int sh = getHeight();
//         highlighted = (mx >= sx && mx <= sx + sw && my >= sy && my <= sy + sh);

//         if (Global.LOG && highlighted) System.out.println("\t\t\tmouse is currently inside picture " + super.getImageName()); //
//         // $DEBUG


//         if (highlighted && !Global.MOUSE.getLMBUsed() && Global.MOUSE.getLMB()) {

//             if (Global.LOG) {System.out.println("\t\tmouse clicked at " + super.getImageName());}

//             Global.MOUSE.setLMBUsed(true);
//             return true;
//         }

//         return highlighted;
//     }

//     @Override
//     public void draw(Graphics2D g) {
//         // TODO: move after visibility check after removing debug
//         int     priority = super.getPriority();
//         boolean visible  = super.isVisible();

//         if (Global.LOG) System.out.printf(
//                 "\t\tdraw picture %d : %s%s%n",
//                 priority,
//                 visible ? " visible" : "!visible",
//                 highlighted ? " : " + "highlighted" : ""); // $DEBUG

//         if (!visible) return;

//         int x = super.getX();
//         int y = super.getY();
//         int w = super.getWidth();
//         int h = super.getHeight();

//         // TODO: make this function use enumeration to identify the type of sprite and draw it appropriately
//         // TODO: *everything after this line*

//         g.drawImage(super.getImage(), x, y, w, h, null);

//         if (highlighted) {
//             if (Global.LOG) System.out.println("\t\t\t\thighlighted"); // $DEBUG
//             g.setColor(new Color(255, 255, 255, 42));
//             g.fillRect(x, y, w, h);
//             highlighted = false;
//         } else {
//             if (Global.LOG) System.out.println("\t\t\t\t!highlighted"); // $DEBUG
//         }
//     }
// }
