import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Tank
 */
public class Tank implements AnimatedObj {
	private int x, y;
	private double theta;
	private Brain brain = new Brain(3, 2, 1);

	private static final Random RAND = new Random();
	private static final int ANIMATION_MAX = 2;
	private static final BufferedImage SPRITE_SHEET = javaSux();
	private static final Dimension SPRITE_DIMENSION = new Dimension(104, 64);

	private static BufferedImage javaSux() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Tank.class.getResource("/assets/imgs/red_tank.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	private int animationState = 0;

	/**
	 * Default constructor randomly chooses param values.
	 */
	public Tank() {
		this(RAND.nextInt(100), RAND.nextInt(100), RAND.nextDouble() * Math.PI * 2);
	}

	public Tank(int x, int y, double theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}

	@Override
	public int getAnimationState() {
		animationState = (animationState + 1) % ANIMATION_MAX;

		return animationState;
	}

	public Brain getBrain() {
		return brain;
	}

	@Override
	public BufferedImage getSpriteSheet() {
		return SPRITE_SHEET; // make dynamic
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	@Override
	public Dimension getSpriteDimension() {
		return SPRITE_DIMENSION;
	}
}
