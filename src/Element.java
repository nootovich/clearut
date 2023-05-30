import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class Element {

    private int[]     dimensions = new int[5]; // x y z w h
    private boolean   visible    = true;
    private boolean   hovered    = false; // TODO: maybe combine states into one variable?
    private boolean   active     = false; // TODO: maybe combine states into one variable?
    private String    name       = "";
    private String    action     = "";
    private Element[] children   = new Element[0];
    private Element   parent     = null;

    Element() {}

    Element(int x, int y, int w, int h, int z) {
        this.dimensions[0] = x;
        this.dimensions[1] = y;
        this.dimensions[2] = z;
        this.dimensions[3] = w;
        this.dimensions[4] = h;
    }

    Element(int x, int y, int w, int h, int z, String name) {
        this(x, y, w, h, z);
        setName(name);
    }

    Element(int x, int y, int w, int h, int z, String name, String action) {
        this(x, y, w, h, z, name);
        setAction(action);
    }

    public boolean update() {
        // TODO: make this function accept a boolean
        //     as a flag that another element has already been activated?
        boolean lmb            = Global.MOUSE.getLMB();
        boolean lmbPrev        = Global.MOUSE.getLMBPrev(); // $DEBUG
        boolean LMBRisingEdge  = Global.MOUSE.isLMBRisingEdge();
        boolean LMBFallingEdge = Global.MOUSE.isLMBFallingEdge();
        int     mx             = Global.MOUSE.getX();
        int     my             = Global.MOUSE.getY();
        int     ex             = getX();
        int     ey             = getY();
        int     ew             = getWidth();
        int     eh             = getHeight();
        String  en             = getName(); // $DEBUG

        if (Global.LOG > 1) {
            System.out.printf("\t\tupdate element - z:%d x:%d y:%d w:%d h:%d %s '%s'%n",
                              getZ(), ex, ey, ew, eh, en, isVisible() ? " visible" : "!visible");
        } // $DEBUG

        setHovered(mx >= ex && mx <= ex + ew && my >= ey && my <= ey + eh);
        setActive(isHovered() && lmb);
        if (isHovered()) {
            if (Global.LOG > 0) {
                System.out.printf("\tmouse hovered over %s, lmb: %b(%b)%n", en, lmb, lmbPrev);
            } // $DEBUG

            if (LMBRisingEdge) {
                if (Global.LOG > 0) {
                    System.out.println("\tmouse clicked at " + en);
                } // $DEBUG

                return true;
            } else if (LMBFallingEdge) {
                Global.ACTIONS.invoke(action);
            }
        } else if (Global.LOG > 1) {
            System.out.printf("mouse not hovered on %s - x:%d y:%d lmb: %b(%b)%n", en, mx, my, lmb, lmbPrev);
        } // $DEBUG
        return updateChildren();
    }

    public boolean updateChildren() {
        if (getChildren() == null) return false;

        boolean   result      = false;
        Element[] descendants = getChildren();
        for (int i = descendants.length - 1; i >= 0; i--) {
            result |= descendants[i].update();
        }
        return result;
    }

    public void draw(Graphics2D g2d) {
        drawChildren(g2d);
    }

    public void drawChildren(Graphics2D g2d) {
        for (Element e : getChildren()) {
            e.draw(g2d);
        }
    }

    public Element getChild(String name) {
        name = name.toUpperCase();

        for (Element e : getChildren()) {
            if (e.getName().equals(name)) return e;
            Element foundChild = e.getChild(name);
            if (foundChild != null) return foundChild;
        }
        return null;
    }

    public Element[] getChildren() {
        return children;
    }

    public void addChild(Element child) {
        child.parent = this;

        Element[] old_array = getChildren();
        children                      = new Element[children.length + 1];
        children[children.length - 1] = child;
        System.arraycopy(old_array, 0, children, 0, old_array.length);

        Arrays.sort(children, new ElementPriorityComparator());

        // TODO: maybe make this function return boolean to signify if it was successful or not
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        parent.addChild(this);
    }

    public void removeChild(int index) {
        // TODO: implement
        // maybe make this function return boolean to signify if it was successful or not
    }

    public void removeChild(String name) {
        // TODO: implement
        // maybe make this function return boolean to signify if it was successful or not
    }

    public int getX() {
        return dimensions[0];
    }

    public void setX(int x) {
        int change = x - getX();
        dimensions[0] = x;
        for (Element e : getChildren()) {
            e.setX(e.getX() + change);
        } // TODO: refactor after adding addX() function
    }

    public int getY() {
        return dimensions[1];
    }

    public void setY(int y) {
        int change = y - getY();
        dimensions[1] = y;
        for (Element e : getChildren()) {
            e.setY(e.getY() + change);
        } // TODO: refactor after adding addY() function
    }

    public int getZ() {
        return dimensions[2];
    }

    public void setZ(int z) {
        dimensions[2] = z;
    }

    public int getWidth() {
        return dimensions[3];
    }

    public void setWidth(int w) {
        dimensions[3] = w;
    }

    public int getHeight() {
        return dimensions[4];
    }

    public void setHeight(int h) {
        dimensions[4] = h;
    }

    public int getCenterX() {
        return getX() + (getWidth() >> 1);
    }

    public int getCenterY() {
        return getY() + (getHeight() >> 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisibility(boolean bool) {
        this.visible = bool;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean bool) {
        this.hovered = bool;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean bool) {
        this.active = bool;
    }
}

class ElementPriorityComparator implements Comparator<Element> {
    @Override
    public int compare(Element a, Element b) {
        return Integer.compare(a.getZ(), b.getZ());
    }
}

// TODO: make into a general class rather than an interface
// change priority to z? (3rd argument of pos)
// make outline a different class or type of Sprite
// make every element Interactive by default
// add colors for each state (idle, highlighted, active) of elements
//		or figure out a proper color scheme
// automate textSize of Text and actually render Text based on its dimentions
// make it so every element can store elements inside of itself?
// add name attribute to every element

// GeneralElement
// pos(x, y, z) size(w, h) visibile(bool) hovered(bool) active(bool) name(String) children(Element[])

// Sprite
// +colors(Color[])

// Text
// +text(String) +colors(Color[])

// Picture
// +image(Image)

// The rest is unchanged

// OLD STATE:

// Sprite 
// pos size priority visibility outline color

// InteractiveSprite 
// pos size priority visibility outline highlighted color

// Text 
// pos size textSize priority visibility text color

// Picture 
// pos size priority visibility imageName image

// InteractivePicture 
// pos size priority visibility highlighted imageName image

// Button 
// pos size priority visibility name elements[]

// UILayer
// priority name elements[]

// Window
// width height layers[]
