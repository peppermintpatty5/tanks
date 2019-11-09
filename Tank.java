/*
 * Name: Shane Arcaro
 * File: Tank.java
 * Date: Nov 9, 2019
 * Description: 
 */

public class Tank {
	private int x, y;
	private double theta;
	private Brain brain = new Brain(3, 2, 1);

	public Tank(int x, int y, double theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}
	
	public Brain getBrain() {
		return brain;
	}
}


