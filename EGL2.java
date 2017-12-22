import java.awt.*;
//import java.awt.geom.Ellipse2D;
//import java.awt.geom.RectangularShape;
//import java.awt.Dimension;
import java.util.ArrayList;
//import java.util.List;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.CopyOnWriteArrayList;

//import DrawingTest.PaintPanel;

/**
 * <code>The most epic of graphics libraries</code>
 * 
 * @author Ben Paulson
 * @author Caleb Hartshorn
 */
public class EGL2 { // No longer extends JPanel. Instead is used to manage frames.
					// Panel is made to draw shapes on.
	
	public static EGL2 egl2;
	public static Color BLACK = new Color(0, 0, 0);
	public static Color BLUE = new Color(0, 0, 255);
	
	// ArrayList of JFrames
	protected static ArrayList<JFrame> frames = new ArrayList<JFrame>();
	
	// Active window. Any methods will function on this window.
	protected static int activeWindow = 0;
	
	// No longer need filled/drawingComponent arraylists
	
	// Need to use CopyOnWriteArrayList to avoid ConcurrentModificationException
	protected static CopyOnWriteArrayList<Rectangle> rects = new CopyOnWriteArrayList<Rectangle>();
	protected static CopyOnWriteArrayList<Circle> circles = new CopyOnWriteArrayList<Circle>();

	
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
		
		// No longer need to add Draw class to the frame because
		// shapes are not drawn in this class.
		
		/*Make a new instance of Draw class & add to frame
		This will draw every shape on the screen
		Draw dc = new Draw();
		frames.get(activeWindow).add(dc);*/
		
		// Pack and make visible (now in EGL2Window class)
		//frames.get(activeWindow).pack();
		//frames.get(activeWindow).setVisible(true);
		
