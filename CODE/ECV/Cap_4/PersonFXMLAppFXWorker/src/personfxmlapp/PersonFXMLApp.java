package personfxmlapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Seccion 4.4 del libro. FXML ENHANCED UI.
 * @author Ernesto Cantú.
 * 06/08/2015
 */
public class PersonFXMLApp extends Application {
    
    @Override //Launch manda ejecutar el método start
    public void start(Stage stage) throws Exception {
        
        //Se carga el fxml y se obtiene de el el Layout.
        Parent root = FXMLLoader.load(getClass().getResource("PersonFXML.fxml"));
        
        //Se asigna el layout a la escena
        Scene scene = new Scene(root);
        
        //se carga la escena al escenario
        stage.setScene(scene);
        stage.setTitle("Person FX Application");
        //Se muestra el escenario
        stage.show();
    }
    
    //Procedimiento principal que manda ejecutar el método launch de la Clase Aplication
    public static void main(String[] args) {
        launch(args);
    }
    
}
