/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.manager.jpa;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.event.SwingPropertyChangeSupport;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@Messages({
    "DBServerFailure=Perhaps you forgot to start JavaDB Database Server?",
    "DBServerEntityError=Cannot get EntityManager.",
    "DBError=Error"
})
@ServiceProvider(service = com.asgteach.familytree.model.FamilyTreeManager.class,
        supersedes = {"com.asgteach.familytree.manager.impl.FamilyTreeManagerImpl"})
public class FamilyTreeManagerJPA implements FamilyTreeManager {

    // SwingPropertyChangeSupport is thread-safe
    // true means fire property change events on the EDT
    private SwingPropertyChangeSupport propChangeSupport = null;

    private static final EntityManagerFactory EMF;
    private static final Logger logger = Logger.getLogger(FamilyTreeManagerJPA.class.getName());

    static {
        try {
            EMF = Persistence.createEntityManagerFactory("PersonFTMPU");
            logger.log(Level.INFO, "Entity Manager Factory Created.");
            // Let's create/close entity manager to make sure JavaDB Server is running
            EntityManager em = EMF.createEntityManager();
            em.close();
        } catch (Throwable ex) {
            logger.log(Level.SEVERE,
                    Bundle.DBServerEntityError(), ex);
            NotifyDescriptor nd = new NotifyDescriptor.Message(
                    Bundle.DBServerFailure(),  NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(nd);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private PropertyChangeSupport getPropertyChangeSupport() {
        if (this.propChangeSupport == null) {
            this.propChangeSupport = new SwingPropertyChangeSupport(this, true);
        }
        return this.propChangeSupport;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    // Person stuff
//    @Override
//    public Person getPerson(Long id) {
//        EntityManager em = EMF.createEntityManager();
//        try {
//            Person person = null;
//            em.getTransaction().begin();
//            PersonEntity p = em.find(PersonEntity.class, id);
//            em.getTransaction().commit();
//            if (p != null) {
//                person = buildPerson(p);
//            }
//            return person;
//        } catch (Exception ex) {
//            logger.log(Level.SEVERE, null, ex);
//            return null;
//        } finally {
//            em.close();           
//        }
//    }
    private Person buildPerson(PersonEntity pe) {
        Person person = new Person(pe.getId());
        person.setFirstname(pe.getFirstname());
        person.setGender(pe.getGender());
        person.setLastname(pe.getLastname());
        person.setMiddlename(pe.getMiddlename());
        person.setNotes(pe.getNotes());
        person.setSuffix(pe.getSuffix());
        return person;
    }

    @Override
    public void addPerson(final Person newPerson) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            PersonEntity person = new PersonEntity();
            person.setFirstname(newPerson.getFirstname());
            person.setLastname(newPerson.getLastname());
            person.setGender(newPerson.getGender());
            person.setMiddlename(newPerson.getMiddlename());
            person.setSuffix(newPerson.getSuffix());
            person.setNotes(newPerson.getNotes());
            em.persist(person);
            em.getTransaction().commit();
            logger.log(Level.INFO, "New Person: {0} successfully added.", newPerson);
            getPropertyChangeSupport().firePropertyChange(
                    FamilyTreeManager.PROP_PERSON_ADDED, null, buildPerson(person));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void updatePerson(final Person p) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            PersonEntity target = em.find(PersonEntity.class, p.getId());
            if (target != null) {
                target.setFirstname(p.getFirstname());
                target.setGender(p.getGender());
                target.setLastname(p.getLastname());
                target.setMiddlename(p.getMiddlename());
                target.setNotes(p.getNotes());
                target.setSuffix(p.getSuffix());
                em.merge(target);
                em.getTransaction().commit();
                logger.log(Level.FINE, "Person {0} successfully updated.", p);
                getPropertyChangeSupport().firePropertyChange(
                        FamilyTreeManager.PROP_PERSON_UPDATED, null, buildPerson(target));
            }
            logger.log(Level.WARNING, "No entity for Person {0}.", p);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void deletePerson(Person p) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            PersonEntity target = em.find(PersonEntity.class, p.getId());
            if (target != null) {
                em.remove(target);
                em.getTransaction().commit();
                logger.log(Level.FINE, "Person {0} successfully removed.", p);
                getPropertyChangeSupport().firePropertyChange(
                        FamilyTreeManager.PROP_PERSON_DESTROYED, null, p);
            } else {
                logger.log(Level.WARNING, "No entity for Person {0}.", p);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getAllPeople() {
        EntityManager em = EMF.createEntityManager();
        try {
            List<Person> people = Collections.synchronizedList(new ArrayList<>());
            em.getTransaction().begin();
            Query q = em.createQuery(
                    "select p from PersonEntity p order by p.lastname asc, p.firstname asc");
            List<PersonEntity> results = (List<PersonEntity>) q.getResultList();
            if (results != null && results.size() > 0) {
                results.stream().forEach((pe) -> {
                    people.add(buildPerson(pe));
                });
            }
            em.getTransaction().commit();
            return Collections.unmodifiableList(people);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

}
