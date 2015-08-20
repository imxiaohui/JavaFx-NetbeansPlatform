
package com.asgteach.familytree.model;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Javafx Bean Person.
 * @author Ernesto Cantú
 * 18 de Agosto del 2015
 */
public class Person implements Serializable {
    
    /*
        JavaFX Properties del bean Person.
    */
    private final long id;
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName", "");
    private final StringProperty middleName = new SimpleStringProperty(this, "middleName", "");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName", "");
    private final StringProperty suffix = new SimpleStringProperty(this, "suffix","");
    private final ObjectProperty<Person.Gender> gender = new SimpleObjectProperty<>(this,"gender",Person.Gender.UNKNOWN);
    private final StringProperty notes = new SimpleStringProperty(this, "notes","");
    
    
    /*
        Objeto Custom Binding del tipo String Binding. Sirve para establecer una
        relacion "customizable" entre propiedades. Aquí se declara que este StringBinding
        busca escuchar cambios en las propiedades firstName, middleName, lastName y
        suffix.
    
        Es importante mencionar que los bindings son una de las herramientas más poderosas
        de JavaFX. Los JavaFX Beans(O Propiedades) son Observables. Esto quiere decir que 
        soportan PropertyChange para indicar cambios a quien esté interesado.
        Existen diferentes tipos:
    
        1.- Unidirectional: Cuando se desea que una propiedad escuche a otra y siempre
            conservan el mismo valor. Aquella que declara su dependencia de otra, su setter
            queda inhabilitado.
        
        2.-  Bidirectional: Cuando ambas propiedades pueden cambiar, y se desea
            que ambas contengan siempre el mismo valor. Se puede usar el setter de ambas.
    
        3.- Fluent API: Cuando una propiedad debe ser operada previo a asignarse a otra.
    
        4.- Custom Binding: Permite ligar el valor de una propiedad a muchas otras y definir su 
            tratamiento.
    */
    private final StringBinding fullNameBinding = new StringBinding() {
        {
            super.bind(firstName,middleName,lastName,suffix);
        }
        
        /*Como se calcula el valor?*/
        @Override
        protected String computeValue() {
            StringBuilder sb = new StringBuilder();
            
            if(!firstName.get().isEmpty()){
                sb.append(firstName.get());
            }
            if(!middleName.get().isEmpty()){
               sb.append(" ").append(middleName.get());
            }
            if(!lastName.get().isEmpty()){
               sb.append(" ").append(lastName.get());
            }
            if(!suffix.get().isEmpty()){
               sb.append(" ").append(suffix.get());
            }
            
            return sb.toString();
        }
    };
    
    //Objeto para crear una propiedad de solo Lectura. Dentro contiene un ReadOnlyStringProperty que no cuenta con Setter.
    //Similar a las wrapper classes de Integer, Double... etc.
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
    
    public Person(Person person){
        this.firstName.set(person.getFirstName());
        this.middleName.set(person.getMiddleName());
        this.lastName.set(person.getLastName());
        this.suffix.set(person.getSuffix());
        this.gender.set(person.getGender());
        this.notes.set(person.getNotes());
        this.id = person.getId();
        this.fullName.bind(fullNameBinding);
    }
    
    public final long getId(){
        return this.id;
    }
    
    //Getter de la propiedad de solo Lectura fullName. Obtiene la propiedad ReadOnlyStringWrapper y regresa su objeto
    //ReadOnlyStringProperty, el cual no tiene setter y garantiza una propiedad de solo Lectura.
    public final ReadOnlyStringProperty fullNameProperty(){
        return fullName.getReadOnlyProperty();
    }
    
    //Es posible settear algun valor a la propiedad fullName (El objeto ligado utiliza un setter), pero
    //Al no proveer un setter de full name y al regresar un ReadOnlyProperty en el PropertyGetter
    //Se previene que código cliente settee esta propiedad de solo lectura.
    private final String getFullName(){
        return fullName.get();
    }
    
    public String getNotes(){
        return notes.get();
    }
    
    public void setNotes(String notes){
        this.notes.set(notes);
    }
    
    public final StringProperty notesProperty(){
        return notes;
    }
    
    public String getFirstName(){
        return firstName.get();
    }
    
    public void setFirstName(String firstName){
        this.firstName.set(firstName);
    }
    
    public final StringProperty firstNameProperty(){
        return firstName;
    }
    
    public Gender getGender(){
        return gender.get();
    }
    
    public void Gender(Gender gender){
        this.gender.set(gender);
    }
    
    public final ObjectProperty<Gender> genderProperty(){
        return gender;
    }
    
    public String getLastName(){
        return lastName.get();
    }
    
    public void setLastName(String lastName){
        this.lastName.set(lastName);
    }
    
    public StringProperty lastNameProperty(){
        return lastName;
    }
    
    public String getMiddleName(){
        return middleName.get();
    }
    
    public void setMiddleName(String middleName){
        this.middleName.set(middleName);
    }
    
    public StringProperty middleNameProperty(){
        return middleName;
    }
    
    public String getSuffix(){
        return suffix.get();
    }
    
    public void setSuffix(String suffix){
        this.suffix.set(suffix);
    }
    
    public StringProperty suffixProperty(){
        return suffix;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(id) 
                         + Objects.hashCode(this.gender)
                         + Objects.hashCode(this.notes)
                         + Objects.hashCode(this.fullName.get());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        
        final Person other = (Person)obj;
        
        return Objects.equals(this.id, other.id)  
                && Objects.equals(this.fullName.get(), other.fullName.get())
                && Objects.equals(this.notes.get(), other.notes.get())
                && Objects.equals(this.gender.get(), other.gender.get());
    }
    
    @Override
    public String toString(){
        return fullName.get();
    }
}
