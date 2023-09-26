import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class Member {

    public        int              z;
    private       String           name     = "";
    public        Member           parent;
    public        Member[]         children = new Member[0];
    private final MemberComparator MBC      = new MemberComparator();

    public void addChild(Member child) {
        child.parent = this;
        Member[] temp = children;
        children                    = new Member[children.length+1];
        children[children.length-1] = child;
        System.arraycopy(temp, 0, children, 0, temp.length);
        if (children.length > 1) Arrays.sort(children, MBC);
    }

    public Member getChild(String searchName) {
        searchName = searchName.toUpperCase();
        for (Member c: children) {
            if (c.name.equals(searchName)) return c;
        }
        return null;
    }

    public void setName(String newName) {
        name = newName.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void update(IO.Mouse mouse) {
        updateHigherChildren(mouse);
        updateLowerChildren(mouse);
    }

    public void updateHigherChildren(IO.Mouse mouse) {
        for (int i = children.length-1; i >= 0 && children[i].z > this.z; i--) {
            children[i].update(mouse);
        }
    }

    public void updateLowerChildren(IO.Mouse mouse) {
        int i = children.length-1;
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

class MemberComparator implements Comparator<Member> {

    @Override
    public int compare(Member a, Member b) {
        return Integer.compare(a.z, b.z);
    }
}
