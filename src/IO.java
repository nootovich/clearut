import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

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
            System.out.println(e.getCause() + "\n" + e.getMessage());
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
            System.out.println(e.getCause() + "\n" + e.getMessage());
            return false;
        }
        return true;
    }

    public static void saveInfo() {

        // DATA LAYOUT
        // int - savedata version number
        // int, int - screen size
        // available actions and objects and their states
        // history of unlocks
        // history of actions
        // TODO: figure it out in the process of development

        // init savedata
        StringBuilder savedata = new StringBuilder("SAVEDATA_VERSION: ").append(Global.SAVEDATA_VERSION);
        savedata.append('\n').append(format(
                "$WINDOW: x=%d y=%d w=%d h=%d",
                Global.WINDOW.getX(), Global.WINDOW.getY(), Global.CANVAS.getWidth(), Global.CANVAS.getHeight()));

        UILayer[] layers = Global.WINDOW.getLayers();
        for (UILayer layer : layers) {
            Element[] descendants = layer.getChildren();

            savedata.append('\n').append(format(
                    "\t$LAYER: name=%s z=%d children_size=%d",
                    layer.getName(), layer.getZ(), descendants.length));

            for (Element descendant : descendants) {
                savedata.append('\n').append(getDescendantData(descendant, 2));
            }
        }

        String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
        saveFile(Global.SAVEDATA_FOLDER + currentTime + ".txt", savedata.toString());

    }

    private static String getDescendantData(Element e, int depth) {
        Element[]     descendants = e.getChildren();
        StringBuilder result      = new StringBuilder("\t".repeat(depth));
        String data = format("$%s: name=%s x=%d y=%d w=%d h=%d z=%d action=%s state=%s%s%s children_size=%d",
                             e.getClass(), e.getName(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getZ(), e.getAction(),
                             e.isVisible() ? "v" : "", e.isHovered() ? "h" : "", e.isActive() ? "a" : "", descendants.length);
        result.append(data);

        for (Element descendant : descendants) {
            result.append("\n").append(getDescendantData(descendant, depth + 1));
        }

        return result.toString();
    }

    public static class Mouse extends MouseAdapter {

        public static boolean DEBUG = false;

        private int     x       = -1;
        private int     y       = -1;
        private boolean LMB     = false;
        private boolean RMB     = false;
        private boolean LMBPrev = false;
        private boolean RMBPrev = false;
        private boolean LMBTemp = false;
        private boolean RMBTemp = false;

        public void update() {
            setX(Global.CANVAS.getMouseX());
            setY(Global.CANVAS.getMouseY());

            if (DEBUG) {
                System.out.printf("mousePos: %dx%d%n", getX(), getY());
            } // $DEBUG

            setLMBPrev(getLMB());
            setRMBPrev(getRMB());

            setLMB(getLMBTemp());
            setRMB(getRMBTemp());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (DEBUG) {
                System.out.println(e);
            } // $DEBUG

            setLMBTemp(getLMBTemp() | (e.getButton() == MouseEvent.BUTTON1));
            setRMBTemp(getRMBTemp() | (e.getButton() == MouseEvent.BUTTON3));

            if (getRMBTemp()) {
                Element menu = Global.findElement("SPAWN_MENU");
                if (menu == null) return;
                menu.setX(getX());
                menu.setY(getY());
                System.out.printf("%dx%d%n", menu.getX(), menu.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (DEBUG) {
                System.out.println(e);
            } // $DEBUG

            setLMBTemp(getLMBTemp() & (e.getButton() != MouseEvent.BUTTON1));
            setRMBTemp(getRMBTemp() & (e.getButton() != MouseEvent.BUTTON3));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (DEBUG) {
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

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (DEBUG) {
                System.out.println(e.paramString());
            } // $DEBUG

            Global.CANVAS.updateScrollAt(e.getX(), e.getY(), e.getWheelRotation());
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

        private static final boolean DEBUG = false;

        private int row = 0;
        private int col = 0;

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
            // 37 - left arrow
            // 38 - up arrow
            // 39 - right arrow
            // 40 - down arrow
            // 112:123 - F1:F12 / DISABLED
            // 127 - delete
            // 144 - num lock / DISABLED
            // 145 - scroll lock / DISABLED
            // 155 - insert
            // 65368 - begin (what is this?) / DISABLED
            int[] THE_LIST = new int[]{
                    16, 17, 18, 19, 20, 33, 34, 35, 36, 37, 38, 39, 40,
                    112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123,
                    127, 144, 145, 155, 65368
            };

            if (DEBUG) {
                System.out.printf("%s%n--------------%n", e.paramString());
            } // $DEBUG

            char key  = e.getKeyChar();
            int  code = e.getKeyCode();

            if (code == KeyEvent.VK_ESCAPE) System.exit(69);

            // notes mode
            if (Global.MODE.equals("NOTES")) {

                for (int THE_FORBIDDEN_CODE : THE_LIST) {
                    if (code == THE_FORBIDDEN_CODE) {
                        if (DEBUG) {
                            System.out.println("forbidden char");
                        } // $DEBUG
                        e.consume();
                        return;
                    }
                }

                // TODO: temporary solution
                //	in the future i would need to handle keys properly
                if (key == KeyEvent.CHAR_UNDEFINED || key == '\0') {
                    System.out.println("unknown char");
                    e.consume();
                    return;
                }

                Text notes = (Text) Global.findElement("openNote");
                //if (notes == null) throw new AssertionError();

                if (e.isControlDown()) {
                    switch (code) {
                        case KeyEvent.VK_BACK_SPACE -> notes.removeLastWord();
                        case KeyEvent.VK_L -> Global.ACTIONS.dumpInfoToConsole();
                        case KeyEvent.VK_N -> Text.DEBUG = !Text.DEBUG;
                    }
                } else {
                    switch (code) {
                        case KeyEvent.VK_BACK_SPACE -> notes.removeLastChar();
                        default -> notes.setText(notes.getText() + key);
                    }
                }

                String txt = notes.getText();
                row = txt.length() - txt.replace("\n", "").length();
                col = txt.length() - txt.lastIndexOf('\n') - 1;

                Sprite cursor = (Sprite) Global.findElement("cursor");
                if (cursor == null) throw new AssertionError();

                // TODO: remove hardcoded values
                cursor.setX(notes.getX() + 8 * col);
                cursor.setY(notes.getY() + 15 * row);

                e.consume();
                return;
            }

            // everything else except notes mode
            switch (key) {
                case 'l' -> Global.ACTIONS.dumpInfoToConsole();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

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
