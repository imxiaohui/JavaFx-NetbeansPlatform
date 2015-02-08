/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package personapp;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author gail
 */
public class PersonAppCoarse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Define a property change listener
        final PropertyChangeListener pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(evt.getPropertyName()
                        + " for " + evt.getNewValue());
            }
        };
        
        FamilyTreeManager ftm = FamilyTreeManager.getInstance();
        ftm.addPropertyChangeListener(pcl);
        
        Person homer = new Person("Homer", "Simpson", Person.Gender.MALE);
        Person marge = new Person("Marge", "Simpson", Person.Gender.FEMALE);
        ftm.addPerson(homer);
        ftm.addPerson(marge);
        
        System.out.println(ftm.getAllPeople());
        
        homer.setMiddlename("Chester");
        homer.setSuffix("Junior");
        ftm.updatePerson(homer);
        
        marge.setMiddlename("Louise");
        marge.setLastname("Bouvier-Simpson");
        
        ftm.updatePerson(marge);
        System.out.println(ftm.getAllPeople());
        
        ftm.deletePerson(marge);
        System.out.println(ftm.getAllPeople());
        // delete marge again
        ftm.deletePerson(marge);
        ftm.removePropertyChangeListener(pcl);
    }
}
