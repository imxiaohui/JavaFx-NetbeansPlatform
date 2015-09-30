package personfxappcoarse;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import javafx.beans.property.StringProperty;
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
        
        //Added event fired
        ftm.addPerson(homer);
        ftm.addPerson(marge);
        
        //event NOT fired
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
    
//    private static final MapChangeListener<Long,Person> mapChangeListener = new MapChangeListener<Long, Person>() {
//
//        @Override
//        public void onChanged(MapChangeListener.Change<? extends Long, ? extends Person> change) {
//            
//        }
//    }
//   Objeto del tipo MapChangeListener(Implementado) Implementado
    private static final MapChangeListener<Long,Person> mapChangeListener = (change) ->{
      if(change.wasAdded() && change.wasRemoved()){
          System.out.println("\tUPDATED");
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
