package com.conciencia;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Tutorial Swing.
 * 
 * Fuente: http://zetcode.com/tutorials/javaswingtutorial
 * 
 * @author ernesto cantu
 * 
 * 19 Marzo 2015
 */
public class ToolTipEx extends JFrame {


    public ToolTipEx() {
        initUI();
    }

    
    private void initUI(){

        JButton quitButton = new JButton("Quit");
        quitButton.setToolTipText("Click to Exit");

        quitButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Exit");
            System.exit(0);
        });
        
        
        
        createLayout(quitButton);
        
        setTitle("ToolTip Example");
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    
    private void createLayout(JComponent... arg){
        
        JPanel pane = (JPanel) getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
        
        pane.setToolTipText("Content Pane");
        
        gl.setAutoCreateContainerGaps(true);
        
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
                .addGap(200)
        );
        
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
                .addGap(200)
        );
        
        pack();
    }
    
    
    public static void main(String[] args) {
        /*
         * Expresión lambda para ejecutar un nuevo runnable
         */
        EventQueue.invokeLater(() -> {
                ToolTipEx ex = new ToolTipEx();
                ex.setVisible(true);
        });
    }
    
}
