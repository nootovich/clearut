// import java.awt.*;
// import java.util.Arrays;
// import java.util.Comparator;

// public class Element {

//     public int 			x, y, w, h, z, additional	= -1;
// 	public boolean		interactive					= true;
//     public boolean		inheritInteractions			= false; // TODO: think of a better name
//     public boolean		scrollable					= false;
//     public boolean		visible						= true;
//     public boolean		hovered						= false; // TODO: maybe combine states into one variable?
//     public boolean		active						= false; // TODO: maybe combine states into one variable?
//     public String		name						= "";
//     public String		action						= "";
//     public Object[]  	children					= new Element[0];
//     public Object		parent						= null;
//     public int			additional					= -1;

//     Element() {}

//     Element(int x, int y, int w, int h, int z) {
//         this.x = x; this.y = y; this.z = z; this.w = w; this.h = h;
//     }

//     Element(int x, int y, int w, int h, int z, String name) {
//         this(x, y, w, h, z);
//         this.name = name;
//     }

//     Element(int x, int y, int w, int h, int z, String name, String action) {
//         this(x, y, w, h, z, name);
//         this.action = action;
//     }

//     private boolean updateSelf() {
//         // TODO: make this function accept a boolean
//         //     as a flag that another element has already been activated?
//         boolean lmb            = Global.MOUSE.getLMB();
//         boolean lmbPrev        = Global.MOUSE.getLMBPrev(); // $DEBUG
//         boolean LMBRisingEdge  = Global.MOUSE.isLMBRisingEdge();
//         boolean LMBFallingEdge = Global.MOUSE.isLMBFallingEdge();
//         int     mx             = Global.MOUSE.getX();
//         int     my             = Global.MOUSE.getY();

//         if (Global.LOG > 1) {
//             System.out.printf("\t\tupdate element - z:%d x:%d y:%d w:%d h:%d %s '%s %s'%n",
//                               z, x, y, w, h, name, visible ? " visible" : "!visible", interactive ? " interactive" : "!interactive");
//         } // $DEBUG

//         if (!isInteractive()) return false;

//         if (isInheritingInteractions()) {
//             setHovered(getParent().isHovered());
//             setActive(getParent().isActive());
//             return false;
//         }

//         setHovered(mx >= ex && mx <= ex + ew && my >= ey && my <= ey + eh);
//         setActive(isHovered() && lmb);
//         if (isHovered()) {
//             if (Global.LOG > 0) {
//                 System.out.printf("\tmouse hovered over %s, lmb: %b(%b)%n", en, lmb, lmbPrev);
//             } // $DEBUG

//             if (LMBRisingEdge) {
//                 if (Global.LOG > 0) {
//                     System.out.println("\tmouse clicked at " + en);
//                 } // $DEBUG

//                 return true;
//             } else if (LMBFallingEdge) {
//                 Global.ACTIONS.invoke(action);
//             }
//         } else if (Global.LOG > 1) {
//             System.out.printf("mouse not hovered on %s - x:%d y:%d lmb: %b(%b)%n", en, mx, my, lmb, lmbPrev);
//         } // $DEBUG

//         return false;
//     }

//     public boolean update(int flags) {
//         if (!isInteractive()) return false;
//         if (updateHigherChildren(flags)) return true;
//         if ((flags | Flags.NONE) == 0) {
//             return updateSelf();
//         } else if ((flags & Flags.MWHEELUP) > 0 || (flags & Flags.MWHEELDN) > 0) {
//             if (isScrollable()) scroll(flags); // moveY(50, 250);
//             return false;
//         }

//         return updateLowerChildren(flags);
//     }

//     public boolean updateHigherChildren(int flags) {
//         if (getChildren() == null) return false;

//         boolean   result      = false;
//         Element[] descendants = getChildren();
//         for (int i = descendants.length - 1; i >= 0 && descendants[i].getZ() > getZ(); i--) {
//             result |= descendants[i].update(flags);
//         }
//         return result;
//     }

//     public boolean updateLowerChildren(int flags) {
//         if (getChildren() == null) return false;

//         boolean   result      = false;
//         Element[] descendants = getChildren();

//         int i = descendants.length - 1;
//         while (i >= 0 && descendants[i].getZ() > getZ()) {
//             i--;
//         }
//         for (; i >= 0; i--) {
//             result |= descendants[i].update(flags);
//         }
//         return result;
//     }

//     public void scroll(int amount) {}

