package observablecollectionexample;

import com.asgteach.familytree.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

/**
 * ejemplo de observable collections
 * @author ernesto
 */
public class ObservableCollectionExample {

    
    private static final MapChangeListener<Long,Person> mapChangeListener = (change)->{
        if(change.wasAdded() && change.wasRemoved()){
            System.out.println("Updates object");
        }
        else if(change.wasRemoved())
            System.out.println("Removed object");
        else if(change.wasAdded())
            System.out.println("Added object");
        
    };
    
    
    public static void main(String[] args) {
        ObservableMap<Long,Person> observableMap = FXCollections.observableHashMap();
        
        observableMap.addListener(mapChangeListener);
        
        Person p1 = new Person("ernesto","cantu",Person.Gender.MALE);
        observableMap.put(p1.getId(), p1);
        
        /*
        NOTA: El update es disparado al eliminar "fisicamente" el objeto p1 y sustiturirlo
              por el objeto p2, cuya información es exactamente la misma(con excepcion de middlename).
        */
        Person p2 = new Person(p1);
        p2.setMiddleName("alguno");
        //Aquí se ejecuta el change event con added y removed en true.
        observableMap.put(p2.getId(), p2);
        
    }
    
}
