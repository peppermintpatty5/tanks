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

	default void drawMyself(Graphics2D g2, int x, int y) {
		var i = getAnimationState();
		var d = getSpriteDimension();
		var subimg = getSpriteSheet().getSubimage(d.width * i, 0, d.width, d.height);

		g2.drawImage(subimg, x, y, null);
	}
}
