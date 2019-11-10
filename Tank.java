import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Tank
 */
public class Tank implements AnimatedObj {

	public enum Teams {
		BLUE, RED
	};

	public int health;
	public double theta, x, y, v = 20;
	public Teams team;

	private Brain brain = new Brain(5, 3, 4);

	private static final Random RAND = new Random();
	private static final int ANIMATION_MAX = 2;
	private static final Map<Teams, BufferedImage> SPRITE_SHEET_MAP = new HashMap<>();
	private static final Dimension SPRITE_DIMENSION = new Dimension(104, 64);

	private int animationState = 0;

	/**
	 * Default constructor randomly chooses param values.
	 */
	public Tank(Teams team) {
		this(RAND.nextInt(100), RAND.nextInt(100), RAND.nextDouble() * Math.PI * 2, team);
	}

	public Tank(int x, int y, double theta, Teams team) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.team = team;

		if (SPRITE_SHEET_MAP.isEmpty())
			for (var t : Teams.values())
				try {
					SPRITE_SHEET_MAP.put(t, ImageIO
							.read((Tank.class.getResource("assets/imgs/" + t.toString().toLowerCase() + "_tank.png"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

	public Brain getBrain() {
		return brain;
	}
	
	public void shoot(List<Bullet> bullets) {
		bullets.add(new Bullet(x, y, theta, this));
	}

	public void update() {
		brain.sendInputs(new double[] { 1, 2, 3 });
		brain.randomizeWeights();
		brain.generateOutput();

		// x += brain.getOutput()[0] == 0 ? -1 : +1;
		// y += brain.getOutput()[1] == 0 ? -1 : +1;

		double a_f = -5;
		v += (1.0 / 60) * a_f;

		System.out.println(v);

		x += Math.cos(theta) * v * (1.0 / 60);
		y += Math.sin(theta) * v * (1.0 / 60);
	}

	@Override
	public int getAnimationState() {
		animationState = (animationState + 1) % (ANIMATION_MAX * 6);

		return animationState / 6;
	}

	@Override
	public BufferedImage getSpriteSheet() {
		return SPRITE_SHEET_MAP.get(team);
	}

	@Override
	public Dimension getSpriteDimension() {
		return SPRITE_DIMENSION;
	}
}
