package com.conciencia;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
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
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void createMenuBar(){
    
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
        
        menuBar.add(file);
        menuBar.add(about);
        
        setJMenuBar(menuBar);
    }
    
    public static void main(String[] args){
        EventQueue.invokeLater(() ->{
            SimpleMenuEx ex = new SimpleMenuEx();
            ex.setVisible(true);
        });
    }
    
}