//     public void draw(Graphics2D g2d) {
//         if (!isVisible()) return;
//         drawChildren(g2d);
//     }

//     public void drawChildren(Graphics2D g2d) {
//         for (Element e : getChildren()) {
//             e.draw(g2d);
//         }
//     }

//     public Element getChild(String name) {
//         name = name.toUpperCase();

//         for (Element e : getChildren()) {
//             if (e.getName().equals(name)) return e;
//             Element foundChild = e.getChild(name);
//             if (foundChild != null) return foundChild;
//         }
//         return null;
//     }

//     public Element[] getChildren() {
//         return children;
//     }

//     public void addChild(Element child) {
//         child.parent = this;

//         Element[] oldArray = getChildren();
//         children                      = new Element[children.length + 1];
//         children[children.length - 1] = child;
//         System.arraycopy(oldArray, 0, children, 0, oldArray.length);

//         Arrays.sort(children, new ElementPriorityComparator());

//         // TODO: maybe make this function return boolean to signify if it was successful or not
//     }

//     public Element getParent() {
//         return parent;
//     }

//     public void setParent(Element parent) {
//         parent.addChild(this);
//     }

//     public void removeChild(int index) {
//         Element[] oldArray = getChildren();
//         if (index >= oldArray.length) {
//             System.out.printf("Element index (%d) is out of range (%d)!%n", index, oldArray.length);
//             return;
//         }

//         if (oldArray.length == 1) {
//             children = new Element[0];
//             return;
//         }

//         children = new Element[oldArray.length - 1];
//         int offset = 0;
//         for (int i = 0; i < oldArray.length; i++) {
//             if (i == index) {
//                 offset = -1;
//                 continue;
//             }
//             children[i + offset] = oldArray[i];
//         }

//         Arrays.sort(children, new ElementPriorityComparator());
//         // TODO: maybe make this function return boolean to signify if it was successful or not
//     }

//     public void removeChild(String name) {
//         Element child = getChild(name);
//         if (child == null) {
//             if (Global.LOG > 0) {
//                 System.out.printf("Element with the name \"%s\" was not found!%n", name);
//             } // $DEBUG
//             return;
//         }

//         Element[] oldArray = getChildren();
//         if (oldArray.length == 1) {
//             children = new Element[0];
//             return;
//         }

//         children = new Element[children.length - 1];
//         int offset = 0;
//         for (int i = 0; i < oldArray.length; i++) {
//             if (oldArray[i].getName().equals(name)) {
//                 offset = -1;
//                 continue;
//             }
//             children[i + offset] = oldArray[i];
//         }

//         Arrays.sort(children, new ElementPriorityComparator());
//         // TODO: maybe make this function return boolean to signify if it was successful or not
//     }

//     public Sprite addSprite(int x, int y, int w, int h, int z, int color) {
//         Sprite s = new Sprite(x, y, w, h, z, color);
//         addChild(s);
//         return s;
//     }

//     public Sprite addSprite(int x, int y, int w, int h, int z, int color, String name) {
//         Sprite s = new Sprite(x, y, w, h, z, color, name);
//         addChild(s);
//         return s;
//     }

//     public Sprite addSprite(int x, int y, int w, int h, int z, String name, String action) {
//         Sprite s = new Sprite(x, y, w, h, z);
//         s.setName(name);
//         s.setAction(action);
//         addChild(s);
//         return s;
//     }

//     public Sprite addSprite(int x, int y, int w, int h, int z, int color, String name, String action) {
//         Sprite s = new Sprite(x, y, w, h, z, color, name, action);
//         addChild(s);
//         return s;
//     }

//     public Text addText(String text) {
//         // TODO: remove constant text size
//         Text t = new Text(getCenterX(), getCenterY(), getWidth(), getHeight(), 12, getZ() + 1, text, 0);
//         addChild(t);
//         return t;
//     }

//     public Text addText(int x, int y, int maxW, int maxH, int size, int z, String text, int color) {
//         Text t = new Text(x, y, maxW, maxH, size, z, text, color);
//         addChild(t);
//         return t;
//     }

//     public Outline addOutline(int thickness, int color) {
//         Outline o = new Outline(this, thickness, color);
//         addChild(o);
//         return o;
//     }

//     public Outline addOutline(int thickness, int idle_color, int hovered_color, int active_color) {
//         Outline o = new Outline(this, thickness, idle_color, hovered_color, active_color);
//         addChild(o);
//         return o;
//     }

