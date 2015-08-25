package com.asgteach.familytree.model.data;

import com.asgteach.familytree.model.FamilyTreeManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.modules.OnStop;
import org.openide.util.Lookup;

/**
 * Runnable que es ejecutado cuando se termina la aplicación.
 * @author ernesto
 */
@OnStop
public class DataRemover implements Runnable {

    private static final Logger logger = Logger.getLogger(DataRemover.class.getName());
    
    
    @Override
    public void run() {
        FamilyTreeManager ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        
        if(ftm != null){
            ftm.getAllPeople().stream().forEach((p)->{
                logger.log(Level.INFO,"Removing {0}",p);
                ftm.deletePerson(p);
            });
            
        }
    }
    
    
    
}
