import java.awt.*;

public class Note extends Text {

    public int id, cursorPos = 0, cursorRow = 0, cursorCol = 0, cursorBol = 0;
    public boolean scrollable = false, cursorAtTitle = true;
    public Text title;

    public static final int titleSize = 32; // TODO: temporary. remove as soon as possible

    public Note(int x, int y, int maxW, int maxH, int z, int size, String name) {
        super(x, y, maxW, maxH, z, size, "", name);
    }

    public Note(int x, int y, int maxW, int maxH, int z, int size, String name, int color) {
        super(x, y, maxW, maxH, z, size, "", name, color);
    }

    public Note(int x, int y, int maxW, int maxH, int z, int size, String name, String text) {
        super(x, y, maxW, maxH, z, size, text, name);
    }

    public Note(int x, int y, int maxW, int maxH, int z, int size, String name, String text, int color) {
        super(x, y, maxW, maxH, z, size, text, name, color);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!visible) return;

        // title
        int savedY = y;
        int savedH = h;
        if (title != null) {
            title.draw(g2d);
            y += (int) (title.h*1.2f);
            h -= (int) (title.h*1.2f);
        }

        // text
        g2d.setClip(x, y, w, h);
        super.draw(g2d);

        // cursor
        if (!cursorAtTitle) {
            String[]    lines      = getLines();
            FontMetrics metrics    = g2d.getFontMetrics();
            int         lineHeight = metrics.getHeight();
            int         cursorX    = metrics.stringWidth(lines[cursorRow].substring(0, cursorCol));
            int         cursorY    = lineHeight*cursorRow;
            long        curTime    = System.currentTimeMillis()%512;
            g2d.setColor(curTime < 256 ? new Color(0, 0, 0, (int) (255-curTime)) : new Color(0, 0, 0, (int) curTime-256));
            g2d.fillRect(x+offsetX+cursorX, y+offsetY+cursorY, 2, lineHeight);
        }

        // reset
        g2d.setClip(null);
        if (title != null) {
            y = savedY;
            h = savedH;
        }

