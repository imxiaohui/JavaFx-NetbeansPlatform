/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.threedee.demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

/**
 *
 * @author gail
 */
public class ThreeDeeController implements Initializable {

    @FXML
    private Group root;
    private static final int rwidth = 200;
    private static final int rheight = 200;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Near
        Circle red = new Circle(rwidth / 2,  Color.CRIMSON);
        red.setTranslateX(10);
        red.setTranslateY(30);
        red.setTranslateZ(1);

        // Far
        Rectangle green = new Rectangle(rwidth, rheight, Color.DARKSEAGREEN);
        green.setTranslateX(140);
        green.setTranslateY(0);
        green.setTranslateZ(350);

        // Mid
        Rectangle blue = new Rectangle(rwidth, rheight, Color.DARKTURQUOISE);
        blue.setTranslateX(-40);
        blue.setTranslateY(100);
        blue.setTranslateZ(250);
        
        // Text
        Text text = new Text("Rotate Me");
        text.setFont(Font.font("Papyrus", FontWeight.BOLD, 80));
        text.setFill(Color.web("#222222"));
        text.setTranslateX(100);
        text.setTranslateY(50);
        text.setTranslateZ(80);

        Group rotationGroup = new Group(red, green, blue, text); // determines rendering order for 2D
        rotationGroup.setTranslateX(125);
        rotationGroup.setTranslateY(125);
        rotationGroup.setRotationAxis(Rotate.Y_AXIS);

        Slider s = new Slider(0, 360, 0);
        s.setBlockIncrement(1);
        s.setTranslateX(130);
        s.setTranslateY(400);
        s.setValue(45);
        rotationGroup.rotateProperty().bind(s.valueProperty());
        root.getChildren().addAll(rotationGroup, s);

    }

}
