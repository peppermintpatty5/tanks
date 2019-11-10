import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Component
 */
public class Component extends JComponent implements KeyListener {

	/**
	 * Static Serial Version Identity Number
	 */
    private static final long serialVersionUID = 1L;

    /**
     * List of tank objects broken up into different team types
     */
    private List<Tank> redTeam, blueTeam;
    
    /**
     * List of bullets shot within the field of play
     */
    private List<Bullet> bullets;

    /**
     * Width and height of the frame component is resting on
     */
    private int width, height;

    /**
     * Background image to be tiled
     */
    private BufferedImage backgroundImage;
    GenAlg gen = new GenAlg(100, 0.85, 0.05, 10, false);

    /**
     * Construct new component
     * @param redTeam the redTeam tanks to be set
     * @param blueTeam the blueTeam tanks to be set
     * @param bullets the bullets to be set
     * @param width the width to be set
     * @param height the height to be set
     */
    public Component(List<Tank> redTeam, List<Tank> blueTeam, List<Bullet> bullets, int width, int height) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
        this.bullets = bullets;
        this.width = width;
        this.height = height;
        this.backgroundImage = createImageBackground();
    }

    /**
     * Dynamically enlarge a buffered image by twice the width and height until it
     * fits on the screen
     * @return enlarged buffered image
     */
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

    /**
     * Enlarge a buffered image by twice the width and height
     * @param image1 image to be enlarged
     * @return enlarged image
     */
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
        
        System.out.println("Size:  " + Main.bullets.size());
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Pressed");
		for (Tank t : redTeam) {
			t.theta += 0.5;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
