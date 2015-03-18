 package personappcoarse;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Ejercicio PersonAppCoarse del Libro
 * JavaFx Rich Client Applications with the Netbeans Platform.
 * 
 * Ojetivo: Conocer el uso de PropertyChangeSupport.
 * 
 * @author Ernesto Cantú
 * 18/03/2015
 */
public class PersonAppCoarse {
    
    /**
     * Método de arranque de la aplicación.
     * @param args 
     */
    public static void main(String[] args) {
        
      
        //al yo crear un objeto del tipo PropertyChangeListener (Interfaz que no cuenta con una 
        //implementación) me veo obligado a implementar el método propertyChange.
        final PropertyChangeListener pcl = new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println( evt.getPropertyName() + " for " + evt.getNewValue());
            }
            
        };
        
        //Suscribo a mi objeto listener al PropertyChangeListeners List de ftm.
        FamilyTreeManager ftm = FamilyTreeManager.getInstance();
        ftm.addPropertyChangeListener(pcl);
        
         //Creo dos beans de la clase Persona.
        Person homer = new Person("Homero" , "Simpson" , Gender.MALE);
        Person marge = new Person("Marge", "Simpson" , Gender.FEMALE);
        
        ftm.addPerson(homer);
        ftm.addPerson(marge);
        
        System.out.println(ftm.getAllPeople());
        
        homer.setMiddeName("Jay");
        homer.setSuffix("Junior");
        ftm.updatePerson(homer);
        
        marge.setMiddeName("Louise");
        marge.setLastName("Bouvier-Simpson");
        ftm.updatePerson(marge);
        
        System.out.println(ftm.getAllPeople());
        
        ftm.deletePerson(marge);
        System.out.println(ftm.getAllPeople());
        ftm.deletePerson(marge);
        ftm.removePropertyChangeListener(pcl);
    }
    
}