//     public Slider addSlider(int x, int y, int w, int h, String name, int low, int high, int val) {
//         Slider s = new Slider(x, y, w, h, getMaxChildZ() + 1, name, low, high, val);
//         addChild(s);
//         return s;
//     }

//     public Slider addSlider(int x, int y, int w, int h, String name, int low, int high, int val, int color) {
//         Slider s = addSlider(x, y, w, h, name, low, high, val);
//         s.setColors(color, color, color);
//         return s;
//     }

//     public Slider addSlider(int x, int y, int w, int h, String name, int low, int high, int val, int color, int lineColor) {
//         Slider s = addSlider(x, y, w, h, name, low, high, val);
//         s.setColors(color, color, color);
//         s.setLineColor(lineColor);
//         return s;
//     }

//     public int getX() {
//         return dimensions[0];
//     }

//     public Element setX(int x) {
//         int change = x - getX();
//         dimensions[0] = x;
//         for (Element e : getChildren()) {
//             e.setX(e.getX() + change);
//         } // TODO: refactor after adding addX() function
//         return this;
//     }

//     public void addX(int x) {
//         setX(getX() + x);
//     }

//     public void moveX(int amount, int time) {
// //        if (timer < 0) timer = System.currentTimeMillis();
//         // TODO: implement animations later

//     }

//     public int getY() {
//         return dimensions[1];
//     }

//     public Element setY(int y) {
//         int change = y - getY();
//         dimensions[1] = y;
//         for (Element e : getChildren()) {
//             e.setY(e.getY() + change);
//         } // TODO: refactor after adding addY() function
//         return this;
//     }

//     public void addY(int y) {
//         setY(getY() + y);
//     }

//     public int getZ() {
//         return dimensions[2];
//     }

//     private int getMaxChildZ() {
//         int max = -10000;

//         Element[] descendants = getChildren();
//         if (descendants.length == 0) return -1;
//         for (Element e : descendants) {
//             int z = e.getZ();
//             if (z > max) max = z;
//         }

//         return max;
//     }

//     public void setZ(int z) {
//         dimensions[2] = z;
//     }

//     public int getWidth() {
//         return dimensions[3];
//     }

//     public void setWidth(int w) {
//         dimensions[3] = w;
//     }

//     public int getHeight() {
//         return dimensions[4];
//     }

//     public Element setHeight(int h) {
//         dimensions[4] = h;
//         return this;
//     }

//     public int getCenterX() {
//         return getX() + (getWidth() >> 1);
//     }

//     public int getCenterY() {
//         return getY() + (getHeight() >> 1);
//     }

//     public String getName() {
//         return name;
//     }

//     public Element setName(String name) {
//         this.name = name.toUpperCase();
//         return this;
//     }

//     public String getAction() {
//         return action;
//     }

//     public void setAction(String action) {
//         this.action = action;
//     }

//     public int getAdditional() {
//         return additional;
//     }

//     public Element setAdditional(int additional) {
//         this.additional = additional;
//         return this;
//     }

//     public boolean isVisible() {
//         return visible;
//     }

//     public Element setVisibility(boolean bool) {
//         this.visible = bool;
//         return this;
//     }

//     public boolean isInteractive() {
//         return interactive;
//     }

//     public Element setInteractive(boolean bool) {
//         this.interactive = bool;
//         return this;
//     }

//     public boolean isScrollable() {
//         return scrollable;
//     }

//     public void setScrollable(boolean scrollable) {
//         this.scrollable = scrollable;
//     }

//     public boolean isInheritingInteractions() {
//         return inheritInteractions;
//     }

//     public void setInheritingInteractions(boolean inheritInteractions) {
//         this.inheritInteractions = inheritInteractions;
//     }

//     public boolean isHovered() {
//         return hovered;
//     }

//     public void setHovered(boolean bool) {
//         this.hovered = bool;
//     }

//     public boolean isActive() {
//         return active;
//     }

//     public void setActive(boolean bool) {
//         this.active = bool;
//     }

//     public abstract class Flags {
//         public static final int NONE = 0b00000000;

//         public static final int MWHEELUP = 0b00000010;
//         public static final int MWHEELDN = 0b00000100;
//     }
// }

// class ElementPriorityComparator implements Comparator<Element> {
//     @Override
//     public int compare(Element a, Element b) {
//         return Integer.compare(a.getZ(), b.getZ());
//     }
// }