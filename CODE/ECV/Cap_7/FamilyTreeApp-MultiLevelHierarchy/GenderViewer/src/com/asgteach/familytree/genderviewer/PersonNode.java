package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
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
public class PersonNode extends AbstractNode implements PropertyChangeListener{
    
    public PersonNode(Person person){
        super(Children.LEAF,Lookups.singleton(person)); //Indica que los nodos son Hojas, y utilizará personas
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
        fireDisplayNameChange(null, getDisplayName());
    }
    
}
