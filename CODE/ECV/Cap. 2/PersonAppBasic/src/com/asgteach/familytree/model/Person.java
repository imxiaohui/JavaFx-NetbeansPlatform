package com.asgteach.familytree.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase Person.
 * 
 * @author Ernesto Cantú
 * 17/03/2015
 */
public class Person implements Serializable{
    
    /** Atributo id. Identificador único de la persona*/
    private final Long id;
    
    /** Atributo firstName. Nombre Propio de la persona*/
    private String firstName;
    
    /** Atributo middeName. Segundo Nombre de la Persona*/
    private String middeName;
    
    /** Atributo lastName. Apellido de la Persona*/
    private String lastName;
    
    /** Atributo suffix. Sufijo (sobrenombre) de la Persona*/
    private String suffix;
    
    /** Atributo gender. Genero de la Persona*/
    private Person.Gender gender;
    
    /** Atributo notes. Notas*/
    private String notes;
   
    /** Control del id*/
    private static long COUNT = 0;
    
    /** Enum con tipos de genero válidos. */
    public enum Gender{
        MALE,FEMALE,UNKNOWN;
    }

    /** Constructor Default. */
    public Person() {
        this("","",Person.Gender.UNKNOWN);
    }
    
    /** 
     * Constructor que inicializa nombre, apellido y genero.
     * @param firstName primer nombre
     * @param lastName apellido
     * @param gender genero
     */
    public Person(String firstName,String lastName, Person.Gender gender){
    
        this.firstName = firstName;
        this.middeName = "";
        this.lastName = lastName;
        this.suffix = "";
        this.gender = gender;
        this.id = COUNT++;
    }

    /**
     * Constructor a partir de otro objeto Person.
     * @param copy Objeto del que se copian las pripiedades
     */
    public Person(Person copy) {
        this.id        = copy.id;
        this.firstName = copy.firstName;
        this.middeName = copy.middeName;
        this.lastName  = copy.lastName;
        this.suffix    = copy.suffix;
        this.gender    = copy.gender;
        this.notes     = copy.notes;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddeName() {
        return middeName;
    }

    public void setMiddeName(String middeName) {
        this.middeName = middeName;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!firstName.isEmpty()){
            sb.append(firstName);
        }
        if(!middeName.isEmpty()){
            sb.append(middeName);
        }
        if(!lastName.isEmpty()){
            sb.append(lastName);
        }
        if(!suffix.isEmpty()){
            sb.append(suffix);
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
