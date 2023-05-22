import java.awt.*;
import java.util.Comparator;

public interface Element {

    boolean update();

    void draw(Graphics2D g);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getWidth();

    void setWidth(int w);

    int getHeight();

    void setHeight(int h);

    Point getPos();

    void setPos(Point pos);

    Point getSize();

    void setSize(Point size);

    int getPriority();

    void setPriority(int priority);

    boolean isVisible();

    void setVisibility(boolean bool);

    Color getColor();

    void setColor(Color color);
}

class ElementPriorityComparator implements Comparator<Element> {
    @Override
    public int compare(Element a, Element b) {
        return Integer.compare(a.getPriority(), b.getPriority());
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
// pos(x, y, z) size(w, h) visibile(bool) highlighted(bool) name(String) children(Element[])

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
