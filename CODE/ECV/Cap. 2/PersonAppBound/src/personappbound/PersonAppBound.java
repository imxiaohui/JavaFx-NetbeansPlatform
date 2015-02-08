package personappbound;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * JavaFX Rich Client Programming on the Netbeans Platform
 * 
 * Ernesto Cantú Valle <ernesto.cantu1989@live.com>
 * Probado el día 8 de Febrero del 2015.
 */
public class PersonAppBound {

    public static void main(String[] args) {
        Person homero = new Person("Homero", "Simpson", Gender.MALE );
        Person marge = new Person("Marge", "Simpson", Gender.FEMALE);
       
        final PropertyChangeListener pcl = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property " + evt.getPropertyName() + " "
                        + "changed from " + evt.getOldValue() + " to " +evt.getNewValue());
            }
        };
        
        
        homero.addPropertyChangeListener(pcl);
        marge.addPropertyChangeListener(pcl);
        
        
        homero.setMiddleName("Chester");
        marge.setMiddleName("Lousie");
        homero.setSuffix("Junior");
        homero.setLastName("Jones");

        System.out.println(homero);
        System.out.println(marge);
        // old and new value same, no pce generated
        homero.setMiddleName("Chester");

        homero.removePropertyChangeListener(pcl);
        marge.removePropertyChangeListener(pcl);
        
    }
    
}
