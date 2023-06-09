import java.awt.*;

public class Note extends Text {

    public int cursorPos = 0;
    public int cursorRow = 0;
    public int cursorCol = 0;
    public int cursorBol = 0;

    public int wrappedCol = 0;
    public int wrappedRow = 0;
    public int wrappedBol = 0;

    public Note(int x, int y, int maxW, int maxH, int size, int z) {
        super(x, y, maxW, maxH, size, z);
    }

    public Note(int x, int y, int maxW, int maxH, int size, int z, String text) {
        super(x, y, maxW, maxH, size, z, text);
    }

    public Note(int x, int y, int maxW, int maxH, int size, int z, String text, int color) {
        super(x, y, maxW, maxH, size, z, text, color);
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);

        FontMetrics metrics = g2d.getFontMetrics();

        int lineHeight = metrics.getHeight();
        int cursorX    = metrics.stringWidth(getWrappedLines()[wrappedRow].substring(0, wrappedCol));
        int cursorY    = lineHeight * wrappedRow;

        long curTime = System.currentTimeMillis() % 400;
        if (curTime > 200) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(getX() + offsetX + cursorX, getY() + offsetY + cursorY, 2, lineHeight);
        }
    }

    @Override
    public void scroll(int flags) {
        int scrollAmount = 5;
        if ((flags & Flags.MWHEELUP) > 0) offsetY += scrollAmount;
        else if ((flags & Flags.MWHEELDN) > 0) offsetY -= scrollAmount;

        int lowerBound = getHeight() - cachedTextHeight;
        if (offsetY < lowerBound) offsetY = lowerBound;

        int higherBound = 0;
        if (offsetY > higherBound) offsetY = higherBound;
    }

    private void updateCursorPos() {
        if (cursorPos < 0) {
            cursorPos  = 0;
            cursorCol  = 0;
            cursorRow  = 0;
            cursorBol  = 0;
            wrappedCol = 0;
            wrappedRow = 0;
            wrappedBol = 0;
            return;
        }

        if (cursorPos > getText().length()) {
            cursorPos = getText().length();
        }

        int      acc   = 0;
        String[] lines = getLines();
        for (int i = 0; i < lines.length; i++) {
            if (acc + lines[i].length() >= cursorPos) {
                cursorBol = acc;
                cursorCol = cursorPos - cursorBol;
                cursorRow = i;
                break;
            }
            acc += lines[i].length() + 1;
        }

        int  lineCount      = 0;
        int  accLineLen     = 0;
        char returnCarriage = (char) 13;

        String[] wrappedLines = getWrappedLines();
        for (int i = 0; i < wrappedLines.length; i++) {

            String  wrappedLine  = wrappedLines[i];
            int     curLineLen   = wrappedLine.length();
            boolean emptyLine    = curLineLen == 0;
            boolean lastIsReturn = !emptyLine && wrappedLine.charAt(curLineLen - 1) == returnCarriage;
            if (lastIsReturn) curLineLen--;
            if (lineCount == cursorRow && cursorCol <= accLineLen + curLineLen) {
                wrappedCol = cursorCol - accLineLen;
                wrappedRow = i;
                break;
            }

            if (!lastIsReturn || emptyLine) {
                lineCount++;
                accLineLen = 0;
            } else {
                accLineLen += curLineLen;
            }
        }

        int maxH              = getHeight();
        int viewableLines     = maxH / cachedLineHeight;
        int offsetLinesTop    = 3;
        int offsetLinesBottom = viewableLines - offsetLinesTop;
        int cursorViewPos     = wrappedRow * cachedLineHeight + offsetY;
        int lowerViewBound    = cachedLineHeight * offsetLinesTop;
        int upperViewBound    = cachedLineHeight * offsetLinesBottom;

        if (cursorViewPos < lowerViewBound) {
            int numOfLineFromTop = wrappedRow - offsetLinesTop;
            offsetY = numOfLineFromTop * -cachedLineHeight;

            if (offsetY > 0) offsetY = 0;

        } else if (cursorViewPos > upperViewBound) {
            int numOfLineFromBottom = getWrappedLines().length - wrappedRow + offsetLinesBottom;
            offsetY = -cachedTextHeight + numOfLineFromBottom * cachedLineHeight;

            if (offsetY > cachedTextHeight - maxH) offsetY = cachedTextHeight - maxH;
        }

    }

    public void moveCursorCharLeft() {
        cursorPos--;
        updateCursorPos();
    }

    public void moveCursorWordLeft() {
        if (cursorPos == 0) return;

        boolean skippingWhitespace = true;
        String  begin              = getText();

        for (int i = cursorPos - 1; i >= 0; i--) {
            char ch = begin.charAt(i);

            if (skippingWhitespace) {
                if (ch != ' ') { // TODO: use isWhitespace()
                    skippingWhitespace = false;
                } else continue;
            }

            if (i == 0) cursorPos = 0;

            else if (ch == ' ') { // TODO: use isWhitespace()
                cursorPos = i + 1;
                break;
            }
        }
        updateCursorPos();
    }

    public void moveCursorCharRight() {
        cursorPos++;
        updateCursorPos();
    }

    public void moveCursorWordRight() {
        if (cursorPos == getText().length()) return;

        boolean skippingWhitespace = true;
        String  begin              = getText();

        for (int i = cursorPos; i < begin.length(); i++) {
            char ch = begin.charAt(i);

            if (skippingWhitespace) {
                if (ch != ' ') { // TODO: use isWhitespace()
                    skippingWhitespace = false;
                } else continue;
            }

            if (i == begin.length() - 1) cursorPos = begin.length();

            else if (ch == ' ') { // TODO: use isWhitespace()
                cursorPos = i;
                break;
            }
        }
        updateCursorPos();
    }

    public void moveCursorUp() {
        if (wrappedRow <= 0) {
            cursorPos = -1;
            updateCursorPos();
            return;
        }

        int prevWrappedLineLen = getWrappedLines()[wrappedRow - 1].length();

        if (wrappedCol < prevWrappedLineLen) cursorPos -= prevWrappedLineLen + 1;
        else cursorPos -= cursorCol + 1;

        updateCursorPos();
    }

    public void moveCursorDown() {
        if (wrappedRow >= getWrappedLines().length - 1) {
            cursorPos = getText().length();
            updateCursorPos();
            return;
        }

        int curLineLen  = getLines()[cursorRow].length();
        int nextLineLen = getLines()[cursorRow + 1].length();

        if (wrappedCol < nextLineLen) {
            cursorPos += curLineLen + 1;
        } else {
            cursorPos += curLineLen - cursorCol + nextLineLen + 1;
        }

        updateCursorPos();
    }

    public void addCharAtCursor(char c) {
        String begin = getText();
        String end   = begin.substring(0, cursorPos) + c + begin.substring(cursorPos);

        setText(end);
        cursorPos++;
        updateCursorPos();
    }

    public void deleteCharAtCursorLeft() {
        if (cursorPos == 0) return;

        String begin = getText();
        String end   = begin.substring(0, cursorPos - 1) + begin.substring(cursorPos);

        setText(end);
        cursorPos--;
        updateCursorPos();
    }

    public void deleteCharAtCursorRight() {
        if (cursorPos == getText().length()) return;

        String begin = getText();
        String end   = begin.substring(0, cursorPos) + begin.substring(cursorPos + 1);

        setText(end);
        updateCursorPos();
    }

    public void deleteWordAtCursorLeft() {
        boolean trimmingWhitespace = true;
        String  begin              = getText();
        String  end                = "";

        for (int i = cursorPos - 1; i >= 0; i--) {
            char c = begin.charAt(i);

            if (trimmingWhitespace) {
                if (c != ' ' && c != '\n') { // TODO: use isWhitespace()
                    trimmingWhitespace = false;
                } else continue;
            }

            if (i == 0) {
                end = begin.substring(cursorPos);
                setText(end);
                cursorPos = 0;
                updateCursorPos();
                break;
            } else if (c == ' ' || c == '\n') { // TODO: use isWhitespace()
                setText(begin.substring(0, i) + begin.substring(cursorPos));
                cursorPos = i;
                updateCursorPos();
                break;
            }
        }
    }

    public void deleteWordAtCursorRight() {
        boolean trimmingWhitespace = true;
        String  begin              = getText();
        String  end                = "";

        for (int i = cursorPos; i < begin.length(); i++) {
            char c = begin.charAt(i);

            if (trimmingWhitespace) {
                if (c != ' ' && c != '\n') { // TODO: use isWhitespace()
                    trimmingWhitespace = false;
                } else continue;
            }

            if (i == begin.length() - 1) {
                end = begin.substring(0, cursorPos);
                setText(end);
                updateCursorPos();
                break;
            } else if (c == ' ' || c == '\n') { // TODO: use isWhitespace()
                end = begin.substring(0, cursorPos) + begin.substring(i + 1);
                setText(end);
                updateCursorPos();
                break;
            }
        }
    }

}
