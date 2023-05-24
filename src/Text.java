import java.awt.*;

public class Text extends Element {

    // TODO: Prob make width and height actually matter
    private int     textSize;
    private String  text;
    private Color   color;

    public Text(int x, int y, int z, int size, String text, Color color) {
		super(x, y, 0, 0, z);
        this.textSize = size;
        this.text     = text;
        this.color    = color;
    }

	@Override
    public boolean update() {
        return false;
    }

	@Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setFont(new Font("Roboto Mono", Font.BOLD, textSize));
        FontMetrics metrics = g.getFontMetrics();
        int         tx      = getX() - (int) (metrics.stringWidth(text) / 2.0f);
        int         ty      = getY() - (int) (metrics.getHeight() 		/ 2.0f) + metrics.getAscent();
        g.drawString(text, tx, ty);
    }

	// public Point getPos() {
 //        return new Point(x, y);
 //    }

 //    public void setPos(Point pos) {
 //        this.x = pos.x;
 //        this.y = pos.y;
 //    }

 //    public Point getSize() {
 //        return new Point(w, h);
 //    }

 //    public void setSize(Point size) {
 //        this.w = size.x;
 //        this.h = size.y;
 //    }

	public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
