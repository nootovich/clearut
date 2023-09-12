public class Notes {


    private static String getNoteName(int index) {
        return Global.NOTES_FOLDER+index+".txt";
    }

    public static void saveNote(int index, String content) {
        IO.saveFile(getNoteName(index), content);
    }

    public static String loadNote(int index) {
        if (noteExists(index)) return IO.loadFile(getNoteName(index));
        return "";
    }

    public static boolean noteExists(int index) {
        return IO.fileExists(getNoteName(index));
    }

    public static void deleteNote(int index) {
        if (noteExists(index)) IO.deleteFile(getNoteName(index));
    }

}
