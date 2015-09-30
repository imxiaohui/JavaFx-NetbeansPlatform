package mybind;


import javafx.scene.shape.Circle;

/**
 * Unidirectional Binding de JavaFX
 * 
 * @author Ernesto Cantu
 * 23 julio 2015
 */
public class MyBind {

    public static void main(String[] args) {
        
        Circle circle1 = new Circle(10.5);
        Circle circle2 = new Circle(15.5);
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        circle1.radiusProperty().bind(circle2.radiusProperty());
        if(circle1.radiusProperty().isBound()){
            System.out.println("Circle1 radiusProperty is Bound");
        }
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        circle2.setRadius(20.5);
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        
        circle1.radiusProperty().unbind();
        if(!circle1.radiusProperty().isBound()){
            System.out.println("Circle1 radiusProperty is Unbound");
        circle2.setRadius(30.5);
        System.out.println("Circle 1 radius: " + circle1.getRadius());
        System.out.println("Circle 2 radius: " + circle2.getRadius());
        }
    }
    
}
