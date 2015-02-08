/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personfxviewer;

import com.asgteach.familytreefx.model.FamilyTreeManager;
import com.asgteach.familytreefx.model.Person;
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
 *
 * @author gail
 */
public class PersonFXViewerController implements Initializable {
    
    @FXML
    private TreeView<Person> personTreeView;
    
    private static final Logger logger = Logger.getLogger(PersonFXViewerController.class.getName());
    private FamilyTreeManager ftm = null; 
    private final InstanceContent instanceContent = new InstanceContent();
    private Person selectedPerson = null;


    private void buildData() {
        ftm.addPerson(new Person("Homer", "Simpson", Person.Gender.MALE));
        ftm.addPerson(new Person("Marge", "Simpson", Person.Gender.FEMALE));
        ftm.addPerson(new Person("Bart", "Simpson", Person.Gender.MALE));
        ftm.addPerson(new Person("Lisa", "Simpson", Person.Gender.FEMALE));
        ftm.addPerson(new Person("Maggie", "Simpson", Person.Gender.FEMALE));
        logger.log(Level.FINE, ftm.getAllPeople().toString());
    }

    private void buildTreeView(TreeItem<Person> root) {
        // listen for changes to the familytreemanager's map
        ftm.addListener(familyTreeListener);
        // Populate the TreeView control
        ftm.getAllPeople().stream().forEach((p) -> {
            root.getChildren().add(new TreeItem<>(p));
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Change level to Level.INFO to reduce console messages
        logger.setLevel(Level.FINE);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);
        try {
            FileHandler fileHandler = new FileHandler();
            // records sent to file javaN.log in user's home directory
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
            logger.log(Level.FINE, "Created File Handler");
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, "Couldn't create FileHandler", ex);
        }
        
        ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        

        buildData(); 
        logger.log(Level.FINE, "Return from buildData");
        ftm.addListener(familyTreeListener);
        // Create a root node and populate the TreeView control
        TreeItem<Person> rootNode = new TreeItem<>(new Person("People", "", Person.Gender.UNKNOWN));
        buildTreeView(rootNode);
        logger.log(Level.FINE, "Return from buildTreeView");
        // Configure the TreeView control
        personTreeView.setRoot(rootNode);
        personTreeView.getRoot().setExpanded(true);
        personTreeView.getSelectionModel().selectedItemProperty().addListener(treeSelectionListener);
        logger.log(Level.FINE, "initialize() finished");
    }
    
    // called from TopComponent
    public InstanceContent getInstanceContent() {
        logger.log(Level.FINE, "return instanceContent");
        return instanceContent;
    }

    // MapChangeListener when underlying FamilyTreeManager changes
    private final MapChangeListener<Long, Person> familyTreeListener = (change) -> {
        if (change.getValueAdded() != null) {
            logger.log(Level.FINE, "changed value = {0}", change.getValueAdded());
            // Find the treeitem that this matches and replace it
            for (TreeItem<Person> node : personTreeView.getRoot().getChildren()) {
                if (change.getKey().equals(node.getValue().getId())) {
                    // an update returns the new value in getValueAdded()
                    node.setValue(change.getValueAdded());
                    return;
                }
            }
        }
    };

    // TreeView selected item event handler
    private final ChangeListener<TreeItem<Person>> treeSelectionListener = 
            (ov, oldValue, newValue) -> {
        TreeItem<Person> treeItem = newValue;
        logger.log(Level.FINE, "selected item = {0}", treeItem);
        
        if (treeItem == null || treeItem.equals(personTreeView.getRoot())) {
            instanceContent.remove(selectedPerson);
            return;
        }
        // set selectedPerson to the selected treeItem value
        selectedPerson = new Person(treeItem.getValue());
        logger.log(Level.FINE, "selected person = {0}", selectedPerson);
        instanceContent.set(Collections.singleton(selectedPerson), null);        
    };
}
