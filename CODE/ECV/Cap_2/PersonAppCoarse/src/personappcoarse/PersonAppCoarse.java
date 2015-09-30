package personappcoarse;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PersonAppCoarse {

    public static void main(String[] args) {
       
//        Clase Interna Anonima que implementa a la interfase PropertyChangeListener         
//        final PropertyChangeListener pcl = new PropertyChangeListener(){
//            
//            @Override
//            public void propertyChange(PropertyChangeEvent evt){
//                System.out.println(evt.getPropertyName() + " for " + evt.getNewValue());
//            }
//        };
        
        
        final PropertyChangeListener pcl = (PropertyChangeEvent evt) -> {
                System.out.println(evt.getPropertyName() 
                        + " for " + evt.getNewValue());
        };
        
        FamilyTreeManager ftm = FamilyTreeManager.getInstance();
        ftm.addPropertyChangeListener(pcl);
        
        Person homer = new Person("Homer", "Simpson", Person.Gender.MALE);
        Person marge = new Person("Marge", "Simpson", Person.Gender.FEMALE);
        Person bart = new Person("Bart", "Simpson", Person.Gender.MALE);
        ftm.addPerson(homer);
        ftm.addPerson(marge);
        ftm.addPerson(bart);
        
        System.out.println(ftm.getAllPeople());
        
        homer.setMiddleName("Jay");
        homer.setSuffix("Jr.");
        ftm.updatePerson(homer);
        
        marge.setMiddleName("Louise");
        marge.setLastName("Bouvier-Simpson");
        ftm.updatePerson(marge);
        
        System.out.println(ftm.getAllPeople());
        
        ftm.deletePerson(bart);
        System.out.println(ftm.getAllPeople());
        
        ftm.deletePerson(marge);
        ftm.deletePerson(homer);
        
        ftm.removePropertyChangeListener(pcl);      
        
    }    
}
