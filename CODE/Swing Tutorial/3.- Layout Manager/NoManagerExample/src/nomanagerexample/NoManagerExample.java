package nomanagerexample;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * Ejemplo de Swing Layouts.
 * 
 * @author ernesto cantú
 * 20 de Marzo 2015.
 */
public class NoManagerExample extends JFrame{

    public NoManagerExample() {
        initUI();
    }

    private void initUI(){
    
        setLayout(null);
        
        JButton ok = new JButton("Ok");
        ok.setBounds(50, 50, 80, 25);
        
        JButton close = new JButton("Close");
        close.setBounds(150, 50, 80, 25);
        
        add(ok);
        add(close);

        setTitle("Absolute positioning");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(()->{
        
            NoManagerExample ex = new NoManagerExample();
            ex.setVisible(true);
        });
    }
    
}
