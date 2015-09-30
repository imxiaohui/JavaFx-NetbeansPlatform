/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model.data;

import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Picture;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.modules.OnStart;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 *
 * @author gail
 */
@OnStart
public final class DataInstaller implements Runnable {

    private static final Logger logger = Logger.getLogger(
            DataInstaller.class.getName());

    @Override
    public void run() {
        FamilyTreeManager ftm = Lookup.getDefault().lookup(
                FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        populateDatabase(ftm);
    }

    private void populateDatabase(FamilyTreeManager ftm) {

        Person Abraham = ftm.newPerson("Abraham", "Simpson", Person.Gender.MALE);
        Person Mona = ftm.newPerson("Mona", "Simpson", Person.Gender.FEMALE);
        Person Homer = ftm.newPerson("Homer", "Simpson", Person.Gender.MALE);
        Person Herb = ftm.newPerson("Herb", "Powell", Person.Gender.MALE);
        Person Marge = ftm.newPerson("Marge", "Simpson", Person.Gender.FEMALE);

        Person Bart = ftm.newPerson("Bart", "Simpson", Person.Gender.MALE);
        Person Lisa = ftm.newPerson("Lisa", "Simpson", Person.Gender.FEMALE);
        Person Maggie = ftm.newPerson("Maggie", "Simpson", Person.Gender.FEMALE);

        Person Clancy = ftm.newPerson("Clancy", "Bouvier", Person.Gender.MALE);
        Person Jacqueline = ftm.newPerson("Jacqueline", "Bouvier", Person.Gender.FEMALE);
        Person Patty = ftm.newPerson("Patty", "Bouvier", Person.Gender.FEMALE);
        Person Selma = ftm.newPerson("Selma", "Bouvier", Person.Gender.FEMALE);
        Person Ling = ftm.newPerson("Ling", "Bouvier", Person.Gender.FEMALE);
        Person Abe = ftm.newPerson("Abraham", "Lincoln", Person.Gender.MALE);

        // Create some pics 
        try {
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/HomerSimpson.png"), Homer);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/AbrahamSimpson.png"), Abraham);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/BartSimpson.png"), Bart);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/LisaSimpson.png"), Lisa);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/MaggieSimpson.png"), Maggie);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/PattyBouvier.png"), Patty);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/SelmaBouvier.png"), Selma);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/MargeSimpson.png"), Marge);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/ClancyBouvier.png"), Clancy);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/HerbPowell.png"), Herb);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/MonaSimpson.png"), Mona);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/LingBouvier.png"), Ling);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/JacquelineBouvier.gif"), Jacqueline);
            createPicture(ftm, new URL("nbres:com/asgteach/familytree/model/resources/Abraham-Lincoln.jpg"), Abe);
        } catch (MalformedURLException ex) {
            logger.log(Level.WARNING, null, ex);
        }
        // Get them back
