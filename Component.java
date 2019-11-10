import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Component
 */
public class Component extends JComponent {

    private static final long serialVersionUID = 1L;

    private List<Tank> redTeam, blueTeam;
    private List<Bullet> bullets;

    private int width, height;

    private BufferedImage backgroundImage;
    GenAlg gen = new GenAlg(100, 0.85, 0.05, 10, false);

    public Component(List<Tank> redTeam, List<Tank> blueTeam, List<Bullet> bullets, int width, int height) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
        this.bullets = bullets;
        this.width = width;
        this.height = height;
        this.backgroundImage = createImageBackground();
    }

    private BufferedImage createImageBackground() {
        BufferedImage image = null;
        try {
            image = enlargeImage(ImageIO.read(new File("assets/imgs/background.png")));

            while (image.getWidth() < width || image.getHeight() < height)
                image = enlargeImage(image);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private BufferedImage enlargeImage(BufferedImage image1) {
        BufferedImage combinedImage = new BufferedImage(image1.getWidth() * 2, image1.getHeight() * 2,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = combinedImage.createGraphics();

        g2.drawImage(image1, 0, 0, null);
        g2.drawImage(image1, image1.getWidth(), 0, null);

        g2.drawImage(image1, 0, image1.getHeight(), null);
        g2.drawImage(image1, image1.getWidth(), image1.getHeight(), null);
        g2.dispose();

        return combinedImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(backgroundImage, 0, 0, this);

        for (Tank t : redTeam)
            t.drawMyself(g2, (int) t.x, (int) t.y, t.theta);

        for (Tank t : blueTeam)
            t.drawMyself(g2, (int) t.x, (int) t.y, t.theta);

        for (Bullet b : bullets)
            b.drawMyself(g2, (int) b.x, (int) b.y, b.theta);

        bullets.removeIf(b -> {
            for (var t : (b.tank.team == Tank.Teams.RED ? blueTeam : redTeam)) {
                if (Math.pow(b.x - t.x, 2) + Math.pow(b.y - t.y, 2) < Math.pow(45, 2)) {
                    t.health--;
                    b.tank.hits++;
                    return true;
                }
            }
            return b.x < 0 || b.y < 0 || b.x > 1920 || b.y > 1080;
        });

        redTeam.removeIf(t -> t.health <= 0);
        blueTeam.removeIf(t -> t.health <= 0);

        // tanks = gen.process(tanks);
    }
}
