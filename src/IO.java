import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IO {

    public static boolean saveFile(String fileName, String content) {
        try {
            File file      = new File(fileName);
            File directory = new File(file.getParent());
            if (!directory.exists()) directory.mkdirs();
            file.createNewFile();

            FileWriter     writer       = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferWriter = new BufferedWriter(writer);

            bufferWriter.write(content);
            bufferWriter.close();
        } catch (IOException e) {
            System.out.println(e.getCause()+"\n"+e.getMessage());
            return false;
        }
        return true;
    }

    public static String loadFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory();
    }

    public static boolean deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            file.delete();
        } catch (Exception e) {
            System.out.println(e.getCause()+"\n"+e.getMessage());
            return false;
        }
        return true;
    }

    // public static void saveInfo() {

    //     // DATA LAYOUT
    //     // int - savedata version number
    //     // int, int - screen size
    //     // available actions and objects and their states
    //     // history of unlocks
    //     // history of actions
    //     // TODO: figure it out in the process of development

    //     // init savedata
    //     StringBuilder savedata = new StringBuilder("SAVEDATA_VERSION: ").append(Global.SAVEDATA_VERSION);
    //     savedata.append('\n').append(format(
    //             "$WINDOW: x=%d y=%d w=%d h=%d",
    //             Main.window.getX(), Main.window.getY(), Global.CANVAS.getWidth(), Global.CANVAS.getHeight()));

    //     UILayer[] layers = Main.window.getLayers();
    //     for (UILayer layer : layers) {
    //         Element[] descendants = layer.getChildren();

    //         savedata.append('\n').append(format(
    //                 "\t$LAYER: name=%s z=%d children_size=%d",
    //                 layer.name, layer.z, layer.children.length));

    //         for (Object c : children) {
    //             savedata.append('\n').append(getChildData(c, 2));
    //         }
    //     }

    //     String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
    //     saveFile(Global.SAVEDATA_FOLDER + currentTime + ".txt", savedata.toString());

    // }

    // private static String getChildData(Element e, int depth) {
    //     Element[]     descendants = e.getChildren();
    //     StringBuilder result      = new StringBuilder("\t".repeat(depth));
    //     String data = format("$%s: name=%s x=%d y=%d w=%d h=%d z=%d action=%s state=%s%s%s children_size=%d",
    //                          e.getClass(), e.getName(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getZ(), e.getAction(),
    //                          e.isVisible() ? "v" : "", e.isHovered() ? "h" : "", e.isActive() ? "a" : "", descendants.length);
    //     result.append(data);

    //     for (Element descendant : descendants) {
    //         result.append("\n").append(getChildData(descendant, depth + 1));
    //     }

    //     return result.toString();
    // }

    public static class Mouse extends MouseAdapter {

        public static boolean DEBUG = false;
        public        int     x     = -1, y = -1;
        public boolean LMB = false, RMB = false, LMBPrev = false, RMBPrev = false, LMBTemp = false, RMBTemp = false;

        public void update(Window window) {
            Point mousePos = window.DBC.getMousePosition();
            if (mousePos != null) { x = mousePos.x; y = mousePos.y; }
            if (DEBUG) System.out.printf("mousePos: %dx%d%n", x, y); // $DEBUG
            LMBPrev = LMB; RMBPrev = RMB; LMB = LMBTemp; RMB = RMBTemp;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (DEBUG) System.out.println(e); // $DEBUG
            LMBTemp |= e.getButton() == MouseEvent.BUTTON1;
            RMBTemp |= e.getButton() == MouseEvent.BUTTON3;
            // if (getRMBTemp()) {
            //     Element menu = Global.findElement("SPAWN_MENU");
            //     if (menu == null) return;
            //     menu.setX(getX());
            //     menu.setY(getY());
            //     System.out.printf("%dx%d%n", menu.getX(), menu.getY());
            // }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (DEBUG) System.out.println(e); // $DEBUG
            LMBTemp &= e.getButton() != MouseEvent.BUTTON1;
            RMBTemp &= e.getButton() != MouseEvent.BUTTON3;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (DEBUG) System.out.println(e); // $DEBUG
            x       = -1; y = -1;
            LMB     = false; RMB = false;
            LMBPrev = false; RMBPrev = false;
            LMBTemp = false; RMBTemp = false;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (DEBUG) System.out.println(e.paramString()); // $DEBUG
            // Global.CANVAS.updateScrollAt(e.getX(), e.getY(), e.getWheelRotation());
        }

        public boolean isLMBRisingEdge() {
            return LMB && !LMBPrev;
        }

        public boolean isLMBFallingEdge() {
            return !LMB && LMBPrev;
        }

        public boolean isRMBRisingEdge() {
            return RMB && !RMBPrev;
        }

        public boolean isRMBFallingEdge() {
            return !RMB && RMBPrev;
        }
    }

    public static class Keyboard extends KeyAdapter {

        private static final boolean DEBUG = false;

        @Override
        public void keyPressed(KeyEvent e) {
            // 8  - backspace / WIP
            // 10 - enter / HANDLED
            // 16 - shift
            // 17 - ctrl / WIP
            // 18 - alt
            // 19 - pause break / DISABLED
            // 20 - caps lock / DISABLED
            // 27 - esc / HANDLED
            // 33 - page up
            // 34 - page down
            // 35 - end
            // 36 - home
            // 37 - left arrow / WIP
            // 38 - up arrow / WIP
            // 39 - right arrow / WIP
            // 40 - down arrow / WIP
            // 112:123 - F1:F12 / DISABLED
            // 127 - delete / WIP
            // 144 - num lock / DISABLED
            // 145 - scroll lock / DISABLED
            // 155 - insert
            // 65368 - begin (what is this?) / DISABLED
            int[] THE_LIST = new int[]{
                16, 18, 19, 20, 33, 34, 35, 36,
                112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123,
                144, 145, 155, 65368
            };

            if (DEBUG) {
                System.out.printf("%s%n--------------%n", e.paramString());
            } // $DEBUG

            char key  = e.getKeyChar();
            int  code = e.getKeyCode();

            if (code == KeyEvent.VK_ESCAPE) System.exit(69);

            // notes mode
            // if (Global.MODE.equals("NOTES")) {

            //     for (int THE_FORBIDDEN_CODE : THE_LIST) {
            //         if (code == THE_FORBIDDEN_CODE) {
            //             System.out.println("forbidden char");
            //             e.consume();
            //             return;
            //         }
            //     }

            //     // TODO: temporary solution
            //     //	in the future i would need to handle keys properly
            //     if (key == '\0') {
            //         System.out.println("unknown char");
            //         e.consume();
            //         return;
            //     }

            //     //Note note = (Note) Global.findElement("openNote");

            //     //Global.asrt(note != null, "Couldn't find openNote");

            //     // if (e.isControlDown()) {
            //     //     switch (code) {
            //     //         case KeyEvent.VK_BACK_SPACE -> note.deleteWordAtCursorLeft();
            //     //         case KeyEvent.VK_DELETE -> note.deleteWordAtCursorRight();
            //     //         case KeyEvent.VK_LEFT -> note.moveCursorWordLeft();
            //     //         case KeyEvent.VK_RIGHT -> note.moveCursorWordRight();
            //     //         case KeyEvent.VK_L -> Global.ACTIONS.dumpInfoToConsole();
            //     //         case KeyEvent.VK_N -> Text.DEBUG = !Text.DEBUG;
            //     //     }
            //     // } else {
            //     //     switch (code) {
            //     //         case KeyEvent.VK_BACK_SPACE -> note.deleteCharAtCursorLeft();
            //     //         case KeyEvent.VK_DELETE -> note.deleteCharAtCursorRight();
            //     //         case KeyEvent.VK_LEFT -> note.moveCursorCharLeft();
            //     //         case KeyEvent.VK_RIGHT -> note.moveCursorCharRight();
            //     //         case KeyEvent.VK_UP -> note.moveCursorUp();
            //     //         case KeyEvent.VK_DOWN -> note.moveCursorDown();
            //     //         default -> note.addCharAtCursor(key);
            //     //     }
            //     // }

            //     e.consume();
            //     return;
            // }

            // // everything else except notes mode
            // switch (key) {
            //     case 'l' -> Global.ACTIONS.dumpInfoToConsole();
            // }
        }

        @Override
        public void keyReleased(KeyEvent e) { }

    }

    // TODO: make window resizeable
    //
    //    ComponentAdapter componentAdapter = new ComponentAdapter() {
    //        @Override
    //        public void componentResized(ComponentEvent e) {
    //            super.componentResized(e);
    //            changeSize(e.getComponent().getSize());
    //        }
    //    };
}
