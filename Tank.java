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

	public double x, y, health;
	public double theta;
	public Teams team;

	private Brain brain = new Brain(5, 4, 3);

	private static final Random RAND = new Random();
	private static final int ANIMATION_MAX = 2;
	private static final Map<Teams, BufferedImage> SPRITE_SHEET_MAP = new HashMap<>();
	private static final Dimension SPRITE_DIMENSION = new Dimension(104, 64);
	private int animationState = 0;

	static {
		for (var team : Teams.values())
			try {
				SPRITE_SHEET_MAP.put(team, ImageIO
						.read((Tank.class.getResource("assets/imgs/" + team.toString().toLowerCase() + "_tank.png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Default constructor randomly chooses param values.
	 */
	public Tank(Teams team) {
		this(RAND.nextInt(400) + 200, RAND.nextInt(400) + 200, RAND.nextDouble() * Math.PI * 2, team);
	}

	public Tank(int x, int y, double theta, Teams team) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.team = team;
	}

	public Brain getBrain() {
		return brain;
	}
	
	public void shoot(List<Bullet> bullets) {
		bullets.add(new Bullet(x, y, theta, this));
	}
	
	public void update() {
		brain.sendInputs(new double[] {1, 2, 3});
		brain.randomizeWeights();
		brain.generateOutput();
		
		x += brain.getOutput()[0] == 0 ? - Math.cos(-theta) : + Math.cos(-theta);
		y += brain.getOutput()[1] == 0 ? - Math.sin(-theta) : + Math.sin(-theta);
		theta += brain.getOutput()[2] == 0 ? - 0.1 : + 0.1;
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
