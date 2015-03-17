 package personappbasic;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;

/**
 * Ejercicio PersonApBasic del Libro
 * JavaFx Rich Client Applications with the Netbeans Platform.
 * 
 * El objetivo de este ejercicio es conocer qué es un JavaBean, y como utilizarlos.
 * 
 * Un JavaBean es una clase cuyos atributos son privados, tiene un constructor por
 * defecto y sigue normas de nombre de propiedades. Ademas Se accede a sus atrubutos 
 * por medio de Getters y Setters.
 * 
 * @author Ernesto Cantú
 * 17/03/2015
 */
public class PersonAppBasic {
    
    /**
     * Método de arranque de la aplicación.
     * @param args 
     */
    public static void main(String[] args) {
        Person homer = new Person("Homero" , "Simpson" , Gender.MALE);
        Person marge = new Person("Marge", "Simpson" , Gender.FEMALE);
        
        homer.setMiddeName("Jay");
        marge.setMiddeName("Louise");
        homer.setSuffix("Junior");
        
        System.out.println(homer);
        System.out.println(marge);
        
        if(homer.equals(marge)){
            System.out.println(homer + " is equals to " + marge);
        }
        else{
            System.out.println(homer + " is not equals to " + marge);
        }
    }
    
}
