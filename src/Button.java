import java.awt.*;
import java.lang.reflect.InvocationTargetException;

// TODO: implement 'addText()' function
public class Button {

	private boolean activated = false;
	private String name   = "";
	private String action = "";
	private Element[] children = null;
	
    public Button(String name, String action) {
//        Sprite body    = new InteractiveSprite(x, y, w, h, 0, Global.COLORS[5]);
//        Sprite outline = new InteractiveSprite(x, y, w, h, 1, Global.COLORS[6]);
        // TODO: make Sprite have different types (prob using enum but maybe different classes)
//        outline.setOutline(true);
//        addElement(body);
//        addElement(outline);
//        Global.WINDOW.getLayer(layerName).addElement(this); // TODO: remove
        this.name   = name;
		this.action = action;
    }

	public boolean update() { // TODO: debug and simplify when i get home
		boolean result = updateChildren();
		if (!isActivated() && result) {
			setActivated(true);				
		} else if (isActivated()) {
			if (!result) {
				setActivated(false);
			}
			if (!Global.MOUSE.getLMB()) {
				try {
            		this.getClass().getMethod(getAction()).invoke(this);
	           	} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
	            	throw new RuntimeException(e);
	           	}	
			}
		}
		return result;
	}
	
    public void draw(Graphics2D g2d) {
        for (Element e : getChildren()) {
            e.draw(g2d);
        }
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

    public void button0() {
        System.out.println("button0 was pressed!");
    }

    public void button1() {
        System.out.println("Hello world!");
    }

    // public void button3() {
    //     setX(getX() + 100);
    //     System.out.println("button3 move right");
    // }

    // public void button5() {
    //     Element[] elements = Global.WINDOW.getLayer("UISIDE").getElements();

    //     for (Element element : elements) {
    //         try {
    //             Button button = (Button) element;
    //             if (button.getName().equals("button3")) {
    //                 button.setX(button.getX() - 50);
    //                 System.out.println("button3 move left");
    //             }
    //         } catch (ClassCastException ignored) {
    //         }
    //     }
    // }

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Element[] getChildren() {
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
        // TODO: implement
        // maybe make this function return boolean to signify if it was successful or not
    }

    public void removeChild(String name) {
        // TODO: implement
        // maybe make this function return boolean to signify if it was successful or not
    }
//    public Point getPos() {
//        return new Point(x, y);
//    }
//
//    public void setPos(Point pos) {
//        int changeX = pos.x - this.x;
//        int changeY = pos.y - this.y;
//        this.x = pos.x;
//        this.y = pos.y;
//        for (Element element : getElements()) {
//            element.setX(element.getX() + changeX);
//            element.setY(element.getY() + changeY);
//        }
//    }
//
//    public Point getSize() {
//        return new Point(w, h);
//    }
//
//    public void setSize(Point size) {
//        this.w = size.x;
//        this.h = size.y;
//    }
//
//    public Point getCenter() {
//        return new Point(x + (w >> 1), y + (h >> 1));
//    }

}
