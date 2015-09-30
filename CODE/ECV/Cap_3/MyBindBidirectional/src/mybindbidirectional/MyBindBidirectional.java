package mybindbidirectional;


import javafx.scene.shape.Circle;

/**
 * Bidirectional Binding de JavaFX
 * 
 * @author Ernesto Cantu
 * 23 julio 2015
 */
public class MyBindBidirectional {

    public static void main(String[] args) {
        
        Circle circle1 = new Circle(10.5);
        Circle circle2 = new Circle(15.5);

        circle1.radiusProperty().bindBidirectional(circle2.radiusProperty());
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        
        circle2.setRadius(20.5);
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        
        circle1.setRadius(30.5);
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        
        circle1.radiusProperty().unbindBidirectional(circle2.radiusProperty());
    }
    
}
