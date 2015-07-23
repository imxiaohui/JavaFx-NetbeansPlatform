package myinvalidationlistener;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Circle;

/**
 * Invalidation Listener de JavaFX
 * 
 * @author Ernesto Cantu
 * 23 julio 2015
 */
public class MyInvalidationListener {

    public static void main(String[] args) {
        
        final Circle circle1 = new Circle(10.5);
        final Circle circle2 = new Circle(15.5);
        
        circle2.radiusProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                System.out.println("Invalidation detected for " + observable);
                circle1.setRadius(((ObservableValue<Number>)observable).getValue().doubleValue());
            }
        });
        
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        circle2.setRadius(20.5);
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        
    }
    
}
