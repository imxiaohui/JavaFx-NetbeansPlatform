
package com.asgteach.familytree.model.data;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.modules.OnStart;
import org.openide.util.Lookup;

/**
 * Clase Runnable que se ejecuta al inicio del proyecto.
 * @author ernesto cantu
 */
@OnStart
public class DataInstaller implements Runnable {

    private static final Logger logger = Logger.getLogger(DataInstaller.class.getName());
    
    @Override
    public void run() {
        FamilyTreeManager ftm = Lookup.getDefault().lookup(FamilyTreeManager.class); // me regresará la instancia que brinda el servicio
        
        if(ftm == null) {
            logger.log(Level.SEVERE,"Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        
        populateMap(ftm);
    }
    
    private void populateMap(FamilyTreeManager ftm){
        ftm.addPerson(new Person("Homer","Simpson",Person.Gender.MALE));
        ftm.addPerson(new Person("Mage","Simpson",Person.Gender.FEMALE));
        ftm.addPerson(new Person("Bart","Simpson",Person.Gender.MALE));
        ftm.addPerson(new Person("Lisa","Simpson",Person.Gender.FEMALE));
        ftm.addPerson(new Person("Maggie","Simpson",Person.Gender.FEMALE));
        logger.log(Level.FINE,ftm.getAllPeople().toString());
    }
    
}
