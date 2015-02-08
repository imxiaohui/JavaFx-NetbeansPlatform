/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package personapp;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;

/**
 *
 * @author gail
 */
public class PersonAppBasic  {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Person homer = new Person("Homer", "Simpson", Gender.MALE);
        Person marge = new Person("Marge", "Simpson", Gender.FEMALE);
        
        homer.setMiddlename("Chester");
        marge.setMiddlename("Lousie");
        homer.setSuffix("Junior");
        
        System.out.println(homer);
        System.out.println(marge);
        if (homer.equals(marge)) {
            System.out.println(homer + " is equal to " + marge);
        } else {
            System.out.println(homer + " is not equal to " + marge);
        }
    }   
}
