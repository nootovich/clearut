import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class IO {
    private static final String infoFileLocation = "./saves/";

    public static void saveInfo() {

        // DATA LAYOUT
        // int - info version number
        // int, int - screen size
        // available actions and objects and their states
        // history of unlocks
        // history of actions
        // TODO: figure it out in the process of development

        // init info
        ArrayList<Object> info = new ArrayList<>();
        info.add(Global.INFO_VERSION);
        info.add(Global.CANVAS.getWidth() + "x" + Global.CANVAS.getHeight());

        // go over all layers and elements
        for (UILayer layer : Global.WINDOW.getLayers())
            for (Element element : layer.getElements()) {
                String elementInfo = "[%d, %d, %d, %d] %d %s".formatted(element.getX(), element.getY(), element.getWidth(), element.getHeight(), element.getPriority(), element.getVisibility());
                try {
                    if (((Sprite) element).getOutline())
                        elementInfo += " outline";
                    elementInfo = "SPRITE : " + elementInfo;
                } catch (Exception ignored) {
                    elementInfo = "TEXT   : " + elementInfo;
                }
                info.add(elementInfo);
            }

        // convert info to String
        StringBuilder infoString = new StringBuilder();
        for (Object infoLine : info)
            infoString.append(infoLine.toString()).append("\n");

        // save info to file
        try {
            String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
            File   file        = new File(infoFileLocation + currentTime + ".txt");
            File   directory   = new File(file.getParent());

            if (!directory.exists())
                directory.mkdirs();
            file.createNewFile();

            FileWriter     writer       = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferWriter = new BufferedWriter(writer);

            bufferWriter.write(infoString.toString());
            bufferWriter.close();
        } catch (IOException e) {
            System.out.println(e.getCause() + "\n" + e.getMessage());
        }
    }

    public static class Mouse extends MouseAdapter {
        private int     x;
        private int     y;
        private boolean LMB         = false;
        private boolean RMB         = false;
        private boolean LMBUsed     = false;
        private boolean RMBUsed     = false;
        private boolean doubleClick = false; // TODO: getter/setter

        public void update() {
            try {
                Point mousePos = Global.CANVAS.getMousePosition();
                this.x = mousePos.x;
                this.y = mousePos.y;
            } catch (NullPointerException ignored) {
            }
        }

        public void mousePressed(MouseEvent e) {
            if (Global.LOG)
                System.out.println(e);
            LMB |= (e.getButton() == MouseEvent.BUTTON1);
            RMB |= (e.getButton() == MouseEvent.BUTTON3);
            if (e.getClickCount() > 1)
                doubleClick = true;
        }

        public void mouseReleased(MouseEvent e) {
            if (Global.LOG)
                System.out.println(e);
            LMB &= e.getButton() != MouseEvent.BUTTON1;
            RMB &= e.getButton() != MouseEvent.BUTTON3;
            LMBUsed &= e.getButton() != MouseEvent.BUTTON1;
            RMBUsed &= e.getButton() != MouseEvent.BUTTON3;
            doubleClick = false;
        }

        public void mouseExited(MouseEvent e) {
            if (Global.LOG)
                System.out.println(e);
            LMB         = false;
            RMB         = false;
            LMBUsed     = false;
            RMBUsed     = false;
            doubleClick = false;
        }

        public Point getPos() {
            return new Point(this.x, this.y);
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public boolean getLMB() {
            return LMB;
        }

        public boolean getRMB() {
            return RMB;
        }

        public boolean getLMBUsed() {
            return LMBUsed;
        }

        public void setLMBUsed(boolean bool) {
            LMBUsed = bool;
        }

        public boolean getRMBUsed() {
            return RMBUsed;
        }

        public void setRMBUsed(boolean bool) {
            RMBUsed = bool;
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
//    KeyAdapter           keyAdapter       = new KeyAdapter() {
//        @Override
//        public void keyPressed(KeyEvent e) {
//            char key = e.getKeyChar();
//            switch (key) {
//                case 'm' -> mainView.scrollLock = !mainView.scrollLock;
//                case '=', '+' -> mainView.changeScale(1);
//                case '-' -> mainView.changeScale(-1);
//                case '[' -> GameOfLife.changeSimStepTime(false);
//                case ']' -> GameOfLife.changeSimStepTime(true);
//                case 'w' -> GameOfLife.keys[0] = true;
//                case 'a' -> GameOfLife.keys[1] = true;
//                case 's' -> GameOfLife.keys[2] = true;
//                case 'd' -> GameOfLife.keys[3] = true;
//                case 'r' -> GameOfLife.randomFill();
//                case '\n' -> GameOfLife.simulate();
//                case ' ' -> {
//                    GameOfLife.paused = !GameOfLife.paused;
//                    GameOfLife.prevSimStep = 0;
//                }
//                case 'q' -> System.exit(1);
//            }
//            prevKey = key;
//        }
//        @Override
//        public void keyReleased(KeyEvent e) {
//            switch (e.getKeyChar()) {
//                case 'w' -> GameOfLife.keys[0] = false;
//                case 'a' -> GameOfLife.keys[1] = false;
//                case 's' -> GameOfLife.keys[2] = false;
//                case 'd' -> GameOfLife.keys[3] = false;
//            }
//            prevKey = '\'';
//            keyLock = false;
//        }
//    };
}
