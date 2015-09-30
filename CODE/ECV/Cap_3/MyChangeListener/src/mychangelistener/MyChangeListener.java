package mychangelistener;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Circle;

/**
 * Change Listener de JavaFX
 * 
 * @author Ernesto Cantu
 * 23 julio 2015
 */
public class MyChangeListener {

    public static void main(String[] args) {
        
        final Circle circle1 = new Circle(10.5);
        final Circle circle2 = new Circle(15.5);
        
        circle2.radiusProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Change detected for " + observable);
                circle1.setRadius(newValue.doubleValue());
            }
            
        });
        
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        circle2.setRadius(20.5);
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        
    }
    
}
