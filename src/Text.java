import java.awt.*;

public class Text extends Element {

    // TODO: Prob make width and height actually matter
    // TODO: Automate textSize of Text and actually render Text based on its dimentions
    private int    textSize;
    private String text;
    private Color  color;

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
        g2d.setColor(color);
        g2d.setFont(new Font("Roboto Mono", Font.BOLD, textSize));
        FontMetrics metrics = g2d.getFontMetrics();
        int         tx      = getX() - (int) (metrics.stringWidth(text) / 2.0f);
        int         ty      = getY() - (int) (metrics.getHeight() / 2.0f) + metrics.getAscent();
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
}
