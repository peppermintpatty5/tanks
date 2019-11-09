import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Component
 */
public class Component extends JComponent {

    private static final long serialVersionUID = 1L;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(480, 360);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawString("Test!", 100, 100);
    }
}
