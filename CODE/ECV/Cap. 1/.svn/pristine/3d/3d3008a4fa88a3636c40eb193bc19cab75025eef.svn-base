/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.persontimeline;

import com.asgteach.familytree.javafxtasks.EventPeople;
import com.asgteach.familytree.javafxtasks.GetEventsTask;
import com.asgteach.familytree.javafxtasks.GetPictureTask;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Picture;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author gail
 */
public class PersonTimeLineController implements Initializable {

    @FXML
    private VBox topVBox;
    @FXML
    private ImageView ivHeading;
    @FXML
    private HBox eventsBox;
    @FXML
    private Label personLabel;
    @FXML
    private Label personNotes;
    @FXML
    private ProgressIndicator personProgress;
    @FXML
    private ProgressIndicator eventProgress;
    private FadeTransition ftTop;
    private Image eventImage;
    private SequentialTransition st;
    private Lighting lighting;
    private Image defaultImage;
    private static final Logger logger = Logger.getLogger(PersonTimeLineController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createDisplay();
    }

    private void createDisplay() {
        // load the image
        defaultImage = new Image(
                "com/asgteach/familytree/persontimeline/personIcon100.png");
        eventImage = new Image(
                "com/asgteach/familytree/persontimeline/eventIcon100.png");
        Light.Distant myLight = new Light.Distant();
        myLight.setAzimuth(225);

        lighting = new Lighting();
        lighting.setLight(myLight);
        lighting.setSurfaceScale(5);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setRadius(10.0);

        // resizes the image while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance

        ivHeading.setImage(defaultImage);
        ivHeading.setFitHeight(70);
        ivHeading.setPreserveRatio(true);
        ivHeading.setSmooth(true);
        ivHeading.setCache(true);
        ivHeading.setEffect(dropShadow);
        final ScaleTransition scaleTr = new ScaleTransition(Duration.millis(1000), ivHeading);
        ivHeading.setOnMouseEntered((MouseEvent t) -> {
            scaleTr.stop();
            scaleTr.setToX(2.0);
            scaleTr.setToY(2.0);
            scaleTr.play();
        });
        ivHeading.setOnMouseExited((MouseEvent t) -> {
            scaleTr.stop();
            scaleTr.setToX(1.0);
            scaleTr.setToY(1.0);
            scaleTr.play();
        });


        personLabel.setEffect(lighting);

        ftTop = new FadeTransition(Duration.millis(759), topVBox);
        ftTop.setFromValue(0.0);
        ftTop.setToValue(1.0);

//        AnchorPane.setBottomAnchor(eventsBox, 25.0);
//        AnchorPane.setLeftAnchor(eventsBox, 15.0);
//        AnchorPane.setRightAnchor(eventsBox, 15.0);
    }

    public void clearPanel() {
        personLabel.setText("");
        personNotes.setText("");
        ivHeading.setImage(defaultImage);
        eventsBox.getChildren().clear();
        personLabel.setOnMouseEntered(null);
        personLabel.setOnMouseExited(null);
    }

    public void updatePerson(Person person) {
        getPictureInBackground(person);
    }

    public void updateEvents(Person person) {
        getTasksInBackground(person);
    }

    public void updateAll(Person person) {
        updatePerson(person);
        updateEvents(person);
    }

    private void getPictureInBackground(final Person person) {
        // This service is instantiated each time
        GetPictureTask getPictureTask = new GetPictureTask();
        getPictureTask.setOnSucceeded((WorkerStateEvent t) -> {
            Picture picture = (Picture) t.getSource().getValue();
            updatePersonControls(person, picture);
            personProgress.setVisible(false);
        });
        getPictureTask.setOnFailed((WorkerStateEvent t) -> {
            logger.log(Level.WARNING, "Failed getting picture for {0}", person);
            personProgress.setVisible(false);
            // Use a null pic if there's a problem
            updatePersonControls(person, null);
        });
        // It should always be READY
        if (getPictureTask.getState() == Worker.State.READY) {
            personProgress.setVisible(true);
            getPictureTask.setPerson(person);
            getPictureTask.start();
        }
    }

