package personfxappbound;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main Application. Accede a main(). En main() se ejecuta el método launch().
 * El método launch() prepara lo necesario para ejecutar código JavaFX y a su vez
 * manda llamar el método start().
 * 
 * En el método start se lee el VBox creado por el FXML. Se asigna como layout para
 * un objeto Escena, y la escena se asigna a el escenario principal.
 * 
 * @author Ernesto Cantú
 */
public class PersonFXAppBound extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //VBox root = FXMLLoader.load(getClass().getResource("PersonFXBound.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("PersonFXBound.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Person Bind Example");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
