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
 * Clase Controller. El FXML tiene acceso a esta clase y puede configurar los elementos
 * del VBox creado con algunos de los miembros de esta clase.
 * 
 * El FXML tiene acceso solo a atributos púbicos, o aquellos marcados con el FXML tag.
 * 
 * p. Ej. El label marge, es accedido por el fxml y tiene un bind al fullname de la clase
 * person.
 * 
 * @author ernesto
 */
public class PersonFXBoundController implements Initializable {
    
    @FXML
    private Label margeLabel;
    
    final Person marge = new Person("Marge","Simpson",Person.Gender.FEMALE);
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        margeLabel.textProperty().bind(marge.fullNameProperty());
    }    

    @FXML
    private void changeButtonAction(ActionEvent event) {
        marge.setMiddleName("Louise");
    }

    @FXML
    private void resetButtonAction(ActionEvent event) {
        marge.setMiddleName("");
    }
    
}
