import java.awt.Color;

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
		//Rectangle taht = EGL2.Draw.rect(0, 0, 1000, 1000, true, Color.WHITE);
		Rectangle stuff = EGL2.Draw.rect(100, 100, 100, 100, true, EGL2.BLUE);
		Rectangle small = EGL2.Draw.rect(250, 200, 10, 40, true, Color.CYAN);
		Circle thing = EGL2.Draw.circle(0, 0, 20, 20, false, EGL2.BLACK);
		//Circle another = EGL2.Draw.circle(60, 90, 40, 70, true, Color.GREEN);
		int windowX = EGL2.getWindowX();
		int windowY = EGL2.getWindowY();
		//EGL2.setBackgroundColor(Color.DARK_GRAY);
		EGL2.setResizable(true);
		Line yet = EGL2.Draw.line(50, 50, 150, 200, Color.BLUE);
		Line grow = EGL2.Draw.line(300, 550, 400, 350, Color.BLACK);
		Line a = EGL2.Draw.line(60, 80, 90, 110, Color.MAGENTA);
		Gradient grad = EGL2.Draw.gradient(0, 0, 100, 100, 0, 0, Color.BLUE, 100, 100, Color.RED, true, "circle");
		Gradient grad1 = EGL2.Draw.gradient(0, 100, 100, 100, 0, 100, Color.BLUE, 100, 100, Color.RED, false, "rectangle");
		Image im = EGL2.Draw.image(150, 150, "sonicExplosion04.png"); // in bin folder
		
		//stuff.x += 500;
		while (true) {
			System.out.println(im.w);
			EGL2.update(60);
			grad.translate(4, 0);
			grad1.translate(4, 0);
			thing.translate(windowSpeed, 0);
			
			if (thing.x > EGL2.getWindowWidth() / 2) {
				thing.hide();
			}
			
			//stuff.translate(rectSpeed, 0);
			//yet.x1 += 1;
			//yet.translate(0, -1);
			//System.out.println(stuff.color);
			
			if (EGL2.getKeyDown("a")) {	
				if (im.w != 100) {
					im.distort(-1, 0);
				}
				//ConcurrentModificationException used to occur
				EGL2.Draw.rect(50, 10, 10, 70, true, Color.YELLOW);
				//stuff.translate(-rectSpeed, 0);
				//stuff.x += -windowSpeed;
				//im.translate(-400, 0);
				//stuff.setColor(Color.BLACK);
			}
			if (EGL2.getKeyDown("d")) {
				//stuff.translate(rectSpeed, 0);
				//im.x += windowSpeed;
				stuff.setColor(Color.RED);
			}
			if (EGL2.getKeyDown("w")) {
				//stuff.translate(0, -rectSpeed);
				//im.y += -windowSpeed;
			}
			if (EGL2.getKeyDown("s")) {
				//stuff.translate(0, rectSpeed);
				//im.y += windowSpeed;
			}
			
			if (EGL2.getKeyDown("j")) {
				windowX -= windowSpeed;
				EGL2.setPosition(windowX, windowY);
				//stuff.translate(rectSpeed, 0);
				stuff.x += windowSpeed;
			}
			if (EGL2.getKeyDown("l")) {
				windowX += windowSpeed;
				EGL2.setPosition(windowX, windowY);
				//stuff.translate(-rectSpeed, 0);
				stuff.x -= windowSpeed;
			}
			if (EGL2.getKeyDown("i")) {
				windowY -= windowSpeed;
				EGL2.setPosition(windowX, windowY);
				//stuff.translate(0, rectSpeed);
				stuff.y += windowSpeed;
			}
			if (EGL2.getKeyDown("k")) {
				windowY += windowSpeed;
				EGL2.setPosition(windowX, windowY);
				//stuff.translate(0, -rectSpeed);
				stuff.y -= windowSpeed;
				thing.show();
			}
			if (EGL2.getKeyDown("t")) {
				small.x += 4;
				//another.x += 5;
				stuff.extrude(-1, -1);
				grad1.hide();
				thing.hide();
			}
		}
	}

}
