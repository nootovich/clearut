import java.awt.*;

public class Cursor extends Sprite {

    Cursor(Text t, int thickness, Color color, String name) {
        super(0, 0, thickness, 0, t.getZ() + 1, color, name);
        setInteractive(false);
        setParent(t);
    }

    @Override
    public boolean update(int flags) {
        Text t = (Text) getParent();
        setX(t.getX() + t.offsetX + t.cachedLastLineWidth);
        setY(t.getY() + t.offsetY - t.cachedLineHeight + t.cachedTextHeight);
        setHeight(t.cachedLineHeight);
        return false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        long curTime = System.currentTimeMillis() % 1000;
        if (curTime > 500) {
            g2d.setColor(getIdleColor());
            g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

}
