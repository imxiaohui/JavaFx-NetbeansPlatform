package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.LifecycleManager;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;

/**
 * Clase PersonNode. Extiende de node. Sirve como clase tipo "Wrapper" y envuelve
 * a un bean para poder ser utilizada por el nodes api.
 * @author ernesto
 */
@NbBundle.Messages({
    "HINT_PersonNode=Person"
})
public class PersonNode extends BeanNode<Person> implements PropertyChangeListener{
    
    public PersonNode(Person person) throws IntrospectionException{
        super(person,Children.LEAF,Lookups.singleton(person)); //Indica que los nodos son Hojas, y utilizará personas
        setIconBaseWithExtension("com/asgteach/familytree/genderviewer/resources/personIcon.png");
        setName(String.valueOf(person.getId())); 
        setDisplayName(person.toString());
        setShortDescription(Bundle.HINT_PersonNode());
    }
    
    //Configura la vista 
    @Override
    public String getHtmlDisplayName(){
        Person person = getLookup().lookup(Person.class);
        StringBuilder sb = new StringBuilder();
        if(person == null){
            return null;
        }
        sb.append("<font color='#5588FF'><b>");
        switch(person.getGender()){
            case MALE:
                sb.append("<font color='#5588FF'><b>| ");
                break;
            case FEMALE:
                sb.append("<font color='#FF8855'><b>* ");
                break;
            case UNKNOWN:
                sb.append("<b>? ");
                break;  
        }
        sb.append(person.toString()).append("</b></font>");
        return sb.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Person person = (Person) evt.getSource();
        FamilyTreeManager ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        
        if(ftm == null){
            //Logger.getLogger(PersonNode.class).log(Level.SEVERE, "No se pudo obtener instancia de FamilyTreeManager");
            LifecycleManager.getDefault().exit();
        }else{
            ftm.updatePerson(person);
            fireDisplayNameChange(null, getDisplayName());
        }
    }
    
}
