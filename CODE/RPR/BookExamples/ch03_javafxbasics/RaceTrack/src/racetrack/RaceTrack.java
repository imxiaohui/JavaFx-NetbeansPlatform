/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package racetrack;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author gail
 */
public class RaceTrack extends Application {
    
    @Override
    public void start(Stage stage) {
       // Constants to control the transition's rate changes
        final double maxRate = 7.0;
        final double minRate = .3;
        final double rateDelta = .3;

        // Constants to position the rectangle and the path
        final int startX = 0;
        final int startY = 0;
        final int pathStrokeWidth = 15;

        final IntegerProperty lapCounterProperty = new SimpleIntegerProperty(0);

        final PathTransition pathTransition
                = MyRectangle.build(startX, startY, pathStrokeWidth);
        
        Line startLine = new Line(startX-25, startY, startX + 10, startY);
        startLine.setStrokeWidth(4);
        startLine.setStroke(Color.BLUE);
        startLine.setStrokeLineCap(StrokeLineCap.ROUND);
        startLine.setEffect(new DropShadow(10, 5, 5, Color.GRAY));

        Text text = new Text();
        text.setFont(Font.font("Verdana", 16));
        text.setEffect(new Reflection());

        // Put the Path, Line, and Rectangle all in a Group so that the
        // StackPane will treat it as one
        Group myGroup = new Group();
        myGroup.getChildren().addAll(pathTransition.getPath(), startLine,
                pathTransition.getNode());
        
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(myGroup, text);

        // We count laps by noticing when the currentTimeProperty changes and the
        // oldValue is greater than the newValue, which is only true once per lap
        // We then increment the lapCounterProperty
        
        pathTransition.currentTimeProperty().addListener((ObservableValue<? extends Duration> ov,
                Duration oldValue, Duration newValue) -> {
                    if (oldValue.greaterThan(newValue)) {
                        lapCounterProperty.set(lapCounterProperty.get() + 1);
                    }

                });

        // Bind the text's textProperty to the lapCounterProperty and format it
        text.textProperty().bind(lapCounterProperty.asString("Lap Counter: %s"));

        Button startPauseButton = new Button();
        startPauseButton.setPrefWidth(80);
        startPauseButton.setOnAction((ActionEvent event) -> {
            if (pathTransition.getStatus() == Animation.Status.RUNNING) {
                pathTransition.pause();
            } else {
                pathTransition.play();
            }
        });
        startPauseButton.textProperty().bind(
                new When(pathTransition.statusProperty()
                        .isEqualTo(Animation.Status.RUNNING))
                .then("Pause").otherwise("Start"));

        Button fasterButton = new Button(" >> ");
        fasterButton.setOnAction((ActionEvent event) -> {
            double currentRate = pathTransition.getRate();
            if (currentRate >= maxRate) {
                return;
            }
            pathTransition.setRate(currentRate + rateDelta);
            System.out.printf("faster rate = %.2f\n", pathTransition.getRate());
        });

        Button slowerButton = new Button(" << ");
        slowerButton.setOnAction((ActionEvent event) -> {
            double currentRate = pathTransition.getRate();
            if (currentRate <= minRate) {
                return;
            }
            pathTransition.setRate(currentRate - rateDelta);
            System.out.printf("slower rate = %.2f\n", pathTransition.getRate());
        });

        fasterButton.disableProperty().bind(pathTransition.statusProperty()
                .isNotEqualTo(Animation.Status.RUNNING));
        slowerButton.disableProperty().bind(pathTransition.statusProperty()
                .isNotEqualTo(Animation.Status.RUNNING));

        HBox buttonBox = new HBox(20, slowerButton, startPauseButton, fasterButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(20, stackPane, buttonBox);
        vbox.setAlignment(Pos.CENTER); 
        vbox.setStyle("-fx-background-color: lightblue;");
        
        final Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Race Track");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
