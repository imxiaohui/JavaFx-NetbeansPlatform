 package personappbound;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Ejercicio PersonAppBound del Libro
 * JavaFx Rich Client Applications with the Netbeans Platform.
 * 
 * Ojetivo: Conocer el uso de PropertyChangeSupport.
 * 
 * @author Ernesto Cantú
 * 18/03/2015
 */
public class PersonAppBound {
    
    /**
     * Método de arranque de la aplicación.
     * @param args 
     */
    public static void main(String[] args) {
        
        //Creo dos beans de la clase Persona.
        Person homer = new Person("Homero" , "Simpson" , Gender.MALE);
        Person marge = new Person("Marge", "Simpson" , Gender.FEMALE);
        
        
        //al yo crear un objeto del tipo PropertyChangeListener (Interfaz que no cuenta con una 
        //implementación) me veo obligado a implementar el método propertyChange.
        final PropertyChangeListener pcl = new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property " + evt.getPropertyName() + " changed from " + evt.getOldValue() + " to " + evt.getNewValue());
            }
            
        };
        
        homer.addPropertyChangeListener(pcl);
        marge.addPropertyChangeListener("lastName",pcl);
        
        homer.setMiddeName("Jay");
        marge.setMiddeName("Louise");
        homer.setSuffix("Junior");
        homer.setSuffix("Jones");
        System.out.println(homer);
        System.out.println(marge);
        
        homer.setMiddeName("Jay");
        
        homer.removePropertyChangeListener(pcl);
        marge.removePropertyChangeListener(pcl);
    }
    
}
