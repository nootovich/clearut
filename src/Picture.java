import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Picture extends Child {

    public int x, y, w, h;
    public boolean visible = true, hovered, active;
    public  String        filepath = "";
    public  Image         image    = null;
    private BufferedImage buffImg;
    public  PictureType   type     = PictureType.RECTANGLE;

    public Picture(int x, int y, int w, int h, int z, String filepath) {
        this.x = x; this.y = y; this.w = w; this.h = h; this.z = z;
        setImageFromFile(filepath);
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
            switch (type) {
                case RECTANGLE -> {
                    if (buffImg == null) {
                        buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                        Graphics2D big2d = buffImg.createGraphics();
                        big2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        big2d.drawImage(image, 0, 0, w, h, null);
                        big2d.dispose();
                    }
                    g2d.drawImage(buffImg, x, y, w, h, null);

                    { // TODO: this is temporary
                        if (active) {
                            g2d.setColor(new Color(0x30000000, true));
                            g2d.fillRect(x, y, w, h);
                        } else if (hovered) {
                            g2d.setColor(new Color(0x20ffffff, true));
                            g2d.fillRect(x, y, w, h);
                        }
                    }
                }
                case CIRCLE -> {
                    if (buffImg == null) {
                        buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D big2d = buffImg.createGraphics();
                        big2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        big2d.fillOval(0, 0, buffImg.getWidth(), buffImg.getHeight());
                        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_IN);
                        big2d.setComposite(ac);
                        big2d.drawImage(image, 0, 0, w, h, null);
                        big2d.dispose();
                    }
                    g2d.drawImage(buffImg, x, y, w, h, null);
                }
                default -> System.out.println("Not implemented image type!");
            }
        }
        drawHigherChildren(g2d);
    }

    // TODO: rename function
    public void setImageFromFile(String filepath) {
        try {
            this.image    = ImageIO.read(new File(filepath));
            this.filepath = filepath;
        } catch (Exception e) {
            System.out.printf("Image %s can't be opened :(%n", filepath);
            e.printStackTrace();
        }
    }

    public enum PictureType {
        RECTANGLE, CIRCLE
    }
}
