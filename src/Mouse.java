import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Mouse extends MouseAdapter {

    public static boolean DEBUG = false;
    public        int     x     = -1, y = -1;
    public boolean LMB = false, RMB = false, LMBPrev = false, RMBPrev = false, LMBTemp = false, RMBTemp = false;

    public void update(Window window) {
        Point mousePos = window.DBC.getMousePosition();
        if (mousePos != null) {
            x = mousePos.x;
            y = mousePos.y;
        }
        if (DEBUG) System.out.printf("mousePos: %dx%d%n", x, y); // $DEBUG
        LMBPrev = LMB;
        RMBPrev = RMB;
        LMB     = LMBTemp;
        RMB     = RMBTemp;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (DEBUG) System.out.println(e); // $DEBUG
        LMBTemp |= e.getButton() == MouseEvent.BUTTON1;
        RMBTemp |= e.getButton() == MouseEvent.BUTTON3;
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
        x       = -1;
        y       = -1;
        LMB     = false;
        RMB     = false;
        LMBPrev = false;
        RMBPrev = false;
        LMBTemp = false;
        RMBTemp = false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (DEBUG) System.out.println(e.paramString()); // $DEBUG
        if (Main.state == Main.State.NOTE) {
            Note note = Notes.getOpenedNote();
            note.scroll(e.getWheelRotation());
        }
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
