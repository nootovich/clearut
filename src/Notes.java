public class Notes {


    private static String getNoteName(int id) {
        return Global.NOTES_FOLDER+id+".txt";
    }

    public static void saveNote(int id, String content) {
        IO.saveFile(getNoteName(id), content);
    }

    public static void saveOpenedNote() {
        if (Main.state == Main.State.NOTE) {
            Note note = (Note) Main.window.getLayer("UI_NOTE").getChild("NOTE");
            if (note.text.isEmpty()) deleteNote(note.id);
            else saveNote(note.id, note.text);
        }
    }

    public static String loadNote(int id) {
        if (noteExists(id)) return IO.loadFile(getNoteName(id));
        return "";
    }

    public static boolean noteExists(int id) {
        return IO.fileExists(getNoteName(id));
    }

    public static void deleteNote(int id) {
        if (noteExists(id)) IO.deleteFile(getNoteName(id));
    }

}
