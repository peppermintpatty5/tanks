import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Animation
 */
public interface AnimatedObj {

	int getAnimationState();

	BufferedImage getSpriteSheet();

	Dimension getSpriteDimension();

	default void drawMyself(Graphics2D g2, int x, int y, double theta) {
		var i = getAnimationState();
		var d = getSpriteDimension();
		var subimg = getSpriteSheet().getSubimage(d.width * i, 0, d.width, d.height);
		
		AffineTransform backup = g2.getTransform();
	    AffineTransform rotation = AffineTransform.getRotateInstance(-theta, subimg.getWidth() / 2, subimg.getHeight() / 2);
	    g2.setTransform(rotation);
	    g2.drawImage(subimg, x, y, null);
	    g2.setTransform(backup);

	}
}