        // title cursor
        if (cursorAtTitle) {
            FontMetrics metrics = g2d.getFontMetrics(new Font("Rubik", Font.BOLD, title.size));
            int         cursorX = metrics.stringWidth(title.text.substring(0, cursorCol));
            long        curTime = System.currentTimeMillis()%512;
            g2d.setColor(curTime < 256 ? new Color(0, 0, 0, (int) (255-curTime)) : new Color(0, 0, 0, (int) curTime-256));
            g2d.fillRect(x+offsetX+cursorX, y, 2, metrics.getHeight());
        }
    }

    @Override
    public void update(Mouse mouse) {
        updateHigherChildren(mouse);

        if ((title.active && mouse.isLMBFallingEdge()) || (active && mouse.isLMBFallingEdge())) {
            cursorAtTitle = title.active;
            updateCursorPos(0);
        }

        title.hovered = mouse.x > title.x && mouse.x < title.x+title.w && mouse.y > title.y && mouse.y < title.y+title.h;
        title.active  = title.hovered && mouse.LMB;
        hovered       = mouse.x > x && mouse.x < x+w && mouse.y > y && mouse.y < y+h;
        active        = hovered && mouse.LMB;

        updateLowerChildren(mouse);
    }

    public void scroll(int wheelRotation) {
        offsetY -= wheelRotation*42;
        if (offsetY < h-cachedTextHeight) offsetY = h-cachedTextHeight;
        if (offsetY > 0) offsetY = 0;
    }

    public void updateCursorPos() {
        updateCursorPos(cursorPos);
    }

    public void updateCursorPos(int pos) {
        cursorPos = pos;
        // resetting
        if (cursorPos <= 0) {
            cursorPos = 0;
            cursorCol = 0;
            cursorRow = 0;
            cursorBol = 0;
            return;
        }

        // title
        if (cursorAtTitle) {
            if (cursorPos > title.text.length()) cursorPos = title.text.length();
            cursorCol = cursorPos;
            return;
        }

        // clamping
        if (cursorPos > text.length()) cursorPos = text.length();

        // calculating cursor col and row
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

        // TODO: reimplement wrapping
        //int  lineCount      = 0;
        //int  accLineLen     = 0;
        //char returnCarriage = (char) 13;
        //String[] wrappedLines = getWrappedLines();
        //for (int i = 0; i < wrappedLines.length; i++) {
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
        //    if (!lastIsReturn || emptyLine) {
        //        lineCount++;
        //        accLineLen = 0;
        //    } else {
        //        accLineLen += curLineLen;
        //    }
        //}

        // some experimenting :)
        // Font              font       = new Font("Rubik", Font.BOLD, size);
        // FontRenderContext frc        = new FontRenderContext(null, true, false);
        // int               lineHeight = (int) (font.getStringBounds("@", frc)).getHeight()+0.5d);
        if (cachedLineHeight == 0) return;
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
        String  txt                = cursorAtTitle ? title.text : text;
        for (int i = cursorPos-1; i >= 0; i--) {
            boolean whitespace = txt.charAt(i) == ' ';
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
        String  txt                = cursorAtTitle ? title.text : text;
        for (int i = cursorPos; i < txt.length(); i++) {
            boolean whitespace = txt.charAt(i) == ' ';
            if (skippingWhitespace) {
                if (whitespace) continue;
                skippingWhitespace = false;
            }
            if (i == txt.length()-1) updateCursorPos(txt.length());
            else if (whitespace) {
                updateCursorPos(i);
                return;
            }
        }
    }

    public void moveCursorUp() {
        if (cursorAtTitle || cursorRow <= 0) {
            updateCursorPos(-1);
            return;
        }
        int prevLineLen = getLines()[cursorRow-1].length();
        cursorPos -= cursorCol < prevLineLen ? prevLineLen+1 : cursorCol+1;
        updateCursorPos();
    }

    public void moveCursorDown() {
        if (cursorAtTitle || cursorRow >= getLines().length-1) {
            updateCursorPos(text.length());
            return;
        }
        int curLineLen  = getLines()[cursorRow].length();
        int nextLineLen = getLines()[cursorRow+1].length();
        cursorPos += cursorCol < nextLineLen ? curLineLen+1 : curLineLen+nextLineLen+1-cursorCol;
        updateCursorPos();
    }

    public void addCharAtCursor(char c) {
        if (cursorAtTitle) title.text = title.text.substring(0, cursorPos)+c+title.text.substring(cursorPos);
        else text = text.substring(0, cursorPos)+c+text.substring(cursorPos);
        cursorPos++;
        updateCursorPos();
    }

    public void deleteCharAtCursorLeft() {
        if (cursorPos == 0) return;
        if (cursorAtTitle) title.text = title.text.substring(0, cursorPos-1)+title.text.substring(cursorPos);
        else text = text.substring(0, cursorPos-1)+text.substring(cursorPos);
        cursorPos--;
        updateCursorPos();
    }

    public void deleteCharAtCursorRight() {
        if ((cursorAtTitle && cursorPos == title.text.length()) || (!cursorAtTitle && cursorPos == text.length())) return;
        if (cursorAtTitle) title.text = title.text.substring(0, cursorPos)+title.text.substring(cursorPos+1);
        else text = text.substring(0, cursorPos)+text.substring(cursorPos+1);
        updateCursorPos();
    }

    public void deleteWordAtCursorLeft() {
        boolean trimmingWhitespace = true;
        String  txt                = cursorAtTitle ? title.text : text;
        for (int i = cursorPos-1; i >= 0; i--) {
            if (i == 0) {
                if (cursorAtTitle) title.text = title.text.substring(cursorPos);
                else text = text.substring(cursorPos);
                updateCursorPos(0);
                return;
            }
            char    c          = txt.charAt(i);
            boolean whitespace = Character.isSpaceChar(c);
            if (trimmingWhitespace) {
                if (whitespace) continue;
                trimmingWhitespace = false;
            }
            if (whitespace) {
                if (cursorAtTitle) title.text = title.text.substring(0, i)+title.text.substring(cursorPos);
                else text = (text.substring(0, i)+text.substring(cursorPos));
                updateCursorPos(i);
                return;
            }
        }
    }

    public void deleteWordAtCursorRight() {
        boolean trimmingWhitespace = true;
        String  txt                = cursorAtTitle ? title.text : text;
        for (int i = cursorPos; i < txt.length(); i++) {
            if (cursorAtTitle && i == title.text.length()-1) {
                title.text = title.text.substring(0, cursorPos);
                return;
            } else if (!cursorAtTitle && i == text.length()-1) {
                text = text.substring(0, cursorPos);
                return;
            }
            char    c          = txt.charAt(i);
            boolean whitespace = Character.isSpaceChar(c);
            if (trimmingWhitespace) {
                if (whitespace) continue;
                trimmingWhitespace = false;
            }
            if (whitespace) {
                if (cursorAtTitle) title.text = title.text.substring(0, cursorPos)+title.text.substring(i+1);
                else text = text.substring(0, cursorPos)+text.substring(i+1);
                return;
            }
        }
    }

}
