import java.lang.reflect.InvocationTargetException;

public class Actions {

	public void invoke(String action) {
		if (action == "") return;
		try {
        	this.getClass().getMethod(action).invoke(this);
	    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
	    	throw new RuntimeException(e);
		}
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

}
