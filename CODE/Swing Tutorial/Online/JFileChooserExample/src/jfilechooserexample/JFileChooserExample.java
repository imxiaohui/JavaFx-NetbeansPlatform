package jfilechooserexample;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;



/**
 * JFileChooser example.
 * @author ernesto
 */
public class JFileChooserExample extends JFrame implements ActionListener {
    
    private JFileChooser fileChooser;
    private JButton openButton;
    private BufferedReader bufferedReader;
    private File file;
    int returnValue;
    String currentLine;

    public JFileChooserExample() {
        initComponents();
    }
    
    public void initComponents(){
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files","json");
        fileChooser.setFileFilter(filter);
        openButton = new JButton("Select");
        
        setPreferredSize(new Dimension(278, 179));
        setLayout(null);
        
        add(openButton);
        openButton.setBounds(85,145,100,25);
        openButton.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(()->{
            new JFileChooserExample().setVisible(true);
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == openButton){
            returnValue = fileChooser.showOpenDialog(this);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                JSONObject json = new JSONObject();
                
                JSONParser parser = new JSONParser();
                
                file = fileChooser.getSelectedFile();
                try{
                    json = (JSONObject) parser.parse(new FileReader(file));                    
                    
                    System.out.println(json.get("firstName"));
                }
                catch(Exception exp){
                    System.out.println(exp.toString());
                }
            }
        }
    }    
}
