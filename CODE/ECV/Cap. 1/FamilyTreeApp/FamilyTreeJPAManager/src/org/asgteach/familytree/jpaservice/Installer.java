/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.asgteach.familytree.jpaservice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    public static final EntityManagerFactory EMF;
    public static final EntityManager EM;
    static {
        try {
            EMF = Persistence.createEntityManagerFactory("FamilyTreeWebPU");
            System.out.println("FTJPAManager(Installer):Entity Manager Factory Created.");
            EM = EMF.createEntityManager();
            System.out.println("FTJPAManager(Installer):Entity Manager Created.");
            
        } catch (Throwable ex) {
            System.err.println(ex.getClass().getSimpleName());
            System.err.println("Make sure that the JavaDB Database Server has been started.");
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void close() {
        EM.close();
        EMF.close();
    }



}
