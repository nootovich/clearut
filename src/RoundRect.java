import java.awt.*;

public class RoundRect extends Rect {

    public int rounding = 0;

    RoundRect(float x, float y, float w, float h, int z, int rounding) {
        super(x, y, w, h, z);
        this.rounding = rounding;
    }

    @Override
    public void draw(Graphics2D g2d) {
        drawLowerChildren(g2d);
        if (visible) {
            g2d.setColor(new Color(getColorBasedOnState()));
            g2d.fillRoundRect((int) x, (int) y, (int) w, (int) h, (int) rounding, (int) rounding);
        }
        drawHigherChildren(g2d);
    }
}
