import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.time.ZonedDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import static java.lang.String.format;

public class IO {
//     private static final String infoFileLocation = "./saves/";
//
//     public static void saveInfo() {
//
//         // DATA LAYOUT
//         // int - info version number
//         // int, int - screen size
//         // available actions and objects and their states
//         // history of unlocks
//         // history of actions
//         // TODO: figure it out in the process of development
//
//         // init info
//         ArrayList<Object> info = new ArrayList<>();
//         info.add("INFO_VERSION: " + Global.INFO_VERSION);
//         info.add(format(
//                 "WINDOW: %dx%d@%dx%d",
//                 Global.CANVAS.getWidth(),
//                 Global.CANVAS.getHeight(),
//                 Global.WINDOW.getX(),
//                 Global.WINDOW.getY()));
//
//         UILayer[] layers = Global.WINDOW.getLayers();
//         for (UILayer layer : layers) {
//             String layerInfo = format(
//                     "$LAYER: priority[%d] name[%s] elements_size[%d];",
//                     layer.getPriority(),
//                     layer.getName(),
//                     layer.getElements().length);
//             info.add(layerInfo);
//             Element[] elements = layer.getElements();
//             for (Element element : elements) { // TODO: extract this into a recursive function
//                 String ElementInfo;
//                 if (element.getClass() == Button.class) {
//                     Button button = (Button) element;
//                     ElementInfo = format(
//                             "\t$BUTTON: priority[%d] name[%s] pos[%d,%d] size[%d,%d] visible[%b] elements_size[%d]",
//                             button.getPriority(),
//                             button.getLMBPrev(),
//                             button.getX(),
//                             button.getY(),
//                             button.getWidth(),
//                             button.getHeight(),
//                             button.isVisible(),
//                             button.getElements().length);
//                 } else {
//                     ElementInfo = format(
//                             "\t$OTHER: priority[%d] type[%s] pos[%d,%d] size[%d,%d] visible[%b]",
//                             element.getPriority(),
//                             element.getClass(),
//                             element.getX(),
//                             element.getY(),
//                             element.getWidth(),
//                             element.getHeight(),
//                             element.isVisible());
//
//                 }
//                 info.add(ElementInfo);
//             }
//         }
//
//         // convert info to String
//         StringBuilder infoString = new StringBuilder();
//         for (Object infoLine : info)
//             infoString.append(infoLine.toString()).append("\n");
//
//         // save info to file
//         try {
//             String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
//             File   file        = new File(infoFileLocation + currentTime + ".txt");
//             File   directory   = new File(file.getParent());
//
//             if (!directory.exists())
//                 directory.mkdirs();
//             file.createNewFile();
//
//             FileWriter     writer       = new FileWriter(file.getAbsoluteFile());
//             BufferedWriter bufferWriter = new BufferedWriter(writer);
//
//             bufferWriter.write(infoString.toString());
//             bufferWriter.close();
//         } catch (IOException e) {
//             System.out.println(e.getCause() + "\n" + e.getMessage());
//         }
//     }

    public static class Mouse extends MouseAdapter {
        private int     x           = -1;
        private int     y           = -1;
        private boolean LMB         = false;
        private boolean RMB     = false;
        private boolean LMBPrev = false;
        private boolean RMBPrev = false;
        private boolean LMBTemp = false;
        private boolean RMBTemp = false;

        public void update() {
            try {
                Point mousePos = Global.CANVAS.getMousePosition();
                setX(mousePos.x);
                setY(mousePos.y);

                if (Global.LOG > 0) {
                    System.out.printf("mousePos: %dx%d%n", getX(), getY());
                } // $DEBUG

                setLMBPrev(getLMB());
                setRMBPrev(getRMB());

                setLMB(getLMBTemp());
                setRMB(getRMBTemp());
            } catch (NullPointerException ignored) {
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (Global.LOG > 3) {
                System.out.println(e);
            } // $DEBUG

            setLMBTemp(getLMBTemp() | (e.getButton() == MouseEvent.BUTTON1));
            setRMBTemp(getRMBTemp() | (e.getButton() == MouseEvent.BUTTON3));

            if (getRMBTemp()) {
                Element menu = Global.WINDOW.getLayer("UI").getChild("SPAWN_MENU");
                if (menu == null) return;
                menu.setX(getX());
                menu.setY(getY());
                System.out.printf("%dx%d%n", menu.getX(), menu.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (Global.LOG > 3) {
                System.out.println(e);
            } // $DEBUG

            setLMBTemp(getLMBTemp() & (e.getButton() != MouseEvent.BUTTON1));
            setRMBTemp(getRMBTemp() & (e.getButton() != MouseEvent.BUTTON3));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (Global.LOG > 3) {
                System.out.println(e);
            } // $DEBUG

            setX(-1);
            setY(-1);
            setLMB(false);
            setRMB(false);
            setLMBPrev(false);
            setRMBPrev(false);
            setLMBTemp(false);
            setRMBTemp(false);
        }

        public boolean isLMBRisingEdge() {
            return getLMB() && !getLMBPrev();
        }

        public boolean isLMBFallingEdge() {
            return !getLMB() && getLMBPrev();
        }

        public boolean isRMBRisingEdge() {
            return getRMB() && !getRMBPrev();
        }

        public boolean isRMBFallingEdge() {
            return !getRMB() && getRMBPrev();
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public boolean getLMB() {
            return LMB;
        }

        public void setLMB(boolean bool) {
            this.LMB = bool;
        }

        public boolean getRMB() {
            return RMB;
        }

        public void setRMB(boolean bool) {
            this.RMB = bool;
        }

        public boolean getLMBPrev() {
            return LMBPrev;
        }

        public void setLMBPrev(boolean bool) {
            LMBPrev = bool;
        }

        public boolean getRMBPrev() {
            return RMBPrev;
        }

        public void setRMBPrev(boolean bool) {
            RMBPrev = bool;
        }

        public boolean getLMBTemp() {
            return LMBTemp;
        }

        public void setLMBTemp(boolean bool) {
            LMBTemp = bool;
        }

        public boolean getRMBTemp() {
            return RMBTemp;
        }

        public void setRMBTemp(boolean bool) {
            RMBTemp = bool;
        }
    }

    public static class Keyboard extends KeyAdapter {

        private boolean[] pressedKeys = new boolean[256];

        @Override
        public void keyPressed(KeyEvent e) {
            pressedKeys[e.getKeyCode()] = true;
            char key = e.getKeyChar();
            switch (key) {
                case 'l' -> Global.ACTIONS.dumpInfoToConsole();
                case 'q' -> System.exit(1);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            pressedKeys[e.getKeyCode()] = false;
        }
    }

    // TODO: this is from my Game of Life project
    // TODO: extract what you need
    //
    //    ComponentAdapter componentAdapter = new ComponentAdapter() {
    //        @Override
    //        public void componentResized(ComponentEvent e) {
    //            super.componentResized(e);
    //            changeSize(e.getComponent().getSize());
    //        }
    //    };
}
