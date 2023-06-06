import java.awt.*;

public class Text extends Element {

    // TODO: Prob make width and height actually matter
    // TODO: Automate textSize of Text and actually render Text based on its dimentions
    private int       textSize;
    private Alignment alignment = Alignment.CENTER;
    private String    text;
    private Color     color;

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

        // TODO: implement the rest of alignments
        switch (getAlignment()) {
            case CENTER -> {
                tx -= metrics.stringWidth(text) >> 1;
                ty -= metrics.getHeight() >> 1;
                ty += metrics.getAscent();
            }
            case LEFT -> ty += metrics.getHeight();
            default -> System.exit(420);
        }

        g2d.drawString(text, tx, ty);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
