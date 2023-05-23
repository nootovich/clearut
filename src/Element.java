import java.awt.*;
import java.util.Comparator;

public class Element {

	int[] dimensions 	  = new int[5]; // x y z w h
	boolean visible 	  = true;
	boolean hovered 	  = false;
	String name 		  = "";
	Element[] children = null;

	Element(int x, int y, int z, int w, int h) {
		this.dimensions[0] = (int) x;
		this.dimensions[1] = (int) x;
		this.dimensions[2] = (int) x;
		this.dimensions[3] = (int) x;
		this.dimensions[4] = (int) x;
	}
	
	Element(int x, int y, int z, int w, int h, String name) {
		this.dimensions[0] = (int) x;
		this.dimensions[1] = (int) x;
		this.dimensions[2] = (int) x;
		this.dimensions[3] = (int) x;
		this.dimensions[4] = (int) x;
		this.name = name;
	}

	public boolean update() {
		return false;
	}

	public void draw(Graphics2D g) {
		return;
	}
	
	public int getX() {
		return dimensions[0];
	}
	
	public int getY() {
		return dimensions[1];
	}
	
	public int getZ() {
		return dimensions[2];
	}
	
	public int getWidth() {
		return dimensions[3];
	}
	
	public int getHeight() {
		return dimensions[4];
	}

	public String getName() {
		return name;
	}

	public Element[] getChildren() {
		return children;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public void setX(int x) {
		dimensions[0] = x;
	}
	
	public void setY(int y) {
		dimensions[1] = y;
	}
	
	public void setZ(int z) {
		dimensions[2] = z;
	}
	
	public void setWidth(int w) {
		dimensions[3] = w;
	}
	
	public void setHeight(int h) {
		dimensions[4] = h;
	}

	public void setVisibility(boolean visible) {
		this.visible = visible;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addChild(Element child) {
		// TODO: implement
		// maybe make this function return boolean to signify if it was successful or not
	}

	public void removeChild(int index) {
		// TODO: implement
		// maybe make this function return boolean to signify if it was successful or not
	}

	public void removeChild(String name) {
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
// pos(x, y, z) size(w, h) visibile(bool) hovered(bool) name(String) children(Element[])

// Sprite
// +color(Color)

// Text
// +text(String) +color(Color)

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
