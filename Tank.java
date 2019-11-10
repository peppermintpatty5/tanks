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

	public int health = 17, hits = 0;
	public double theta, x, y, v = 100;
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
		this(700 + RAND.nextInt(500), 300 + RAND.nextInt(500), RAND.nextDouble() * Math.PI * 2, team);
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

	public void update() {
		double a_f = -20;

		brain.sendInputs(new double[] { 1, 2, 3 });
		brain.randomizeWeights();
		brain.generateOutput();

		a_f += brain.getOutput()[0] == 0 ? -5 : +5;

		if (v >= 0)
			v += (1.0 / 60) * a_f;
		else
			v = 0;

		x += Math.cos(theta) * v * (1.0 / 60);
		y += Math.sin(theta) * v * (1.0 / 60);

		theta += 0.3;

		Main.bullets.add(new Bullet(x, y, theta, this));
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

	@Override
	public Dimension getSpriteOffset() {
		return new Dimension(16, 16);
	}
}
