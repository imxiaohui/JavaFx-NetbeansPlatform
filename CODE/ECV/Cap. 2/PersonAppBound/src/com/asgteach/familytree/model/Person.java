package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Objects;
import java.beans.PropertyChangeSupport;

/**
    Java Bean Person con property change support.

    ¿Qué es el property change support?
    Es una clase que permite a los beans que la utilizan
    ser event sources. Esto es, la clase Person tiene un objeto
    PropertyChangeSupport el cual le brinda a la clase
    la habilidad de crear eventos del tipo property change.
 */
public class Person implements Serializable{

    /* Propiedades de la clase.  */
    private final long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private Person.Gender gender;
    private String notes;

    /*
	Declaro un atributo de la clase PropertyChangeSupport. Este
	Me permitirá ser fuente de eventos de cambio de propiedades.
     */
    private PropertyChangeSupport propertyChangeSupport = null;

    /*
	Estas etiquetas me permitirán saber a que propiedad me refiero.
     */
    public static final String PROP_FIRST = "firstName";
    public static final String PROP_MIDDLE = "middleName";
    public static final String PROP_LAST = "lastName";
    public static final String PROP_SUFFIX = "suffix";
    public static final String PROP_GENDER = "gender";
    public static final String PROP_NOTES = "notes";


    /* Constante de la clase que permite llevar la cuenta de cuantos objetos Person se han creado.  */
    private static long count = 0;

    /*
	Enum que define la cantidad de generos que puede haber.
     */
    public enum Gender{
 	MALE,FEMALE,UNKNOWN
    }


   /*
	Constructores.
    */

    public Person(){
	this("","",Gender.UNKNOWN);
    }

    public Person(String firstName, String lastName, Person.Gender gender){
	this.firstName = firstName;
	this.middleName = "";
	this.lastName = lastName;
	this.suffix = "";
	this.gender = gender;
	this.notes = "";
	this.id = count++;
    }

    public Person(Person person){

	this.firstName = person.getFirstName();
	this.middleName = person.getMiddleName();
	this.lastName = person.getLastName();
	this.suffix = person.getSuffix();
	this.gender = person.getGender();
	this.notes = person.getNotes();
	this.id = person.getId();
    }


    /*
	¿Cómo funciona el PropertyChangeSupport?
        Este método me permite crear el propChangeSupport
     */
    private PropertyChangeSupport getPropertyChangeSupport(){
	if(this.propertyChangeSupport == null){
	   this.propertyChangeSupport = new PropertyChangeSupport(this);
    	}
	return this.propertyChangeSupport;
    }

    //Agrego y elmimino listeners!!!
    
    public void addPropertyChangeSupport(PropertyChangeListener listener){
	getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    public void removePropertyChangeSupport(PropertyChangeListener listener){
	getPropertyChangeSupport().removePropertyChangeListener(listener);
    }
    
    

    /* Getters and setters */
    public long getId(){
	return id;
    }  

    public String getFirstName(){
	return firstName;
    }    

    public void setFirstName(String firstName){
	String oldFirst = this.firstName;
        this.firstName = firstName;
        getPropertyChangeSupport().firePropertyChange(PROP_FIRST,oldFirst,firstName);
    }

   public String getMiddleName(){
       return middleName;
    }

    public void setMiddleName(String middleName){
        String oldMiddle = this.middleName;
        this.middleName = middleName;
        getPropertyChangeSupport().firePropertyChange(PROP_MIDDLE,oldMiddle,middleName);
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        String oldLast = this.lastName;
        this.lastName = lastName;
        getPropertyChangeSupport().firePropertyChange(PROP_LAST,oldLast,lastName);
    }

    public String getSuffix(){
        return suffix;
    }

    public void setSuffix(String suffix){
        String oldSuffix = this.suffix;
        this.suffix = suffix;
        getPropertyChangeSupport().firePropertyChange(PROP_SUFFIX,oldSuffix,suffix);
    }

    public Person.Gender getGender(){
	return gender;
    }

    public void setGender(Person.Gender gender){
        Gender oldGender = this.gender;
	this.gender = gender;
        getPropertyChangeSupport().firePropertyChange(PROP_GENDER, oldGender, gender);
    }

    public String getNotes(){
	return notes;
    }

    public void setNotes(String notes){
        String oldNotes = this.notes;
	this.notes = notes;
        getPropertyChangeSupport().firePropertyChange(PROP_NOTES,oldNotes,notes);
    }

    @Override
    public int hashCode(){
	int hash = 3;
	hash = 97 * hash + Objects.hashCode(this.id);
	return hash;
    }

    @Override
    public boolean equals(Object obj){
	if(obj == null){
	   return false;
	}

	if(getClass() != obj.getClass()){
           return false;
	}

	final Person other = (Person) obj;

        return Objects.equals(this.id,other.id);
    }

    @Override
    public String toString(){
	StringBuilder sb = new StringBuilder();
        if(!firstName.isEmpty()){
	   sb.append(firstName);
	}

	if(!middleName.isEmpty()){
	   sb.append(" ").append(middleName);
	}

	if(!lastName.isEmpty()){
	   sb.append(" ").append(lastName);
	}

	if(!suffix.isEmpty()){
	   sb.append(" ").append(suffix);
	}
        return sb.toString();
    }
}//fin de la clase Person.
