import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Bullet
 *
 */
public class Bullet implements AnimatedObj {

	private static final int ANIMATION_MAX = 8;
	private static BufferedImage SPRITE_SHEET = null;
	private static final Dimension SPRITE_DIMENSION = new Dimension(32, 32);
	private int animationState = 0;

	public double x, y, theta;
	private Tank tank;

	public Bullet(double x, double y, double theta, Tank tank) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.tank = tank;

		try {
			if (SPRITE_SHEET == null)
				SPRITE_SHEET = ImageIO.read(Bullet.class.getResource("assets/imgs/minesphere.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		x += Math.cos(theta) * 100;
		y += Math.sin(theta) * 100;
		System.out.println(Math.cos(theta) + ", shit , " + Math.sin(theta));
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
