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

    public static void saveNote(int id, String title, String content) {
        IO.saveFile(getNoteName(id), title+'\n'+content);
    }

    public static void saveOpenedNote() {
        Note note = getOpenedNote();
        if (note.text.isEmpty() && note.title.text.isEmpty()) deleteNote(note.id);
        else saveNote(note.id, note.title.text, note.text);
    }

    public static String[] loadNote(int id) {
        if (!noteExists(id)) return new String[]{"", ""};
        String        file  = IO.loadFile(getNoteName(id));
        StringBuilder title = new StringBuilder();
        int           i     = 0;
        for (i = 0; i < file.length(); i++) {
            char c = file.charAt(i);
            if (c == '\n') break;
            title.append(c);
        }
        if (i >= file.length()-1) return new String[]{title.toString(), ""};
        return new String[]{title.toString(), file.substring(i+1)};
    }

    public static boolean noteExists(int id) {
        return IO.fileExists(getNoteName(id));
    }

    public static void deleteNote(int id) {
        if (noteExists(id)) IO.deleteFile(getNoteName(id));
    }

}
