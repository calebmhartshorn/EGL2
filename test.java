//import javax.swing.JFrame;
// No longer need any of these for it to work
//import java.awt.Frame;
//import java.awt.Rectangle;
//import java.awt.Rectangle;
//import java.awt.geom.Ellipse2D;
//import java.awt.geom.Ellipse2D.Double;

//import javax.swing.JFrame;


public class test {

	public static void main(String[] args) {
		/*JFrame window = new JFrame();
		window.setSize(300, 300);
		window.setTitle("det");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawingComponents dc = new drawingComponents();
		window.add(dc);
		window.setVisible(true);*/
		EGL2.init(500, 300);
		int rectSpeed = 250;
		int windowSpeed = 4;

		//Ellipse2D.Double det = EGL2.Draw.oval(50, 50, 20, 20, false);
		//Rectangle stuff = EGL2.Draw.rect(100, 100, 100, 100, false);
		Rectangle stuff = EGL2.Draw.rect(100, 100, 100, 100, true);
		Circle thing = EGL2.Draw.circle(0, 0, 20, 20, false);
		int windowX = EGL2.getWindowX();
		int windowY = EGL2.getWindowY();
		
		//stuff.x += 500;
		while (true) {
			
			EGL2.update(60);
			
			thing.translate(300, 0);
			
			if (EGL2.getKeyDown("a")) {	
				stuff.translate(-rectSpeed, 0);
			}
			if (EGL2.getKeyDown("d")) {
				stuff.translate(rectSpeed, 0);
			}
			if (EGL2.getKeyDown("w")) {
				stuff.translate(0, -rectSpeed);
			}
			if (EGL2.getKeyDown("s")) {
				stuff.translate(0, rectSpeed);
			}
			
			if (EGL2.getKeyDown("j")) {
				windowX -= windowSpeed;
				EGL2.setPosition(windowX, windowY);
				stuff.translate(rectSpeed, 0);
			}
			if (EGL2.getKeyDown("l")) {
				windowX += windowSpeed;
				EGL2.setPosition(windowX, windowY);
				stuff.translate(-rectSpeed, 0);
			}
			if (EGL2.getKeyDown("i")) {
				windowY -= windowSpeed;
				EGL2.setPosition(windowX, windowY);
				stuff.translate(0, rectSpeed);
			}
			if (EGL2.getKeyDown("k")) {
				windowY += windowSpeed;
				EGL2.setPosition(windowX, windowY);
				stuff.translate(0, -rectSpeed);
			}
		}
	}

}
