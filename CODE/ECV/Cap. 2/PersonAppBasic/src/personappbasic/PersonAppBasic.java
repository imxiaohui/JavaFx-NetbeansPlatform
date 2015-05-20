package personappbasic;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;

/**
 * PersonAppBasic. Ejemplo del Libro.
 * 
 * Pag. 25
 * 
 * @author ernesto
 */
public class PersonAppBasic {

    
    public static void main(String[] args) {
        
        /*
            Creando objetos Person.
        */
        Person homer = new Person("Homer","Simpson",Gender.MALE);
        Person marge = new Person("Marge","Simpson",Gender.FEMALE);
        
        homer.setMiddleName("Jay");
        marge.setMiddleName("Louise");
        homer.setSuffix("jr.");
        
        System.out.println(homer);
        System.out.println(marge);
        
        if(homer.equals(marge)){
            System.out.println(homer + " is equal to " + marge);
        }else{
            System.out.println(homer + " is not equal to " + marge);
        }
    }
}
