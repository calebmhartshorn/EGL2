
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;

public class EGL2 extends JPanel{

	// ArrayList of JFrames
	private static ArrayList<JFrame> frames = new ArrayList<JFrame>();
	
	// Active window. Any methods will function on this window.
	private static int activeWindow = 0;

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
		EGL2 panel = new EGL2();
		panel.setPreferredSize(new Dimension(windowWidth, windowHeight));
		frames.add(new JFrame("EGL2 Window - " + activeWindow));
		frames.get(activeWindow).getContentPane().add(panel);
		frames.get(activeWindow).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
}
