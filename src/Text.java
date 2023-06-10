import java.awt.*;

public class Text extends Element {

    // TODO: Prob make width and height actually matter
    // TODO: Automate textSize of Text and actually render Text based on its dimentions
    private int       textSize = 10;
    private Alignment alignment = Alignment.CENTER;
    private String    text = "";
    private Color     color = Color.BLACK;

    public Text(int x, int y, int size, int z) {
        super(x, y, 0, 0, z);
        this.textSize = size;
    }

    public Text(int x, int y, int size, int z, String text) {
        this(x, y, size, z);
        this.text = text;
    }

    public Text(int x, int y, int size, int z, String text, Color color) {
        this(x, y, size, z, text);
        this.color = color;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!isVisible()) return;

        g2d.setColor(color);
        g2d.setFont(new Font("Roboto Mono", Font.BOLD, textSize));
        FontMetrics metrics = g2d.getFontMetrics();

        int tx = getX();
        int ty = getY();

        String[] lines = text.split("\\r?\\n", -1);
        for (int i = 0; i < lines.length; i++) {

            String line = lines[i];

            // TODO: implement the rest of alignments
            switch (getAlignment()) {
                case CENTER -> {
                    tx -= metrics.stringWidth(line) >> 1;
                    ty -= metrics.getHeight() >> 1;
                    ty += metrics.getAscent() * (i + 1);
                }
                case LEFT -> ty += metrics.getHeight();
                default -> System.exit(420);
            }

            g2d.drawString(line, tx, ty);
        }

		drawChildren(g2d);
    }

    public String getText() {
        return text;
    }

    // public int getTextLength() {
    //        return text.length();
    //    }

    // public String[] getLines() {
    //        return text.split("\\r?\\n");
    //    }

    // public int getNumOfLines() {
    //        return text.split("\\r?\\n").length;
    //    }

    public void setText(String text) {
        this.text = text;
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
