import java.awt.*;

public class Child {
    // TODO: maybe rename to "member"?
    public int     z               = 0;
    public String  name            = "";
    public Child   parent          = null;
    public Child[] children        = new Child[0];
    public boolean inheritPosition = false, inheritInteractions = false;

    public void addChild(Child newChild) {
        newChild.parent = this;
        Child[] temp = children;
        children                      = new Child[children.length + 1];
        children[children.length - 1] = newChild;
        System.arraycopy(temp, 0, children, 0, temp.length);
        //Arrays.sort(children, new Child(Member)PriorityComparator());
    }

    public Child getChild(String searchName) {
        searchName = searchName.toUpperCase();
        for (Child c : children) {
            if (c.name.equals(searchName)) return c;
        }
        return null;
    }

    public void update(IO.Mouse mouse) {
        updateHigherChildren(mouse);
        updateLowerChildren(mouse);
    }

    public void updateHigherChildren(IO.Mouse mouse) {
        for (int i = children.length - 1; i >= 0 && children[i].z > this.z; i--) {
            children[i].update(mouse);
        }
    }

    public void updateLowerChildren(IO.Mouse mouse) {
        int i = children.length - 1;
        while (i >= 0 && children[i].z > this.z) i--;
        for (; i >= 0; i--) children[i].update(mouse);
    }

    public void draw(Graphics2D g2d) {
        drawLowerChildren(g2d);
        drawHigherChildren(g2d);
    }

    public void drawLowerChildren(Graphics2D g2d) {
        for (int i = 0; i < children.length && children[i].z <= this.z; i++) {
            children[i].draw(g2d);
        }
    }

    public void drawHigherChildren(Graphics2D g2d) {
        for (int i = 0; i < children.length; i++) {
            if (children[i].z > this.z) children[i].draw(g2d);
        }
    }
}