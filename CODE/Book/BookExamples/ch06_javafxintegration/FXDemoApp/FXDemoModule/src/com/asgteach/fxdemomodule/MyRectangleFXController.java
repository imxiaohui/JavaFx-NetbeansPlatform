/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.fxdemomodule;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author gail
 */
public class MyRectangleFXController implements Initializable {

    @FXML
    private Rectangle rectangle;
    @FXML
    private StackPane stackpane;
    private RotateTransition rt;

    @FXML
    private void handleMouseClick(MouseEvent evt) {
        rt.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rectangle.setStrokeWidth(5.0);
        rectangle.setStroke(Color.GOLDENROD);
        // Create and configure RotateTransition rt
        rt = new RotateTransition(Duration.millis(3000), stackpane);
        rt.setToAngle(180);
        rt.setFromAngle(0);
        rt.setAutoReverse(true);
        rt.setCycleCount(4);
    }
}
