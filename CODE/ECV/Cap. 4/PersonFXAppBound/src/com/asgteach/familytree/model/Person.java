package com.asgteach.familytree.model;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import java.io.Serializable;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * JavaFX Bean de la clase Persona.
 * 
 * Manejo de propiedades.
 * 
 * Binding
 * 
 * @author Ernesto Cantú
 * 05/08/2015
 */
public class Person implements Serializable {
    
    private final long id;
    private  final StringProperty firstName = new SimpleStringProperty(this,"firstName","");
    private  final StringProperty middleName = new SimpleStringProperty(this,"middleName","");
    private  final StringProperty lastName = new SimpleStringProperty(this,"lastName","");
    private  final StringProperty suffix = new SimpleStringProperty(this,"suffix","");
    private final ObjectProperty<Person.Gender> gender = new SimpleObjectProperty<Person.Gender>(this,"gender",Gender.UNKNOWN);
    private  final StringProperty notes = new SimpleStringProperty(this,"notes","");
    
    private final StringBinding fullNameBinding = new StringBinding() {
        {
            super.bind(firstName,middleName,lastName,suffix);
        }
        @Override
        protected String computeValue() {
            StringBuilder sb = new StringBuilder();
            if(!firstName.get().isEmpty()){
               sb.append(firstName);
            }

            if(!middleName.get().isEmpty()){
               sb.append(" ").append(middleName);
            }

            if(!lastName.get().isEmpty()){
               sb.append(" ").append(lastName);
            }

            if(!suffix.get().isEmpty()){
               sb.append(" ").append(suffix);
            }
            return sb.toString();
        }
    };
    
    private final ReadOnlyStringWrapper fullName = new ReadOnlyStringWrapper(this, "fullName");
    private static long count = 0;
    
    public enum Gender{
        MALE,FEMALE,UNKNOWN;
    }

    public Person() {
        this("","",Gender.UNKNOWN);
    }
    
    public Person(String firstName,String lastName,Gender gender){
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.gender.set(gender);
        this.id = count++;
        this.fullName.bind(fullNameBinding);
    }
    
    
}
