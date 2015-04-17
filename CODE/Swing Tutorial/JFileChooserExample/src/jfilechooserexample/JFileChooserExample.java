package jfilechooserexample;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.SwingUtilities;

/**
 * JFileChooser example.
 * @author ernesto
 */
public class JFileChooserExample extends JPanel implements ActionListener {
    
    private JFileChooser fileChooser;
    private JButton openButton;
    private BufferedReader bufferedReader;
    private File file;
    int returnValue;
    String currentLine;

    public JFileChooserExample() {
        initComponents();
    }
    
    private void initComponents(){
        fileChooser = new JFileChooser();
        openButton = new JButton("Select");
        
        setPreferredSize(new Dimension(278, 179));
        setLayout(null);
        
        add(openButton);
        openButton.setBounds(85,145,100,25);
        openButton.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == openButton){
            returnValue = fileChooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();
                
                try{
                    bufferedReader = new BufferedReader(new FileReader(file));
                    
                    while((currentLine = bufferedReader.readLine()) != null){
                        System.out.println(currentLine);
                    };
                }
                catch(Exception error){
                    error.printStackTrace();
                }
            }
        }
    }
   

    public static void main(String[] args) {
        
        JFileChooserExample fileChooser = new JFileChooserExample();
        fileChooser.setVisible(true);
    }
    
}
