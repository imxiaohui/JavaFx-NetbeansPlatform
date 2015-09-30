package myrectanglefxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Primer ejercicio JavaFX con FXML.
 * 
 * El código está estructurado de la siguiente manera: 
 * 
 * El archivo FXML es un tipo de archivo de configuración para la Escena.
 * Cuando la aplicación arranca, se le proporciona el archivo FXML al FXMLLoader.
 * Al FXML se le proporciona una Clase Controller, en la cual se indican
 * las acciones a tomar en los elementos de la pantalla.
 * 
 * Por pantalla hay un FXML y un Controller.
 * 
 * @author Ernesto Cantú Valle
 * 22 de Julio 2015
 */
public class MyRectangleFXApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //El FXMLLoader carga el FXML, genera la Pantalla y la regresa.
        Parent root = FXMLLoader.load(getClass().getResource(
               "MyRectangleFX.fxml"));
        
        Scene scene = new Scene(root,Color.LIGHTBLUE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
