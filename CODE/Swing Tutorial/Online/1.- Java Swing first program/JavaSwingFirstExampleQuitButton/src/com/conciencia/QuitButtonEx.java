package com.conciencia;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Tutorial Swing.
 * 
 * Fuente: http://zetcode.com/tutorials/javaswingtutorial
 * 
 * @author ernesto cantu
 * 
 * 19 Marzo 2015
 */
public class QuitButtonEx extends JFrame {

    /*
        La clase extiende a la clase JFrame, la cual es el componente swing que 
        crea ventanas. Dentro JFrame ( Y todas sus subclases) se pueden colocar 
        otros elementos. 
        
        Es importante recordar qué: "Es una buena práctica de programación no 
        incluir código de la aplicación ene el constructor y mejor delegar dicha
        tarea a una rutina." Por eso el constructor llama a initUI().
    */
    public QuitButtonEx() {
        initUI();
    }

    
    private void initUI(){
    
        /* 
            Botón swing. Simplemente envía un String para que se coloque dicha cadena
            en el botón.
        */
        JButton quitButton = new JButton("Quit");
        quitButton.setToolTipText("Click to Exit");
        
        /*
            Expresión Lambda que crea un nuevo ActionListener para el quitButton.
            Realmente estamos creando un objeto de la Interfaz ActionListener,
            el cual tiene un solo método llamado actionPerformed. Este método
            será llamado cada vez que se de click al botón.
        */
        quitButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Exit");
            System.exit(0);
        });
        
        /*
        quitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Exit");
                System.exit(0);
            }
            
        });*/
        
        createLayout(quitButton);
        
        setTitle("QuitButton Example");
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /*
        Rutina que coloca todos los componentes swing en el frame.
    
        Container es un objeto que puede contener a otros objetos visuales.
        El método getContentPane() regresa el container de este JFrame.
    
        Layout es el objeto que organiza a los JComponents en el pane.
    
        En la instrucción pane.setLayout() indico que el layout gl es el layout
        del Container pane.
    
        La rutina setAutoCreateContainerGaps(true) indica si layout creará de
        forma automática espacios entre los bordes del frame y los objetos contenidos.
        
    */
    private void createLayout(JComponent... arg){
        
        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
                
        gl.setAutoCreateContainerGaps(true);
        
        gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(arg[0]));
        
        gl.setVerticalGroup(gl.createSequentialGroup().addComponent(arg[0]));
    }
    
    
    public static void main(String[] args) {
        /*
         * Expresión lambda para ejecutar un nuevo runnable
         */
        EventQueue.invokeLater(() -> {
                QuitButtonEx ex = new QuitButtonEx();
                ex.setVisible(true);
        });
    }
    
}
