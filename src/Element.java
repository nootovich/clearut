import java.awt.*;
import java.util.Comparator;

public class Element {

    int[]     dimensions = new int[5]; // x y z w h
    boolean   visible    = true;
    boolean   hovered    = false; // TODO: maybe combine states into one variable?
    boolean   active     = false; // TODO: maybe combine states into one variable?
    String    name       = "";
    Element[] children   = null;

    Element(int x, int y, int w, int h, int z) {
        this.dimensions[0] = x;
        this.dimensions[1] = y;
        this.dimensions[2] = z;
        this.dimensions[3] = w;
        this.dimensions[4] = h;
    }

    Element(int x, int y, int w, int h, int z, String name) {
        this(x, y, w, h, z);
        this.name = name;
    }

    public boolean update() {
        // TODO: make this function accept a boolean
        //     as a flag that another element has already been activated?
        boolean lmb 	= Global.MOUSE.getLMB();
		boolean lmbUsed = Global.MOUSE.getLMBUsed();
        int mx = Global.MOUSE.getX();
        int my = Global.MOUSE.getY();
        int ex = getX();
        int ey = getY();
        int ew = getWidth();
        int eh = getHeight();
		String en = getName(); // TODO: rename to ename
		
        if (Global.LOG > 1) {
			System.out.printf("\t\tupdate element - z:%d x:%d y:%d w:%d h:%d %s '%s'%n",
                			  getZ(), ex, ey, ew, eh, en, isVisible() ? " visible" : "!visible");
		} // $DEBUG

        setHovered(mx >= ex && mx <= ex + ew && my >= ey && my <= ey + eh);
        if (isHovered()) {
            if (Global.LOG > 0) {
                System.out.printf("\tmouse hovered over %s, lmb: %b(%b)%n", en, lmb, lmbUsed);
            } // $DEBUG
			
            if (!lmbUsed && lmb) {
                if (Global.LOG > 0) {
                    System.out.println("\tmouse clicked at " + en);
                } // $DEBUG

                setActive(true);
                Global.MOUSE.setLMBUsed(true);
                return true;
            } else if (!lmb) {
                setActive(false);
            }
        } else if (Global.LOG > 1) {
            System.out.printf("mouse not hovered on %s - x:%d y:%d lmb: %b(%b)%n", en, mx, my, lmb, lmbUsed);
		} // $DEBUG
        return updateChildren();
    }

    public void draw(Graphics2D g2d) {
        return;
    }

    public boolean updateChildren() {
		// TODO: make Element and Button extend some intermediary class with this function
		if (getChildren() == null) return false;
		
        boolean result = false;
        Element[] descendants = getChildren();
        for (int i = descendants.length - 1; i >= 0; i--) {
            result |= descendants[i].update();
        }
        return result;
    }

    public void drawChildren(Graphics2D g2d) {
 		// TODO: make Element and Button extend some intermediary class with this function
       for (Element e : getChildren()) {
            e.draw(g2d);
        }
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
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Element[] getChildren() {
		// TODO: make Element and Button extend some intermediary class with this function
        return children;
    }

    public void addChild(Element child) {
		// TODO: make Element and Button extend some intermediary class with this function

        if (getChildren() == null) {
            children    = new Element[1];
            children[0] = child;
            return;
        }

        Element[] old_array 		  = getChildren();
        children                      = new Element[children.length + 1];
        children[children.length - 1] = child;
        for (int i = 0; i < old_array.length; i++) {
            children[i] = old_array[i];
        }

        // TODO: maybe make this function return boolean to signify if it was successful or not
    }

    public void removeChild(int index) {
		// TODO: make Element and Button extend some intermediary class with this function
        // TODO: implement
        // maybe make this function return boolean to signify if it was successful or not
    }

    public void removeChild(String name) {
		// TODO: make Element and Button extend some intermediary class with this function
        // TODO: implement
        // maybe make this function return boolean to signify if it was successful or not
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
