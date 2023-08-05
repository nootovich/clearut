// public class Notes {

//     public static void openList() {
//         // TODO: when a basic menuing system is done move this into Notes.closeList();
//         Text openNote = (Text) Global.findElement("openNote");
//         if (openNote != null) {
//             if (openNote.getText().strip().equals("")) {
//                 deleteNote(openNote.getAdditional());
//             } else {
//                 saveNote(openNote.getAdditional(), openNote.getText());
//             }
//             Global.layer("UIMAIN").removeChild("openNote");
//         }


//         // TODO: implement findSprite(), findText() and similar functions
//         Sprite windowBG  = (Sprite) Global.findElement("window_bg");
//         Sprite sideBG    = (Sprite) Global.findElement("side_bg");
//         Sprite profileBG = (Sprite) Global.findElement("profile_bg");

//         Global.asrt(windowBG != null, "windowBG was not found!");
//         Global.asrt(sideBG != null, "sideBG was not found!");
//         Global.asrt(profileBG != null, "profileBG was not found!");

//         int[] yellow1 = Colors.yellow1();
//         int[] yellow2 = Colors.yellow2();

//         windowBG.setIdleColor(yellow1[6]);
//         sideBG.setIdleColor(yellow2[7]);
//         profileBG.setIdleColor(yellow1[8]);

//         Global.MODE = "NOTES"; // TODO: rework


//         // TODO: remove hardcoded $SCALING
//         int horizNotes = 5;
//         int vertNotes  = 2;
//         int x          = sideBG.getWidth();
//         int y          = profileBG.getHeight();
//         int freeW      = Global.CANVAS.getWidth() - x;
//         int freeH      = Global.CANVAS.getHeight() - y;
//         int spacing    = freeW >> 5;
//         int nw         = (freeW - spacing * (horizNotes + 1)) / horizNotes;
//         int nh         = (freeH - spacing * (vertNotes + 1)) / vertNotes;

//         Group notesGroup = new Group("notesGroup");

//         for (int i = 0; i < vertNotes; i++) {
//             for (int j = 0; j < horizNotes; j++) {

//                 int index = (i * horizNotes + j);
//                 int nx    = x + spacing * (j + 1) + nw * j;
//                 int ny    = y + spacing * (i + 1) + nh * i;

//                 Sprite note = new Sprite(nx, ny, nw, nh, 3);
//                 note.setColors(yellow2[5], yellow2[6], yellow2[7]);
//                 note.setName("note" + index);
//                 note.setAction("openNote:int:" + index);

//                 notesGroup.addChild(note);

//                 if (noteExists(index)) {
//                     int  pad      = 5;
//                     Text noteText = new Text(nx + pad, ny + pad, nw - pad * 2, nh - pad * 2, 14, 4, loadNote(index));
//                     noteText.setAlignment(Text.Alignment.LEFT);
//                     note.addChild(noteText);
//                     continue;
//                 }


//                 // $SCALING
//                 // TODO: remove hardcoded values.

//                 // TODO: introduce copy() or clone() function for Elements
//                 int    size      = 25;
//                 int    thickness = 3;
//                 int    ncx       = nx + nw / 2;
//                 int    ncy       = ny + nh / 2;
//                 Sprite s1        = new Sprite(ncx - thickness, ncy - size, thickness << 1, size << 1, 4);
//                 Sprite s2        = new Sprite(ncx - size, ncy - thickness, size << 1, thickness << 1, 4);
//                 s1.setColors(yellow2[3], yellow2[9], yellow2[5]);
//                 s2.setColors(yellow2[3], yellow2[9], yellow2[5]);
//                 s1.setInheritingInteractions(true);
//                 s2.setInheritingInteractions(true);
//                 note.addChild(s1);
//                 note.addChild(s2);
//             }
//         }

//         Global.layer("UIMAIN").addChild(notesGroup);
//     }

//     public static void openNote(int index) {
//         Global.layer("UIMAIN").removeChild("notesGroup");

//         String noteText = Notes.noteExists(index) ? Notes.loadNote(index) : "";

//         Sprite sideBG    = (Sprite) Global.findElement("side_bg");
//         Sprite profileBG = (Sprite) Global.findElement("profile_bg");

//         Global.asrt(sideBG != null, "sideBG was not found!");
//         Global.asrt(profileBG != null, "profileBG was not found!");

//         int padding = 25;

//         int x = sideBG.getWidth() + padding;
//         int y = profileBG.getHeight() + padding;
//         int w = Global.CANVAS.getWidth() - sideBG.getWidth() - padding * 2;
//         int h = Global.CANVAS.getHeight() - profileBG.getHeight() - padding * 2;

//         // TODO: add a way to set the value of z based on what you want to do with it
//         Note note = new Note(x, y, w, h, 20, 4, noteText, 0);
//         note.setAlignment(Text.Alignment.LEFT);
//         note.setAdditional(index);
//         note.setScrollable(true);
//         note.setName("openNote");
//         Global.layer("UIMAIN").addChild(note);
//     }

//     private static String getNoteName(int index) {
//         return Global.NOTES_FOLDER + index + ".txt";
//     }

//     public static void saveNote(int index, String content) {
//         IO.saveFile(getNoteName(index), content);
//     }

//     public static String loadNote(int index) {
//         if (noteExists(index)) return IO.loadFile(getNoteName(index));
//         return "";
//     }

//     public static boolean noteExists(int index) {
//         return IO.fileExists(getNoteName(index));
//     }

//     public static void deleteNote(int index) {
//         if (noteExists(index)) IO.deleteFile(getNoteName(index));
//     }


// }
