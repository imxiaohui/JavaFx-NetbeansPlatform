/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mychangelistener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Circle;

/**
 *
 * @author gail
 */
public class MyChangeListener {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
               
        // Define some circles
        final Circle circle1 = new Circle(10.5);        
        final Circle circle2 = new Circle(15.5);
     
        // Use change listener to track changes to circle2's radius property
        circle2.radiusProperty().addListener(new ChangeListener<Number>() {       

            @Override
            public void changed(ObservableValue<? extends Number> ov, 
                            Number oldValue, Number newValue) {
                System.out.println("Change detected for " + ov);
                circle1.setRadius(newValue.doubleValue());
            }
        });
        
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());
        circle2.setRadius(20.5);
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());
    }
    
}
