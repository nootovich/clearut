import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

public class IO {

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

        // save savedata to file
        try {
            String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
            File   file        = new File(Global.SAVEDATA_FOLDER + currentTime + ".txt");
            File   directory   = new File(file.getParent());

            if (!directory.exists()) {
                directory.mkdirs();
            }
            file.createNewFile();

            FileWriter     writer       = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferWriter = new BufferedWriter(writer);

            bufferWriter.write(savedata.toString());
            bufferWriter.close();
        } catch (IOException e) {
            System.out.println(e.getCause() + "\n" + e.getMessage());
        }
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
        private int     x       = -1;
        private int     y       = -1;
        private boolean LMB     = false;
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
                case 'q' -> System.exit(0);
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
