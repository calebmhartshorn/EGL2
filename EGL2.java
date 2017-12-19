import java.awt.*;
import java.awt.geom.Ellipse2D;
//import java.awt.geom.RectangularShape;
//import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <code>The most epic of graphics libraries</code>
 * 
 * @author Ben Paulson
 * @author Caleb Hartshorn
 */
public class EGL2 extends JPanel {
	
	// Not sure why
	private static final long serialVersionUID = 1L;
	
	// ArrayList of JFrames
	private static ArrayList<JFrame> frames = new ArrayList<JFrame>();
	private static JLabel label;
	
	// Active window. Any methods will function on this window.
	private static int activeWindow = 0;
	
	// Lists to store all the shapes that are drawn on the screen
	private static List<Shape> drawingComponent = new ArrayList<Shape>();
	private static List<Shape> filledDrawingComponent = new ArrayList<Shape>();

	
	private static int FPS;
	//private static long frameRate;
	
	// Array of keys that can be pressed
	private static String[] keysArray = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
									"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
									};
	
	// HashMap where keys are elements from the keysArray, and values are booleans
	// that show whether the key is currently being pressed (true) or not (false)
	private static HashMap<String, Boolean> keysMap = new HashMap<String, Boolean>();
	
	// keyEventsArray is an array of each keyEvent (corresponds to the elements of the keysArray
	// Used for checking if the key has been pressed or released
	private static int[] keyEventsArray = {KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E,
			KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
			KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R, KeyEvent.VK_S,
			KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X, KeyEvent.VK_Y, KeyEvent.VK_Z, 
										};
	
	// addKeys loops through each key in the keysArray and adds it to the key
	// HashMap and automatically sets the value to false (not being pressed)
	
	private static void addKeys() {
		
		for (int i = 0; i < keysArray.length; i++) {
			
			keysMap.put(keysArray[i], false);
		}
	}
	
	/**
	 * <code>public static void init(int windowWidth, int windowHeight)</code></br></br>
	 * 
	 * Initiates EGL2 and sets up and displays window
	 * Must call init() to use any other EGL2 methods
	 * 
	 * @param windowWidth equal to number of pixels horizontal inside frame
	 * @param windowHeight equal to number of pixels vertical inside frame
	 */
	
	public static void init(int windowWidth, int windowHeight) {
			// Add keys to keyMap first
			addKeys();
			
			// Setup and make visible
			setupJFrame(windowWidth, windowHeight);
			makeVisible();
	}

	/**
	 * <code>public static void init()</code></br></br>
	 *  Fullscreen version of init()
	 */
	public static void init() {
		
		// Setup
		setupJFrame(640, 360);
		
		// Set fullscreen
		frames.get(activeWindow).setExtendedState(JFrame.MAXIMIZED_BOTH);
		frames.get(activeWindow).setUndecorated(true);
		
		// Make visible
		makeVisible();	
	}
	
	// Private method show window
	private static void makeVisible() {
		
		// Make a new instance of Draw class & add to frame
		// This will draw every shape on the screen
		Draw dc = new Draw();
		frames.get(activeWindow).add(dc);
		
		// Pack and make visible
		frames.get(activeWindow).pack();
		frames.get(activeWindow).setVisible(true);
		
		// Default location centered
		frames.get(activeWindow).setLocationRelativeTo(null);
	}
	
	// Private method to setup window
	private static void setupJFrame(int windowWidth, int windowHeight) {
			
		// Switches active window to window being creating
		activeWindow = frames.size();
		
		// Create window
		frames.add(new JFrame("EGL2 Window"));
		frames.get(activeWindow).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// If user closes the last window, this will terminate the program
		// Rather than only disposing of the window.
		// Without this, a while (true) loop will keep running
		if (frames.size() == 1) {
			frames.get(activeWindow).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		label = new JLabel();
		frames.get(activeWindow).setMinimumSize(new Dimension(windowWidth, windowHeight));
		label.setPreferredSize(new Dimension(windowWidth, windowHeight));
		//frame.getContentPane().add(label, BorderLayout.PAGE_START);
		frames.get(activeWindow).getContentPane().add(label);
		
		/*
		 * Add a key listener to the frame
		 * 
		 * keyPressed and Released loop through each key/value pair in the
		 * keysMap and set the value to either true/false based on the event that occurred
		 */
		
		frames.get(activeWindow).addKeyListener(new KeyListener() {
			
			// Changes the value of any key pressed to true
			public void keyPressed(KeyEvent e) {
				
				for (int i = 0; i < keyEventsArray.length; i++) {
					
					if (e.getKeyCode() == keyEventsArray[i]) {
						
						keysMap.put(keysArray[i], true);
					}
				}				
			}
			// keyTyped is required else program will not run
			public void keyTyped(KeyEvent e) {}
			
			// Changes the value of any key released to false
			public void keyReleased(KeyEvent e) {
				
				for (int i = 0; i < keyEventsArray.length; i++) {
					
					if (e.getKeyCode() == keyEventsArray[i]) {
						
						keysMap.put(keysArray[i], false);
					}
				}
			}
		});
	}

	/**
	 * <code>public static void exit()</code></br></br>
	 * Closes active window
	 */
	public static void exit() {

		if (activeWindow < frames.size()) {
		
			frames.get(activeWindow).setVisible(false);
			frames.get(activeWindow).dispose();
			frames.remove(activeWindow);
			
			// If closing last window, sets active window to previous window
			if (activeWindow >= frames.size()) {
				
				activeWindow = frames.size() - 1;
			}
		}
	}
	
	/**
	 * <code>public static void setActiveWindow(int window)</code></br></br>
	 * Sets active window
	 * 
	 * @param window int value equal to window to switch to
	 * Windows are zero-indexed
	 * Pass in -1 to active window to last window
	 */
	public static void setActiveWindow(int window) {
		
		if (window == -1) {
			
			activeWindow = frames.size() - 1;
		} else if (window < frames.size()) {
			
			activeWindow = window;
		}
	}

	/**
	 * <code>public static void setTitle(String title)</code></br></br>
	 * Sets title of active window
	 * 
	 * @param title String value equal to new name of window
	 */
	public static void setTitle(String title) {

		if (activeWindow < frames.size()) {
			
			frames.get(activeWindow).setTitle(title);
		}
	}

	// Note: Backslashes need to be replaced with double backslashes in all filenames.
	// Ex: "C:\\Users\\CalebHartshorn\\workspace\\MyProject\\Sprites\\SpriteName.png"
	/**
	 * <code>public static void setIcon(String filepath)</code></br></br>
	 * Sets icon of active window
	 * 
	 * @param filepath equals String value equal to filepath to image.
	 */
	public static void setIcon(String filepath) {

		if (activeWindow < frames.size()) {
			
			ImageIcon img = new ImageIcon(filepath);
			frames.get(activeWindow).setIconImage(img.getImage());
		}
	}

	/**
	 * <code>public static void setPosition(int x, int y)</code></br></br>
	 * Sets location of active window
	 * 
	 * @param x equal to number of pixels horizontal from top left of frame to top left of screen
	 * @param y equal to number of pixels vertical from top left of frame to top left of screen
	 */
	public static void setPosition(int x, int y) {

		if (activeWindow < frames.size()) {
			
			frames.get(activeWindow).setLocation(x, y);
		}
	}

	/**
	 * <code>public static void setPosition()</code></br></br>
	 * Center active window to middle of screen
	 */
	public static void setPosition() {

		if (activeWindow < frames.size()) {
			
			frames.get(activeWindow).setLocationRelativeTo(null);
		}
	}
	
	/**
	 * <code>public static void setResizable(boolean resizable)</code></br></br>
	 * Sets whether screen is resizable by dragging edges
	 * 
	 * @param resizable boolean true if allowing frame to be resizable.
	 * Default true
	 */
	public static void setResizable(boolean resizable) {

		if (activeWindow < frames.size()) {
			
			frames.get(activeWindow).setResizable(resizable);
		}
	}
	
	/**
	 * <code>public static int getWindowWidth()</code></br></br>
	 * @return integer equal to number of pixels horizontal (width) of interior of frame
	 */
	public static int getWindowWidth() {

		if (activeWindow < frames.size()) {
			
			return frames.get(activeWindow).getComponent(0).getWidth();
		} else {

			return 0;
		}
	}
	
	/**
	 * <code>public static int getWindowHeight()</code></br></br>
	 * @return integer equal to number of pixels vertical (height) of interior of frame
	 */
	public static int getWindowHeight() {

		if (activeWindow < frames.size()) {
			
			return frames.get(activeWindow).getComponent(0).getHeight();
			
		} else {

			return 0;
		}
	}
	
	/**
	 * <code>public static int getWindowX()</code></br></br>
	 * @return the window's x coordinate in pixels on the screen
	 */
	public static int getWindowX() {

		if (activeWindow < frames.size()) {
			
			return frames.get(activeWindow).getX();
			
		} else {

			return 0;
		}
	}
	
	/**
	 * <code>public static int getWindowY()</code></br></br>
	 * @return the window's y coordinate in pixels on the screen
	 */
	public static int getWindowY() {

		if (activeWindow < frames.size()) {
			
			return frames.get(activeWindow).getY();
			
		} else {

			return 0;
		}
	}
	
	/**
	 * <code>public static void update(int fps)</code></br></br>
	 * Method to keep a steady fps and update screen
	 * 
	 * @param fps frame rate at which to update the screen
	 */
	public static void update(int fps) {
		
		if (fps <= 0) {
			fps = 60;
		}
		
		setFPS(fps);
		
		try {
			// Thread sleeps for 1 second / fps
			Thread.sleep((long)(1000.0 / getFPS()));
		}
		catch (InterruptedException e) {}
		
	}
	
	// This is the function the user will use for key events
	
	/**
	 * <code>public static boolean getKeyDown(String key)</code></br></br>
	 * Tests whether or not a key is pressed. For example, if "a"
	 * is held down, the return value will be true for "a".
	 * 
	 * @param key String key being tested
	 * @return True if key is pressed down, false if not.
	 */
	public static boolean getKeyDown(String key) {
			
		// User enters a string as a parameter
		// The parameter is a key in the keysMap
		
		// If the value of this key is true, getKeyDown returns
		// true for that key
		if (keysMap.get(key)) {
				
			return true;
		} else {
				
			return false;
		}		
	}
	
	/**
	 * <code>public static int getFPS()</code></br></br>
	 * @return current frame rate
	 */
	public static int getFPS() {
		return FPS;
	}

	private static void setFPS(int fPS) {
		FPS = fPS;
	}

	
	/**
	 * The Draw class is used for drawing shapes to the
	 * screen. Its methods can be accessed through EGL2.Draw
	 *
	 */
	public static class Draw extends JComponent {
		
		// Still not sure why, but I don't like warnings
		private static final long serialVersionUID = 1L;

		/*
		 * paintComponent draws all shapes on the screen (automatically,
		 * does not need to be called) using the drawingComponent List
		 * which holds every shape that is created using any of the following
		 * methods
		 */
		
		public void paintComponent(Graphics g) {
			
			Graphics2D g2 = (Graphics2D) g;
			
			// Draw all the "non-filled" components
			for (int i = 0; i < drawingComponent.size(); i++) {
				
				Object thisShape = drawingComponent.get(i);
				
				g2.draw((Shape) thisShape);
				
			}
			
			// Draw all filled components from the List
			for (int i = 0; i < filledDrawingComponent.size(); i++) {
				
				Object thisFilledShape = filledDrawingComponent.get(i);
				
				g2.fill((Shape) thisFilledShape);
			}
			repaint();
		}
		
		/*
		 * The following methods are used to create new shapes
		 * which are added to the drawingComponent List and then
		 * are all drawin in the paintComponent method
		 * 
		 * boolean filled controls which list the shape is added to,
		 * either the filled components or non-filled components
		 */
		
		/**
		 * <code>public static Rectangle rect(int x, int y, int w, int h, boolean filled)</code></br></br>
		 * This method is used to create a new rectangle and draw it to the screen.
		 * @param x The x position on the screen in which to draw the rectangle
		 * @param y The y position on the screen in which to draw the rectangle
		 * @param w The width of the rectangle
		 * @param h The height of the rectangle
		 * @param filled true if filled, false if not
		 * @return The new Rectangle object
		 */
		public static Rectangle rect(int x, int y, int w, int h, boolean filled) {
			
			// User cannot access the Rectangle constructor - they need to use the rect method
			// to create a new rectangle
			Rectangle rect = new Rectangle(x, y, w, h, filled, filledDrawingComponent, drawingComponent);
			
			// Has been moved to Rectangle class below - no longer needed here.
			// User can now create rectangles without importing java.awt.Rectangle
			/*if (filled) {
				filledDrawingComponent.add(rect);
			} else {
				drawingComponent.add(rect);
			}*/
			
			return rect;
		}
		
		/**
		 * <code>public static Circle circle(int x, int y, int w, int h, boolean filled)</code></br></br>
		 * This method is used to create a new circle and draw it to the screen.
		 * @param x The x position on the screen in which to draw the circle
		 * @param y The y position on the screen in which to draw the circle
		 * @param w The width of the circle
		 * @param h The height of the circle
		 * @param filled true if filled, false if not
		 * @return The new Circle object
		 */
		public static Circle circle(int x, int y, int w, int h, boolean filled) {
			
			Circle newCircle = new Circle(x, y, w, h, filled, filledDrawingComponent, drawingComponent);
			
			return newCircle;
		}
		
	}
	
}

