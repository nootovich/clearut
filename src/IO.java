import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class IO {
    private static final String infoFileLocation = "./saves/";

    public static void saveInfo() {

        // DATA LAYOUT
        // int - info version number
        // int, int - screen size
        // available actions and objects and their states
        // history of unlocks
        // history of actions
        // TODO: figure it out in the process of development

        ArrayList<Object> info = new ArrayList<>();
        info.add(Global.INFO_VERSION);
        info.add(Global.CANVAS.getWidth() + "x" + Global.CANVAS.getHeight());

        for (UILayer layer : Global.WINDOW.getLayers()) {
            for (Element element : layer.getElements()) {
                String elementInfo = "["
                        + element.getX() + ", "
                        + element.getY() + ", "
                        + element.getWidth() + ", "
                        + element.getHeight() + "] "
                        + element.getPriority() + " "
                        + element.getVisibility();
                try {
                    if (((Sprite) element).getOutline()) elementInfo += " outline";
                    elementInfo = "SPRITE : " + elementInfo;
                } catch (Exception ignored) {
                    elementInfo = "TEXT   : " + elementInfo;
                }
                info.add(elementInfo);
            }
        }

        StringBuilder infoString = new StringBuilder();
        for (Object infoLine : info) {
            infoString.append(infoLine.toString()).append("\n");
        }

        try {
            String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
            File   file        = new File(infoFileLocation + currentTime + ".txt");
            File   directory   = new File(file.getParent());

            if (!directory.exists()) directory.mkdirs();
            file.createNewFile();

            FileWriter     writer       = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferWriter = new BufferedWriter(writer);

            bufferWriter.write(infoString.toString());
            bufferWriter.close();
        } catch (IOException e) {
            System.out.println(e.getCause() + "\n" + e.getMessage());
        }
    }

    public static class Mouse {
        int x, y;
        boolean LMB = false, RMB = false;


        public void update() {
            Point mousePos;

            try {
                mousePos = Global.WINDOW.getMousePosition();
                this.x   = mousePos.x;
                this.y   = mousePos.y;
            } catch (NullPointerException ignored) {
            }

            // TODO: add mouse buttons logic
        }

        public Point getPos() {
            return new Point(this.x, this.y);
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }
}
