public class Menu extends Element {

    private boolean minimized = false;

    public Menu(int x, int y, int buttonW, int buttonH, int z, String name) {
        super(x, y, buttonW, buttonH, z, name);
    }

    @Override
    public boolean update(int flags) {
        if (updateHigherChildren(flags)) return true;

        if (Global.LOG > 1) {
            System.out.println("update menu " + getName());
        } // $DEBUG

        return updateLowerChildren(flags);
    }

    public boolean isMinimized() {
        return minimized;
    }

    public void setMinimized(boolean bool) {
        this.minimized = bool;
    }

}
