
import java.awt.*;
import javax.swing.*;

public class DrawPanelExample{

	public static void main(String[] args){

		JFrame frame = new JFrame();

		MyDrawPanel dp = new MyDrawPanel();
		frame.getContentPane().add(dp);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(300,300);
      	frame.setVisible(true);	

	}
}