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

    private List<Tank> redTeam, blueTeam;
    GenAlg gen = new GenAlg(100, 0.85, 0.05, 10, false);

    public Component(List<Tank> redTeam, List<Tank> blueTeam) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(480, 360);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (Tank t : redTeam) {
            t.update();
            t.drawMyself(g2, t.x, t.y, t.theta);
        }

        for (Tank t : blueTeam) {
            t.update();
            t.drawMyself(g2, t.x, t.y, t.theta);
        }
        // tanks = gen.process(tanks);
    }
}
