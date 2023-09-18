import java.awt.*;

public class Note extends Text {

    public int id, cursorPos = 0, cursorRow = 0, cursorCol = 0, cursorBol = 0;
    public boolean scrollable = false;

    public Note(int x, int y, int maxW, int maxH, int z, int id, int size, String name) {
        super(x, y, maxW, maxH, z, size, "", name);
        this.id = id;
    }

    public Note(int x, int y, int maxW, int maxH, int z, int id, int size, String name, int color) {
        super(x, y, maxW, maxH, z, size, "", name, color);
        this.id = id;
    }

    public Note(int x, int y, int maxW, int maxH, int z, int id, int size, String name, String text) {
        super(x, y, maxW, maxH, z, size, text, name);
        this.id = id;
    }

    public Note(int x, int y, int maxW, int maxH, int z, int id, int size, String name, String text, int color) {
        super(x, y, maxW, maxH, z, size, text, name, color);
        this.id = id;
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        String[]    lines      = getLines();
        FontMetrics metrics    = g2d.getFontMetrics();
        int         lineHeight = metrics.getHeight();
        int         cursorX    = metrics.stringWidth(lines[cursorRow].substring(0, cursorCol));
        int         cursorY    = lineHeight*cursorRow;
        long        curTime    = System.currentTimeMillis()%512;
        g2d.setColor(curTime < 256 ? new Color(0, 0, 0, (int) (255-curTime)) : new Color(0, 0, 0, (int) curTime-256));
        g2d.fillRect(x+offsetX+cursorX, y+offsetY+cursorY, 2, lineHeight);
    }

    public void scroll(int wheelRotation) {
        offsetY -= wheelRotation*42;
        if (offsetY < h-cachedTextHeight) offsetY = h-cachedTextHeight;
        if (offsetY > 0) offsetY = 0;
    }

    private void updateCursorPos() {
        updateCursorPos(cursorPos);
    }

    private void updateCursorPos(int pos) {
        cursorPos = pos;
        // resetting
        if (cursorPos < 0) {
            cursorPos = 0;
            cursorCol = 0;
            cursorRow = 0;
            cursorBol = 0;
            return;
        }

        // clamping
        if (cursorPos > text.length()) cursorPos = text.length();

        int      accumulatedLength = 0;
        String[] lines             = getLines();
        for (int i = 0; i < lines.length; i++) {
            if (accumulatedLength+lines[i].length() >= cursorPos) {
                cursorBol = accumulatedLength;
                cursorCol = cursorPos-cursorBol;
                cursorRow = i;
                break;
            }
            accumulatedLength += lines[i].length()+1;
        }

        //int  lineCount      = 0;
        //int  accLineLen     = 0;
        //char returnCarriage = (char) 13;
        //
        //String[] wrappedLines = getWrappedLines();
        //for (int i = 0; i < wrappedLines.length; i++) {
        //
        //    String  wrappedLine  = wrappedLines[i];
        //    int     curLineLen   = wrappedLine.length();
        //    boolean emptyLine    = curLineLen == 0;
        //    boolean lastIsReturn = !emptyLine && wrappedLine.charAt(curLineLen-1) == returnCarriage;
        //    if (lastIsReturn) curLineLen--;
        //    if (lineCount == cursorRow && cursorCol <= accLineLen+curLineLen) {
        //        wrappedCol = cursorCol-accLineLen;
        //        wrappedRow = i;
        //        break;
        //    }
        //
        //    if (!lastIsReturn || emptyLine) {
        //        lineCount++;
        //        accLineLen = 0;
        //    } else {
        //        accLineLen += curLineLen;
        //    }
        //}

        int viewableLines     = h/cachedLineHeight;
        int offsetLinesTop    = 3;
        int offsetLinesBottom = viewableLines-offsetLinesTop;
        int cursorViewPos     = cursorRow*cachedLineHeight+offsetY;
        int lowerViewBound    = cachedLineHeight*offsetLinesTop;
        int upperViewBound    = cachedLineHeight*offsetLinesBottom;
        if (cursorViewPos < lowerViewBound) {
            int numOfLineFromTop = cursorRow-offsetLinesTop;
            offsetY = numOfLineFromTop*-cachedLineHeight;
            if (offsetY > 0) offsetY = 0;
        } else if (cursorViewPos > upperViewBound) {
            int numOfLineFromBottom = getLines().length-cursorRow+offsetLinesBottom;
            offsetY = -cachedTextHeight+numOfLineFromBottom*cachedLineHeight;
            if (offsetY > cachedTextHeight-h) offsetY = cachedTextHeight-h;
        }
    }

