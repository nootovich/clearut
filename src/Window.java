import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Window extends JFrame {

	public DoubleBufferedCanvas DBC;
	
    public Window(int width, int height, IO.Mouse mouse) {
		DBC = new DoubleBufferedCanvas(width, height, mouse);
		add(DBC);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	public void addLayer(String name, int z) {
		DBC.addLayer(new Layer(name, z));
	}
	
	public Layer getLayer(String name) {
		return DBC.getLayer(name);
	}

}