// So that the user does not need to import java.awt.geom.Ellipse2D.Double to create
// a Circle object
/**
 * Class used for creating new Circle objects
 */
class Circle extends EGL2 {
	
	// I don't know
	private static final long serialVersionUID = 1L;
	
	// The shape is actually an Ellipse2D.Double, but since it is
	// in the Circle class, the user can use it as type Circle
	static Ellipse2D.Double thisShape;
		
	// Constructor not accessible to user, need to use circle method in Draw class
	public Circle(int x, int y, int w, int h, boolean filled, List<Shape> filledDrawingComponent, List<Shape> drawingComponent) {
			
		thisShape = new Ellipse2D.Double(x, y, w, h);
			
		// Draw it
		if (filled) {
			filledDrawingComponent.add(thisShape);
		} else {
			drawingComponent.add(thisShape);
		}
			
	}
	
	/**
	 * <code>public void translate(int x, int y)</code></br></br>
	 * Moves the object across the screen speeds and direction indicated by parameters
	 * @param x direction (sign) and speed at which to move in the x direction
	 * @param y direction (sign) and speed at which to move in the y direction
	 */
	public void translate(int x, int y) {
		int currentFPS = getFPS();
		thisShape.x += x / currentFPS;
		thisShape.y += y / currentFPS;
	}
		
}

