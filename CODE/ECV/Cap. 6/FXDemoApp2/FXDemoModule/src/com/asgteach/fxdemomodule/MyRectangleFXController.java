package com.asgteach.fxdemomodule;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

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
    
    private RotateTransition rt;
    
    @FXML
    private Rectangle rectangle;
    
    @FXML
    private StackPane stackpane;
    
    @FXML
    private SwingNode swingNode;
    
    private JButton clickButton;
    
    @FXML
    private void handleMouseClick(MouseEvent evt){
        rt.play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Despues de que el FXML crea la pantalla (Escena),instancía 
        //a la Clase MyRectangleFXController y ejecuta el método 
        //initialize. Aquí se configurará el controller.
        
        rectangle.setStrokeWidth(5.0);
        rectangle.setStroke(Color.GOLDENROD);
        
        rt = new RotateTransition(Duration.millis(3000),stackpane);
        rt.setToAngle(180);
        rt.setFromAngle(0);
        rt.setAutoReverse(true);
        rt.setCycleCount(4);
        createAndSetSwingContent(swingNode);
    }    
    
    //la accion del boton debe correr en EDT, mas como se ejecuta código fx... 
    //debe correr en JavaFx Application Thread
    private void createAndSetSwingContent(final SwingNode swingNode){
        SwingUtilities.invokeLater(()-> {
            swingNode.setContent(clickButton = new JButton("Click me"));
            clickButton.addActionListener((ActionEvent e)->{
                Platform.runLater(() -> rt.play());
            });
        });
        
    }
}