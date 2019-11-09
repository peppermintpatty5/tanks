import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Animation
 */
public interface AnimatedObj {

	int getAnimationState();

	BufferedImage getSpriteSheet();

	Dimension getSpriteDimension();

	default void drawMyself(Graphics2D g2) {

	}
}
