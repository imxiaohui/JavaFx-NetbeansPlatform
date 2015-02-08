/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.threedee.solid;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

/**
 *
 * @author gail
 */
public class ThreeDSolidController implements Initializable {

    @FXML
    private Group root;
    private static final int rwidth = 150;
    private static final int rheight = 150;
    private static final int rdepth = 150;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Blue Box
        final Box blueBox = new Box(rwidth, rheight, rdepth);
        blueBox.setRotationAxis(Rotate.Y_AXIS);
        blueBox.setRotate(45);
        final PhongMaterial blue = new PhongMaterial();
        blue.setDiffuseColor(Color.DARKTURQUOISE);
        blue.setSpecularColor(Color.TURQUOISE);
        blueBox.setMaterial(blue);
        final Rotate rx = new Rotate(45, Rotate.X_AXIS);
        final Rotate ry = new Rotate(45, Rotate.Y_AXIS);
        final Rotate rz = new Rotate(45, Rotate.Z_AXIS);
        blueBox.getTransforms().addAll(rz, ry, rx); 


        Slider s = new Slider(0, 360, 0);
        s.setBlockIncrement(1);
        s.setTranslateX(-100);
        s.setTranslateY(200);
        s.setValue(55);
        blueBox.rotateProperty().bind(s.valueProperty());
        root.getChildren().addAll(blueBox, s);

    }

}
