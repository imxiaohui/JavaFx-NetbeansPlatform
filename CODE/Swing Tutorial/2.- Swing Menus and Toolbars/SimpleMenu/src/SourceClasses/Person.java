package SourceClasses;

import java.io.Serializable;
import java.util.Objects;

/**
 * JavaBean Person.
 * 
 * Un JavaBean es una clase capaz de almacenar información de negocio en Java y 
 * que contiene las siguientes características:
 * 
 * 1.- Implementa la interfaz "Serializable".
 * 2.- Sus atributos son privados (Tambien llamados propiedades)
 * 3.- Se accede y modifica el valor de sus propiedades por medio de getters y setters.
 * 4.- Se sobre-escriben los métodos toString, equals y hashcode.
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
   
    /** Control del id*/
    private static long COUNT = 0;
    
    /** Enum con tipos de genero válidos. */
    public enum Gender{
        MALE,FEMALE,UNKNOWN;
    }

    /** Constructor Default. */
    public Person() {
        this("","");
    }
    
    /** 
     * Constructor que inicializa nombre, apellido y genero.
     * @param firstName primer nombre
     * @param lastName apellido
     * @param gender genero
     */
    public Person(String firstName,String lastName){
    
        this.firstName = firstName;
        this.middeName = "";
        this.lastName = lastName;
        this.suffix = "";
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
        this.firstName = firstName;
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
        this.middeName = middeName;
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
        this.lastName = lastName;
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
        this.suffix = suffix;
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