    public void moveCursorCharLeft() {
        updateCursorPos(--cursorPos);
    }

    public void moveCursorWordLeft() {
        boolean skippingWhitespace = true;
        for (int i = cursorPos-1; i >= 0; i--) {
            boolean whitespace = text.charAt(i) == ' '; // TODO: use isWhitespace()
            if (skippingWhitespace) {
                if (whitespace) continue;
                skippingWhitespace = false;
            }
            if (i == 0) updateCursorPos(0);
            else if (whitespace) {
                updateCursorPos(i+1);
                return;
            }
        }
    }

    public void moveCursorCharRight() {
        updateCursorPos(++cursorPos);
    }

    public void moveCursorWordRight() {
        boolean skippingWhitespace = true;
        for (int i = cursorPos; i < text.length(); i++) {
            boolean whitespace = text.charAt(i) == ' '; // TODO: use isWhitespace()
            if (skippingWhitespace) {
                if (whitespace) continue;
                skippingWhitespace = false;
            }
            if (i == text.length()-1) updateCursorPos(text.length());
            else if (whitespace) {
                updateCursorPos(i);
                return;
            }
        }
    }

    public void moveCursorUp() {
        if (cursorRow <= 0) {
            updateCursorPos(-1);
            return;
        }
        int prevLineLen = getLines()[cursorRow-1].length();
        cursorPos -= cursorCol < prevLineLen ? prevLineLen+1 : cursorCol+1;
        updateCursorPos();
    }

    public void moveCursorDown() {
        if (cursorRow >= getLines().length-1) {
            updateCursorPos(text.length());
            return;
        }
        int curLineLen  = getLines()[cursorRow].length();
        int nextLineLen = getLines()[cursorRow+1].length();
        cursorPos += cursorCol < nextLineLen ? curLineLen+1 : curLineLen+nextLineLen+1-cursorCol;
        updateCursorPos();
    }

    public void addCharAtCursor(char c) {
        text = text.substring(0, cursorPos)+c+text.substring(cursorPos);
        cursorPos++;
        updateCursorPos();
    }

    public void deleteCharAtCursorLeft() {
        if (cursorPos == 0) return;
        text = text.substring(0, cursorPos-1)+text.substring(cursorPos);
        cursorPos--;
        updateCursorPos();
    }

    public void deleteCharAtCursorRight() {
        if (cursorPos == text.length()) return;
        text = text.substring(0, cursorPos)+text.substring(cursorPos+1);
        updateCursorPos();
    }

    public void deleteWordAtCursorLeft() {
        boolean trimmingWhitespace = true;
        for (int i = cursorPos-1; i >= 0; i--) {
            if (i == 0) {
                text = text.substring(cursorPos);
                updateCursorPos(0);
                return;
            }
            char    c          = text.charAt(i);
            boolean whitespace = c == ' ' || c == '\n'; // TODO: use isWhitespace()
            if (trimmingWhitespace) {
                if (whitespace) continue;
                trimmingWhitespace = false;
            }
            if (whitespace) {
                text = (text.substring(0, i)+text.substring(cursorPos));
                updateCursorPos(i);
                return;
            }
        }
    }

    public void deleteWordAtCursorRight() {
        boolean trimmingWhitespace = true;
        for (int i = cursorPos; i < text.length(); i++) {
            if (i == text.length()-1) {
                text = text.substring(0, cursorPos);
                return;
            }
            char    c          = text.charAt(i);
            boolean whitespace = c == ' ' || c == '\n'; // TODO: use isWhitespace()
            if (trimmingWhitespace) {
                if (whitespace) continue;
                trimmingWhitespace = false;
            }
            if (whitespace) {
                text = text.substring(0, cursorPos)+text.substring(i+1);
                return;
            }
        }
    }

}
