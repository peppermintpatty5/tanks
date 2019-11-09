import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * Bullet
 */
public class Bullet implements AnimatedObj {

	private static final int ANIMATION_MAX = 2;
	private static final BufferedImage SPRITE_SHEET = ImageIO
			.read(new Object().getClass().getResource("assets/imgs/tanks.png"));
	private static final Dimension SPRITE_DIMENSION = null;

	private int animationState = 0;

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
