import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Picture extends Element {

    private String filepath = "";
    private Image  image    = null;

    public Picture(int x, int y, int w, int h, int z, String filepath) {
        super(x, y, w, h, z);
        change(filepath);
    }

    @Override
    public void draw(Graphics2D g2d) {
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();

        g2d.drawImage(image, x, y, w, h, null);

        // TODO: this is temporary
        if (isActive()) {
            g2d.setColor(new Color(0x30000000, true));
            g2d.fillRect(x, y, w, h);
        } else if (isHovered()) {
            g2d.setColor(new Color(0x20ffffff, true));
            g2d.fillRect(x, y, w, h);
        }
    }

    public void change(String filepath) {
        this.filepath = filepath;
        try {
            this.image = ImageIO.read(new File(getFilepath()));
        } catch (Exception e) {
            System.out.printf("Image %s can't be opened :(%n", filepath);
            e.printStackTrace();
        }
    }

    public String getFilepath() {
        return filepath;
    }

    public Image getImage() {
        return image;
    }

}
