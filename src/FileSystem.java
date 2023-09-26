import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class FileSystem {

    public static boolean saveFile(String fileName, String content) {
        try {
            File file      = new File(fileName);
            File directory = new File(file.getParent());
            if (!directory.exists()) directory.mkdirs();
            file.createNewFile();

            FileWriter     writer       = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferWriter = new BufferedWriter(writer);

            bufferWriter.write(content);
            bufferWriter.close();
        } catch (IOException e) {
            System.out.println(e.getCause()+"\n"+e.getMessage());
            return false;
        }
        return true;
    }

    public static String loadFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory();
    }

    public static boolean deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            file.delete();
        } catch (Exception e) {
            System.out.println(e.getCause()+"\n"+e.getMessage());
            return false;
        }
        return true;
    }

    public static void saveTasks() {
        LocalDate now   = LocalDate.now();
        String    today = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        saveFile("tasks\\list_"+today+".txt", Arrays.toString(Calendar.TASK_LIST));

    }


    public static void saveData() {
        saveTasks();
    }


    // public static void saveInfo() {

    //     // DATA LAYOUT
    //     // int - savedata version number
    //     // int, int - screen size
    //     // available actions and objects and their states
    //     // history of unlocks
    //     // history of actions
    //     // TODO: figure it out in the process of development

    //     // init savedata
    //     StringBuilder savedata = new StringBuilder("SAVEDATA_VERSION: ").append(Global.SAVEDATA_VERSION);
    //     savedata.append('\n').append(format(
    //             "$WINDOW: x=%d y=%d w=%d h=%d",
    //             Main.window.getX(), Main.window.getY(), Global.CANVAS.getWidth(), Global.CANVAS.getHeight()));

    //     UILayer[] layers = Main.window.getLayers();
    //     for (UILayer layer : layers) {
    //         Element[] descendants = layer.getChildren();

    //         savedata.append('\n').append(format(
    //                 "\t$LAYER: name=%s z=%d children_size=%d",
    //                 layer.name, layer.z, layer.children.length));

    //         for (Object c : children) {
    //             savedata.append('\n').append(getChildData(c, 2));
    //         }
    //     }

    //     String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
    //     saveFile(Global.SAVEDATA_FOLDER + currentTime + ".txt", savedata.toString());

    // }

    // private static String getChildData(Element e, int depth) {
    //     Element[]     descendants = e.getChildren();
    //     StringBuilder result      = new StringBuilder("\t".repeat(depth));
    //     String data = format("$%s: name=%s x=%d y=%d w=%d h=%d z=%d action=%s state=%s%s%s children_size=%d",
    //                          e.getClass(), e.getName(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getZ(), e.getAction(),
    //                          e.isVisible() ? "v" : "", e.isHovered() ? "h" : "", e.isActive() ? "a" : "", descendants.length);
    //     result.append(data);

    //     for (Element descendant : descendants) {
    //         result.append("\n").append(getChildData(descendant, depth + 1));
    //     }

    //     return result.toString();
    // }

}
