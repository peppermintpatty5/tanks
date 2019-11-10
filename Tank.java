import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
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

	public int health = 1000, hits = 0, roundsFired = 0, x0, y0;
	public double theta, x, y, v = 100, accuracy, dist = 0, maxDisplacement = 0;
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
		this(100 + RAND.nextInt(400), 100 + RAND.nextInt(400), RAND.nextDouble() * Math.PI * 2, team);
	}

	public Tank(int x, int y, double theta, Teams team) {
		this.x = x;
		this.y = y;
		this.x0 = x;
		this.y0 = y;
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

	public Bullet getClosestThreat() {
		return Main.bullets.stream().filter(b -> b.tank.team != team)
				.sorted((a, b) -> getRadius(a) < getRadius(b) ? -1 : 1).findFirst().orElse(null);
	}

	/**
	 * Radius between this tank and some bullet.
	 */
	public double getRadius(Bullet b) {
		return Math.sqrt(Math.pow(x - b.x, 2) + Math.pow(y - b.y, 2));
	}

	/**
	 * Get angle in radians. 0 is a head-on threat.
	 */
	public double getAngle(Bullet b) {
		return Math.abs(theta - (b.theta + Math.PI));
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
		dist += v * (1.0 / 60);

		maxDisplacement = Math.max(maxDisplacement, Math.pow(x - x0, 2) + Math.pow(y - y0, 2));

		theta += 0.3;

		Main.bullets.add(new Bullet(x + 70 * Math.cos(theta) + 24, y + 70 * Math.sin(theta) + 28, theta, this));
		roundsFired++;

		accuracy = (double) hits / roundsFired;
		System.out.println("Accuracy: " + accuracy);
	}

	public void reset() {
		health = 3;
		hits = 0;
		roundsFired = 0;
		v = 100;
		accuracy = 0.0d;
		animationState = 0;
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
		return new Dimension(32, 32);
	}
}
