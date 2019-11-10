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

	/**
	 * Get offset from top left corner.
	 */
	Dimension getSpriteOffset();

	default void drawMyself(Graphics2D g2, int x, int y, double theta) {
		var i = getAnimationState();
		var d = getSpriteDimension();
		var subimg = getSpriteSheet().getSubimage(d.width * i, 0, d.width, d.height);

		((Graphics2D) (subimg.getGraphics())).rotate(Math.PI, 16, 16);
		g2.drawImage(subimg, x, y, null);
	}
}
