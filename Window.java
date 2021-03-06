import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/*
 * Name: Shane Arcaro
 * File: Window.java
 * Date: Nov 9, 2019
 * Description: 
*/

/**
 * Window class that extends JFrame. Allow for the basic construction of a JFrame as well
 * as leaving out complicated coding in other areas.
 * @author Shane
 *
 */
public class Window extends JFrame {
	
	/**
	 * Generated Serial Version Identity Number
	 */
	private static final long serialVersionUID = 1493862333646063873L;
	
	/**
	 * Default Title attribute
	 */
	private static final String DEFAULT_TITLE = "Window";
	
	/**
	 * Default Width attribute
	 */
	private static final int DEFAULT_WIDTH = 650;
	
	/**
	 * Default Height attribute
	 */
	private static final int DEFAULT_HEIGHT = 500;
	
	/**
	 * Main full parameter constructor
	 * @param title the title to set
	 * @param width the width to set
	 * @param height the height to set
	 */
	public Window(String title, int width, int height) {
		super(title);
		this.setPreferredSize(new Dimension(width, height));
		this.pack();
		this.windowInit();
	}
	
	/**
	 * Default title constructor
	 * @param width the width to set
	 * @param height the height to set
	 */
	public Window(int width, int height) {
		this(Window.DEFAULT_TITLE, width, height);
	}
	
	/**
	 * Default size constructor
	 * @param title the title to set
	 */
	public Window(String title, boolean extendedState) {
		super(title);
		super.setExtendedState(JFrame.MAXIMIZED_BOTH);
		super.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		super.pack();
		this.windowInit();
	}
	
	
	/**
	 * Default constructor
	 */
	public Window() {
		this(Window.DEFAULT_TITLE, Window.DEFAULT_WIDTH, Window.DEFAULT_HEIGHT);
	}
	
	/**
	 * Initializer constructor
	 */
	public void windowInit() {
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLocationRelativeTo(null);
		super.setResizable(false);
		super.setVisible(true);
	}
}


