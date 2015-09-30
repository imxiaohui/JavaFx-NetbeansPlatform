/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.modules.OnStop;

/**
 *
 * @author gail
 */
@OnStop
public final class JPAShutdown implements Runnable {

    private static final Logger logger = Logger.getLogger(
            JPAShutdown.class.getName());

    @Override
    public void run() {
        FamilyTreeDAOJPA.closeEMF();
        logger.log(Level.FINE, "Entity Manager closed");
    }

}
