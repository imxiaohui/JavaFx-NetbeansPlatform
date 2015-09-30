package com.asgteach.familytree.model;

import java.io.Serializable;
import java.util.Objects;

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

    /* Getters and setters */
    public long getId(){
	return id;
    }  

    public String getFirstName(){
	return firstName;
    }    

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

   public String getMiddleName(){
       return middleName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getSuffix(){
        return suffix;
    }

    public void setSuffix(String suffix){
        this.suffix = suffix;
    }

    public Person.Gender getGender(){
	return gender;
    }

    public void setGender(Person.Gender gender){
	this.gender = gender;
    }

    public String getNotes(){
	return notes;
    }

    public void setNotes(String notes){
	this.notes = notes;
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
