package personappbasic;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;

/**
 * JavaFX Rich Client Programming on the Netbeans Platform
 * 
 * Ernesto Cantú Valle <ernesto.cantu1989@live.com>
 * Probado el día 8 de Febrero del 2015.
 */
public class PersonAppBasic {

    public static void main(String[] args) {
        Person homero = new Person("Homero", "Simpson", Gender.MALE );
        Person marge = new Person("Marge", "Simpson", Gender.FEMALE);
        
        homero.setMiddleName("Jay");
        marge.setMiddleName("Louise");
        homero.setSuffix("Junior");
        
        System.out.println(homero);
        System.out.println(marge);
        
        if(homero.equals(marge)){
            System.out.println(homero + " is equal to " + marge);
        }
        else{
            System.out.println(homero + " is not equal to " + marge);
        }
    }
    
}
