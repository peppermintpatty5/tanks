import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Thank you stackoverflow :)
 *
 * java is a pile of crap
 */
public class ImageRotation {
	public static BufferedImage rotateImage(BufferedImage sourceImage, double angle) {
		int width = sourceImage.getWidth() * 2;
		int height = sourceImage.getHeight() * 2;

		BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = destImage.createGraphics();

		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, width / 2, height / 2);
		g2d.drawRenderedImage(sourceImage, transform);

		g2d.dispose();
		return destImage;
	}
}
