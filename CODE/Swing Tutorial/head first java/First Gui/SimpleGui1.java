import javax.swing.*;


public class SimpleGui1{
	public static void main(String[] args){
		
		//Frame es el objeto sobre el que se ponen el resto de los componentes visuales.
		//Se agrega al content pane del frame.
		JFrame frame = new JFrame();

		//Boton con constructor para mensaje
      JButton button = new JButton("Click me");

      //Operación a realizar al cerrar la ventana
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //agrego el boton al content pane de frame
      frame.getContentPane().add(button);

      //Habilito ventana
      frame.setSize(300,300);
      frame.setVisible(true);	
   }
}
