package com.asgteach.familytree.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * JavaFX Rich Client Programming on the Netbeans Platform
 * 
 * Ernesto Cantú Valle <ernesto.cantu1989@live.com>
 * Probado el día 8 de Febrero del 2015.
 */
public class Person implements Serializable{
    
    /**
     * Atributos de la clase.
     */
    private final long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private Person.Gender gender;
    private String notes;
       
    private static long COUNT = 0;
   
    /**
     * Genero de la persona.
     */
    public enum Gender{
        MALE,FEMALE,UNKNOWN;   
    }

    /**
     * Constructor Default.
     */
    public Person() {
        this("","", Gender.UNKNOWN);
    }

    /**
     * Constructor con los atributos firstName,lastName y gender.
     * 
     * 
     * @param firstName
     * @param lastName
     * @param gender
     */
    public Person(String firstName, String lastName, Person.Gender gender) {
        this.firstName = firstName;
        this.middleName = "";
        this.lastName = lastName;
        this.suffix = "";
        this.gender = gender;
        this.id = COUNT++;
    }

    public Person(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.middleName = person.getMiddleName();
        this.lastName = person.getLastName();
        this.suffix = person.getSuffix();
        this.gender = person.getGender();
        this.notes = person.getNotes();
    }
    
    
    public long getId() {
        return id;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Person.Gender getGender() {
        return gender;
    }

    public void setGender(Person.Gender gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!firstName.isEmpty()) {
            sb.append(firstName);
        }
        if (!middleName.isEmpty()) {
            sb.append(" ").append(middleName);
        }
        if (!lastName.isEmpty()) {
            sb.append(" ").append(lastName);
        }
        if (!suffix.isEmpty()) {
            sb.append(" ").append(suffix);
        }
        return sb.toString();
    }
    
}
