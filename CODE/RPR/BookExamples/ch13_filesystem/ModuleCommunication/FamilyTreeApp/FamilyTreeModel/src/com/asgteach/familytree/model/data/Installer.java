/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model.data;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.OnStart;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

/**
 *
 * @author gail
 */
@OnStart
public final class Installer implements Runnable {

    private static final Logger logger = Logger.getLogger(Installer.class.getName());

    @Override
    public void run() {
        FamilyTreeManager ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit(1);
        }
        populateMap(ftm);
        // Read configuration information from the system filesystem
        FileObject root = FileUtil.getConfigRoot();
        FileObject persistencetype = root.getFileObject("PersistenceType");
        if (persistencetype != null) {
            final String persistenceTypeName
                    = persistencetype.getAttribute("pTypeName").toString();
            if (persistenceTypeName != null) {
                WindowManager.getDefault().invokeWhenUIReady(() -> {
                    WindowManager.getDefault().getMainWindow().setTitle(
                            WindowManager.getDefault().getMainWindow().getTitle()
                            + " Using "
                            + persistenceTypeName);
                });
            }
        }

    }

    private void populateMap(FamilyTreeManager ftm) {
        // put some test data into the FamilyTreeManager map
        ftm.addPerson(new Person("Homer", "Simpson", Person.Gender.MALE));
        ftm.addPerson(new Person("Marge", "Simpson", Person.Gender.FEMALE));
        ftm.addPerson(new Person("Bart", "Simpson", Person.Gender.MALE));
        ftm.addPerson(new Person("Lisa", "Simpson", Person.Gender.FEMALE));
        ftm.addPerson(new Person("Maggie", "Simpson", Person.Gender.FEMALE));
        logger.log(Level.INFO, "Map populated: {0}", ftm.getAllPeople());
    }

}
