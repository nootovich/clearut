import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Picture extends Child {

    public int x, y, w, h;
    public boolean visible = true, hovered, active;
    public String filepath = "";
    public Image  image    = null;

    public Picture(int x, int y, int w, int h, int z, String filepath) {
        this.x = x; this.y = y; this.w = w; this.h = h; this.z = z;
        change(filepath);
    }

    public Picture(int x, int y, int w, int h, int z, String filepath, String name) {
        this(x, y, w, h, z, filepath);
        this.name = name.toUpperCase();
    }

    @Override
    public void update(IO.Mouse mouse) {
        updateHigherChildren(mouse);
        // if (active && mouse.isLMBFallingEdge()) Global.ACTIONS.invoke(action); // TODO: refactor
        hovered = mouse.x > x && mouse.x < x+w && mouse.y > y && mouse.y < y+h;
        active  = hovered && mouse.LMB;
        updateLowerChildren(mouse);
    }

    @Override
    public void draw(Graphics2D g2d) {
        drawLowerChildren(g2d);
        if (visible) {
            g2d.drawImage(image, x, y, w, h, null);
            // TODO: this is temporary
            if (active) {
                g2d.setColor(new Color(0x30000000, true));
                g2d.fillRect(x, y, w, h);
            } else if (hovered) {
                g2d.setColor(new Color(0x20ffffff, true));
                g2d.fillRect(x, y, w, h);
            }
        }
        drawHigherChildren(g2d);
    }

    // TODO: rename function
    public void change(String filepath) {
        try {
            this.image    = ImageIO.read(new File(filepath));
            this.filepath = filepath;
        } catch (Exception e) {
            System.out.printf("Image %s can't be opened :(%n", filepath);
            e.printStackTrace();
        }
    }
}
