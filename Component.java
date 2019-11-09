import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Component
 */
public class Component extends JComponent {

    private static final long serialVersionUID = 1L;
    /**
     * caches images for faster performance
     */
    private static final Map<String, BufferedImage> IMG_MAP = new HashMap<>();

    private final List<Tank> tanks;

    public Component(List<Tank> tanks) {
        this.tanks = tanks;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(480, 360);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (Tank t : tanks) {
            g2.drawImage(getImgResource("assets/imgs/tanks.png"), t.getX(), t.getY(), null);
        }
    }

    private BufferedImage getImgResource(String name) {
        var img = IMG_MAP.get(name);

        if (img == null) {
            try {
                img = ImageIO.read(getClass().getResource(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
            IMG_MAP.put(name, img);
        }

        return img;
    }
}
