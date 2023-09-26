import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {

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
        if (Main.state == Main.State.NOTE) {

            for (int THE_FORBIDDEN_CODE: THE_LIST) {
                if (code == THE_FORBIDDEN_CODE) {
                    System.out.println("forbidden char");
                    e.consume();
                    return;
                }
            }

            // TODO: temporary solution
            //       in the future i would need to handle keys properly
            if (key == '\0') {
                System.out.println("unknown char");
                e.consume();
                return;
            }

            Note note = (Note) Main.window.getLayer("UI_NOTE").getChild("NOTE");

            if (e.isControlDown()) {
                switch (code) {
                    case KeyEvent.VK_BACK_SPACE -> note.deleteWordAtCursorLeft();
                    case KeyEvent.VK_DELETE -> note.deleteWordAtCursorRight();
                    case KeyEvent.VK_LEFT -> note.moveCursorWordLeft();
                    case KeyEvent.VK_RIGHT -> note.moveCursorWordRight();
                    // case KeyEvent.VK_L -> Global.ACTIONS.dumpInfoToConsole();
                    case KeyEvent.VK_N -> Text.DEBUG = !Text.DEBUG;
                }
            } else {
                switch (code) {
                    case KeyEvent.VK_BACK_SPACE -> note.deleteCharAtCursorLeft();
                    case KeyEvent.VK_DELETE -> note.deleteCharAtCursorRight();
                    case KeyEvent.VK_LEFT -> note.moveCursorCharLeft();
                    case KeyEvent.VK_RIGHT -> note.moveCursorCharRight();
                    case KeyEvent.VK_UP -> note.moveCursorUp();
                    case KeyEvent.VK_DOWN -> note.moveCursorDown();
                    default -> note.addCharAtCursor(key);
                }
            }

            e.consume();
            // return;
        }

        // // everything else except notes mode
        // switch (key) {
        //     case 'l' -> Global.ACTIONS.dumpInfoToConsole();
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
