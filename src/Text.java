import java.awt.*;

public class Text extends Child {

    public static boolean DEBUG = false;

	public int       x, y, w, h, size = 10, color = 0, offsetX = 0, offsetY = 0;
	public int       cachedTextHeight = 0, cachedLineHeight = 0; // TODO: remove (should be in program, not in library)
	public boolean   visible = true, hovered, active;
    public String    text = "";//, wrappedText = "";
    public Alignment alignment = Alignment.CENTER;

    public Text(int x, int y, int w, int h, int z, int size) {
    	this.x = x; this.y = y; this.w = w; this.h = h; this.z = z; this.size = size;
	}

    public Text(int x, int y, int w, int h, int z, int size, String text) {
        this(x, y, w, h, z, size);
		this.text = text;
    }

    public Text(int x, int y, int w, int h, int size, int z, String text, int color) {
        this(x, y, w, h, z, size, text);
        this.color = color;
    }

	@Override
    public void draw(Graphics2D g2d) {
		drawLowerChildren(g2d);
		if (!visible) {
			drawHigherChildren(g2d);
			return;
		}
        g2d.setFont(new Font("Rubik", Font.BOLD, size));
        FontMetrics metrics = g2d.getFontMetrics();
        if (DEBUG) {
            if (alignment == Alignment.CENTER) {
                x -= w >> 1;
                y -= metrics.getAscent() >> 1;
            } else if (alignment != Alignment.LEFT) {
                System.out.println("This text alignment is not implemented");
                System.exit(69);
            }
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(x, y, w, h);
        } // $DEBUG
        if (!visible) return;
        g2d.setColor(new Color(color));
        int lineHeight = metrics.getHeight();
        String[] lines = getLines();//getWrappedLines();
        for (int i = 0; i < lines.length; i++) {
            drawLine(g2d, lines[i], x, y + i * lineHeight);
        }
        cachedLineHeight = lineHeight;
        cachedTextHeight = lineHeight * lines.length;
        drawHigherChildren(g2d);
    }

    private void drawLine(Graphics2D g2d, String line, int x, int y) {
        FontMetrics metrics = g2d.getFontMetrics();
        int curX = x + offsetX;
        int curY = y + offsetY;
        if (curY - y > h) return;
        if (curY + metrics.getHeight() < y) return;
        // TODO: implement the rest of alignments
        switch (alignment) {
            case CENTER -> {
                curX -= metrics.stringWidth(line) >> 1;
                curY -= metrics.getAscent() >> 1;
            }
            case LEFT -> {}
            default -> {
                System.out.println("This text alignment is not implemented");
                System.exit(69);
            }
        }
        if (DEBUG) {
            int ma = metrics.getAscent();
            int md = metrics.getDescent();
            int mw = metrics.stringWidth(line);
            int mh = metrics.getHeight();
            g2d.setColor(new Color(0x55ff0000, true));
            g2d.fillRect(curX, curY, mw, ma);
            g2d.setColor(new Color(0x550000ff, true));
            g2d.fillRect(curX, curY + ma, mw, md);
            g2d.setColor(Color.GREEN);
            g2d.drawRect(curX, curY, mw, mh);
        } // $DEBUG
        g2d.setColor(new Color(color));
        g2d.drawString(line, curX, curY + metrics.getAscent());
//        cachedLastLineWidth = metrics.stringWidth(line);
    }

    // public void setText(String text) {
    //     this.text        = text;
    //     this.wrappedText = wrapText();
    // }

    public String[] getLines() {
        return text.split("\n", -1);
    }

    // public String[] getWrappedLines() {
    //     return getWrappedText().split("\n", -1);
    // }

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
        if (text.length() == 0) return;
        text = text.substring(0, text.length() - 1);
    }

    public void removeCharAt(int index) {
        if (true) throw new AssertionError("Not implemented");
    }

    public void removeCharAt(int row, int col) {
        if (true) throw new AssertionError("Not implemented");
    }

    public void removeLastWord() {
        removeLastChar();
        while (text.length() > 0) {
            if (Character.isWhitespace(text.charAt(text.length() - 1))) break;
            text = text.substring(0, text.length() - 1);
        }
    }
	
    public enum Alignment {
        CENTER, LEFT, RIGHT, TOP, BOTTOM;
    }
}
