public class Notes {


    private static String getNoteName(int id) {
        return Global.NOTES_FOLDER+id+".txt";
    }

    public static Note getOpenedNote() {
        if (Main.state == Main.State.NOTE) {
            Note note = (Note) Main.window.getLayer("UI_NOTE").getChild("NOTE");
            if (note != null) return note;
            System.out.println("Couldn't find opened note");
            return null;
        }
        System.out.println("Trying to get nonexistent opened note");
        return null;
    }

    public static void saveNote(int id, String content) {
        IO.saveFile(getNoteName(id), content);
    }

    public static void saveOpenedNote() {
        Note note = getOpenedNote();
        if (note.text.isEmpty()) deleteNote(note.id);
        else saveNote(note.id, note.text);
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
