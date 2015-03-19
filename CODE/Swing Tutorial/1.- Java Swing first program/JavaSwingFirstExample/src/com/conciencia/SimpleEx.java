package com.conciencia;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Tutorial Swing.
 * 
 * Fuente: http://zetcode.com/tutorials/javaswingtutorial
 * 
 * @author ernesto cantu
 * 
 * 19 Marzo 2015
 */
public class SimpleEx extends JFrame {

    /*
        La clase extiende a la clase JFrame, la cual es el componente swing que 
        crea ventanas. Dentro JFrame ( Y todas sus subclases) se pueden colocar 
        otros elementos. 
        
        Es importante recordar qué: "Es una buena práctica de programación no 
        incluir código de la aplicación ene el constructor y mejor delegar dicha
        tarea a una rutina." Por eso el constructor llama a initUI().
    */
    
    public SimpleEx() {
        
        initUI();
    }

    /**
     * Método que inicializa las propiedades de la ventana a desplegar.
     */
    private void initUI(){
        setTitle("Simple Example");
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /**
     * Método main. Como en cualquier aplicación de consola, 
     * este método es el primero en arrancar.
     * @param args No recibo ningun parámetro.
     */
    public static void main(String[] args) {
        
        /*
         * Expresión lambda para ejecutar un nuevo runnable
         */
        EventQueue.invokeLater(() -> {
                SimpleEx ex = new SimpleEx();
                ex.setVisible(true);
        });
        
        /*
            Internamente creamos un objeto que implementa la Interfaz Runnable,
            pero no lo implementamos como una clase como tal, sino, le damos una
            implementación "on the fly".
        
            EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
                SimpleEx ex = new SimpleEx();
                ex.setVisible(true);
            }
        });
        */
    }
    
}
