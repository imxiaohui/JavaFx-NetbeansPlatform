/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybindbidirectional;

import javafx.scene.shape.Circle;

/**
 *
 * @author gail
 */
public class MyBindBidirectional {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Circle circle1 = new Circle(10.5);
        Circle circle2 = new Circle(15.5);

        // circle1 takes on value of circle2 radius
        circle1.radiusProperty().bindBidirectional(circle2.radiusProperty());
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());
               
        circle2.setRadius(20.5);
        // Both circles are now 20.5
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());

        circle1.setRadius(30.5);
        // Both circles are now 30.5
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());

        circle1.radiusProperty().unbindBidirectional(circle2.radiusProperty());
    }
    
}
