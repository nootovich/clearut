import java.awt.*;

public class Text extends Child {

    public static boolean DEBUG = false;

    public int x, y, w, h, size = 10, color = 0, offsetX = 0, offsetY = 0;
    public int cachedTextHeight = 0, cachedLineHeight = 0; // TODO: remove (should be in program, not in library)
    public boolean visible = true, hovered, active;
    public String    text      = "";
    public Alignment alignment = Alignment.CENTER;

    public Text(int x, int y, int w, int h, int z, int size) {
        this.x = x; this.y = y; this.w = w; this.h = h; this.z = z; this.size = size;
    }

    public Text(int x, int y, int w, int h, int z, int size, String text) {
        this(x, y, w, h, z, size);
        this.text = text;
    }

    public Text(int x, int y, int w, int h, int z, int size, String text, int color) {
        this(x, y, w, h, z, size, text);
        this.color = color;
    }

    public Text(int x, int y, int w, int h, int z, int size, String text, String name) {
        this(x, y, w, h, z, size, text);
        this.name = name.toUpperCase();
    }

    public Text(int x, int y, int w, int h, int z, int size, String text, String name, int color) {
        this(x, y, w, h, z, size, text, name);
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2d) {
        drawLowerChildren(g2d);
        if (visible) {
            g2d.setFont(new Font("Rubik", Font.BOLD, size));
            FontMetrics metrics = g2d.getFontMetrics();

            if (DEBUG) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(1));
                switch (alignment) {
                    case LEFT -> g2d.drawRect(x, y, w, h);
                    case CENTER -> g2d.drawRect(x-w/2, y-h/2, w, h);
                }
            } // $DEBUG

            g2d.setColor(new Color(color));
            int      lineHeight = metrics.getHeight();
            String[] lines      = getLines();
            for (int i = 0; i < lines.length; i++) drawLine(g2d, metrics, lines[i], x, y+i*lineHeight);

            cachedLineHeight = lineHeight;
            cachedTextHeight = lineHeight*lines.length;
        }
        drawHigherChildren(g2d);
    }

    protected void drawLine(Graphics2D g2d, FontMetrics metrics, String line, int x, int y) {
        int curX = x+offsetX;
        int curY = y+offsetY;

        // TODO: implement the rest of alignments
        switch (alignment) {
            case LEFT -> {
                if (curX < this.x-metrics.stringWidth("@") || curX > this.x+w || curY < this.y-metrics.getHeight() || curY > this.y+h) return;
            }
            case CENTER -> {
                // TODO: is this correct?
                // TODO: also need to add inbounds check
                curX -= metrics.stringWidth(line)>>1;
                curY -= metrics.getAscent()>>1;
            }
            default -> {
                System.out.println("This text alignment is not implemented");
                System.exit(69);
            }
        }

        if (DEBUG) {
            int ma = metrics.getAscent();
            int mw = metrics.stringWidth(line);
            g2d.setColor(new Color(0x55ff0000, true));
            g2d.fillRect(curX, curY, mw, ma);
            g2d.setColor(new Color(0x550000ff, true));
            g2d.fillRect(curX, curY+ma, mw, metrics.getDescent());
            g2d.setColor(Color.GREEN);
            g2d.drawRect(curX, curY, mw, metrics.getHeight());
        } // $DEBUG

        g2d.setColor(new Color(color));
        g2d.drawString(line, curX, curY+metrics.getAscent());
    }

    public String[] getLines() {
        return text.split("\n", -1);
    }

    // public String wrapText() {
    //     StringBuilder result = new StringBuilder();
    //     Graphics2D g2d = (Graphics2D) Global.IMAGE.getGraphics();
    //     g2d.setFont(new Font("Rubik", Font.BOLD, textSize));
    //     FontMetrics metrics = g2d.getFontMetrics();

    //     int textX     = getX();
    //     int textY     = getY();
    //     int maxWidth  = getWidth();
    //     int maxHeight = getHeight();
    //     int lineCount = 0;

    //     String[] lines = getLines();
    //     for (int i = 0; i < lines.length; i++) {

    //         int lineWidth = metrics.stringWidth(lines[i]);
    //         if (lineWidth <= w) {
    //             result.append(lines[i]).append("\n");
    //             lineCount++;
    //             continue;
    //         }

    //         StringBuilder line  = new StringBuilder();
    //         String[]      words = lines[i].split(" ");
    //         for (int j = 0; j < words.length; j++) {

    //             lineWidth = metrics.stringWidth(line + words[j]);
    //             if (lineWidth < w) {
    //                 if (line.length() > 0) line.append(" ");
    //                 line.append(words[j]);
    //                 continue;
    //             }

    //             if (j != 0) {
    //                 result.append(line.toString()).append("\r\n");
    //                 line = new StringBuilder();
    //                 lineCount++;
    //             }

    //             int wordWidth = metrics.stringWidth(words[j]);
    //             if (wordWidth < w) {
    //                 if (line.length() > 0) line.append(" ");
    //                 line.append(words[j]);
    //                 continue;
    //             }

    //             int    wordBegin = 0;
    //             String word      = words[j];
    //             for (int k = 1; k < word.length(); k++) {
    //                 String partialWord  = word.substring(wordBegin, k);
    //                 int    partialWidth = metrics.stringWidth(partialWord + " ");
    //                 if (partialWidth < w) continue;
    //                 result.append(partialWord).append("\r\n");
    //                 wordBegin = k;
    //                 lineCount++;
    //             }

    //             line.append(word.substring(wordBegin));
    //         }
    //         if (line.length() > 0) {
    //             result.append(line.toString()).append("\n");
    //             lineCount++;
    //         }
    //     }

    //     return result.deleteCharAt(result.length() - 1).toString();
    // }

    public void removeLastChar() {
        if (text.isEmpty()) return;
        text = text.substring(0, text.length()-1);
    }

    public void removeCharAt(int index) {
        throw new AssertionError("Not implemented");
    }

    public void removeCharAt(int row, int col) {
        throw new AssertionError("Not implemented");
    }

    public void removeLastWord() {
        removeLastChar();
        while (!(text.isEmpty() || Character.isWhitespace(text.charAt(text.length()-1)))) {
            removeLastChar();
        }
    }

    public enum Alignment {
        CENTER, LEFT, RIGHT, TOP, BOTTOM;
    }
}
