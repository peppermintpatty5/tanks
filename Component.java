import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;

/**
 * Component
 */
public class Component extends JComponent {

    private static final long serialVersionUID = 1L;

    private final List<Tank> tanks;

    public Component(List<Tank> tanks) {
        this.tanks = tanks;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(480, 360);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (Tank t : tanks) {
            t.drawMyself(g2, t.getX(), t.getY());
        }
    }
}
