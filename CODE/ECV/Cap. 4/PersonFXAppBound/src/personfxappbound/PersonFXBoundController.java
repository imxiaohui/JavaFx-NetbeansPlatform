/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personfxappbound;

import com.asgteach.familytree.model.Person;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author ernesto
 */
public class PersonFXBoundController implements Initializable {
    
    @FXML
    private Label margeLabel;
    
    final Person marge = new Person("Marge", "Simpson",Person.Gender.FEMALE);
    
    @FXML
    private void changeButtonAction(ActionEvent event) {
        marge.setMiddleName("Louise");
    }
    
    @FXML
    private void resetButtonAction(ActionEvent event){
        marge.setMiddleName("");
    }
    
    //Este método es ejecutado despues de que la aplicación lee el FXML
    //Y Crea la Escena.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Mantiene el texto del label siempre igual a la propiedad fullName del objeto Persona.
        margeLabel.textProperty().bind(marge.fullNameProperty());
    }    
    
}
