import java.awt.*;


public class Text extends Element {

    public static boolean DEBUG = false;

    public  int       offsetX          = 0;
    public  int       offsetY          = 0;
    public  int       cachedTextHeight = 0;
    public  int       cachedLineHeight = 0;
    public  int       textSize         = 10;
    private String    text             = "";
    private String    wrappedText      = "";
    private int       color            = 0;
    private Alignment alignment        = Alignment.CENTER;

    public Text(int x, int y, int maxW, int maxH, int size, int z) {
        super(x, y, maxW, maxH, z);
        this.textSize = size;
    }

    public Text(int x, int y, int maxW, int maxH, int size, int z, String text) {
        this(x, y, maxW, maxH, size, z);
        setText(text);
    }

    public Text(int x, int y, int maxW, int maxH, int size, int z, String text, int color) {
        this(x, y, maxW, maxH, size, z, text);
        this.color = color;
    }

    public boolean updateSelf() {
        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setFont(new Font("Rubik", Font.BOLD, textSize));
        FontMetrics metrics = g2d.getFontMetrics();

        if (DEBUG) {
            int x = getX();
            int y = getY();
            int w = getWidth();
            int h = getHeight();

            if (getAlignment() == Alignment.CENTER) {
                x -= w >> 1;
                y -= metrics.getAscent() >> 1;
            } else if (getAlignment() != Alignment.LEFT) {
                System.out.println("This text alignment is not implemented");
                System.exit(69);
            }

            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(x, y, w, h);
        } // $DEBUG

        if (!isVisible()) return;

        g2d.setColor(new Color(color));

        int textX      = getX();
        int textY      = getY();
        int lineHeight = metrics.getHeight();

        String[] lines = getWrappedLines();
        for (int i = 0; i < lines.length; i++) {
            drawLine(g2d, lines[i], textX, textY + i * lineHeight);
        }

        cachedLineHeight = lineHeight;
        cachedTextHeight = lineHeight * lines.length;
        drawChildren(g2d);
    }

    private void drawLine(Graphics2D g2d, String line, int x, int y) {
        FontMetrics metrics = g2d.getFontMetrics();

        x += offsetX;
        y += offsetY;
        if (y - getY() > getHeight()) return;
        if (y + metrics.getHeight() < getY()) return;


        // TODO: implement the rest of alignments
        switch (getAlignment()) {
            case CENTER -> {
                x -= metrics.stringWidth(line) >> 1;
                y -= metrics.getAscent() >> 1;
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
            g2d.fillRect(x, y, mw, ma);

            g2d.setColor(new Color(0x550000ff, true));
            g2d.fillRect(x, y + ma, mw, md);

            g2d.setColor(Color.GREEN);
            g2d.drawRect(x, y, mw, mh);
        } // $DEBUG

        g2d.setColor(new Color(getColor()));
        g2d.drawString(line, x, y + metrics.getAscent());

//        cachedLastLineWidth = metrics.stringWidth(line);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text        = text;
        this.wrappedText = wrapText();
    }

    public String getWrappedText() {
        return wrappedText;
    }

    public String[] getLines() {
        return getText().split("\n", -1);
    }

    public String[] getWrappedLines() {
        return getWrappedText().split("\n", -1);
    }

    public String wrapText() {

        StringBuilder result = new StringBuilder();

        Graphics2D g2d = (Graphics2D) Global.IMAGE.getGraphics();
        g2d.setFont(new Font("Rubik", Font.BOLD, textSize));
        FontMetrics metrics = g2d.getFontMetrics();

        int textX     = getX();
        int textY     = getY();
        int maxWidth  = getWidth();
        int maxHeight = getHeight();
        int lineCount = 0;

        String[] lines = getLines();
        for (int i = 0; i < lines.length; i++) {

            int lineWidth = metrics.stringWidth(lines[i]);
            if (lineWidth <= maxWidth) {
                result.append(lines[i]).append("\n");
                lineCount++;
                continue;
            }

            StringBuilder line  = new StringBuilder();
            String[]      words = lines[i].split(" ");
            for (int j = 0; j < words.length; j++) {

                lineWidth = metrics.stringWidth(line + words[j]);
                if (lineWidth < maxWidth) {
                    if (line.length() > 0) line.append(" ");
                    line.append(words[j]);
                    continue;
                }

                if (j != 0) {
                    result.append(line.toString()).append("\r\n");
                    line = new StringBuilder();
                    lineCount++;
                }

                int wordWidth = metrics.stringWidth(words[j]);
                if (wordWidth < maxWidth) {
                    if (line.length() > 0) line.append(" ");
                    line.append(words[j]);
                    continue;
                }

                int    wordBegin = 0;
                String word      = words[j];
                for (int k = 1; k < word.length(); k++) {
                    String partialWord  = word.substring(wordBegin, k);
                    int    partialWidth = metrics.stringWidth(partialWord + " ");
                    if (partialWidth < maxWidth) continue;
                    result.append(partialWord).append("\r\n");
                    wordBegin = k;
                    lineCount++;
                }

                line.append(word.substring(wordBegin));
            }
            if (line.length() > 0) {
                result.append(line.toString()).append("\n");
                lineCount++;
            }
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }

    public void addChar(char c) {
        setText(getText() + c);
    }

    public void addString(String text) {
        setText(getText() + text);
    }

    public void removeLastChar() {
        String txt = getText();
        if (txt.length() == 0) return;
        setText(txt.substring(0, txt.length() - 1));
    }

    public void removeCharAt(int index) {
        if (true) throw new AssertionError("Not implemented");
    }

    public void removeCharAt(int row, int col) {
        if (true) throw new AssertionError("Not implemented");
    }

    public void removeLastWord() {
        removeLastChar();
        String txt = getText();
        while (txt.length() > 0) {
            if (Character.isWhitespace(txt.charAt(txt.length() - 1))) break;
            txt = txt.substring(0, txt.length() - 1);
        }
        setText(txt);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Text setAlignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public Text setOffsetX(int x) {
        offsetX = x;
        return this;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public Text setOffsetY(int y) {
        offsetY = y;
        return this;
    }

    public enum Alignment {
        CENTER, LEFT, RIGHT, TOP, BOTTOM;
    }
}
