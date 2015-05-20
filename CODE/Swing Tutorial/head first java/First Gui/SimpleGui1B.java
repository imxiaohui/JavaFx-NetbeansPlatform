import javax.swing.*;
import java.awt.event.*;


public class SimpleGui1B {

   JButton button;
   ActionListener listener = new ActionListener(){
	public void actionPerformed(ActionEvent event){
	   button.setText("i've been clicked");
        }
   };

   public static void main(String[] args){
	SimpleGui1B gui = new SimpleGui1B();
	gui.go();
      
   }

   public void go(){
      JFrame frame = new JFrame();
      button = new JButton("Click me");

      button.addActionListener(listener);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(button);
      frame.setSize(300,300);
      frame.setVisible(true);
   }

   
}
