package personfxmlapp;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;

/**
 * Clase controller para PersonFXML.
 * @author Ernesto Cantú
 * 6 de Agosto del 2015
 */
public class PersonFXMLController implements Initializable {
    
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField suffixNameTextField;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private RadioButton unkownRadioButton;
    @FXML
    private TextArea notesTextArea;
    @FXML
    private ToggleGroup genderToggleGroup;;
    @FXML
    private TreeView<Person> personTreeView;
    @FXML
    private Button updateButton;
    
    private static final Logger logger = Logger.getLogger(
                         PersonFXMLController.class.getName());
    
    private final FamilyTreeManager ftm = FamilyTreeManager.getInstance();
    private Person thePerson = null;
    private ObjectBinding<Gender> genderBinding;
    private boolean changeOk = false;
    private BooleanProperty enableUpdateProperty;

    
    //Primer metodo llamado del controller. Inicializa la clase.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        
        enableUpdateProperty = new SimpleBooleanProperty(this, "enableUpdate", false);
        updateButton.disableProperty().bind(enableUpdateProperty.not());
        
        genderBinding = new ObjectBinding<Gender>() {
            {
                super.bind(maleRadioButton.selectedProperty(),
                           femaleRadioButton.selectedProperty(),
                           unkownRadioButton.selectedProperty());   
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
        
        buildData();
        //Nodo principal
        TreeItem<Person> rootNode = new TreeItem<>(new Person("Person", "", Gender.UNKNOWN));
        //Creando nodos
        buildTreeView(rootNode);
        personTreeView.setRoot(rootNode);
        personTreeView.getRoot().setExpanded(true);
        personTreeView.getSelectionModel().selectedItemProperty()
                .addListener(treeSelectionListener);
        
    }// initialize    
    
     //Método de inicialización. Simula la carga de info. No es necesario
    private void buildData(){
        ftm.addPerson(new Person("Homer","Simpson",Person.Gender.MALE));
        ftm.addPerson(new Person("Mage","Simpson",Person.Gender.FEMALE));
        ftm.addPerson(new Person("Bart","Simpson",Person.Gender.MALE));
        ftm.addPerson(new Person("Lisa","Simpson",Person.Gender.FEMALE));
        ftm.addPerson(new Person("Maggie","Simpson",Person.Gender.FEMALE));
        logger.log(Level.FINE,ftm.getAllPeople().toString());
    }
    
    
    private void buildTreeView(TreeItem<Person> root){
        ftm.addListener(familyTreeListener);
        ftm.getAllPeople().stream().forEach((p)-> {
            root.getChildren().add(new TreeItem<>(p));
        });
    }
    
    //ChangeListener para TreeItem del treeView
    private final ChangeListener<TreeItem<Person>> treeSelectionListener =
            (ov,oldValue,newValue)->{
            
        TreeItem<Person> treeItem = newValue;
        logger.log(Level.FINE,"Selected item = {0}",treeItem);
        enableUpdateProperty.set(false);
        changeOk=false;
        
        if(treeItem == null || treeItem.equals(personTreeView.getRoot())){
            clearForm();
            return;
        }
        
        thePerson = new Person(treeItem.getValue());
        logger.log(Level.FINE,"Selected item = {0}",thePerson);
        configureEditPanelBindings(thePerson);
        if(thePerson.getGender().equals(Person.Gender.MALE)){
            maleRadioButton.setSelected(true);
        }
        else if(thePerson.getGender().equals(Person.Gender.FEMALE)){
            femaleRadioButton.setSelected(true);
        }
        else{
            unkownRadioButton.setSelected(true);
        }
        thePerson.genderProperty().bind(genderBinding);
        changeOk=true;
    };
    
    private final MapChangeListener<Long,Person> familyTreeListener = (change)->{
        if(change.getValueAdded() != null){
            logger.log(Level.FINE,"change value {0}",change.getValueAdded());
        }
        for(TreeItem<Person> node: personTreeView.getRoot().getChildren()){
            if(change.getKey().equals(node.getValue().getId())){
                node.setValue(change.getValueAdded());
                return;
            }
        }
    };
    
    private void configureEditPanelBindings(Person p){
        firstNameTextField.textProperty().bindBidirectional(p.firstNameProperty());
        middleNameTextField.textProperty().bindBidirectional(p.middleNameProperty());
        lastNameTextField.textProperty().bindBidirectional(p.lastNameProperty());
        suffixNameTextField.textProperty().bindBidirectional(p.suffixProperty());
        notesTextArea.textProperty().bindBidirectional(p.notesProperty());
    }
    
    private void clearForm(){
        firstNameTextField.setText("");
        middleNameTextField.setText("");
        lastNameTextField.setText("");
        suffixNameTextField.setText("");
        notesTextArea.setText("");
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
        unkownRadioButton.setSelected(false);
    }
    
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

    @FXML
    private void updateButtonAction(ActionEvent event) {
        enableUpdateProperty.set(false);
        ftm.updatePerson(thePerson);
    }
    
}
