/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.model.AdoptedChildRecord;
import com.asgteach.familytree.model.Adoption;
import com.asgteach.familytree.model.Birth;
import com.asgteach.familytree.model.ChildParentEvent;
import com.asgteach.familytree.model.ChildRecord;
import com.asgteach.familytree.model.Death;
import com.asgteach.familytree.model.Divorce;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Marriage;
import com.asgteach.familytree.model.ParentChildEvent;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import com.asgteach.familytree.model.Picture;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.event.SwingPropertyChangeSupport;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@ServiceProvider(service = FamilyTreeManager.class)
public class FamilyTreeDAOJPA implements FamilyTreeManager {
 
    // SwingPropertyChangeSupport is thread-safe
    // true means fire property change events on the EDT
    private final SwingPropertyChangeSupport propChangeSupport =
            new SwingPropertyChangeSupport(this, true);
    private static final EntityManagerFactory EMF;
    private final static int INITIAL_SIZE = 64000;
    private static final Logger logger = Logger.getLogger(FamilyTreeDAOJPA.class.getName());

    static {
        try {
            EMF = Persistence.createEntityManagerFactory("FamilyTreePU");
            logger.log(Level.INFO, "Entity Manager Factory Created.");
        } catch (Throwable ex) {
            logger.log(Level.SEVERE, 
                    "Make sure that the JavaDB Database Server has been started.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public FamilyTreeDAOJPA() {
    }

    public static void closeEMF() {
        if (EMF != null) {
            EMF.close();
        }
    }

    private void fireChangeOnEDT(final String propname, final Object oldValue, final Object newValue) {
        logger.log(Level.FINER, "Firing for {0}", propname);
        this.propChangeSupport.firePropertyChange(propname, oldValue, newValue);
    }

    // Person stuff
    @Override
    public Person getPerson(Long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            Person person = null;
            em.getTransaction().begin();
            PersonEntity p = em.find(PersonEntity.class, id);
            em.getTransaction().commit();
            if (p != null) {
                person = buildPerson(p);
            }
            return person;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();           
        }
    }

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
    public Person newPerson(String first, String last, Gender gender) {
        EntityManager em = EMF.createEntityManager();
        try {          
            PersonEntity person = new PersonEntity();
            person.setFirstname(first);
            person.setLastname(last);
            person.setGender(gender);
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            final Person newPerson = buildPerson(person);
            logger.log(Level.FINE, "New Person: {0} successfully added.", newPerson);
            fireChangeOnEDT(PROP_PERSON_ADDED, newPerson, null);
            return newPerson;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();           
        }
    }

    @Override
    public Person newPerson(final Person newPerson) {
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
            logger.log(Level.FINE, "New Person: {0} successfully added.", newPerson);
            fireChangeOnEDT(PROP_PERSON_ADDED, newPerson, null);
            return buildPerson(person);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();           
        }
    }

