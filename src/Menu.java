// public class Menu extends Element {

//     private boolean minimized = true;

//     private int[] colors = new int[3];

//     public Menu(int w, int h, int z, String name, int buttonsColor) {
//         super(-1, -1, w, h, z, name);
//         setColors(buttonsColor, buttonsColor, buttonsColor);
//     }

//     public Menu(int w, int h, int z, String name, int idleButtonsColor, int hoverButtonsColor, int activeButtonsColor) {
//         super(-1, -1, w, h, z, name);
//         setColors(idleButtonsColor, hoverButtonsColor, activeButtonsColor);
//     }

//     @Override
//     public boolean update(int flags) {
//         if (updateHigherChildren(flags)) return true;

//         if (Global.LOG > 1) {
//             System.out.println("update menu " + getName());
//         } // $DEBUG

//         return updateLowerChildren(flags);
//     }

//     public void open(int x, int y) {
//         setMinimized(false);

//         if (x > Global.CANVAS.getWidth() >> 1) x -= getWidth();
//         if (y >= Global.CANVAS.getHeight() >> 1) y -= getHeight();

//         Element[] descendants = getChildren();
//         if (descendants.length == 0) return;
//         int ySpacing = getHeight() / descendants.length;

//         for (int i = 0; i < descendants.length; i++) {
//             Element d = descendants[i];
//             d.setX(x);
//             d.setY(y + i * ySpacing);
//             d.setHeight(ySpacing);
//             d.setVisibility(true);
//             d.setInteractive(true);

//             Text txt = (Text) d.getChild(d.getName() + ":text");
//             Global.asrt(txt != null, "Couldn't find text of a button " + d.getName() + " in menu " + getName());
//             txt.setOffsetY(ySpacing >> 1).setHeight(ySpacing).setY(y + i * ySpacing).setVisibility(true).setInteractive(true);
//         }
//     }

//     public void close() {
//         setMinimized(true);

//         Element[] descendants = getChildren();
//         for (int i = 0; i < descendants.length; i++) {
//             descendants[i].setX(-1);
//             descendants[i].setY(-1);
//             descendants[i].setVisibility(false);
//             descendants[i].setInteractive(false);
//         }
//     }

//     public Sprite addButton(String text, String name, String action) {
//         Sprite b = addSprite(-1, -1, getWidth(), 0, getZ(), name, action);
//         b.setColors(getIdleColor(), getHoveredColor(), getActiveColor());
//         b.setVisibility(false).setInteractive(false);
//         b.addOutline(3, Colors.yellow1(6));
//         b.addText(text).setName(b.getName() + ":text").setVisibility(false).setInteractive(false).setInheritingInteractions(true);
//         return b;
//     }

//     public int[] getColors() {
//         return colors;
//     }

//     public int getIdleColor() {
//         return colors[0];
//     }

//     public void setIdleColor(int color) {
//         colors[0] = color;
//     }

//     public int getHoveredColor() {
//         return colors[1];
//     }

//     public void setHoveredColor(int color) {
//         colors[1] = color;
//     }

//     public int getActiveColor() {
//         return colors[2];
//     }

//     public void setActiveColor(int color) {
//         colors[2] = color;
//     }

//     public void setColors(int idleColor, int hoveredColor, int activeColor) {
//         colors = new int[]{idleColor, hoveredColor, activeColor};
//     }

//     private int getColorBasedOnState() {
//         if (isActive()) return getActiveColor();
//         if (isHovered()) return getHoveredColor();
//         return getIdleColor();
//     }

//     public boolean isMinimized() {
//         return minimized;
//     }

//     public void setMinimized(boolean bool) {
//         this.minimized = bool;
//     }

// }
