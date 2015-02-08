/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.model.data;

import com.asgteach.familytree.model.FamilyTreeManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.modules.OnStop;
import org.openide.util.Lookup;

/**
 *
 * @author gail
 */
@OnStop
public final class DataRemover implements Runnable {
    private static final Logger logger = Logger.getLogger(DataRemover.class.getName());

    @Override
    public void run() {
        FamilyTreeManager ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm != null) {            
            ftm.getAllPeople().stream().forEach((p) -> {
                logger.log(Level.INFO, "Removing {0}.", p);
                ftm.deletePerson(p);
            });                   
        }
    }
    
}