    private void updatePersonControls(Person person, Picture picture) {
        if (picture != null) {
            // we have an image
            WritableImage image = null;
            image = SwingFXUtils.toFXImage(picture.getImage(), image);
            ivHeading.setImage(image);
        } else {
            ivHeading.setImage(defaultImage);
        }
        final ScaleTransition scaleTr = new ScaleTransition(Duration.millis(1000), personLabel);
        personLabel.setText(person.toString());
        personLabel.setOnMouseEntered((MouseEvent t) -> {
            scaleTr.stop();
            scaleTr.setToX(1.5);
            scaleTr.setToY(1.5);
            scaleTr.play();
        });
        personLabel.setOnMouseExited((MouseEvent t) -> {
            scaleTr.stop();
            scaleTr.setToX(1.0);
            scaleTr.setToY(1.0);
            scaleTr.play();
        });
        personNotes.setText(person.getNotes());
        ftTop.playFromStart();
    }

    private void getTasksInBackground(final Person person) {
        // This service is instantiated each time
        GetEventsTask getEventsTask = new GetEventsTask();
        getEventsTask.setOnSucceeded((WorkerStateEvent t) -> {
            List<EventPeople> eventPeople = (List<EventPeople>) t.getSource().getValue();
            updateEventControls(eventPeople);
            eventProgress.setVisible(false);
        });
        getEventsTask.setOnFailed((WorkerStateEvent t) -> {
            logger.log(Level.WARNING, "Failed getting events for {0}", person);
            // Just clear the events area if there's a problem
            eventsBox.getChildren().clear();
            eventProgress.setVisible(false);
        });
        // It should always be READY
        if (getEventsTask.getState() == Worker.State.READY) {
            eventProgress.setVisible(true);
            getEventsTask.setPerson(person);
            getEventsTask.start();
        }
    }

    private void updateEventControls(List<EventPeople> eventPeople) {

        eventsBox.getChildren().clear();
        eventPeople.stream().map((ep) -> {
            Event event = ep.getEvent();
            final VBox vbox = new VBox();
            vbox.setOpacity(0.0);
            vbox.setMaxWidth(130);
            vbox.setAlignment(Pos.TOP_CENTER);
            vbox.setPadding(new Insets(0, 0, 0, 0));
            final ScaleTransition scaleTr = new ScaleTransition(Duration.millis(1000), vbox);
            vbox.setOnMouseEntered((MouseEvent t) -> {
                scaleTr.stop();
                scaleTr.setToX(1.5);
                scaleTr.setToY(1.5);
                scaleTr.play();
            });
            vbox.setOnMouseExited((MouseEvent t) -> {
                scaleTr.stop();
                scaleTr.setToX(1.0);
                scaleTr.setToY(1.0);
                scaleTr.play();
            });
            Rectangle hRectangle = new Rectangle(130, 3, Paint.valueOf("#0076a3"));
            Rectangle vRectangle = new Rectangle(3, 10, Paint.valueOf("#0076a3"));
            ImageView iv = new ImageView();
            iv.setImage(eventImage);
            iv.setFitWidth(45);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);
            DropShadow shadow = new DropShadow();
            shadow.setOffsetX(5.0);
            shadow.setOffsetY(5.0);
            shadow.setRadius(10.0);
            iv.setEffect(shadow);
            Label eventNameLabel = new Label(event.getEventName());
            eventNameLabel.setEffect(lighting);
            eventNameLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
            eventNameLabel.setTextFill(Paint.valueOf("#0076a3"));
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
            Label eventDateLabel = new Label(format.format(event.getEventDate()));
            eventDateLabel.setEffect(lighting);
            eventDateLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
            vbox.getChildren().addAll(hRectangle, vRectangle,
                    iv, eventNameLabel, eventDateLabel);
            for (Person p : ep.getPeople()) {
                Label labelpeople = new Label(p.toString());
                labelpeople.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
                labelpeople.setWrapText(true);
                labelpeople.setTextAlignment(TextAlignment.CENTER);
                vbox.getChildren().add(labelpeople);
            }
            return vbox;
        }).forEach((vbox) -> {
            eventsBox.getChildren().add(vbox);
        });

        // Build a fadetransition for each VBox in the eventsBox node
        st = new SequentialTransition();
        for (Node node : eventsBox.getChildren()) {
            FadeTransition ft = new FadeTransition(Duration.millis(500), node);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            st.getChildren().add(ft);
        }
        st.playFromStart();
    }
}
