package com.asgteach.familytree.personfxviewer;

import com.asgteach.familytreefx.model.FamilyTreeManager;
import com.asgteach.familytreefx.model.Person;
import com.asgteach.familytreefx.model.Person.Gender;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Clase controller para PersonFXViewer Module.
 * 
 * Funciones:
 * 
 * 1.- Obtener una instancia de FamilyTreeManager del Global Lookup y desplegarlos en el TreeView
 * 2.- Implementar un listener para responder a cambios el el HashMap del FamilyTreeManager
 * 3.- Detectar selecciones en el TreeView para publicar la selección en el Lookup de la pantalla.
 * 
 * Su trabajo, en pocas palabras, consiste en desplegar las personas que estan en el DataSource,
 * Si hay cambios actualizar la vista del arbol, y poner disponible el objeto seleccionado.
 * 
 */
public class PersonFXViewerController implements Initializable {
    
    //Elementos de la escena
    @FXML
    private TreeView<Person> personTreeView;    
    //Logger
    private static final Logger logger = Logger.getLogger(PersonFXViewerController.class.getName());
    //FamilyTreeManager es la clase que maneja y administra el ObservableMap que contiene a las personas.
    private FamilyTreeManager ftm = null;
    //InstanceContent para publicar objeto seleccionado en el GlobalLookup y ponerlo disponible para cualquiera
    private final InstanceContent instanceContent = new InstanceContent();
    //Objeto persona representacion del modelo.
    private Person selectedPerson = null;
    

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

        //obtengo una instancia del FamilyTreeManager(FamilyTreeManagerImpl) del Lookup
        //Si en un futuro decido que el origen de los datos es una bd, o un archivo,
        //Genero su implementación correspondiente en un módulo nuevo
        //Y cambio el módulo que provee servicios.
        ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if(ftm == null){
            logger.log(Level.SEVERE,"No se pudo obtener una instancia del FamilyTreeManager");
            LifecycleManager.getDefault().exit();
        }
        
        
        //Creo Datos
        buildData();
        ftm.addListener(familyTreeListener);
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
        //Listener para cambios en el hashmap del FamilyTreeManager
        ftm.addListener(familyTreeListener);
        
        //Poblamos el TreeView
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
       
        //si escogí el root node o ningun nodo, quito el objeto anterior del Instance Content
        if(treeItem == null || treeItem.equals(personTreeView.getRoot())){
            /* 
            NOTA : Incluí este try-catch debido a que al arrancar la aplicación, se selecciona 
                   el nodo root y se ejecuta este código. Debido a que no existen personas
                   en el instance content, ocurre un null pointer exception.
            */
            try{
                instanceContent.remove(selectedPerson);
            }catch(NullPointerException e){
                logger.log(Level.FINE, "No hay Personas en Instance Content");
            }
            
            return;
        }
        
        //Guardo a la persona seleccionada en el modelo 
        selectedPerson = new Person(treeItem.getValue());
        logger.log(Level.FINE,"Selected item = {0}",selectedPerson);
        
        //Pongo a la persona seleccionada en el instance content.
        instanceContent.set(Collections.singleton(selectedPerson), null);
    };
    
    //Método público de comunicacion entre TopComponent y el controlador, el cual
    //Permitirá acceder al objeto seleccionado.
    public InstanceContent getInstanceContent(){
        return this.instanceContent;
    }
    
    //change listener para el ObservableMap de FTM. Si se dispara un cambio en el FTM
    // este listener busca el nodo en el TreeView y lo actualiza.
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
    
    
    
}
