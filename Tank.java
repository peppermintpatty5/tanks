import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
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

	public int health = 3, hits = 0, roundsFired = 0, x0, y0;
	public double theta, x, y, v = 100, accuracy, dist = 0, maxDisplacement = 0, midDist;
	public Teams team;
	
	public boolean isEnabled = true;
	
	private int cooldown = 30;

	private Brain brain = new Brain(7, 6, 3);

	private static final Random RAND = new Random();
	private static final int ANIMATION_MAX = 2;
	private static final Map<Teams, BufferedImage> SPRITE_SHEET_MAP = new HashMap<>();
	private static final Dimension SPRITE_DIMENSION = new Dimension(104, 64);

	private int animationState = 0;

	/**
	 * Default constructor randomly chooses param values.
	 */
	public Tank(Teams team) {
		this(200 + RAND.nextInt(1200), 200 + RAND.nextInt(500), RAND.nextDouble() * Math.PI * 2, team);
	}

	public Tank(int x, int y, double theta, Teams team) {
		this.x = x;
		this.y = y;
		this.x0 = x;
		this.y0 = y;
		this.theta = theta;
		this.team = team;
		brain.randomizeWeights();

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
		return Math.abs(theta - (b.theta + Math.PI)) % (2 * Math.PI);
	}

	public Brain getBrain() {
		return brain;
	}

	public void update() {
		if(!isEnabled)
			return;
		double a_f = -2;
		
		Bullet b = getClosestThreat();
		
		double radius = b != null ? getRadius(b) : 1 / Double.POSITIVE_INFINITY;
		double angle = b != null ? getAngle(b) : Math.PI;
		
		x += Math.cos(theta) * v * (1.0 / 60);
		y += Math.sin(theta) * v * (1.0 / 60);
		dist += v * (1.0 / 60);
		
		if (v >= 0)
			v += (1.0 / 60) * a_f;
		else
			v = 0;
		
		midDist = Math.sqrt(Math.pow(1920 / 2 - x, 2) + Math.pow(1080 / 2 - y, 2));

		maxDisplacement = Math.max(maxDisplacement, Math.sqrt(Math.pow(x - x0, 2) + Math.pow(y - y0, 2)));

		brain.sendInputs(new double[] { midDist, radius, angle, accuracy, dist, maxDisplacement, hits });
		
		double fitness = roundsFired * 10 + hits * 3 + Main.frames * 0.1 + health * 15;
		brain.setFitness(fitness > 0 ? fitness : 0);
		
		brain.generateOutput();
//		System.out.println("Fitness: " + brain.getFitness());
//		System.out.println("Input: " + Arrays.toString(brain.getInput()));
//		System.out.println("Hidden: " + Arrays.toString(brain.getHidden()));
//		System.out.println("Output: " + Arrays.toString(brain.getOutput()));
//		System.out.println("Weigths: " + Arrays.toString(brain.getWeights()));
//		System.out.println("Accuracy: " + accuracy);
//		System.out.println("Frames: " + Main.frames);
//		System.out.println("Seconds: " + Main.seconds);
//		System.out.println();
		
		if(x + 104 < 0 || x > 1920 || y + 32 < 0 || y > 1080) {
			health = 0;
			brain.setFitness(brain.getFitness() * .1);
		}

//		a_f += brain.getOutput()[0] < 0 ? - 5 : + 5;
//		theta += brain.getOutput()[1] < 0 ? - 0.1 : + 0.1;
		a_f += brain.getOutput()[0] == 0 ? 0 : brain.getOutput()[0] < 0 ? - 5 : + 5;
		theta += brain.getOutput()[1] == 0 ? 0 : brain.getOutput()[1] < 0 ? - 0.05 : + 0.05;
		
		cooldown = (cooldown + 1) % 31;
//		System.out.println("Cooldown: " + cooldown);
		if(brain.getOutput()[2] == 1 && cooldown >= 30) {
			Main.bullets.add(new Bullet(x + 70 * Math.cos(theta) + 24, y + 70 * Math.sin(theta) + 28, theta, this));
			roundsFired++;
			cooldown = 0;
		}

		accuracy = roundsFired > 0 ? (double) hits / roundsFired : 0;
		
	}

	public void reset() {
		health = 3;
		hits = 0;
		roundsFired = 0;
		v = 100;
		accuracy = 0.0d;
		animationState = 0;
		isEnabled = true;
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
