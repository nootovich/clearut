import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class Element {

    private int[]     dimensions          = new int[5]; // x y z w h
    private boolean   interactive         = true;
    private boolean   inheritInteractions = false;
    private boolean   visible             = true;
    private boolean   hovered             = false; // TODO: maybe combine states into one variable?
    private boolean   active              = false; // TODO: maybe combine states into one variable?
    private String    name                = "";
    private String    action              = "";
    private Element[] children            = new Element[0];
    private Element   parent              = null;
    private int       additional          = -1;

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
            System.out.printf("\t\tupdate element - z:%d x:%d y:%d w:%d h:%d %s '%s %s'%n",
                              getZ(), ex, ey, ew, eh, en,
                              isVisible() ? " visible" : "!visible",
                              isInteractive() ? " interactive" : "!interactive");
        } // $DEBUG

        if (!isInteractive()) return false;

        if (isInheritingInteractions()) {
            setHovered(getParent().isHovered());
            setActive(getParent().isActive());
            return updateChildren();
        }

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

        Element[] oldArray = getChildren();
        children                      = new Element[children.length + 1];
        children[children.length - 1] = child;
        System.arraycopy(oldArray, 0, children, 0, oldArray.length);

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
        Element[] oldArray = getChildren();
        if (index >= oldArray.length) {
            System.out.printf("Element index (%d) is out of range (%d)!%n", index, oldArray.length);
            return;
        }

        if (oldArray.length == 1) {
            children = new Element[0];
            return;
        }

        children = new Element[oldArray.length - 1];
        int offset = 0;
        for (int i = 0; i < oldArray.length; i++) {
            if (i == index) {
                offset = -1;
                continue;
            }
            children[i + offset] = oldArray[i];
        }

        Arrays.sort(children, new ElementPriorityComparator());
        // TODO: maybe make this function return boolean to signify if it was successful or not
    }

    public void removeChild(String name) {
        Element child = getChild(name);
        if (child == null) {
            System.out.printf("Element with the name \"%s\" was not found!%n", name);
            return;
        }

        Element[] oldArray = getChildren();
        if (oldArray.length == 1) {
            children = new Element[0];
            return;
        }

        children = new Element[children.length - 1];
        int offset = 0;
        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i].getName().equals(name)) {
                offset = -1;
                continue;
            }
            children[i + offset] = oldArray[i];
        }

        Arrays.sort(children, new ElementPriorityComparator());
        // TODO: maybe make this function return boolean to signify if it was successful or not
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

    public int getAdditional() {
        return additional;
    }

    public void setAdditional(int additional) {
        this.additional = additional;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisibility(boolean bool) {
        this.visible = bool;
    }

    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean bool) {
        this.interactive = bool;
    }

    public boolean isInheritingInteractions() {
        return inheritInteractions;
    }

    public void setInheritingInteractions(boolean inheritInteractions) {
        this.inheritInteractions = inheritInteractions;
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