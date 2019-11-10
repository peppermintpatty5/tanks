import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Bullet
 *
 */
public class Bullet implements AnimatedObj {

	private static final int ANIMATION_MAX = 2;
	private static final BufferedImage SPRITE_SHEET = javaSux();
	private static final Dimension SPRITE_DIMENSION = new Dimension(32, 32);
	
	private int animationState = 0;

	private static BufferedImage javaSux() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new Object().getClass().getResource("assets/imgs/minesphere.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
	
	public double x;
	public double y;
	public double theta;
	private Tank tank;
	
	public Bullet(double x, double y, double theta, Tank tank) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.tank = tank;
	}
	
	public void upate() {
		x += Math.cos(theta);
		y += Math.sin(theta);
	}
	

	@Override
	public int getAnimationState() {
		animationState = (animationState + 1) % ANIMATION_MAX;

		return animationState;
	}

	@Override
	public BufferedImage getSpriteSheet() {
		return SPRITE_SHEET;
	}

	@Override
	public Dimension getSpriteDimension() {
		return SPRITE_DIMENSION;
	}
}
