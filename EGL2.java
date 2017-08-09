import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
//import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class EGL2 extends JPanel {

	// ArrayList of JFrames
	private static ArrayList<JFrame> frames = new ArrayList<JFrame>();
	private static JLabel label;
	
	// Active window. Any methods will function on this window.
	private static int activeWindow = 0;
	
	// Lists to store all the shapes that are drawn on the screen
	private static List<Shape> drawingComponent = new ArrayList<Shape>();
	private static List<Shape> filledDrawingComponent = new ArrayList<Shape>();

	// Initiates EGL2 and sets up and displays window
	// Must call init() to use any other EGL2 methods
	//
	// Parameter "windowWidth" equal to number of pixels horizontal inside frame
	// Parameter "windowHeight" equal to number of pixels vertical inside frame
	public static void init(int windowWidth, int windowHeight) {

			// Setup and make visible
			setupJFrame(windowWidth, windowHeight);
			makeVisible();
	}

	// Fullscreen version of init()
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
		label = new JLabel();
		frames.get(activeWindow).setMinimumSize(new Dimension(windowWidth, windowHeight));
		label.setPreferredSize(new Dimension(windowWidth, windowHeight));
		//frame.getContentPane().add(label, BorderLayout.PAGE_START);
		frames.get(activeWindow).getContentPane().add(label);
	}

	// Closes active window
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
	
	// Sets active window
	//
	// Parameter "window" int value equal to window to switch to
	// Windows are zero-indexed
	// Pass in -1 to active window to last window
	public static void setActiveWindow(int window) {
		
		if (window == -1) {
			
			activeWindow = frames.size() - 1;
		} else if (window < frames.size()) {
			
			activeWindow = window;
		}
	}

	// Sets title of active window
	//
	// Parameter "title" String value equal to new name of window
	public static void setTitle(String title) {

		if (activeWindow < frames.size()) {
			
			frames.get(activeWindow).setTitle(title);
		}
	}

	// Sets icon of active window
	//
	// Parameter "filepath" equals String value equal to filepath to image.
	// Note: Backslashes need to be replaced with double backslashes in all filenames.
	// Ex: "C:\\Users\\CalebHartshorn\\workspace\\MyProject\\Sprites\\SpriteName.png"
	public static void setIcon(String filepath) {

		if (activeWindow < frames.size()) {
			
			ImageIcon img = new ImageIcon(filepath);
			frames.get(activeWindow).setIconImage(img.getImage());
		}
	}

	// Sets location of active window
	//
	// Parameter "x" equal to number of pixels horizontal from top left of frame to top left of screen
	// Parameter "y" equal to number of pixels vertical from top left of frame to top left of screen
	public static void setPosition(int x, int y) {

		if (activeWindow < frames.size()) {
			
			frames.get(activeWindow).setLocation(x, y);
		}
	}

	// Center active window to middle of screen
	public static void setPosition() {

		if (activeWindow < frames.size()) {
			
			frames.get(activeWindow).setLocationRelativeTo(null);
		}
	}

	// Sets whether screen is resizable by dragging edges
	//
	// Parameter "resizable" boolean true if allowing frame to be resizable.
	// Default true
	public static void setResizable(boolean resizable) {

		if (activeWindow < frames.size()) {
			
			frames.get(activeWindow).setResizable(resizable);
		}
	}

	// Returns integer equal to number of pixels horizontal (width) of interior of frame
	public static int getWindowWidth() {

		if (activeWindow < frames.size()) {
			
			return frames.get(activeWindow).getComponent(0).getWidth();
		} else {

			return 0;
		}
	}

	// Returns integer equal to number of pixels vertical (height) of interior of frame
	public static int getWindowHeight() {

		if (activeWindow < frames.size()) {
			
			return frames.get(activeWindow).getComponent(0).getHeight();
		} else {

			return 0;
		}
	}
	
	static class Draw extends JComponent {
		
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
			
		}
		
		/*
		 * The following methods are used to create new shapes
		 * which are added to the drawingComponent List and then
		 * are all drawin in the paintComponent method
		 * 
		 * boolean filled controls which list the shape is added to,
		 * either the filled components or non-filled components
		 */
		
		public static Rectangle rect(int x, int y, int w, int h, boolean filled) {
			
			Rectangle rect = new Rectangle(x, y, w, h);
			
			if (filled) {
				filledDrawingComponent.add(rect);
			} else {
				drawingComponent.add(rect);
			}
			
			return rect;
		}
		
		public static void circle(int x, int y, int r, boolean filled) {
			
			Ellipse2D.Double circle = new Ellipse2D.Double(x, y, r * 2, r * 2);
			
			if (filled) {
				filledDrawingComponent.add(circle);
			} else {
				drawingComponent.add(circle);
			}
		}
		
		public static void oval(int x, int y, int w, int h, boolean filled) {
			
			Ellipse2D.Double oval = new Ellipse2D.Double(x, y, w, h);
			
			if (filled) {
				filledDrawingComponent.add(oval);
			} else {
				drawingComponent.add(oval);
			}
		}
		
	}
}