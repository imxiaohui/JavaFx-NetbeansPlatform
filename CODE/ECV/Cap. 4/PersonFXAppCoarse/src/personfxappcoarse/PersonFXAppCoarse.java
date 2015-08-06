package personfxappcoarse;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import javafx.collections.MapChangeListener;

/**
 * Ejercicio de PersonFXAppCoarse.
 * Seccion 4.3 Observable Collections
 * @author Ernesto Cantú
 */
public class PersonFXAppCoarse {

    public static void main(String[] args) {
        
        final FamilyTreeManager ftm = FamilyTreeManager.getInstance();
        
        ftm.addListener(mapChangeListener);
        
        final Person homer = new Person("homer","simpson",Person.Gender.MALE);
        final Person marge = new Person("marge","simpson",Person.Gender.FEMALE);
        
        
        ftm.addPerson(homer);
        ftm.addPerson(marge);
        
        ftm.addPerson(marge);
        
        homer.setMiddleName("Jay");
        homer.setSuffix("Junior");
        
        ftm.updatePerson(homer);
        
        marge.setMiddleName("Louise");
        marge.setLastName("Bouvier-Simpson");
        
        
        ftm.updatePerson(marge);
        ftm.deletePerson(marge);
        ftm.deletePerson(marge);
        
    }
    
    private static final MapChangeListener<Long,Person> mapChangeListener = (change) ->{
      if(change.wasAdded() && change.wasRemoved()){
          System.out.println("\tUpdated");
      }else if(change.wasAdded()){
          System.out.println("\tADDED");
      }else if(change.wasRemoved()){
          System.out.println("\tREMOVED");
      }
        System.out.println("\tmap = " + change.getMap());
        System.out.println("\t\t" + change.getValueAdded()
                + " was Added [" + change.getKey() + "].");
        System.out.println("\t\t" + change.getValueRemoved()
                + " was Removed [" + change.getKey() + "].");
      
    };
    
}
