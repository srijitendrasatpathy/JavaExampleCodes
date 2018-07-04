import javax.swing.*;
import java.awt.*;

public class SimpleAnimation {
	
	int x = 70;
	int y = 70;
	
	public static void main(String[] args) {
		SimpleAnimation gui =new SimpleAnimation();
		gui.go();
	}
	
	public void go() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyDrawPanel drawPanel = new MyDrawPanel();
		
		frame.getContentPane().add(drawPanel);	
		frame.setSize(300, 300);
		frame.setVisible(true);
		
		/*Since the inner classes has access to all the instance variables the co-ordinates
			in the paintComponent() function can be initialized and modified in the outer class
		*/
		for(int i = 0; i < 130; i++) {
			x++;
			y++;
			
			drawPanel.repaint();
			//Slowing down the repaint operation to slow it down a bit.
			try {
				Thread.sleep(50);
			}catch(Exception e) { }
		}
	}
	
	class MyDrawPanel extends JPanel{
		
		public void paintComponent(Graphics g) {
			//Need to re-set the background to it's original color each time to see the animation.
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			g.setColor(Color.ORANGE);
			g.fillOval(x, y, 40, 40);
		}
	}
}
