/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myrectanglefxapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controller Class que se asocia al FXML y que indica las acciones a tomar
 * por cada elemento de la Escena.
 * 
 * La anotación @FXML sirve para indicar que las variables son elementos del FXML.
 * 
 * @author Ernesto Cantú
 * 22/07/2015
 */
public class MyRectangleFXController implements Initializable {
    
    @FXML
    private Rectangle rectangle;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Despues de que el FXML crea la pantalla (Escena),instancía 
        //a la Clase MyRectangleFXController y ejecuta el método 
        //initialize. Aquí se configurará el controller.
        
        rectangle.setStrokeWidth(5.0);
        rectangle.setStroke(Color.GOLDENROD);
    }    
    
}