		// Default location centered
		frames.get(activeWindow).setLocationRelativeTo(null);
	}
	
	// Private method to setup window
	private static void setupJFrame(int windowWidth, int windowHeight) {
			
		// Switches active window to window being creating
		activeWindow = frames.size();
		
		// Create window
		//frames.add(new JFrame("EGL2 Window"));
		frames.add(new EGL2Window(windowWidth, windowHeight));
		frames.get(activeWindow).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// If user closes the last window, this will terminate the program
		// Rather than only disposing of the window.
		// Without this, a while (true) loop will keep running
		if (frames.size() == 1) {
			frames.get(activeWindow).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}


		// setMinimumSize now in EGL2Window class
		//label.setPreferredSize(new Dimension(windowWidth, windowHeight));
		//frame.getContentPane().add(label, BorderLayout.PAGE_START);
		//frames.get(activeWindow).getContentPane().add(panel);
		
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
		
		/*int newRects = rects.size() - numRects;
		
		if (newRects > 0) {
			frames.get(activeWindow).getContentPane().removeAll();
			DrawPanel drawables = new DrawPanel();
			frames.get(activeWindow).getContentPane().add(drawables, BorderLayout.CENTER);
			frames.get(activeWindow).pack();
		}*/
		
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
	
	/*public void paint(Graphics2D g) {
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, 1000, 1000);
		repaint();
	}*/

	
	/**
	 * The Draw class is used for drawing shapes to the
	 * screen. Its methods can be accessed through EGL2.Draw
	 *
	 */
	public static class Draw extends JComponent {
		
		// Still not sure why, but I don't like warnings
		private static final long serialVersionUID = 1L;

		// Old paintComponent()
		
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
		public static Rectangle rect(int x, int y, int w, int h, boolean filled, Color color) {
			
			// User cannot access the Rectangle constructor - they need to use the rect method
			// to create a new rectangle
			Rectangle rect = new Rectangle(x, y, w, h, filled, color);
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
		public static Circle circle(int x, int y, int w, int h, boolean filled, Color color) {
			
			// Does the same thing as rect()
			Circle newCircle = new Circle(x, y, w, h, filled, color);
			return newCircle;
		}
		
	}
	
}

// So that the user does not need to import java.awt.geom.Ellipse2D.Double to create
// a Circle object
/**
 * Class used for creating new Circle objects.
 * The circle's x, y, width, and height variables
 * are mutable.</br></br>
 * 
 * Example:</br>
 * <code>circle.x += 4;</code>
 */
class Circle {
	
	boolean filled;
	Color color;
	int x, y, w, h;
		
	/**
	 * <code>public Circle(int _x, int _y, int _w, int _h, boolean fill, Color _color)</code></br></br>
	 * This constructor can be used to create a new circle. It will
	 * operate the same as the EGL2.Draw.circle() method
	 * @param _x coordinate
	 * @param _y coordinate
	 * @param _w width of circle
	 * @param _h height of circle
	 * @param fill will be filled in if true, outlined if false
	 * @param _color color of the circle
	 */
	public Circle(int _x, int _y, int _w, int _h, boolean fill, Color _color) {
			
		super();
		color = _color;
		filled = fill;
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		
		// Add this object to list of circles, to be drawn later
		EGL2.circles.add(this);
			
	}
	
	/**
	 * <code>public void setColor(Color c)</code></br></br>
	 * Change the color of the circle
	 * @param c - Color to change to
	 */
	public void setColor(Color c) {
		color = c;
	}
		
}

// So that the user does not need to import java.awt.Rectangle to create
// a Rectangle object
/**
 * Class used for creating new Rectangle objects.
 * The rectangle's x, y, width, and height variables
 * are mutable.</br></br>
 * 
 * Example:</br>
 * <code>rect.x += 4;</code>
 */
class Rectangle {
	
	boolean filled;
	Color color;
	int x, y, w, h;
	
	/**
	 * <code>public Rectangle(int _x, int _y, int _w, int _h, boolean fill, Color _color)</code></br></br>
	 * This constructor can be used to create a new rectangle. It will
	 * operate the same as the EGL2.Draw.rect() method
	 * @param _x coordinate
	 * @param _y coordinate
	 * @param _w width of rectangle
	 * @param _h height of rectangle
	 * @param fill will be filled in if true, outlined if false
	 * @param _color color of the rectangle
	 */
	public Rectangle(int _x, int _y, int _w, int _h, boolean fill, Color _color) {
		super();
		filled = fill;
		color = _color;
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		
		// Add to list of rects, to be drawn later
		EGL2.rects.add(this);
	}
	
	/**
	 * <code>public void setColor(Color c)</code></br></br>
	 * Changes the color of the rectangle
	 * @param c - Color to change to
	 */
	public void setColor(Color c) {
		color = c;
	}
	
}

/**
 * This class is the panel that all the shapes are drawn on.
 * Do not use this class. To create a new shape, use the EGL2.Draw
 * class instead.
 */
class DrawPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Should not be called. This method updates shapes. To create a new shape,
	 * use the EGL2.Draw class instead
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (Rectangle rect : EGL2.rects) {
			g2.setColor(rect.color);
			g2.fillRect(rect.x, rect.y, rect.w, rect.h);
		}
		
		for (Circle circ : EGL2.circles) {
			g2.setColor(circ.color);
			g2.fillOval(circ.x, circ.y, circ.w, circ.h);
		}
		
		repaint();
	}
}

/**
 * This class creates a new EGL2 window. Although it can be used,
 * it is recommended to use the EGL2.init() method instead.
 */
class EGL2Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Can be used to create a new window, but it is recommended
	 * to use EGL2.init() instead.
	 * @param windowWidth width of the new window
	 * @param windowHeight height of the new window
	 */
	public EGL2Window(int windowWidth, int windowHeight) {
		// was in setupJFrame()
        setMinimumSize(new Dimension(windowWidth, windowHeight));
        // Adds the shapes to the window
        DrawPanel drawables = new DrawPanel();
        getContentPane().add(drawables, BorderLayout.CENTER);
        // was in makeVisible()
        pack();
        setVisible(true);
        // Default title, can be changed using setTitle()
        setTitle("EGL2 Window");
        
	}
	
}