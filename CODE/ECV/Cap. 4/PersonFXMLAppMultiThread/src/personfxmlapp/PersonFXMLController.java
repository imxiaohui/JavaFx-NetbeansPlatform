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
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Clase controller para PersonFXML.
 * Todo el trabajo es realizado por la clase Controller.
 * La Clase main solo carga la escena del fxml.
 * El FXML hace referencia a esta clase.
 * Esta clase (Controller) hace toda la lógica.
 * @author Ernesto Cantú
 * 6 de Agosto del 2015
 */
public class PersonFXMLController implements Initializable {
    
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
    private TreeView<Person> personTreeView;    
    @FXML
    private Button updateButton;
    
    //Logger
    private static final Logger logger = Logger.getLogger(
                        PersonFXMLController.class.getName());
    
    //FamilyTreeManager es la clase que maneja y administra el ObservableMap que contiene a las personas.
    private final FamilyTreeManager ftm = FamilyTreeManager.getInstance();
    
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
        
        //Creo Datos
        buildData();
        
        //Creo Nodo principal
        TreeItem<Person> rootNode = new TreeItem<>(new Person("Person", "", Gender.UNKNOWN));
        //Creando nodos con los datos
        buildTreeView(rootNode);
        
        //Una vez que construí los nodos hijos del root node, los asigno al person Tree View.
        personTreeView.setRoot(rootNode);
        personTreeView.getRoot().setExpanded(true);
        
        //Le asigno un changeListener al ObservableHashMap
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
    
    //Creo nodos y los agrego al root node.
    private void buildTreeView(TreeItem<Person> root){
        ftm.addListener(familyTreeListener);
        ftm.getAllPeople().stream().forEach((p)-> {
            root.getChildren().add(new TreeItem<>(p));
        });
    }
    
    //ChangeListener para detectar selecciones en el TreeItem del treeView
    private final ChangeListener<TreeItem<Person>> treeSelectionListener =
            (ov,oldValue,newValue)->{
        //Nodo seleccionado    
       TreeItem<Person> treeItem = newValue;
        logger.log(Level.FINE,"Selected item = {0}",treeItem);
        
        //pongo en false el update prop, osea, deshabilito el boton.
        enableUpdateProperty.set(false);
        //al poner el false changeOk, puedo actualizar el form, sin causar ejecución de listeners
        changeOk=false;
        
        //si escogí el root node o ningun nodo, limpio la pantalla
        if(treeItem == null || treeItem.equals(personTreeView.getRoot())){
            clearForm();
            return;
        }
        
        //Guardo a la persona seleccionada en el modelo 
        thePerson = new Person(treeItem.getValue());
        logger.log(Level.FINE,"Selected item = {0}",thePerson);
        
        //Creo ligaduras entre el modelo y la pantalla
        configureEditPanelBindings(thePerson);
        
        
        if(thePerson.getGender().equals(Person.Gender.MALE)){
            maleRadioButton.setSelected(true);
        }
        else if(thePerson.getGender().equals(Person.Gender.FEMALE)){
            femaleRadioButton.setSelected(true);
        }
        else{
            unknownRadioButton.setSelected(true);
        }
        
        //ligo el genero de la persona al gender binding, calculado por el radio button seleccionado
        thePerson.genderProperty().bind(genderBinding);
        
        //Habilito cambios.
        changeOk=true;
    };
    
    //change listener para el ObservableMap de FTM. 
    // busca el nodo y lo actualiza.
    private final MapChangeListener<Long,Person> familyTreeListener = (change)->{
        if(Platform.isFxApplicationThread()){
            logger.log(Level.FINE, "Is JavaFX Application Thread");
            updateTree(change);
        }
        else{
            logger.log(Level.FINE, "Is BACKGROUND Thread");
            Platform.runLater(()->updateTree(change));
        }
    };
    
    private void updateTree(MapChangeListener.Change<? extends Long, ? extends Person> change){
        if(change.getValueAdded() != null){
            for(TreeItem<Person> node: personTreeView.getRoot().getChildren()){
                if(change.getKey().equals(node.getValue().getId())){
                    node.setValue(change.getValueAdded());
                    return;
                }
            }
        }
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
