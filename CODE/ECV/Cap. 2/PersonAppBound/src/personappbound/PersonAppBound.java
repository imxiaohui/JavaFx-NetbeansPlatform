package personappbound;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * PersonAppBound para prueba de Property Change Support
 * @author ernesto
 */
public class PersonAppBound {

    public static void main(String[] args) {
        
        Person homer = new Person("Homer", "Simpson", Gender.MALE);
        Person marge = new Person("Marge", "Simpson", Gender.FEMALE);
        
        final PropertyChangeListener pcl = new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property " + evt.getPropertyName() + " changed for " + evt.getSource());
            }
            
        };
        
        homer.addPropertyChangeSupport(pcl);
        marge.addPropertyChangeSupport(pcl);
        
        homer.setMiddleName("Jay");
        marge.setMiddleName("Louise");
        
        homer.setSuffix("Junior");
        marge.setLastName("Jones");
        
        System.out.println(homer);
        System.out.println(marge);
        
        homer.setMiddleName("Chester");
        
        homer.removePropertyChangeSupport(pcl);
        marge.removePropertyChangeSupport(pcl);
    }    
}
