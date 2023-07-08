import java.awt.*;

public class Slider extends Sprite {

    private int lowerBound;
    private int upperBound;
    private int lineColor;


    public Slider(int x, int y, int w, int h, int z, String name, int start, int end, int value) {
        super(x, y, w, h, z);
        setLowerBound(start);
        setUpperBound(end);
        setAdditional(value);
    }

    public Slider(int x, int y, int w, int h, int z, String name, int start, int end, int value, int color) {
        this(x, y, w, h, z, name, start, end, value);
        setColors(color, color, color);
        setLineColor(0);
    }

    public Slider(int x, int y, int w, int h, int z, String name, int start, int end, int value, int color, int lineColor) {
        this(x, y, w, h, z, name, start, end, value);
        setColors(color, color, color);
        setLineColor(lineColor);
    }

    @Override
    public boolean update(int flags) {
        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(getLineColor()));
        g2d.fillRect(getX(), getY() + getHeight() / 2 - 2, getWidth(), getHeight() / 2 + 2);

        float pos    = (getAdditional() - getLowerBound() + 0.f) / (getUpperBound() - getLowerBound() + 0.f);
        int   cx     = (int) (getWidth() * pos + getX());
        int   offset = 5;

        g2d.setColor(new Color(getIdleColor()));
        g2d.fillRect(cx - offset, getY() + getHeight() / 6, offset * 2, getHeight());
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int color) {
        lineColor = color;
    }
}
