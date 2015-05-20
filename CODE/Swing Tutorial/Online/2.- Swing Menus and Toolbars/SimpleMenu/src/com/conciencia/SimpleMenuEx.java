package com.conciencia;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 * Simple Menu Example.
 * 
 * Contiene ejemplo de como agregar diferentes tipos de Menus en la aplicación.
 * 
 * Conceptos
 * 
 * 1.- Menubar : Objeto que se crea en la parte superior y que contine objetos JMenu.
 * 2.- Menu: Objetos JMenu son las etiquetas que aparecen para seleccionar.Pueden Contener
 *     Objetos jmenu o jmenuitem.
 * 3.- JMenuItem: Son botones existentes en los menus que realizan acciones.
 * 
 * Es importante recordar que para hacer submenus lo que se hace es crear un JMenu 
 * que esté contenido en otro.
 * 
 * @author ernesto cantu
 * http://zetcode.com/tutorials/javaswingtutorial/menusandtoolbars/
 */
public class SimpleMenuEx extends JFrame {
    
    /*
        Objeto Popup menu. Menú que se despliega al hacer click con un boton
        sobre el frame.
    */
    JPopupMenu pMenu;

    /*
        Constructor que manda inicializar todos los objetos a contenerse dentro
        del frame.
    */
    public SimpleMenuEx() {
        initUI();
    }
    
    /*
        Método que inicializa todos los objetos.
     */
    private void initUI(){
        
        createMenuBar();
        
        setTitle("Simple Menu Example");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void createMenuBar(){
        
        /*
            Creo un objeto del tipo MenuBar
        */
        JMenuBar menuBar = new JMenuBar();
        
        /*
            Menu "file". El primer menú incluido en el Menu Bar.
        
            Contiene un objeto JMenu llamado subMenu. subMenu tiene un JMenuItem
            llamado subMenuMessage y aparece como "Show Me a MEssage". Ejecuta un
            actionListener.
        
            Menu ademas contiene otro JMenuItem el cual ejecuta la funcion Exit.
        */
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        
        
        JMenu subMenu = new JMenu("Submenu");
        
        JMenuItem subMenuMessage = new JMenuItem("Show Me a Message");
        subMenuMessage.addActionListener((ActionEvent event)->{
            JOptionPane.showMessageDialog(null,"Show Me a Message");
        });
        subMenu.add(subMenuMessage);
        
        file.add(subMenu);
        file.addSeparator();
        
        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit");
        
        eMenuItem.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(null, "Exit");
            System.exit(0);
        });
        
        file.add(eMenuItem);
        
        
        /*
            Menu "About". Crea el menú About this... el cual abre el frame "About".
            Contiene un MenuItem llamado aboutMenu el cual despliega el mensaje "About this.." y abre el frame.
        */
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
        
        
        /*
            Menú create. Contiene un JMenuItem llamado createPerson.
            Despliega el menú "Create new +" el cual manda abrir un "Formulario"
            para crear un objeto Persona
         */
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
        
        pMenu = new JPopupMenu();
        
        JMenuItem popupMenuItem = new JMenuItem("Pop Up Menu");
        popupMenuItem.addActionListener((ActionEvent event)->{
           JOptionPane.showMessageDialog(null,"Show Me a Message"); 
        });
        
        /*
            Menu popup.
        */
        pMenu.add(popupMenuItem);
        
        addMouseListener(new MouseAdapter(){
            
            @Override
            public void mouseReleased(MouseEvent event){
                if(event.getButton() == MouseEvent.BUTTON3){
                    pMenu.show(event.getComponent(),event.getX(),event.getY());
                }
            }
        });
        
        /*
            Agrego los JMenu al menuBar.
        */        
        menuBar.add(file);
        menuBar.add(about);
        menuBar.add(create);
        
        setJMenuBar(menuBar);
        
    }
    
    public static void main(String[] args){
        EventQueue.invokeLater(() ->{
            SimpleMenuEx ex = new SimpleMenuEx();
            ex.setVisible(true);
        });
    }
    
}
