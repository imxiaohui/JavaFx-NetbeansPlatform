package com.asgteach.familytree.personfxeditor;

import com.asgteach.familytreefx.model.FamilyTreeManager;
import com.asgteach.familytreefx.model.Person;
import com.asgteach.familytreefx.model.Person.Gender;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;



/**
 * Controlador del Person Editor.
 * 
 * funciones:
 * 
 * 1.- Obtener una instancia del FTM del Global LookUp para actualizar personas.
 * 2.- Implementar event handlers para manejar cambios hechos por el usuario
 * 3.- Implementar el event handler del boton update para ejecutar el updatePerson del ftm
 * 4.- Ejecutar el método doUpdate() el cual es ejecutado por el topcomponent cuando se detecta un 
 *     cambio en el InstanceContent del PersonViewer. Sustituye al TreeSelectionListener.
 * 
 */
public class PersonFXEditorController implements Initializable {
    
    //Elementos de la escena
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField suffixTextField;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private RadioButton unknownRadioButton;
    @FXML
    private TextArea notesTextArea;   
    @FXML
    private Button updateButton;
    
    //Logger
    private static final Logger logger = Logger.getLogger(PersonFXEditorController.class.getName());
    
    //FamilyTreeManager es la clase que maneja y administra el ObservableMap que contiene a las personas.
    private FamilyTreeManager ftm = null;
    
    //Objeto persona representacion del modelo.
    private Person thePerson = null;
    
    //Custom binding para la propiedad género. Mantiene actualizados el genero del form y del objeto
    private ObjectBinding<Gender> genderBinding;
    
    //Propiedades para manejo de update button
    private boolean changeOk = false;
    
    //Control para el disable property del boton update.
    private BooleanProperty enableUpdateProperty;
    

    //Primer metodo llamado del controller. Inicializa la clase.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Handlers
        logger.setLevel(Level.FINE);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);
        
        try{
            FileHandler fileHandler = new FileHandler();
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
            logger.log(Level.FINE,"Created file handler");
        }catch(IOException | SecurityException ex){
            logger.log(Level.SEVERE,"Couldn't create handler");
        }    

        // creo la propiedad enableUpdate. si no está permitido el Update, disable es verdadero, y el boton está deshabilitado.
        enableUpdateProperty = new SimpleBooleanProperty(this, "enableUpdate", false);
        //Hago bind entre la propiedad disable y la negacion de enableUpdate. (!false = true... disable prop del boton está
        //en true, osea, deshabilitado.)
        updateButton.disableProperty().bind(enableUpdateProperty.not());
       
        //Custom binding para la propiedad Genero que está ligada a los radio button.
        //Si el selected Prop de un radio button cambia, se ejecutará el método computeValue.
        genderBinding = new ObjectBinding<Gender>() {
            {
                super.bind(maleRadioButton.selectedProperty(),
                           femaleRadioButton.selectedProperty(),
                           unknownRadioButton.selectedProperty());   
            }
            
            @Override
            protected Gender computeValue() {
                if(maleRadioButton.isSelected()){
                    return Gender.MALE;
                }
                else if(femaleRadioButton.isSelected()){
                    return Gender.FEMALE;
                }
                else {
                    return Gender.UNKNOWN;
                }
            }
        };
        
        ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if(ftm == null){
            logger.log(Level.SEVERE,"No se pudo obtener una instancia del Family Tree Manager");
            LifecycleManager.getDefault().exit();
        }
        
    }// initialize    
    
    
    //Método publico del controlador, por el cual el top component del módulo puede
    //Enviar un objeto person.
    public void doUpdate(Collection<? extends Person> people){
        enableUpdateProperty.set(false);
        changeOk=false;
        if(people.isEmpty()){
            logger.log(Level.FINE,"No Selection");
            clearForm();
            return;
        }
        thePerson = people.iterator().next();
        logger.log(Level.FINE,"{0} seleccionado",thePerson);
        configureEditPanelBindings(thePerson);
        if(thePerson.getGender().equals(Person.Gender.MALE))
            maleRadioButton.setSelected(true);
        else if(thePerson.getGender().equals(Person.Gender.FEMALE))
            femaleRadioButton.setSelected(true);
        else
            unknownRadioButton.setSelected(true);
        changeOk = true;
    }
    
    
    //liga al objeto thePerson con el formulario.
    private void configureEditPanelBindings(Person p){
        firstNameTextField.textProperty().bindBidirectional(p.firstNameProperty());
        middleNameTextField.textProperty().bindBidirectional(p.middleNameProperty());
        lastNameTextField.textProperty().bindBidirectional(p.lastNameProperty());
        suffixTextField.textProperty().bindBidirectional(p.suffixProperty());
        notesTextArea.textProperty().bindBidirectional(p.notesProperty());
    }
    
    //limpio la pantalla
    private void clearForm(){
        firstNameTextField.setText("");
        middleNameTextField.setText("");
        lastNameTextField.setText("");
        suffixTextField.setText("");
        notesTextArea.setText("");
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
        unknownRadioButton.setSelected(false);
    }
    
    //Cuando se selecciona un nodo, se actualiza el formulario, y se pone en changeOk true.
    //Entonces, si se selecciona un radioButtom o se escribe en un text form se habilita la edicion..
    //Se pone en false el disable del Boton y se habilita el boton para cambios.
    @FXML
    private void genderSelectionAction(ActionEvent event) {
        if(changeOk){
            enableUpdateProperty.set(true);
        }
    }

    @FXML
    private void handleKeyAction(KeyEvent event) {
        if(changeOk){
            enableUpdateProperty.set(true);
        }
    }

    //Cuando se selecciona un nodo del TreeView, se hace doble binding entre el nodo y
    // el objeto Person. Por lo tanto, al dar update, el objeto Person está listo para ser enviado
    //al observable map de la clase family tree manager
    @FXML
    private void updateButtonAction(ActionEvent event) {
        enableUpdateProperty.set(false);
        ftm.updatePerson(thePerson);
    }
    
}
