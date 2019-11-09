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

	public int x, y, health;
	public double theta;
	public Teams team;

	private Brain brain = new Brain(5, 3, 4);

	private static final Random RAND = new Random();
	private static final int ANIMATION_MAX = 2;
	private static final Map<Teams, BufferedImage> SPRITE_SHEET_MAP = new HashMap<>();
	private static final Dimension SPRITE_DIMENSION = new Dimension(104, 64);

	static {
		for (var team : Teams.values())
			try {
				SPRITE_SHEET_MAP.put(team, ImageIO
						.read((Tank.class.getResource("assets/imgs/" + team.toString().toLowerCase() + "_tank.png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

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
	}

	public Brain getBrain() {
		return brain;
	}
	
	public void update() {
		brain.sendInputs(new double[] {1, 2, 3});
		brain.randomizeWeights();
		brain.generateOutput();
		
		x += brain.getOutput()[0] == 0 ? - 1 : + 1;
		y += brain.getOutput()[1] == 0 ? - 1 : + 1;
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
