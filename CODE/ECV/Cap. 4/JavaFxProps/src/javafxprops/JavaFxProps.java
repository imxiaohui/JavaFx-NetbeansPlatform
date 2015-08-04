package javafxprops;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * JavaFX Properties
 * @author Ernesto Cantu
 */
public class JavaFxProps {

    
    public static void main(String[] args) {
        
        
        Person ernesto = new Person();
        
        ernesto.setName("Ernesto");
        ernesto.setAge(26);
        System.out.println(ernesto.getName());
        System.out.println(ernesto.getAge());
        ernesto.nameProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("change detected for " + observable);
                System.out.println("old name = " + oldValue);
                System.out.println("new name = " + newValue);
            } 
        });
        
        ernesto.ageProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("change detected for " + observable);
                System.out.println("old name = " + oldValue);
                System.out.println("new name = " + newValue);
            }
        });
        ernesto.setName("Ernesto Cantú");
        ernesto.setAge(27);
        System.out.println(ernesto.getName());
        System.out.println(ernesto.getAge());
        
    }
    
}
