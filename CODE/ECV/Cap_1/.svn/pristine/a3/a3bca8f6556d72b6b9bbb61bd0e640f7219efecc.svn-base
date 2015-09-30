/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personfxeditor;

import com.asgteach.familytree.javafxtasks.GetPictureTask;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Picture;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author gail
 */
public class PersonFXEditorController implements Initializable {

    @FXML
    private Label idLabel;
    @FXML
    private TextField firstTF;
    @FXML
    private TextField middleTF;
    @FXML
    private TextField lastTF;
    @FXML
    private TextField suffixTF;
    @FXML
    private RadioButton maleGenderButton;
    @FXML
    private RadioButton femaleGenderButton;
    @FXML
    private RadioButton unknownGenderButton;
    @FXML
    private TextArea notesTextArea;
    @FXML
    private ImageView ivHeading;
    @FXML
    private ProgressIndicator progressIndicator;
    boolean inSync = true;
    private Lighting lighting;
    private Image defaultImage;
    private static final Logger logger = Logger.getLogger(PersonFXEditorController.class.getName());
    // SwingPropertyChangeSupport is thread-safe
    // true means fire property change events on the EDT
    private SwingPropertyChangeSupport propChangeSupport;
    public static final String PROP_PERSONEDITOR_MODIFIED = "PersonEditorModified";

    @FXML
    private void handleKeyEvent(KeyEvent event) {
        // we only need to fire a property change event if the controls are
        // marked inSync with the model
        if (inSync) {
            inSync = false;
            logger.log(Level.FINER, "Key Event from {0}", event.getSource());
            this.propChangeSupport.firePropertyChange(PROP_PERSONEDITOR_MODIFIED, event, null);
        }
    }

    @FXML
    public void handleRadioEvent(ActionEvent event) {       
        // we only need to fire a property change event if the controls are
        // marked inSync with the model
        if (inSync) {
            inSync = false;
            logger.log(Level.FINER, "Radio Button Changed {0}", event.getSource());
            this.propChangeSupport.firePropertyChange(PROP_PERSONEDITOR_MODIFIED, event, null);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        propChangeSupport = new SwingPropertyChangeSupport(this, true);
        defaultImage = new Image(
                "com/asgteach/familytree/personfxeditor/personIcon100.png");
        Light.Distant myLight = new Light.Distant();
        myLight.setAzimuth(225);
        lighting = new Lighting();
        lighting.setLight(myLight);
        lighting.setSurfaceScale(5);

        // ImageView properties are set in fxml 
        ivHeading.setImage(defaultImage);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setRadius(10.0);
        ivHeading.setEffect(dropShadow);
        progressIndicator.setProgress(-1);
    }

    // Called from the TopComponent to initialize/update the JavaFX controls
    // Must be called from the JavaFX Application thread
    public void updateForm(Person person) {
        idLabel.setText(person.getId().toString());
        firstTF.setText(person.getFirstname());
        middleTF.setText(person.getMiddlename());
        lastTF.setText(person.getLastname());
        suffixTF.setText(person.getSuffix());
        notesTextArea.setText(person.getNotes());
        if (person.getGender().equals(Person.Gender.FEMALE)) {
            this.femaleGenderButton.setSelected(true);
        } else if (person.getGender().equals(Person.Gender.MALE)) {
            this.maleGenderButton.setSelected(true);
        } else {
            this.unknownGenderButton.setSelected(true);
        }
        inSync = true;
    }
    
    public void updatePicture(final Person person) {
        getPictureInBackground(person);        
    }
    
    // Called from the TopComponent to update Person from
    // the JavaFX controls
    // Must be called from the JavaFX Application thread
    public Person updatePerson(Person person) {
        person.setFirstname(firstTF.getText());
        person.setMiddlename(middleTF.getText());
        person.setLastname(lastTF.getText());
        person.setSuffix(suffixTF.getText());
        person.setNotes(notesTextArea.getText());
        if (femaleGenderButton.isSelected()) {
            person.setGender(Person.Gender.FEMALE);
        } else if (maleGenderButton.isSelected()) {
            person.setGender(Person.Gender.MALE);
        } else  {
            person.setGender(Person.Gender.UNKNOWN);
        }
        return person;    
    }
    
    private void updatePictureControls(Picture picture) {
        if (picture != null) {
            // we have an image
            WritableImage image = null;
            image = SwingFXUtils.toFXImage(picture.getImage(), image);
            ivHeading.setImage(image);
        } else {
            ivHeading.setImage(defaultImage);
        }
    }
    
    private void getPictureInBackground(final Person person) {
        // This service is instantiated each time
        GetPictureTask getPictureTask = new GetPictureTask();
        getPictureTask.setOnSucceeded((WorkerStateEvent t) -> {
            Picture picture = (Picture) t.getSource().getValue();
            updatePictureControls(picture);
            progressIndicator.setVisible(false);
        });
        getPictureTask.setOnFailed((WorkerStateEvent t) -> {
            logger.log(Level.WARNING, "Failed getting picture for {0}", person);
            progressIndicator.setVisible(false);
            // Use a null pic if there's a problem
            updatePictureControls(null);
        });
        // It should always be READY
        if (getPictureTask.getState() == Worker.State.READY) {
            progressIndicator.setVisible(true);
            getPictureTask.setPerson(person);
            getPictureTask.start();
        }
    }

    public void addPropertyChangeListener(
            PropertyChangeListener listener) {
        this.propChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(
            PropertyChangeListener listener) {
        this.propChangeSupport.removePropertyChangeListener(listener);
    }
}