// So that the user does not need to import java.awt.Rectangle to create
// a Rectangle object
/**
 * Class used for creating new Rectangle objects
 */
class Rectangle extends EGL2 {
	
	// still here
	private static final long serialVersionUID = 1L;
	
	// The shape is actually a java.awt.Rectangle, but since it is
	// in the Rectangle class, the user can use it as type Rectangle without importing java.awt.Rectangle
	static java.awt.Rectangle thisShape;
	
	// Constructor not accessible to user, need to use rect method in Draw class
	public Rectangle(int x, int y, int w, int h, boolean filled, List<Shape> filledDrawingComponent, List<Shape> drawingComponent) {
		
		thisShape = new java.awt.Rectangle(x, y, w, h);
		
		// Draw the shape
		if (filled) {
			filledDrawingComponent.add(thisShape);
		} else {
			drawingComponent.add(thisShape);
		}
	}
	
	/**
	 * <code>public void translate(int x, int y)</code></br></br>
	 * Moves the object across the screen speeds and direction indicated by parameters
	 * @param x direction (sign) and speed at which to move in the x direction
	 * @param y direction (sign) and speed at which to move in the y direction
	 */
	public void translate(int x, int y) {
		int currentFPS = getFPS();
		thisShape.x += x / currentFPS;
		thisShape.y += y / currentFPS;
	}
	
}
