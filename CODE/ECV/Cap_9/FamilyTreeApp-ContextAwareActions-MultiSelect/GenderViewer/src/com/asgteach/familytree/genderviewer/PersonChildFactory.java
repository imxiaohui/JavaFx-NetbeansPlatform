package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.model.Person;
import java.util.List;
import org.openide.nodes.ChildFactory;
import com.asgteach.familytree.model.FamilyTreeManager;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.WeakListeners;

/**
 * Generador de Nodos del tipo PersonNode
 * @author ernesto
 */
public class PersonChildFactory extends ChildFactory<Person>{
    
    private final FamilyTreeManager ftm;
    private static final Logger logger = Logger.getLogger(PersonChildFactory.class.getName());
    private final Person.Gender gender;
    
    
    public PersonChildFactory(Person.Gender gender) {
        this.gender = gender;
        this.ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if(ftm == null){
            logger.log(Level.SEVERE,"No fue posible encontrar una instancia del Family Tree Manager");
            LifecycleManager.getDefault().exit();
        }
        ftm.addPropertyChangeListener(familyTreeListener);
    }

    //Indica como esta Fabrica de Nodos creara los nodos.
    @Override
    protected boolean createKeys(List<Person> list) {
        ftm.getAllPeople().stream().forEach((Person p)->{
            if(p.getGender().equals(this.gender)){
                list.add(p);
            }
        });
        logger.log(Level.FINER,"createKeys called {0}",ftm.getAllPeople());
        return true;
    }

    //Recibe un objeto y lo envuelve en un nodo.
    @Override
    protected Node createNodeForKey(Person key) {
        logger.log(Level.FINER,"CreateNodForKey {0}",key);
        PersonNode node = null;
        node = new PersonNode(key);
        key.addPropertyChangeListener(WeakListeners.propertyChange(node, key));
        
        return node;
    }
    
    
    //Crea un Listener para detectar cambios en el modelo.
    private final PropertyChangeListener familyTreeListener =
                                       (PropertyChangeEvent evt) ->{
        if(evt.getPropertyName().equals(FamilyTreeManager.PROP_PERSON_UPDATED) && evt.getNewValue() != null){
            this.refresh(true);
        }                               
    };
}
