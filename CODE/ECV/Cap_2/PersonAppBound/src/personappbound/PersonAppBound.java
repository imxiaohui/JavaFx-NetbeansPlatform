package personappbound;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * PersonAppBound para prueba de Property Change Support
 * @author ernesto
 */
public class PersonAppBound {

    public static void main(String[] args) {
        
        Person homer = new Person("Homer", "Simpson", Gender.MALE);
        Person marge = new Person("Marge", "Simpson", Gender.FEMALE);
        Person bart = new Person("Bart", "Simpson", Gender.MALE);
        
        //Implemento la clase PropertyChangeListener aquí
        final PropertyChangeListener pcl = (PropertyChangeEvent evt) ->{
                System.out.println("Property " + evt.getPropertyName() + " changed for " + evt.getSource());            
            
        };
        
//        final PropertyChangeListener pcl = new PropertyChangeListener(){
//            
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                System.out.println("Property " + evt.getPropertyName() + " changed for " + evt.getSource());
//            }
//            
//        };
        
        
        // Aquí le estoy solicitando a los objetos homer, marge y bart que le
        // den aviso a la clase PersonAppBound que ocurrió un property change event.
        // Los objetos homer, marge y bart ejecutarán el método propertyChange
        // del objeto plc de la clase PersonAppBound.
        homer.addPropertyChangeListener(pcl);
        marge.addPropertyChangeListener(pcl);
        bart.addPropertyChangeListener("firstName",pcl);
        
        homer.setMiddleName("Jay");
        marge.setMiddleName("Louise");
        
        homer.setSuffix("Junior");
        marge.setLastName("Jones");
        
        //Cambio no detectado
        bart.setMiddleName("J.");
        bart.setFirstName("Bartolome");
        
        System.out.println(homer);
        System.out.println(marge);
        
        homer.setMiddleName("Chester");
        
        homer.removePropertyChangeListener(pcl);
        marge.removePropertyChangeListener(pcl);
    }    
}
