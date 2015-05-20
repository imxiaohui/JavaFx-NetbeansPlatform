package toolbarex;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

/**
 * Ejemplo de JToolbar.
 *
 * @author ernesto cantú
 * 20 de marzo 2015
 */
public class ToolbarEx extends JFrame {

    public ToolbarEx() {
        initUI();
    }
    
    private void initUI(){
        
        createMenuBar();
        createToolBar();
        
        setTitle("Simple Toolbar");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void createMenuBar(){
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");
        
        exit.addActionListener((ActionEvent e)->{
            System.exit(0);
        });
        
        file.add(exit);
        menuBar.add(file);
        
        setJMenuBar(menuBar);
    }
    
    private void createToolBar(){
        
        JToolBar toolbar = new JToolBar();
        Icon icon = new ImageIcon("msg.jpg");
        
        JButton messageButton = new JButton(icon);
        toolbar.add(messageButton);
        
        messageButton.addActionListener((ActionEvent e)->{
            JOptionPane.showMessageDialog(null,"Reading Messages");
        });
        
        add(toolbar,BorderLayout.NORTH);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            ToolbarEx tbEx = new ToolbarEx();
            tbEx.setVisible(true);
        });        
    }
    
}