//        Picture homerPic = ftm.getPicture(Homer);
//        if (homerPic != null) {
//            logger.log(Level.FINER, "picture for {0}, file={1}",
//                    new Object[]{homerPic.getPerson(), homerPic.getFilename()});
//        }
//        Picture margePic = ftm.getPicture(Marge);
//        if (margePic != null) {
//            logger.log(Level.FINER, "picture for {0}, file={1}",
//                    new Object[]{margePic.getPerson(), margePic.getFilename()});
//        }

        // Now we get to make up some stuff
        // Abraham
        ftm.newMarriage(Abraham, Mona,
                new GregorianCalendar(1955, 1, 8).getTime(),
                "Springfield", "", "USA");

        // Homer
        ftm.newBirth(Homer, new GregorianCalendar(1960, 4, 21).getTime(),
                "Springfield", "", "USA");
        ftm.addParent(Homer, Abraham);
        ftm.addParent(Homer, Mona);
        ftm.newMarriage(Homer, Marge,
                new GregorianCalendar(1977, 6, 15).getTime(),
                "Springfield", "", "USA");

        // Herb
        ftm.newBirth(Herb, new GregorianCalendar(1958, 4, 25).getTime(),
                "Springfield", "", "USA");
        ftm.addParent(Herb, Abraham);
        Herb.setNotes("Older half-brother of Homer.");
        ftm.updatePerson(Herb);

        // Marge
        ftm.newBirth(Marge, new GregorianCalendar(1962, 5, 8).getTime(),
                "Springfield", "", "USA");
        ftm.addParent(Marge, Clancy);
        ftm.addParent(Marge, Jacqueline);

        // Bart
        ftm.newBirth(Bart, new GregorianCalendar(1980, 3, 1).getTime(),
                "Springfield", "", "USA");
        ftm.addParent(Bart, Homer);
        ftm.addParent(Bart, Marge);

        // Lisa
        ftm.newBirth(Lisa, new GregorianCalendar(1982, 4, 9).getTime(),
                "Springfield", "", "USA");
        ftm.addParent(Lisa, Homer);
        ftm.addParent(Lisa, Marge);

        // Maggie
        ftm.newBirth(Maggie, new GregorianCalendar(1990, 8, 15).getTime(),
                "Springfield", "", "USA");
        ftm.addParent(Maggie, Homer);
        ftm.addParent(Maggie, Marge);

        // Ling
        ftm.newBirth(Ling, new GregorianCalendar(1991, 6, 15).getTime(),
                "", "", "China");
        ftm.newAdoption(Ling, new GregorianCalendar(1992, 6, 15).getTime(),
                "Springfield", "", "USA");
        ftm.addAdoptedParent(Ling, Selma);

        // Patty
        ftm.newBirth(Patty, new GregorianCalendar(1959, 8, 15).getTime(),
                "Springfield", "", "USA");
        ftm.addParent(Patty, Clancy);
        ftm.addParent(Patty, Jacqueline);

        // Selma
        ftm.newBirth(Selma, new GregorianCalendar(1959, 8, 15).getTime(),
                "Springfield", "", "USA");
        ftm.addParent(Selma, Clancy);
        ftm.addParent(Selma, Jacqueline);

        // Clancy
        ftm.newMarriage(Clancy, Jacqueline,
                new GregorianCalendar(1944, 9, 19).getTime(),
                "Springfield", "", "USA");

        ftm.newDeath(Clancy, new GregorianCalendar(1969, 11, 6).getTime(),
                "Springfield", "", "USA");

        // Abraham Lincoln
        ftm.newBirth(Abe, new GregorianCalendar(1809, 1, 12).getTime(),
                "Hodgenville", "Kentucky", "USA");
        ftm.newDeath(Abe, new GregorianCalendar(1865, 3, 15).getTime(),
                "Washington, DC", "", "USA");
        Abe.setNotes(new StringBuilder("Abraham Lincoln was the 16th president of the United States.")
                .append("\nHe preserved the Union during the U.S. Civil War")
                .append("\nand brought about the emancipation of slaves.")
                .toString());
        ftm.updatePerson(Abe);

        // Display all people in database
        List<Person> thesepeople = ftm.getAllPeople();
        mydisplay("Getting All People");
        thesepeople.stream().forEach((p) -> {
            mydisplay(p.toString());
        });

        // Display all events in database
        List<Event> allEvents = ftm.getAllEvents();
        mydisplay("Getting All Events");
        allEvents.stream().forEach((e) -> {
            mydisplay(e.toString());
        });
    }

    private void createPicture(FamilyTreeManager ftm, URL filename, Person p) {
        // Store a pic
        final Picture pic = new Picture();
        pic.setFilename(filename.getFile());
        logger.log(Level.FINER, "reading file {0}", filename);
        Image iTemp = ImageUtilities.loadImage(filename.getPath());
        if (iTemp instanceof BufferedImage) {
            final BufferedImage image = (BufferedImage) iTemp;
            pic.setImage(image);
            pic.setPerson(p);
            ftm.storePicture(pic);
        }
    }

    private void mydisplay(String str) {
        logger.log(Level.FINER, str);
    }

}
