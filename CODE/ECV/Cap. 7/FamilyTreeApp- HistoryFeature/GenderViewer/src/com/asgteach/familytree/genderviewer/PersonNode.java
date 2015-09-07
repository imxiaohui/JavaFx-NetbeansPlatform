package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import org.openide.ErrorManager;
import org.openide.LifecycleManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
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
public class PersonNode extends AbstractNode implements PropertyChangeListener{
    
    public PersonNode(Person person) throws IntrospectionException{
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

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Person person = getLookup().lookup(Person.class);
        
        Sheet.Set setNames = Sheet.createPropertiesSet();
        setNames.setDisplayName("Names");
        
        Sheet.Set readOnlySet = new Sheet.Set();
        readOnlySet.setDisplayName("Identification");
        readOnlySet.setValue("tabName", " Id Info ");
        
        Sheet.Set infoSet = new Sheet.Set();
        infoSet.setName("Additional Information");
        

        sheet.put(setNames);
        sheet.put(infoSet);
        sheet.put(readOnlySet);
        
        try{
            
            Property<String> firstNameProp = new PropertySupport.ReadOnly<String>("firstName",String.class,"first name","Person's first name"){

                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return person.getFirstName();
                }
                
            };
            Property<String> middleNameProp = new PropertySupport.ReadOnly<String>("middleName",String.class,"middle name","Person's middle name"){

                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return person.getMiddleName();
                }
                
            };
            
            Property<String> lastNameProp = new PropertySupport.ReadOnly<String>("lastName",String.class,"last name","Person's last name"){

                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return person.getLastName();
                }
                
            };
            
            Property<String> suffixProp = new PropertySupport.ReadOnly<String>("suffix",String.class,"suffix","Person's suffix"){

                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return person.getSuffix();
                }
                
            };
            
            setNames.put(firstNameProp);
            setNames.put(middleNameProp);
            setNames.put(lastNameProp);
            setNames.put(suffixProp);
            
            Property<Person.Gender> genderProp = new PropertySupport.ReadOnly<Person.Gender>("gender",Person.Gender.class,"gender","Person's gender"){

                @Override
                public Person.Gender getValue() throws IllegalAccessException, InvocationTargetException {
                    return person.getGender();
                }
                
            };
            Property<String> notesProp = new PropertySupport.Reflection<>(person,String.class,"notes");
            
            infoSet.put(genderProp);
            infoSet.put(notesProp);
            
            Property<Long> idProp = new PropertySupport.Reflection<>(person,Long.class,"getId",null);
            readOnlySet.put(idProp);
        }catch(NoSuchMethodException ex){
            ErrorManager.getDefault();
                
        }
        return sheet;
    }
    
    
    
}
