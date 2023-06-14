import java.awt.*;


public class Text extends Element {

    public static boolean DEBUG = true;

    // TODO: Prob make width and height actually matter
    // TODO: Automate textSize of Text and actually render Text based on its dimentions
    private int       textSize  = 10;
    private Alignment alignment = Alignment.CENTER;
    private String    text      = "";
    private Color     color     = Color.BLACK;

    public Text(int x, int y, int maxW, int maxH, int size, int z) {
        super(x, y, maxW, maxH, z);
        this.textSize = size;
    }

    public Text(int x, int y, int maxW, int maxH, int size, int z, String text) {
        this(x, y, maxW, maxH, size, z);
        this.text = text;
    }

    public Text(int x, int y, int maxW, int maxH, int size, int z, String text, Color color) {
        this(x, y, maxW, maxH, size, z, text);
        this.color = color;
    }

    @Override
    public boolean update() {
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

        g2d.setColor(color);

        int textX      = getX();
        int textY      = getY();
        int lineHeight = metrics.getHeight();
        int maxWidth   = getWidth();
        int maxHeight  = getHeight();
        int lineCount  = 0;

        String[] lines = getLines();
        for (int i = 0; i < lines.length && lineHeight * lineCount < maxHeight; i++) {

            int lineWidth = metrics.stringWidth(lines[i]);
            if (lineWidth <= maxWidth) {

                drawLine(g2d, lines[i], textX, textY + lineHeight * lineCount);
                lineCount++;
                continue;

            }

            StringBuilder line  = new StringBuilder();
            String[]      words = lines[i].split(" ");
            for (int j = 0; j < words.length && lineHeight * lineCount < maxHeight; j++) {

                lineWidth = metrics.stringWidth(line + words[j]);
                if (lineWidth < maxWidth) {
                    line.append(words[j]).append(" ");
                    continue;
                }

                if (j != 0) {
                    drawLine(g2d, line.toString(), textX, textY + lineHeight * lineCount);
                    line      = new StringBuilder();
                    lineCount++;
                }

                int wordWidth = metrics.stringWidth(words[j]);
                if (wordWidth < maxWidth) {
                    line.append(words[j]).append(" ");
                    continue;
                }

                int    wordBegin = 0;
                String word      = words[j];
                for (int k = 1; k < word.length() && lineHeight * lineCount < maxHeight; k++) {

                    String partialWord  = word.substring(wordBegin, k);
                    int    partialWidth = metrics.stringWidth(partialWord + " ");
                    if (partialWidth < maxWidth) continue;

                    drawLine(g2d, partialWord, textX, textY + lineHeight * lineCount);
                    wordBegin = k;
                    lineCount++;

                }

                if (lineHeight * lineCount < maxHeight) {
                    line.append(word.substring(wordBegin)).append(" ");
                }

            }

            if (line.length() > 0 && lineHeight * lineCount < maxHeight) {
                drawLine(g2d, line.toString(), textX, textY + lineHeight * lineCount);
                lineCount++;
            }

        }

        drawChildren(g2d);
    }

    private void drawLine(Graphics2D g2d, String line, int x, int y) {
        FontMetrics metrics = g2d.getFontMetrics();

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

        g2d.setColor(getColor());
        g2d.drawString(line, x, y + metrics.getAscent());

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getLines() {
        return text.split("\\r?\\n", -1);
    }

    public void addChar(char c) {
        setText(getText() + c);
    }

    public void addText(String text) {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public enum Alignment {
        CENTER, LEFT, RIGHT, TOP, BOTTOM
    }
}
