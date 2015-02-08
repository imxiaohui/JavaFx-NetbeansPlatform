/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybind;

import javafx.scene.shape.Circle;

/**
 *
 * @author gail
 */
public class MyBind {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Circle circle1 = new Circle(10.5);
        Circle circle2 = new Circle(15.5);
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());
        // Bind circle1 radius to circle2 radius
        circle1.radiusProperty().bind(circle2.radiusProperty());
        if (circle1.radiusProperty().isBound()) {
            System.out.println("Circle1 radiusProperty is bound");
        }
        
        // Radius properties are now the same
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());
        
        // Both radius properties will now update
        circle2.setRadius(20.5);
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());

        // circle1 radius no longer bound to circle2 radius
        circle1.radiusProperty().unbind();
        if (!circle1.radiusProperty().isBound()) {
            System.out.println("Circle1 radiusProperty is unbound");
        }

        // Radius properties are now no longer the same
        circle2.setRadius(30.5);
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());
    }
    
}
