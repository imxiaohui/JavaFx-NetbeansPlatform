package personappbound;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * JavaFX Rich Client Programming on the Netbeans Platform
 * 
 * Ernesto Cantú Valle <ernesto.cantu1989@live.com>
 * Probado el día 8 de Febrero del 2015.
 */
public class PersonAppCoarse {

    public static void main(String[] args) {
        
        //Registro el Receptor y defino la accion a realizarse. En este caso
        //Se escribe un mensaje en consola
        final PropertyChangeListener pcl = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(evt.getPropertyName() + " for " + evt.getNewValue());
            }
        };
        
        //Obtengo accesso al FamilyTreeManager y registro a main como un Receptor
        FamilyTreeManager familyTreeManager = FamilyTreeManager.getInstance();
        familyTreeManager.addPropertyChangeListener(pcl);
        
        Person homero = new Person("Homero", "Simpson", Person.Gender.MALE);
        Person marge = new Person("Marge", "Simpson", Person.Gender.FEMALE);
        
        //Se dispara el evento Person_Added
        familyTreeManager.addPerson(homero);
        familyTreeManager.addPerson(marge);
        
        System.out.println(familyTreeManager.getAll());
        
        //Se dispara el evento Person_Updated
        homero.setMiddleName("Jay");
        homero.setSuffix("Junior");
        familyTreeManager.updatePerson(homero);
        marge.setMiddleName("Louise");
        marge.setLastName("Bouvier-Simpson");
        familyTreeManager.updatePerson(marge);
        
        System.out.println(familyTreeManager.getAll());
        
        //Se dispara el evento Person_Deleted
        familyTreeManager.deletePerson(marge);
        System.out.println(familyTreeManager.getAll());
        familyTreeManager.deletePerson(marge);
        familyTreeManager.removePropertyChangeListener(pcl);
    }
    
}
