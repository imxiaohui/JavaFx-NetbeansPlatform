package com.asgteach.familytree.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Java Bean de la clase Person.
 *  La clase cuenta con campos definidos y debido a que sus 
 *  métodos de acceso siguen el estandar, a estos campos
 *  los denominaremos propiedades del bean.
 *
 *  La clase tiene tres constructores:
 *  1.- Default
 *  2.- Uno que recibe firstName,lastName y gender
 *  3.- Uno que recibe otro objeto Person y copia
 *	    sus propiedades.
 *
 *  El hecho de que la clase sea serializable indica que
 *  Los objetos se pueden convertir a un stream de bytes o
 *  recuperarse de ese stream de bytes.
 *
 *  Recordar: Una clase es un java bean si:
 *	1.- Es serializable.
 *	2.- Todos sus atributos o campos son privados
 *	3.- Se accede a sus atributos por medio de getters y setters
 * 	4.- Tiene un constructor vacio.
 * @author Ernesto Cantú
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
    
    //Constructor vacio. Parte del estandar de java beans.
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
        Métodos getters y setters. Parte del estandar de java beans.
    */
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

    /*
        Sobreescritura de métodos.
    */
    
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
