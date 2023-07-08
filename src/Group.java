// TODO: make Element a subclass of Group. not the oher way around
public class Group extends Element {

    Group(String name) {
        super(0, 0, 0, 0, 0);
        setName(name);
    }

    @Override
    public boolean update(int flags) {
        if (!isInteractive()) return false;
        if (updateHigherChildren(flags)) return true;
        return updateLowerChildren(flags);
    }

}