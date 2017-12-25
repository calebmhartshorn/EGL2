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
		
		EGL2.init(500, 300);

		int windowSpeed = 4;
		int myFavoriteLayer = 2;
		
		Rectangle stuff = EGL2.Draw.rect(300, 100, 100, 100, true, EGL2.BLUE);
		Rectangle small = EGL2.Draw.rect(250, 200, 10, 40, true, Color.CYAN);
		Circle thing = EGL2.Draw.circle(0, 0, 20, 20, false, EGL2.BLACK);
		Image im2 = EGL2.Draw.image(0, 100, "sonicExplosion04.png");
		
		int windowX = EGL2.getWindowX();
		int windowY = EGL2.getWindowY();
		//EGL2.setBackgroundColor(Color.DARK_GRAY);
		EGL2.setResizable(true);
		Gradient ge = EGL2.Draw.gradient(100, 0, 100, 100, 100, 0, Color.black, 100, 100, Color.white, true, "rectangle");
		
		
		Image im = EGL2.Draw.image(150, 100, "sonicExplosion04.png"); // in bin folder
		
		im2.setLayer(0);
		EGL2.addLayer();
		stuff.setLayer(1);
		
		//stuff.x += 500;
		while (true) {
			
			System.out.println(im2.touches(im));
			EGL2.update(60);
			
			if (EGL2.getKeyDown("a")) {	
				
				im2.translate(-windowSpeed, 0);
			}
			if (EGL2.getKeyDown("d")) {
				//stuff.translate(rectSpeed, 0);
				stuff.setColor(Color.RED);
				im.hide();
				im2.translate(windowSpeed, 0);
			}
			if (EGL2.getKeyDown("w")) {
				//stuff.translate(0, -rectSpeed);
				im2.translate(0, -windowSpeed);
			}
			if (EGL2.getKeyDown("s")) {
				//stuff.translate(0, rectSpeed);
				im2.translate(0, windowSpeed);
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
				//grad1.hide();
				thing.hide();
				thing.setLayer(1);
				EGL2.addLayer();
				im2.setLayer(myFavoriteLayer);
				stuff.setLayer(0);
			}
		}
	}

}
