/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.asgteach.familytree.jpaservice;

import java.beans.PropertyChangeListener;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.asgteach.familytree.model.*;
import org.asgteach.familytree.model.Person.Gender;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@ServiceProvider(service = FamilyTreeManager.class)
public class FamilyTreeJPAManagerImpl implements FamilyTreeManager {

    private EntityManager em = Installer.EM;

    public FamilyTreeJPAManagerImpl() {
        initialize();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Person getPerson(Long id) {
        em.getTransaction().begin();
        Query q = em.createQuery("select p from Person p where p.id = :idParam");
        q.setParameter("idParam", id);
        List<Person> people = (List<Person>) q.getResultList();
        em.getTransaction().commit();
        return (people != null) ? people.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getAllPeople() {
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select p from Person p order by p.lastname asc, p.firstname asc");
        List<Person> people = (List<Person>) q.getResultList();
        em.getTransaction().commit();
        return people;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Person> getTopAncestors() {
        Set<Person> topAncestors = new HashSet<Person>();
        // Find all the parents
        // If a person is in the childrenmap as a key,
        // then they are registered as a parent to somebody
        em.getTransaction().begin();
        TypedQuery q = em.createQuery(
                "select p.parents from Birth p", Person.class);
        List<Person> people = (List<Person>) q.getResultList();
        em.getTransaction().commit();

        for (Person person : people) {
            // If a person doesn't have a birth record,
            // then there's no record of a parent
            System.out.println("Found " + person);
            Birth birthRecord = findBirthRecord(person);
            if (birthRecord == null || birthRecord.getParents().isEmpty()) {
                System.out.println("Adding " + person + " to top ancestors list");
                topAncestors.add(person);
            }

        }

        return topAncestors;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Person> getLeafDescendents() {
        Set<Person> leafDescendents = new HashSet<Person>();
        em.getTransaction().begin();
        TypedQuery q = em.createQuery(
                "select p.person from Birth p", Person.class);
        List<Person> people = (List<Person>) q.getResultList();
        em.getTransaction().commit();

        for (Person person : people) {
            Set<Person> children = getChildren(person);
            for (Person kid : children) {
                if (getChildren(kid).isEmpty()) {
                    leafDescendents.add(kid);
                }
            }

        }
        return leafDescendents;
    }

    @Override
    public void addBirth(Birth birthRecord) {
        em.getTransaction().begin();
        em.persist(birthRecord);
        em.getTransaction().commit();
    }

    @Override
    public void addDeath(Death deathRecord) {
        em.getTransaction().begin();
        em.persist(deathRecord);
        em.getTransaction().commit();
    }

    @Override
    public void addMarriage(Marriage marriageRecord) {
        Marriage m2 = new Marriage();

        m2.setSpouse(marriageRecord.getPerson());
        m2.setEventDate(marriageRecord.getEventDate());
        m2.setCountry(marriageRecord.getCountry());
        m2.setPerson(marriageRecord.getSpouse());
        m2.setState_province(marriageRecord.getState_province());
        m2.setTown(marriageRecord.getTown());


        em.getTransaction().begin();
        em.persist(marriageRecord);
        em.persist(m2);
        em.getTransaction().commit();
    }

    @Override
    public void addDivorce(Divorce divorceRecord) {
        Divorce d2 = new Divorce();

        d2.setEx(divorceRecord.getPerson());
        d2.setEventDate(divorceRecord.getEventDate());
        d2.setCountry(divorceRecord.getCountry());
        d2.setPerson(divorceRecord.getEx());
        d2.setState_province(divorceRecord.getState_province());
        d2.setTown(divorceRecord.getTown());


        em.getTransaction().begin();
        em.persist(divorceRecord);
        em.persist(d2);
        em.getTransaction().commit();
    }

    @Override
    public void addAdoption(Adoption adoptionRecord) {
        em.getTransaction().begin();
        em.persist(adoptionRecord);
        em.getTransaction().commit();
    }

    @Override
    public Set<Person> getChildren(Person parent) {
        // get the children of this parent
        // look in birth table and find person objects
        Set<Person> children = new HashSet<Person>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select b.person from Birth b, IN(b.parents) p WHERE p.id = :idParam");
        q.setParameter("idParam", parent.getId());

        children.addAll((Collection<? extends Person>) q.getResultList());
        em.getTransaction().commit();
        return children;
    }

    @Override
    public Set<Person> getAdoptedChildren(Person parent) {
        Set<Person> adoptedChildren = new HashSet<Person>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select b.person from Adoption b, IN(b.parents) p WHERE p.id = :idParam");
        q.setParameter("idParam", parent.getId());

        adoptedChildren.addAll((Collection<? extends Person>) q.getResultList());
        em.getTransaction().commit();
        return adoptedChildren;
    }

    @Override
    public Set<Person> getChildren(Person parent1, Person parent2) {
        // get the children of both parents
        // look in the birth table and find person objects
        // match both parents' ids in birth.parents
        Set<Person> children = new HashSet<Person>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select b.person from Birth b, IN(b.parents) p WHERE p.id = :parentId");
        q.setParameter("parentId", parent1.getId());
        children.addAll((Collection<? extends Person>) q.getResultList());

        q.setParameter("parentId", parent2.getId());
        children.retainAll((Collection<? extends Person>) q.getResultList());
        em.getTransaction().commit();
        return children;
    }

    @Override
    public Set<Person> getChildrenAll(Person parent1, Person parent2) {
        // get the children of either parents
        // look in the birth table and find person objects
        // match both parents' ids in birth.parents
        Set<Person> children = new HashSet<Person>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select b.person from Birth b, IN(b.parents) p WHERE p.id = :parent1Id"
                + " or p.id = :parent2Id");
        q.setParameter("parent1Id", parent1.getId()).
                setParameter("parent2Id", parent2.getId());

        children.addAll((Collection<? extends Person>) q.getResultList());
        em.getTransaction().commit();
        return children;
    }

    @Override
    public Set<Person> getSiblingsFull(Person thisPerson) {
        // return only full siblings of thisPerson (both parents must match)
        // exclude thisPerson from the set

        Set<Person> siblings = new HashSet<Person>();
        List<Person> parents = new ArrayList<Person>();

        em.getTransaction().begin();
        Query q = em.createQuery(
                "select b.parents from Birth b, IN(b.person) p WHERE p.id = :personId"
                + " or p.id = :parent2Id");
        q.setParameter("personId", thisPerson.getId());


        parents.addAll((Collection<? extends Person>) q.getResultList());

        if (!parents.isEmpty()) {
            if (parents.size() == 2) {
                siblings = getChildren(parents.get(0), parents.get(1));
            }

        }
        em.getTransaction().commit();
        siblings.remove(thisPerson);
        return siblings;
    }

    @Override
    public Set<Person> getSiblingsAll(Person thisPerson) {
        // return full and half siblings of thisPerson
        // exclude thisPerson from the set

        Set<Person> siblings = new HashSet<Person>();
        List<Person> parents = new ArrayList<Person>();

        em.getTransaction().begin();
        Query q = em.createQuery(
                "select b.parents from Birth b, IN(b.person) p WHERE p.id = :personId"
                + " or p.id = :parent2Id");
        q.setParameter("personId", thisPerson.getId());


        parents.addAll((Collection<? extends Person>) q.getResultList());

        if (!parents.isEmpty()) {
            if (parents.size() == 2) {
                siblings = getChildrenAll(parents.get(0), parents.get(1));
            } else if (parents.size() == 1) {
                siblings = getChildren(parents.get(0));
            }

        }
        em.getTransaction().commit();
        siblings.remove(thisPerson);
        return siblings;
    }

    @Override
    public Person getSpouse(Person person) {
        List<Person> people = new ArrayList<Person>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select m.spouse from Marriage m "
                + "WHERE m.person.id = :personId order by m.eventdate desc");
        q.setParameter("personId", person.getId());

        people.addAll((Collection<? extends Person>) q.getResultList());
        em.getTransaction().commit();
        return (!people.isEmpty() ? people.get(0) : null);
    }

    @Override
    public List<Person> getSpouses(Person person) {
        List<Person> people = new ArrayList<Person>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select m.spouse from Marriage m "
                + "WHERE m.person.id = :personId order by m.eventdate desc");
        q.setParameter("personId", person.getId());

        people.addAll((Collection<? extends Person>) q.getResultList());
        em.getTransaction().commit();
        return people;
    }

    @Override
    public Set<Person> getParents(Person person) {
        Set<Person> parents = new HashSet<Person>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select b.parents from Birth b WHERE b.id = :personId");
        q.setParameter("personId", person.getId());

        parents.addAll((Collection<? extends Person>) q.getResultList());
        em.getTransaction().commit();
        return parents;
    }

    @Override
    public List<Event> getChildrenBirths(Person person) {
        // get the birth records of the children of this parent
        List<Birth> birthRecords = new ArrayList<Birth>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select b from Birth b, IN(b.parents) p WHERE p.id = :idParam");
        q.setParameter("idParam", person.getId());

        birthRecords.addAll((Collection<? extends Birth>) q.getResultList());

        List<Event> childrenBirths = new ArrayList<Event>();
        for (Birth b : birthRecords) {
            if (b != null) {
                ChildRecord cr = new ChildRecord();
                cr.setEventDate(b.getEventDate());
                cr.setPerson(person);
                cr.setChild(b.getPerson());
                cr.setTown(b.getTown());
                cr.setState_province(b.getState_province());
                cr.setCountry(b.getCountry());
                childrenBirths.add(cr);
            }
        }
        em.getTransaction().commit();
        return childrenBirths;
    }

    @Override
    public List<Event> getChildAdoptionRecords(Person person) {
        // get the birth records of the children of this parent
        List<Adoption> adoptionRecords = new ArrayList<Adoption>();
        em.getTransaction().begin();
        TypedQuery<Adoption> q = em.createQuery(
                "select b from Adoption b, IN(b.parents) p WHERE p.id = :idParam", Adoption.class);
        q.setParameter("idParam", person.getId());

        adoptionRecords.addAll(q.getResultList());

        List<Event> adoptions = new ArrayList<Event>();
        for (Adoption b : adoptionRecords) {
            if (b != null) {
                AdoptedChildRecord cr = new AdoptedChildRecord();
                cr.setEventDate(b.getEventDate());
                cr.setPerson(person);
                cr.setChild(b.getPerson());
                cr.setTown(b.getTown());
                cr.setState_province(b.getState_province());
                cr.setCountry(b.getCountry());
                adoptions.add(cr);
            }
        }
        em.getTransaction().commit();
        return adoptions;
    }

    @Override
    public void addParent(Person child, Person parent) {
        em.getTransaction().begin();
        TypedQuery<Birth> q = em.createQuery(
                "select b from Birth b where b.person.id = :idParam", Birth.class);
        q.setParameter("idParam", child.getId());

        List<Birth> results = (List<Birth>)q.getResultList();
        Birth b = (results != null && results.size() > 0) ? results.get(0) : null;
        if (b != null) {
            b.addParent(parent);
            em.merge(b);
        
        }
        em.getTransaction().commit();
    }

    @Override
    public void addAdoptedParent(Person child, Person parent) {
        em.getTransaction().begin();
        TypedQuery<Adoption> q = em.createQuery(
                "select b from Adoption b where b.person.id = :idParam", Adoption.class);
        q.setParameter("idParam", child.getId());

        List<Adoption> results = (List<Adoption>)q.getResultList();
        Adoption a = (results != null && results.size() > 0) ? results.get(0) : null;
        if (a != null) {
            a.addParent(parent);
            em.merge(a);       
        }
        em.getTransaction().commit();
    }

    @Override
    public void addPerson(Person person) {
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    @Override
    public List<Generation> getDescendents(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Generation> getAncestors(Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Person newPerson(String first, String last, Gender gender) {
        Person person = new Person(first, last, gender);
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        return person;
    }

    @Override
    public List<Event> findAllEvents(Person person) {
        List<Event> allEvents = new ArrayList<Event>();
        // Polymorphic queries don't work for
        // @MappedSuperclass entities ! :-(
        Event e = findBirthRecord(person);
        if (e != null) {
            allEvents.add(e);
        }
        e = findDeathRecord(person);
        if (e != null) {
            allEvents.add(e);
        }
        e = findAdoptionRecord(person);
        if (e != null) {
            allEvents.add(e);
        }
        allEvents.addAll(findMarriages(person));
        allEvents.addAll(findDivorces(person));
        allEvents.addAll(getChildrenBirths(person));
        allEvents.addAll(getChildAdoptionRecords(person));

        System.out.println("findAllEvents: size of allEvents = " + allEvents.size());
        return allEvents;

    }

    @Override
    public Death findDeathRecord(Person person) {
        // Could be null
        List<Death> myresults = new ArrayList<Death>();

        em.getTransaction().begin();
        TypedQuery<Death> q = em.createQuery(
                "select b from Death b WHERE b.person.id = :idParam",
                Death.class);
        q.setParameter("idParam", person.getId());

        myresults.addAll(q.getResultList());
        em.getTransaction().commit();
        return myresults.size() >= 1 ? myresults.get(0) : null;

    }

    @Override
    public Birth findBirthRecord(Person person) {

        // Could be null
        List<Birth> myresults = new ArrayList<Birth>();

        em.getTransaction().begin();
        TypedQuery<Birth> q = em.createQuery(
                "select b from Birth b WHERE b.person.id = :idParam",
                Birth.class);
        q.setParameter("idParam", person.getId());

        myresults.addAll(q.getResultList());
        em.getTransaction().commit();
        return myresults.size() >= 1 ? myresults.get(0) : null;

    }

    @Override
    public List<Marriage> findMarriages(Person person) {
        List<Marriage> marriages = new ArrayList<Marriage>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select m from Marriage m WHERE m.person.id = :personId");
        q.setParameter("personId", person.getId());

        marriages.addAll((Collection<? extends Marriage>) q.getResultList());
        em.getTransaction().commit();
        return marriages;
    }

    @Override
    public List<Divorce> findDivorces(Person person) {
        List<Divorce> divorces = new ArrayList<Divorce>();
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select d from Divorce d WHERE d.person.id = :personId");
        q.setParameter("personId", person.getId());

        divorces.addAll((Collection<? extends Divorce>) q.getResultList());
        em.getTransaction().commit();
        return divorces;
    }

    @Override
    public Adoption findAdoptionRecord(Person person) {
        // Could be null        
        em.getTransaction().begin();
        Query q = em.createQuery(
                "select a from Adoption a WHERE a.person.id = :idParam");
        q.setParameter("idParam", person.getId());
        List<Adoption> results = (List<Adoption>) q.getResultList();
        em.getTransaction().commit();
        return (results != null && results.size() > 0) ? results.get(0) : null;
    }

    @Override
    public void updateBirth(Birth b) {
        em.getTransaction().begin();
        Birth target = em.find(Birth.class, b.getId());
        target.setCountry(b.getCountry());
        target.setEventDate(b.getEventDate());
        target.setPerson(b.getPerson());
        target.setState_province(b.getState_province());
        target.setTown(b.getTown());       
        target.setParents(b.getParents());
        em.merge(target);
        em.getTransaction().commit();
    }

    @Override
    public void updatePerson(Person p) {
        em.getTransaction().begin();
        Person target = em.find(Person.class, p.getId());
        target.setFirst(p.getFirst());
        target.setGender(p.getGender());
        target.setLast(p.getLast());
        target.setMiddle(p.getMiddle());
        target.setNotes(p.getNotes());
        target.setSuffix(p.getSuffix());
        em.merge(target);
        em.getTransaction().commit();
        
    }

    @Override
    public void updateDeath(Death d) {
        em.getTransaction().begin();
        Death target = em.find(Death.class, d.getId());
        target.setCountry(d.getCountry());
        target.setEventDate(d.getEventDate());
        target.setPerson(d.getPerson());
        target.setState_province(d.getState_province());
        target.setTown(d.getTown());
        em.merge(target);
        em.getTransaction().commit();
        
    }

    @Override
    public void updateMarriage(Marriage m) {
        em.getTransaction().begin();
        Marriage target = em.find(Marriage.class, m.getId());
        target.setCountry(m.getCountry());
        target.setEventDate(m.getEventDate());
        target.setPerson(m.getPerson());
        target.setState_province(m.getState_province());
        target.setTown(m.getTown());
        target.setSpouse(m.getSpouse());
        em.merge(target);
        em.getTransaction().commit();
    }

    @Override
    public void updateDivorce(Divorce d) {
        em.getTransaction().begin();
        Divorce target = em.find(Divorce.class, d.getId());
        target.setCountry(d.getCountry());
        target.setEventDate(d.getEventDate());
        target.setPerson(d.getPerson());
        target.setState_province(d.getState_province());
        target.setTown(d.getTown());
        target.setEx(d.getEx());
        em.merge(target);
        em.getTransaction().commit();
    }

    @Override
    public void updateAdoption(Adoption a) {
        em.getTransaction().begin();
        Adoption target = em.find(Adoption.class, a.getId());
        target.setCountry(a.getCountry());
        target.setEventDate(a.getEventDate());
        target.setPerson(a.getPerson());
        target.setState_province(a.getState_province());
        target.setTown(a.getTown());
        target.setParents(a.getParents());
        em.merge(target);
        em.getTransaction().commit();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void initialize() {

        Person JohnLennon = newPerson("John", "Lennon", Person.Gender.MALE);
        JohnLennon.setNotes("Famous songwriter and member of the Beatles musical group.");

        Person yoko = newPerson("Yoko", "Ono", Person.Gender.FEMALE);
        Person cynthia = newPerson("Cynthia", "Powell", Person.Gender.FEMALE);
        Person julian = newPerson("Julian", "Lennon", Person.Gender.MALE);

        Birth b = new Birth();
        b.setEventDate(new GregorianCalendar(1963, 3, 8).getTime());
        b.setPerson(julian);
        b.addParent(JohnLennon);
        b.addParent(cynthia);
        b.setTown("Liverpool");
        b.setCountry("England");
        addBirth(b);


        Person sean = newPerson("Sean", "Lennon", Person.Gender.MALE);
        b = new Birth();
        b.setEventDate(new GregorianCalendar(1975, 9, 9).getTime());
        b.setPerson(sean);
        b.setTown("Liverpool");
        b.setCountry("England");
        b.addParent(JohnLennon);
        b.addParent(yoko);
        addBirth(b);



        Person mimi = newPerson("Mary Elizabeth", "Smith", Person.Gender.FEMALE);

        mimi.setMiddle("Mimi");
        mimi.setNotes("Mimi became nephew John's guardian");



        Person Isoko = newPerson("Isoko", "Ono", Person.Gender.FEMALE);
        Person Yeisuke = newPerson("Yeisuke", "Ono", Person.Gender.MALE);

        // Get person JohnLennon
        Person person = getPerson(JohnLennon.getId());
        person.setMiddle("Winston");
        mydisplay(person.toString());

        mydisplay(JohnLennon.getMiddle());

        // birth = October 9, 1940
        // death = December 8, 1980
        b = new Birth();
        b.setEventDate(new GregorianCalendar(1940, 9, 9).getTime());
        b.setPerson(JohnLennon);
        b.setTown("Liverpool");
        b.setCountry("England");
        addBirth(b);

        Death dd = new Death();
        dd.setPerson(JohnLennon);
        dd.setEventDate(new GregorianCalendar(1980, 11, 8).getTime());
        dd.setTown("New York");
        dd.setState_province("New York");
        dd.setCountry("USA");
        addDeath(dd);

        // add more people to John Lennon's ancestry
        Person julia = newPerson("Julia", "Stanley", Person.Gender.FEMALE);


        Person alf = newPerson("Alfred", "Lennon", Person.Gender.MALE);
        addParent(JohnLennon, alf);
        addParent(JohnLennon, julia);


        Adoption a = new Adoption();
        a.setEventDate(new GregorianCalendar(1946, 6, 1).getTime());
        a.setPerson(JohnLennon);
        a.setTown("Liverpool");
        a.setCountry("England");
        //a.addParent(mimi);
        addAdoption(a);
        addAdoptedParent(JohnLennon, mimi);

        dd = new Death();
        dd.setPerson(mimi);
        dd.setEventDate(new GregorianCalendar(1991, 11, 6).getTime());
        dd.setTown("Poole");
        dd.setState_province("Dorset");
        dd.setCountry("England");
        addDeath(dd);

        b = new Birth();
        b.setEventDate(new GregorianCalendar(1933, 1, 18).getTime());
        b.setPerson(yoko);
        b.setTown("Tokyo");
        b.setCountry("Japan");
        addBirth(b);

        Marriage m = new Marriage();
        m.setEventDate(new GregorianCalendar(1969, 2, 20).getTime());
        m.setPerson(JohnLennon);
        m.setSpouse(yoko);
        m.setTown("Gilbraltar");
        m.setCountry("Gibraltar");
        addMarriage(m);

        addParent(yoko, Isoko);
        addParent(yoko, Yeisuke);
        List<Person> thesepeople = getAllPeople();
        mydisplay("\nGetting All People");
        for (Person p : thesepeople) {
            mydisplay(p.toString());
        }

        Set<Person> thesechildren = getChildren(JohnLennon);
        mydisplay("\nGetting Children of " + JohnLennon);
        for (Person p : thesechildren) {
            mydisplay(p.toString());
        }

        thesechildren = getChildren(JohnLennon, yoko);
        mydisplay("\nGetting Children of both " + JohnLennon + " and " + yoko);
        for (Person p : thesechildren) {
            mydisplay(p.toString());
        }

        thesechildren = getChildrenAll(JohnLennon, yoko);
        mydisplay("\nGetting Children of either " + JohnLennon + " and " + yoko);
        for (Person p : thesechildren) {
            mydisplay(p.toString());
        }

        //Events stuff
        List<Event> events = findAllEvents(JohnLennon);
        mydisplay("Finding all events for " + JohnLennon);
        for (Event e : events) {
            if (e != null) {
                mydisplay(e.toString());
            }
        }

        events = findAllEvents(yoko);
        mydisplay("Finding all events for " + yoko);
        for (Event e : events) {
            if (e != null) {
                mydisplay(e.toString());
            }
        }

        events = findAllEvents(cynthia);

        mydisplay("Finding all events for " + cynthia);
        for (Event e : events) {
            if (e != null) {
                mydisplay(e.toString());
            }
        }
    }

    public void mydisplay(String str) {
        System.out.println(str);
    }
}
