package com.conciencia;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Simple Menu Example.
 * 
 * @author ernesto cantu
 * http://zetcode.com/tutorials/javaswingtutorial/menusandtoolbars/
 */
public class SimpleMenuEx extends JFrame {

    public SimpleMenuEx() {
        initUI();
    }
    
    private void initUI(){
        
        createMenuBar();
        
        setTitle("Simple Menu");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void createMenuBar(){
    
        JButton button = new JButton("Click to finish");
        button.setSize(10, 10);
        button.setToolTipText("Click to close");
        
        button.addActionListener((ActionEvent event) ->{
            System.exit(0);
        });
        
        JMenuBar menuBar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");
        
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem eMenuItem = new JMenuItem("Exit",icon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit");
        
        eMenuItem.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(null, "Exit");
            System.exit(0);
        });
        
        file.add(eMenuItem);
        
        JMenu about = new JMenu("About");
        about.setMnemonic(KeyEvent.VK_P);
        JMenuItem aboutMenu = new JMenuItem("About this...");
        aboutMenu.setToolTipText("About this...");
        
        aboutMenu.addActionListener((ActionEvent event)-> {
            EventQueue.invokeLater(() ->{
                this.dispose();
                About aboutFrame = new About();
                aboutFrame.setVisible(true);
            });
        });
        
        about.add(aboutMenu);
        
        JMenu create = new JMenu("Create");
        JMenuItem createPerson = new JMenuItem("Create new +");
        
        createPerson.addActionListener((ActionEvent event)->{
            EventQueue.invokeLater(() ->{
                this.dispose();
                Formulario persona = new Formulario();
                persona.setVisible(true);
            });
        });
        
        create.add(createPerson);
        
        
        menuBar.add(file);
        menuBar.add(about);
        menuBar.add(create);
        
        setJMenuBar(menuBar);
        
        add(button);
    }
    
    public static void main(String[] args){
        EventQueue.invokeLater(() ->{
            SimpleMenuEx ex = new SimpleMenuEx();
            ex.setVisible(true);
        });
    }
    
}
