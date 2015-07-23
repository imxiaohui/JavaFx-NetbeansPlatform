/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racetrackfxapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author ernesto
 */
public class RaceTrackController implements Initializable {
    
    @FXML
    private Rectangle rectangle;
    
    @FXML
    private Path path;
    
    @FXML
    private Text text;
    
    @FXML
    private Button startPauseButton;
    
    @FXML
    private Button slowerButton;
    
    @FXML
    private Button fasterButton;
    
    private PathTransition pathTransition;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        pathTransition = new PathTransition(Duration.seconds(6), path,rectangle);
        
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        
        //3.21 3.22 y 3.23
    }    
    
}
