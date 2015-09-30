/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.dao.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.modules.OnStart;

/**
 *
 * @author gail
 */
@OnStart
public final class JPAStartup implements Runnable {
    
    // The parent of all FamilyTree Loggers in this application
    private static final Logger logger = Logger.getLogger("com.asgteach.familytree");
     
     @Override
     public void run() {
         // Adjust level for FamilyTree App loggers
            logger.setLevel(Level.FINE); 
            logger.log(Level.INFO, "Logger level set");    
     }

    
}
