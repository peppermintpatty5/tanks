/**
 * Bullet
 */
public class Bullet implements AnimatedObj {

	private static final int ANIMATION_MAX = 2;

	private int animationState = 0;

	@Override
	public int getAnimationState() {
		animationState = (animationState + 1) % ANIMATION_MAX;

		return animationState;
	}
}
