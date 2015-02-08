/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package personapp;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author gail
 */
public class PersonAppBound {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Person homer = new Person("Homer", "Simpson", Gender.MALE);
        Person marge = new Person("Marge", "Simpson", Gender.FEMALE);

        // create a property change listener
        final PropertyChangeListener pcl = (PropertyChangeEvent evt) -> 
            System.out.println("Property " + evt.getPropertyName()
                    + " changed for " + evt.getSource());

        homer.addPropertyChangeListener(pcl);
        marge.addPropertyChangeListener(pcl);

        homer.setMiddlename("Chester");
        marge.setMiddlename("Lousie");
        homer.setSuffix("Junior");
        homer.setLastname("Jones");

        System.out.println(homer);
        System.out.println(marge);
        // old and new value same, no pce generated
        homer.setMiddlename("Chester");

        homer.removePropertyChangeListener(pcl);
        marge.removePropertyChangeListener(pcl);
    }
}