    @Override
    public Person updatePerson(final Person p) {
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
                fireChangeOnEDT(PROP_PERSON_UPDATED, p, null);
                return buildPerson(target);
            }
            logger.log(Level.WARNING, "No entity for Person {0}.", p);
            return null;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();           
        }
    }

    @Override
    public void removePerson(Person p) {
        // This will also remove all events associated with
        // this person except events listing this person
        // as a parent (for either adoption event or birth event)

        // First, let's find all the events associated with this person
        // so we can fire PROP_EVENT_DESTROYED to any listeners
        List<Event> events = new ArrayList<>(findAllEvents(p));
        events.addAll(findCollateralEvents(p));

        // Now remove the person
        // The cascading delete database will remove the events 
        // from the database.
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            PersonEntity target = em.find(PersonEntity.class, p.getId());
            if (target != null) {
                // Shouldn't have to do this, but
                // cascade delete doesn't seem to work
                // with one-to-one relationships
                if (target.getPicture() != null) {
                    PictureEntity picEntity =
                            target.getPicture();
                    em.remove(picEntity);
                    em.getTransaction().commit();
                    logger.log(Level.FINER, "Removing person's pic too.");
                    em.getTransaction().begin();
                }
                em.remove(target);
                em.getTransaction().commit();
                logger.log(Level.FINE, "Person {0} successfully removed.", p);
                fireChangeOnEDT(PROP_PERSON_DESTROYED, p, null);
            } else {
                logger.log(Level.WARNING, "No entity for Person {0}.", p);
            }
            // Now send property change events to listeners
            for (Event event : events) {
                fireChangeOnEDT(PROP_EVENT_DESTROYED, event, null);
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
            List<Person> people = Collections.synchronizedList(new ArrayList<Person>());
            em.getTransaction().begin();
            Query q = em.createQuery(
                    "select p from PersonEntity p order by p.lastname asc, p.firstname asc");
            List<PersonEntity> results = (List<PersonEntity>) q.getResultList();
            if (results != null && results.size() > 0) {
                for (PersonEntity pe : results) {
                    people.add(buildPerson(pe));
                }
            }            
            em.getTransaction().commit();           
            return Collections.unmodifiableList(people);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }  finally {
            em.close();           
        }
    }

    // Picture stuff
    @SuppressWarnings("unchecked")
    @Override
    public Picture getPicture(Person p) {
        EntityManager em = EMF.createEntityManager();
        Picture picture = null;
        try {
            em.getTransaction().begin();
            Query q = em.createQuery(
                    "select p from PictureEntity p "
                    + "where p.owner.id = :idOwner ");
            q.setParameter("idOwner", p.getId());
            // JPA complains if you get single result and there isn't any
            // so we get a list and just grab the first (and only) one
            // If the list is empty, we don't have any
            List<PictureEntity> results = (List<PictureEntity>) q.getResultList();
            if (results != null && results.size() > 0) {
                picture = buildPicture(results.get(0));
            }
            em.getTransaction().commit();
            return picture;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();           
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void storePicture(Picture pic) {
        EntityManager em = EMF.createEntityManager();
        try {
            // First see if there already is a pic 
            em.getTransaction().begin();
            boolean newPic = true;
            Query q = em.createQuery(
                    "select p from PictureEntity p "
                    + "where p.owner.id = :idOwner ");
            q.setParameter("idOwner", pic.getPerson().getId());
            // JPA complains if you get single result and there isn't any
            // so we get a list and just grab the first (and only) one
            // If the list is empty, we don't have any
            PictureEntity picEntity = new PictureEntity();
            List<PictureEntity> results = (List<PictureEntity>) q.getResultList();
            if (results != null && results.size() > 0) {
                picEntity = results.get(0);
                newPic = false;
                logger.log(Level.FINE, "Updating pic for {0}", picEntity.getOwner());
            }
            picEntity.setFilename(pic.getFilename());
            picEntity.setPicData(convertFromImage(pic.getImage(), pic.getFormat()));

            if (newPic) {
                // Find the person & persist new pic
                logger.log(Level.FINE, "Store new pic for {0}", pic.getPerson());
                PersonEntity pe = em.find(PersonEntity.class, pic.getPerson().getId());
                if (pe != null) {
                    picEntity.setOwner(pe);
                    em.persist(picEntity);
                    pe.setPicture(picEntity);
                    em.merge(pe);
                }
            } else {
                // otherwise, just update the pic we have
                em.merge(picEntity);
            }
            em.getTransaction().commit();
            logger.log(Level.FINER, "Store Picture: {0} successfully stored", pic);
            fireChangeOnEDT(PROP_IMAGE_STORED, pic, null);
            fireChangeOnEDT(PROP_PERSON_UPDATED, pic.getPerson(), null);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }  finally {
            em.close();           
        }
    }

    private Picture buildPicture(PictureEntity pe) throws IOException {
        logger.log(Level.FINER, "buildPicture for {0}", pe.getId());
        final Picture pic = new Picture(pe.getId());
        pic.setFilename(pe.getFilename());
        pic.setPerson(buildPerson(pe.getOwner()));
        pic.setImage(convertToImage(pe.getPicData()));
        return pic;
    }

    private BufferedImage convertToImage(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            final BufferedImage image = ImageIO.read(bais);
            bais.close();
            return image;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private byte[] convertFromImage(BufferedImage image, String format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(INITIAL_SIZE);
        try {
            ImageIO.write(image, format, baos);
            final byte[] data = baos.toByteArray();
            baos.close();
            return data;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // Event stuff
    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getAllEvents() {
        EntityManager em = EMF.createEntityManager();
        try {
            List<Event> events = Collections.synchronizedList(new ArrayList<Event>());
            em.getTransaction().begin();
            // Reminder: field names are case sensitive!
            Query q = em.createQuery(
                    "select e from EventEntity e order by e.person.lastname asc, "
                    + "e.person.firstname asc, e.eventDate asc");
            List<EventEntity> results = (List<EventEntity>) q.getResultList();
            em.getTransaction().commit();
            if (results != null && results.size() > 0) {
                for (EventEntity ee : results) {
                    events.add(buildEventTypeObject(ee));
                }
            }
            return Collections.unmodifiableList(events);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }  finally {
            em.close();           
        }
    }

    @Override
    public List<Person> findPeopleFromEvent(Event event) {
        EntityManager em = EMF.createEntityManager();
        List<Person> persons = new ArrayList<>();
        try {
            em.getTransaction().begin();
            EventEntity target = em.find(EventEntity.class, event.getId());
            if (target != null) {
                if (target instanceof ParentChildEventEntity) {
                    ParentChildEventEntity pce = (ParentChildEventEntity) target;
                    for (PersonEntity person : pce.getParents()) {
                        persons.add(buildPerson(person));
                    }
                } else if (target instanceof ChildParentEventEntity) {
                    ChildParentEventEntity cpe = (ChildParentEventEntity) target;
                    persons.add(buildPerson(cpe.getChild()));

                } else if (target instanceof DivorceEntity) {
                    DivorceEntity d = (DivorceEntity) target;
                    persons.add(buildPerson(d.getEx()));

                } else if (target instanceof MarriageEntity) {
                    MarriageEntity m = (MarriageEntity) target;
                    persons.add(buildPerson(m.getSpouse()));
                }
                em.getTransaction().commit();
            }
            return Collections.unmodifiableList(persons);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

    // Birth Stuff
    @Override
    public ParentChildEvent findBirthAdopt(Long id) {
        EntityManager em = EMF.createEntityManager();
        ParentChildEvent record = null;
        try {
            em.getTransaction().begin();
            ParentChildEventEntity be = em.find(ParentChildEventEntity.class, id);
            em.getTransaction().commit();
            if (be != null) {
                record = buildBirthAdopt(be);
            }
            return record;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }  finally {
            em.close();
        }
    }

    @Override
    public boolean isDuplicateEvent(Event event) {
        // only check for duplicates for Birth/Adoption/Death
        if (event instanceof ParentChildEvent || event instanceof Death) {
            for (Event e : findAllEvents(event.getPerson())) {
                if (e.getEventName().equals(event.getEventName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Event createEvent(Event event) {
        // We don't create ChildParentEventEntity's directly
        // These are created as a fallout of created a ParentChildEventEntity
        if (event instanceof ParentChildEvent) {
            ParentChildEvent pce;
            if (((ParentChildEvent) event).isBioBirth()) {
                pce = newBirth(event.getPerson(),
                        event.getEventDate(), event.getTown(),
                        event.getState_province(), event.getCountry());

            } else {
                pce = newAdoption(event.getPerson(),
                        event.getEventDate(), event.getTown(),
                        event.getState_province(), event.getCountry());
            }
            for (Person parent : ((ParentChildEvent) event).getParents()) {
                pce = addParentHelper(event.getPerson(), parent, pce.isBioBirth());
            }
            return pce;

        } else if (event instanceof Death) {
            return newDeath(event.getPerson(), event.getEventDate(),
                    event.getTown(), event.getState_province(), event.getCountry());
        } else if (event instanceof Marriage) {
            return newMarriage(event.getPerson(), ((Marriage) event).getSpouse(),
                    event.getEventDate(), event.getTown(),
                    event.getState_province(), event.getCountry());
        } else if (event instanceof Divorce) {
            return newDivorce(event.getPerson(), ((Divorce) event).getEx(),
                    event.getEventDate(), event.getTown(),
                    event.getState_province(), event.getCountry());
        }
        // this shouldn't happen!
        return null;
    }

    @Override
    public Birth newBirth(Person p, Date d, String town, String state, String country) {
        return (Birth) newParentChildHelper(p, d, town, state, country, true);

    }

    @Override
    public Adoption newAdoption(Person p, Date d, String town, String state, String country) {
        return (Adoption) newParentChildHelper(p, d, town, state, country, false);
    }

    private ParentChildEvent newParentChildHelper(
            Person p, Date d, String town, String state, String country, boolean biobirth) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            ParentChildEventEntity be = new ParentChildEventEntity();
            be = (ParentChildEventEntity) this.setEventInfo(em, be, p, d, town, state, country);
            be.setBioBirth(biobirth);
            em.persist(be);
            em.getTransaction().commit();
            ParentChildEvent pce = buildBirthAdopt(be);
            logger.log(Level.FINE, "Event {0} created.", pce);
            fireChangeOnEDT(PROP_EVENT_ADDED, pce, null);
            return pce;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

    private ParentChildEvent buildBirthAdopt(ParentChildEventEntity be) {
        ParentChildEvent pce;
        if (be.isBioBirth()) {
            pce = new Birth(be.getId());
        } else {
            pce = new Adoption(be.getId());
        }
        for (PersonEntity pe : be.getParents()) {
            pce.addParent(buildPerson(pe));
        }
        return (ParentChildEvent) buildEvent(be, pce);
    }

    public Event updateDeath(Event event) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            DeathEntity target = em.find(DeathEntity.class, event.getId());
            target = (DeathEntity) updateEvent(event, target);
            em.merge(target);
            em.getTransaction().commit();
            Event de = buildDeath(target);
            fireChangeOnEDT(PROP_EVENT_UPDATED, de, null);
            return de;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }  finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public ParentChildEvent updateParentChildEvent(Event event) {
        // When you update a given ParentChildEvent, there could be
        // as many as three records affected: 2 ChildParentEvent (one for
        // each potential parent) and one ParentChildEvent (a birth or 
        // adoption record). The ChildParentEvent.getChild() matches
        // the ParentChildEvent.getPerson() and isBioBirth must also 
        // match to refer to the same record.
        EntityManager em = EMF.createEntityManager();
        ParentChildEvent pcEvent = (ParentChildEvent) event;
        logger.log(Level.FINER, "Update ParentChildEvent {0}", event);
        final List<Event> updatedEventsList = new ArrayList<>();
        try {
            em.getTransaction().begin();
            // Find the event
            ParentChildEventEntity pcee = em.find(ParentChildEventEntity.class, event.getId());
            if (pcee != null) {
                pcee = (ParentChildEventEntity) updateEvent(event, pcee);
                em.merge(pcee);
                pcEvent = buildBirthAdopt(pcee);
                updatedEventsList.add(pcEvent);
            }

            if (pcee != null && !pcee.getParents().isEmpty()) {
                // Update the parents' child records too
                // There could be one or two
                Query q = em.createQuery(
                        "select e from ChildParentEventEntity e "
                        + "where e.child.id = :idChild "
                        + "and e.bioBirth = :bioBirth");
                q.setParameter("idChild", pcEvent.getPerson().getId());
                q.setParameter("bioBirth", pcEvent.isBioBirth());

                List<ChildParentEventEntity> results2 =
                        (List<ChildParentEventEntity>) q.getResultList();
                if (results2 != null) {
                    for (ChildParentEventEntity cpe : results2) {
                        cpe = (ChildParentEventEntity) updateEvent(event, cpe);
                        em.merge(cpe);
                        ChildParentEvent updatedEvent = buildChildAdopt(cpe);
                        updatedEventsList.add(updatedEvent);
                    }
                }
            }
            em.getTransaction().commit();
            // fire the property changes
            for (Event e : updatedEventsList) {
                fireChangeOnEDT(PROP_EVENT_UPDATED, e, null);
            }
            return pcEvent;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }  finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public ChildParentEvent updateChildParentEvent(Event event) {
        // When you update a given ChildParentEvent, there could be
        // as many as three records affected: 2 ChildParentEvent (one for
        // each potential parent) and one ParentChildEvent (a birth or 
        // adoption record). The ChildParentEvent.getChild() matches
        // the ParentChildEvent.getPerson() and isBioBirth must also 
        // match to refer to the same record.
        EntityManager em = EMF.createEntityManager();
        ChildParentEvent cpEvent = (ChildParentEvent) event;
        logger.log(Level.FINER, "Update ChildParentEvent {0}", event);
        final List<Event> updatedEventsList = new ArrayList<>();
        try {
            em.getTransaction().begin();
            // Find not only this event but possibly other
            // ChildParentEventEntitys that identify the same child (that is,
            // other parents of this child) 
            Query q = em.createQuery("select e from ChildParentEventEntity e "
                    + "where e.child.id = :idChild "
                    + "and e.bioBirth = :bioBirth");
            q.setParameter("idChild", cpEvent.getChild().getId());
            q.setParameter("bioBirth", cpEvent.isBioBirth());
            List<ChildParentEventEntity> results =
                    (List<ChildParentEventEntity>) q.getResultList();
            if (results != null) {
                for (ChildParentEventEntity cpe : results) {
                    cpe = (ChildParentEventEntity) updateEvent(event, cpe);
                    em.merge(cpe);

                    ChildParentEvent updatedEvent = buildChildAdopt(cpe);
                    updatedEventsList.add(updatedEvent);
                }
            }

            // Now find the associated birth/adopt record for this child

            if (results != null) {
                q = em.createQuery("select e from ParentChildEventEntity e "
                        + "where e.person.id = :idPerson "
                        + "and e.bioBirth = :idBioParam");

                q.setParameter("idPerson", cpEvent.getChild().getId());
                q.setParameter("idBioParam", cpEvent.isBioBirth());

                List<ParentChildEventEntity> results2 =
                        (List<ParentChildEventEntity>) q.getResultList();
                if (results2 != null && results2.size() > 0) {
                    ParentChildEventEntity pcee = results2.get(0);
                    pcee = (ParentChildEventEntity) updateEvent(event, pcee);
                    em.merge(pcee);
                    ParentChildEvent updatedEvent = buildBirthAdopt(pcee);
                    updatedEventsList.add(updatedEvent);
                }
            }
            em.getTransaction().commit();
            for (Event e : updatedEventsList) {
                fireChangeOnEDT(PROP_EVENT_UPDATED, e, null);
            }
            return cpEvent;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public Divorce updateDivorce(Event event) {
        final EntityManager em = EMF.createEntityManager();
        Divorce dEvent = (Divorce) event;
        logger.log(Level.FINER, "Update Divorce {0}", event);
        final List<Event> updatedEventsList = new ArrayList<>();
        try {
            em.getTransaction().begin();
            DivorceEntity de1 = em.find(DivorceEntity.class, event.getId());
            if (de1 != null) {
                de1 = (DivorceEntity) updateEvent(event, de1);
                em.merge(de1);
                dEvent = buildDivorce(de1);
                updatedEventsList.add(dEvent);
            }

            // Now find the associated DivorceEntity belonging to the ex
            DivorceEntity de2;
            Query q = em.createQuery(
                    "select e from DivorceEntity e where e.person.id = :idPerson "
                    + "and e.ex.id = :idEx");
            q.setParameter("idPerson", dEvent.getEx().getId());
            q.setParameter("idEx", dEvent.getPerson().getId());

            List<DivorceEntity> results = (List<DivorceEntity>) q.getResultList();
            if (results != null && results.size() > 0) {
                de2 = results.get(0);
                de2 = (DivorceEntity) updateEvent(event, de2);
                em.merge(de2);
                Divorce d1 = buildDivorce(de2);
                updatedEventsList.add(d1);
            }
            em.getTransaction().commit();
            // fire the property changes
            for (Event e : updatedEventsList) {
                fireChangeOnEDT(PROP_EVENT_UPDATED, e, null);
            }
            return dEvent;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }  finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public Marriage updateMarriage(Event event) {
        EntityManager em = EMF.createEntityManager();
        Marriage m = (Marriage) event;
        logger.log(Level.FINER, "Update Marriage {0}", event);
        List<Event> updatedEventsList = new ArrayList<>();
        try {
            em.getTransaction().begin();
            MarriageEntity me1 = em.find(MarriageEntity.class, event.getId());
            if (me1 != null) {
                me1 = (MarriageEntity) updateEvent(event, me1);
                em.merge(me1);
                m = buildMarriage(me1);
                updatedEventsList.add(m);
            }

            // Now find the associated MarriageEntity belonging to the spouse
            MarriageEntity me2;
            Query q = em.createQuery(
                    "select e from MarriageEntity e where e.person.id = :idPerson "
                    + "and e.spouse.id = :idSpouse");
            q.setParameter("idPerson", m.getSpouse().getId());
            q.setParameter("idSpouse", m.getPerson().getId());

            List<MarriageEntity> results = (List<MarriageEntity>) q.getResultList();
            if (results != null && results.size() > 0) {
                me2 = results.get(0);
                me2 = (MarriageEntity) updateEvent(event, me2);
                em.merge(me2);
                Marriage m2 = buildMarriage(me2);
                updatedEventsList.add(m2);
            }
            em.getTransaction().commit();
            // fire the property changes
            for (Event e : updatedEventsList) {
                fireChangeOnEDT(PROP_EVENT_UPDATED, e, null);
            }
            return m;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Event updateEvent(Event event) {
        EntityManager em = EMF.createEntityManager();
        logger.log(Level.FINE, "Update Event {0}", event);
        try {
            em.getTransaction().begin();
            EventEntity eventEntity = em.find(EventEntity.class, event.getId());
            em.getTransaction().commit();
            em.close();
            if (eventEntity != null) {
                return eventEntity.update(event, this);
            } else {
                return null;
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            if (em.isOpen()) {
                em.close();
            }
            return null;
        }


    }

    @Override
    public ParentChildEvent addParent(Person child, Person parent) {
        return addParentHelper(child, parent, true);

    }

    @Override
    public ParentChildEvent addAdoptedParent(Person child, Person parent) {
        return addParentHelper(child, parent, false);
    }

    @SuppressWarnings("unchecked")
    private ParentChildEvent addParentHelper(Person child, Person parent, boolean biobirth) {
        // When we add a parent, we also create a ChildParentEventEntity
        EntityManager em = EMF.createEntityManager();
        try {
            ParentChildEventEntity be;
            ParentChildEvent eventUpdated;
            em.getTransaction().begin();
            // Note: fields in entity classes are case sensitive!
            Query q = em.createQuery(
                    "select b from ParentChildEventEntity b where b.person.id = :idParam "
                    + "and b.bioBirth = :bioParam");
            q.setParameter("idParam", child.getId()).setParameter("bioParam", biobirth);
            List<ParentChildEventEntity> results = (List<ParentChildEventEntity>) q.getResultList();
            em.getTransaction().commit();
            if (results != null && results.size() > 0) {
                em.getTransaction().begin();
                be = results.get(0);
                PersonEntity p = em.find(PersonEntity.class, parent.getId());
                be.addParent(p);
                ChildParentEventEntity cpe = new ChildParentEventEntity();
                cpe = (ChildParentEventEntity) this.setEventInfo(em, cpe, parent, be.getEventDate(),
                        be.getTown(), be.getState_province(), be.getCountry());
                cpe.setBioBirth(biobirth);
                cpe.setChild(be.getPerson());
                // Add event entity cpe
                em.persist(cpe);
                ChildParentEvent eventAdded = buildChildAdopt(cpe);
                // Update event entity be
                em.merge(be);
                eventUpdated = buildBirthAdopt(be);
                em.getTransaction().commit();
                logger.log(Level.FINE, "Event {0} created.", eventAdded);
                logger.log(Level.FINE, "Event {0} updated.", eventUpdated);
                fireChangeOnEDT(PROP_EVENT_UPDATED, eventUpdated, null);
                fireChangeOnEDT(PROP_EVENT_ADDED, eventAdded, null);
            } else {
                logger.log(Level.WARNING, "addParentHelper: attempting to add parent but can't find birth record.");
                logger.log(Level.WARNING, "trying to add parent {0} to {1}", new Object[]{parent, child});
                return null;
            }
            return eventUpdated;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }  finally {
            em.close();
        }

    }

    @Override
    public Death newDeath(Person p, Date d, String town, String state, String country) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            DeathEntity de = new DeathEntity();
            de = (DeathEntity) this.setEventInfo(em, de, p, d, town, state, country);
            em.persist(de);
            em.getTransaction().commit();
            Death d1 = buildDeath(de);
            fireChangeOnEDT(PROP_EVENT_ADDED, d1, null);
            //this.propChangeSupport.firePropertyChange(PROP_EVENT_ADDED, d1, null);
            return d1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

    private Event buildEvent(EventEntity ee, Event target) {
        target.setEventDate(ee.getEventDate());
        target.setCountry(ee.getCountry());
        target.setPerson(buildPerson(ee.getPerson()));
        target.setState_province(ee.getState_province());
        target.setTown(ee.getTown());
        return target;
    }

    private Death buildDeath(DeathEntity de) {
        Death death = new Death(de.getId());
        return (Death) buildEvent(de, death);
    }

    private EventEntity setEventInfo(EntityManager em,
            EventEntity ee, Person p, Date d, String town, String state, String country) {
        // em is set in caller!
        PersonEntity target = em.find(PersonEntity.class, p.getId());
        ee.setPerson(target);
        ee.setEventDate(d);
        ee.setCountry(country);
        ee.setState_province(state);
        ee.setTown(town);
        return ee;
    }

    @Override
    public Marriage newMarriage(Person p, Person spouse, Date d, String town,
            String state, String country) {
        // create 2 marriage records: one for person and one for spouse
        EntityManager em = EMF.createEntityManager();
        try {

            em.getTransaction().begin();
            MarriageEntity be = new MarriageEntity();
            MarriageEntity be2 = new MarriageEntity();
            be = (MarriageEntity) this.setEventInfo(em, be, p, d, town, state, country);
            be2 = (MarriageEntity) this.setEventInfo(em, be2, spouse, d, town, state, country);

            PersonEntity spouseEntity = em.find(PersonEntity.class, spouse.getId());
            be.setSpouse(spouseEntity);
            PersonEntity person = em.find(PersonEntity.class, p.getId());
            be2.setSpouse(person);
            em.persist(be);
            em.persist(be2);
            em.getTransaction().commit();
            Marriage m = buildMarriage(be);
            Marriage m2 = buildMarriage(be2);
            logger.log(Level.FINE, "Event {0} created.", m);
            logger.log(Level.FINE, "Event {0} created.", m2);
            fireChangeOnEDT(PROP_EVENT_ADDED, m, null);
            fireChangeOnEDT(PROP_EVENT_ADDED, m2, null);
            return m;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

    private Marriage buildMarriage(MarriageEntity me) {
        Marriage marriage = new Marriage(me.getId());
        marriage.setSpouse(
                buildPerson(me.getSpouse()));
        return (Marriage) buildEvent(me, marriage);
    }

    @Override
    public Divorce newDivorce(Person p, Person exSpouse, Date d, String town,
            String state, String country) {
        // create 2 divorce records: one for person and one for exSpouse
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            DivorceEntity de = new DivorceEntity();
            DivorceEntity de2 = new DivorceEntity();
            de = (DivorceEntity) this.setEventInfo(em, de, p, d, town, state, country);
            de2 = (DivorceEntity) this.setEventInfo(em, de2, exSpouse, d, town, state, country);

            PersonEntity exEntity = em.find(PersonEntity.class, exSpouse.getId());
            de.setEx(exEntity);
            PersonEntity person = em.find(PersonEntity.class, p.getId());
            de2.setEx(person);

            em.persist(de);
            em.persist(de2);
            em.getTransaction().commit();
            Divorce div = buildDivorce(de);
            Divorce div2 = buildDivorce(de2);
            logger.log(Level.FINE, "Event {0} created.", div);
            logger.log(Level.FINE, "Event {0} created.", div2);
            fireChangeOnEDT(PROP_EVENT_ADDED, div, null);
            fireChangeOnEDT(PROP_EVENT_ADDED, div2, null);
            return div;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

    private Divorce buildDivorce(DivorceEntity de) {
        Divorce divorce = new Divorce(de.getId());
        divorce.setEx(buildPerson(de.getEx()));
        return (Divorce) buildEvent(de, divorce);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void removeEvent(Event event) {
        EntityManager em = EMF.createEntityManager();
        List<Event> destroyedEventsList = new ArrayList<>();
        try {
            em.getTransaction().begin();
            EventEntity target = em.find(EventEntity.class, event.getId());
            em.remove(target);
            destroyedEventsList.add(event);
            if (target instanceof ParentChildEventEntity) {
                // find the two child events if they exist and remove them too
                Query q = em.createQuery(
                        "select e from ChildParentEventEntity e where "
                        + "e.person.id = :idParam "
                        + "and e.child.id = :childId");
                for (PersonEntity p : ((ParentChildEventEntity) target).getParents()) {
                    q.setParameter("idParam", p.getId()).setParameter(
                            "childId", target.getPerson().getId());
                    List<ChildParentEventEntity> results = (List<ChildParentEventEntity>) q.getResultList();
                    if (results != null && results.size() > 0) {
                        ChildParentEventEntity cpe = results.get(0);
                        em.remove(cpe);
                        logger.log(Level.FINER, "removeEvent: related Child Event removed for {0}", p);
                        Event removedEvent = buildChildAdopt(cpe);
                        destroyedEventsList.add(removedEvent);
                    }
                }
            } else if (target instanceof ChildParentEventEntity) {
                // and find the ParentChildEvent for this child and remove it
                Query q1 = em.createQuery(
                        "select e from ParentChildEventEntity e where "
                        + "e.person.id = :idParam "
                        + " and e.bioBirth = :bioBirth ");
                q1.setParameter("idParam", ((ChildParentEventEntity) target).getChild().getId());
                q1.setParameter("bioBirth", ((ChildParentEventEntity) target).isBioBirth());
                List<ParentChildEventEntity> results =
                        (List<ParentChildEventEntity>) q1.getResultList();
                PersonEntity otherParent = null;
                if (results != null && results.size() > 0) {
                    ParentChildEventEntity pce = results.get(0);
                    for (PersonEntity p : pce.getParents()) {
                        if (!p.equals(target.getPerson())) {
                            otherParent = p;
                            break;
                        }
                    }
                    em.remove(pce);
                    logger.log(Level.FINER, 
                            "removeEvent: related Birth/Adopt Event removed for {0}", 
                            pce.getPerson());
                    Event removedEvent = buildBirthAdopt(pce);
                    destroyedEventsList.add(removedEvent);
                }
                // find the child event related to the other parent
                if (otherParent != null) {
                    Query q2 = em.createQuery(
                            "select e from ChildParentEventEntity e where "
                            + "e.person.id = :idParam "
                            + "and e.child.id = :childId");
                    q2.setParameter("idParam", otherParent.getId()).setParameter(
                            "childId", ((ChildParentEventEntity) target).getChild().getId());
                    List<ChildParentEventEntity> results2 =
                            (List<ChildParentEventEntity>) q2.getResultList();
                    if (results2 != null && results2.size() > 0) {
                        ChildParentEventEntity cpe = results2.get(0);
                        em.remove(cpe);                        
                        logger.log(Level.FINER, 
                            "removeEvent: related Child Event removed for {0}", 
                            cpe.getPerson());
                        Event removedEvent = buildChildAdopt(cpe);
                        destroyedEventsList.add(removedEvent);
                    }
                }
            } else if (target instanceof MarriageEntity) {
                // find the spouse's MarriageEntity and remove it
                Query q = em.createQuery(
                        "select e from MarriageEntity e where "
                        + "e.person.id = :idParam "
                        + "and e.spouse.id = :spouseId");

                q.setParameter("idParam", ((MarriageEntity) target).getSpouse().getId()).setParameter(
                        "spouseId", target.getPerson().getId());
                List<MarriageEntity> results3 = (List<MarriageEntity>) q.getResultList();
                if (results3 != null && results3.size() > 0) {
                    MarriageEntity me = results3.get(0);
                    em.remove(me);
                    logger.log(Level.FINER, 
                            "removeEvent: related Marriage Event removed for {0}", 
                            me.getPerson());
                    Event removedEvent = buildMarriage(me);
                    destroyedEventsList.add(removedEvent);
                }
            } else if (target instanceof DivorceEntity) {
                // find the ex's DivorceEntity and remove it
                Query q = em.createQuery(
                        "select e from DivorceEntity e where "
                        + "e.person.id = :idParam "
                        + "and e.ex.id = :exId");

                q.setParameter("idParam", ((DivorceEntity) target).getEx().getId()).setParameter(
                        "exId", target.getPerson().getId());
                List<DivorceEntity> results3 = (List<DivorceEntity>) q.getResultList();
                if (results3 != null && results3.size() > 0) {
                    DivorceEntity de = results3.get(0);
                    em.remove(de);
                    logger.log(Level.FINER, 
                            "removeEvent: related Divorce Event removed for {0}", 
                            de.getPerson());
                    Event removedEvent = buildDivorce(de);
                    destroyedEventsList.add(removedEvent);
                }
            }
            em.getTransaction().commit();
            logger.log(Level.FINE, "removeEvent: {0} successfully removed", event);
            // fire the property changes
            for (Event e : destroyedEventsList) {
                fireChangeOnEDT(PROP_EVENT_DESTROYED, e, null);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }

    private EventEntity updateEvent(Event event, EventEntity target) {
        target.setEventDate(event.getEventDate());
        target.setCountry(event.getCountry());
        target.setState_province(event.getState_province());
        target.setTown(event.getTown());
        return target;
    }

    @SuppressWarnings("unchecked")
    private List<Event> findCollateralEvents(Person person) {
        // find events that will get deleted when this person
        // is deleted so we can fire delete events to the listeners
        List<Event> events = new ArrayList<>();
        EntityManager em = EMF.createEntityManager();
        try {

            // find spouse events for this person
            em.getTransaction().begin();
            Query q = em.createQuery(
                    "select e from MarriageEntity e where e.spouse.id = :idParam ");
            q.setParameter("idParam", person.getId());
            List<EventEntity> results = (List<EventEntity>) q.getResultList();

            for (EventEntity ee : results) {
                events.add(buildEventTypeObject(ee));
            }

            // find ex events for this person
            Query q2 = em.createQuery(
                    "select e from DivorceEntity e where e.ex.id = :idParam ");
            q2.setParameter("idParam", person.getId());
            results = (List<EventEntity>) q2.getResultList();
            for (EventEntity ee : results) {
                events.add(buildEventTypeObject(ee));
            }

            // find child events for this person
            Query q3 = em.createQuery(
                    "select e from ChildParentEventEntity e where e.child.id = :idParam ");
            q3.setParameter("idParam", person.getId());
            results = (List<EventEntity>) q3.getResultList();

            em.getTransaction().commit();
            for (EventEntity ee : results) {
                events.add(buildEventTypeObject(ee));
            }
            return Collections.unmodifiableList(events);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> findAllEvents(Person person) {
        logger.log(Level.FINER, "findAllEvents for {0}", person);
        List<Event> events = new ArrayList<>();
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery(
                    "select e from EventEntity e where e.person.id = :idParam "
                    + "order by e.eventDate asc");
            q.setParameter("idParam", person.getId());
            List<EventEntity> results = (List<EventEntity>) q.getResultList();
            em.getTransaction().commit();
            for (EventEntity ee : results) {
                events.add(buildEventTypeObject(ee));
            }
            return Collections.unmodifiableList(events);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }  finally {
            em.close();
        }
    }

    private Event buildEventTypeObject(EventEntity ee) {
        if (ee instanceof DeathEntity) {
            return buildDeath((DeathEntity) ee);
        } else if (ee instanceof ParentChildEventEntity) {
            return buildBirthAdopt((ParentChildEventEntity) ee);
        } else if (ee instanceof MarriageEntity) {
            return buildMarriage((MarriageEntity) ee);
        } else if (ee instanceof DivorceEntity) {
            return buildDivorce((DivorceEntity) ee);
        } else if (ee instanceof ChildParentEventEntity) {
            return buildChildAdopt((ChildParentEventEntity) ee);
        }
        // This is not good
        return null;
    }

    private ChildParentEvent buildChildAdopt(ChildParentEventEntity cpe) {
        if (cpe.isBioBirth()) {
            Event event = new ChildRecord(cpe.getId());
            event = buildEvent(cpe, event);
            ((ChildRecord) event).setChild(buildPerson(cpe.getChild()));
            return (ChildRecord) event;
        } else {
            Event event = new AdoptedChildRecord(cpe.getId());
            event = buildEvent(cpe, event);
            ((AdoptedChildRecord) event).setChild(buildPerson(cpe.getChild()));
            return (AdoptedChildRecord) event;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Person> getTopAncestors() {
        EntityManager em = EMF.createEntityManager();
        Set<Person> topAncestors = new HashSet<>();
        try {
            em.getTransaction().begin();
            // get all people who are listed as parents
            TypedQuery<PersonEntity> q = em.createQuery(
                    "select p.person from ChildParentEventEntity p ", PersonEntity.class);
            List<PersonEntity> people = q.getResultList();
            em.getTransaction().commit();
            for (PersonEntity pe : people) {
                // If a person doesn't have a birth record,
                // then there's no record of a parent
                Person person = buildPerson(pe);
                Birth birthRecord = findBirthRecord(person);
                if (birthRecord == null || birthRecord.getParents().isEmpty()) {
                    topAncestors.add(person);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }  finally {
            em.close();
        }
        return Collections.unmodifiableSet(topAncestors);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Person> getLeafDescendents() {
        Set<Person> leafDescendents = new HashSet<>();
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            // get all people that have a birth event
            TypedQuery<PersonEntity> q = em.createQuery(
                    "select p.person from ParentChildEventEntity p", PersonEntity.class);
            List<PersonEntity> people = q.getResultList();
            em.getTransaction().commit();
            for (PersonEntity pe : people) {
                Set<Person> children = getChildren(buildPerson(pe));
                for (Person kid : children) {
                    if (getChildren(kid).isEmpty()) {
                        leafDescendents.add(kid);
                    }
                }

            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }  finally {
            em.close();
        }
        return Collections.unmodifiableSet(leafDescendents);
    }

    @Override
    public Death findDeathRecord(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Birth findBirthRecord(Person person) {

        List<ParentChildEventEntity> myresults = new ArrayList<>();
        EntityManager em = EMF.createEntityManager();
        try {

            em.getTransaction().begin();
            TypedQuery<ParentChildEventEntity> q = em.createQuery(
                    "select b from ParentChildEventEntity b WHERE b.person.id = :idParam "
                    + " and b.bioBirth = true",
                    ParentChildEventEntity.class);
            q.setParameter("idParam", person.getId());

            myresults.addAll(q.getResultList());
            em.getTransaction().commit();
            em.close();
            if (myresults.size() >= 1) {
                return (Birth) buildBirthAdopt(myresults.get(0));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public List<Marriage> findMarriages(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Divorce> findDivorces(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Adoption findAdoptionRecord(Person person) {
        List<ParentChildEventEntity> myresults = new ArrayList<>();
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<ParentChildEventEntity> q = em.createQuery(
                    "select b from ParentChildEventEntity b WHERE b.person.id = :idParam "
                    + " and b.bioBirth = false",
                    ParentChildEventEntity.class);
            q.setParameter("idParam", person.getId());

            myresults.addAll(q.getResultList());
            em.getTransaction().commit();
            if (myresults.size() >= 1) {
                return (Adoption) buildBirthAdopt(myresults.get(0));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public void addPropertyChangeListener(
            PropertyChangeListener listener) {
        this.propChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(
            PropertyChangeListener listener) {
        this.propChangeSupport.removePropertyChangeListener(listener);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Person> getChildren(Person parent) {
        // get the children of this parent
        Set<Person> children = new HashSet<>();
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery(
                    "select b.child from ChildParentEventEntity b WHERE b.person.id = :idParam ");
            q.setParameter("idParam", parent.getId());
            List<PersonEntity> people = (List<PersonEntity>) q.getResultList();
            for (PersonEntity pe : people) {
                children.add(buildPerson(pe));
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return Collections.unmodifiableSet(children);
    }

    // Put some data in the database 
    private void initialize() {
        
    }

}
