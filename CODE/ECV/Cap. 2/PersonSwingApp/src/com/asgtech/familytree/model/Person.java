package com.asgtech.familytree.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @author Ernesto Cantú
 * 18/03/2015
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
    
    /** Atributo propertyChangeSupport. Da la capacidad a este java bean de
     * notificar a objetos que fue modificado.
     */
    private PropertyChangeSupport propertyChangeSupport;
      
    
   
    /** Control del id*/
    private static long COUNT = 0;
    
    /*
     * Constantes que me permiten darle un nombre a mis atributos a la hora de
     * llamar al método firePropertyChange.
     */
    private static final String PROP_FIRST = "firstName";
    private static final String PROP_MIDDLE = "middeName";
    private static final String PROP_LAST = "lastName";
    private static final String PROP_SUFFIX = "suffix";
    private static final String PROP_GENDER = "gender";
    private static final String PROP_NOTES = "notes";
    
    
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
     * Constructor que Copia a partir de otro objeto Person.
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
    
    /**
     * Método que inicializa el atributo propertyChangeSupport.
     */
    private PropertyChangeSupport getPropertyChangeSupport(){
        if(this.propertyChangeSupport == null){
            this.propertyChangeSupport=new PropertyChangeSupport(this);
        }
        return this.propertyChangeSupport;
    }
    
    /**
     * Método que da de alta a un objeto Listener 
     * @param listener el objeto interesado en conocer los cambios.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }
    
    /**
     * Método que da de alta a un objeto Listener 
     * @param propertyName propiedad a la cual se suscribirá el listener
     * @param listener el objeto interesado en conocer los cambios.
     */
    public void addPropertyChangeListener(String propertyName,PropertyChangeListener listener){
        getPropertyChangeSupport().addPropertyChangeListener(propertyName,listener);
    }
    
    /**
     * Método que da de baja a un objeto Listener 
     * @param listener el objeto no interesado en conocer los cambios.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }
    
    /**
     * Método que da de baja a un objeto Listener 
     * @param propertyName propiedad a la cual se elimina la suscripcion del listener
     * @param listener el objeto interesado en conocer los cambios.
     */
    public void removePropertyChangeListener(String propertyName,PropertyChangeListener listener){
        getPropertyChangeSupport().removePropertyChangeListener(propertyName,listener);
    }
    
    /**
     * Metodo de acceso al atributo id.
     * @return el atributo id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo de acceso al atributo firstName.
     * @return el atributo firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Método modificador del atributo firstName.
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        String oldFirstName = this.firstName;
        this.firstName = firstName;
        getPropertyChangeSupport().firePropertyChange(PROP_FIRST,oldFirstName,firstName);
    }

    /**
     * Metodo de acceso al atributo middeName.
     * @return el atributo middeName.
     */
    public String getMiddeName() {
        return middeName;
    }

    
    /**
     * Método modificador del atributo middeName.
     * @param middeName 
     */
    public void setMiddeName(String middeName) {
        String oldMiddleName = this.middeName;
        this.middeName = middeName;
        getPropertyChangeSupport().firePropertyChange(PROP_MIDDLE, oldMiddleName, middeName);
    }

    /**
     * Metodo de acceso al atributo lastName.
     * @return el atributo lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Método modificador del atributo lastName.
     * @param lastName 
     */
    public void setLastName(String lastName) {
        String oldLastName = this.lastName;
        this.lastName = lastName;
        getPropertyChangeSupport().firePropertyChange(PROP_LAST, oldLastName, lastName);
    }

    /**
     * Metodo de acceso al atributo suffix.
     * @return el atributo suffix.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Método modificador del atributo suffix.
     * @param suffix 
     */
    public void setSuffix(String suffix) {
        String oldSuffix = this.suffix;
        this.suffix = suffix;
        getPropertyChangeSupport().firePropertyChange(PROP_SUFFIX, oldSuffix, suffix);
    }

    /**
     * Metodo de acceso al atributo gender.
     * @return el atributo gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Método modificador del atributo gender.
     * @param gender 
     */
    public void setGender(Gender gender) {
        Person.Gender oldGender = this.gender;
        this.gender = gender;
        getPropertyChangeSupport().firePropertyChange(PROP_GENDER, oldGender, gender);
    }

    /**
     * Metodo de acceso al atributo notes.
     * @return el atributo notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Método modificador del atributo notes.
     * @param notes 
     */
    public void setNotes(String notes) {
        String oldNotes = this.notes;
        this.notes = notes;
        getPropertyChangeSupport().firePropertyChange(PROP_NOTES, oldNotes, notes);
    }

    /**
     * Sobre-escritura del método toString.
     * @return cadena con el nombre de la persona.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!firstName.isEmpty()){
            sb.append(firstName);
        }
        if(!middeName.isEmpty()){
            sb.append(" ").append(middeName);
        }
        if(!lastName.isEmpty()){
            sb.append(" ").append(lastName);
        }
        if(!suffix.isEmpty()){
            sb.append(" ").append(suffix);
        }
        return sb.toString();
    }

    /**
     * Sobre-escritura del método hash.
     * 
     * @return el valor hash del objeto.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Sobre-escritura del método equals.
     * @param obj objeto contra el cual comparar.
     * @return si ambos objetos son iguales
     */
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
