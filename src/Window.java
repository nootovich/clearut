import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {

    public DoubleBufferedCanvas DBC;

    public Window(int width, int height, IO.Mouse mouse) {
        DBC = new DoubleBufferedCanvas(width, height, mouse);
        add(DBC);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("CLOSED");
                super.windowClosing(e);
            }
        });
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Layer addLayer(String name, int z) {
        return DBC.addLayer(new Layer(name, z));
    }

    public Layer getLayer(String name) {
        return DBC.getLayer(name);
    }

}
