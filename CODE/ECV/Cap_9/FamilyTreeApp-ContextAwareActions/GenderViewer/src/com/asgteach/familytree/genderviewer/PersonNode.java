package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.capabilities.CalendarCapability;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.ErrorManager;
import org.openide.LifecycleManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Clase PersonNode. Extiende de node. Sirve como clase tipo "Wrapper" y envuelve
 * a un bean para poder ser utilizada por el nodes api.
 * @author ernesto
 */
@NbBundle.Messages({
    "HINT_PersonNode=Person",
    "CTL_CalendarTitle=Calendar Action"
})
public class PersonNode extends AbstractNode implements PropertyChangeListener{
    
    private final InstanceContent instanceContent;
    
    
    public PersonNode(Person person) {
        this(person,new InstanceContent());
    }
    
    private PersonNode(Person person, InstanceContent ic) {
        super(Children.LEAF,new AbstractLookup(ic)); //Indica que los nodos son Hojas, y utilizará personas
        instanceContent = ic;
        instanceContent.add(person);
        
        setIconBaseWithExtension("com/asgteach/familytree/genderviewer/resources/personIcon.png");
        setName(String.valueOf(person.getId())); 
        setDisplayName(person.toString());
        setShortDescription(Bundle.HINT_PersonNode());
        
        
        instanceContent.add(new CalendarCapability() {

            @Override
            public void doCalendar() {
                CalendarPanel panel = new CalendarPanel();
                panel.setText("<html>Calendar Action applied to<p/>" + person + "</html>");
                DialogDescriptor dd = new DialogDescriptor(panel, Bundle.CTL_CalendarTitle());
                DialogDisplayer.getDefault().notify(dd);
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Action[] getActions(boolean context) {
        List<Action> personActions = new ArrayList<>(Arrays.asList(super.getActions(context)));
        // add a separator
        personActions.add(null);
        personActions.addAll(Utilities.actionsForPath("Actions/Nodes"));
        return personActions.toArray(
                new Action[personActions.size()]);
    }

    @Override
    public Action getPreferredAction() {
        System.out.println("Double Click");
        List<Action> personActions = (List<Action>)Utilities.actionsForPath("Actions/Nodes");
        
        if(!personActions.isEmpty()){
            return personActions.get(0);
        }else{
            return super.getPreferredAction();
        }
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
        infoSet.setDisplayName("Additional Information");
        infoSet.setValue("tabName", "Additional Info.");

        sheet.put(setNames);
        sheet.put(infoSet);
        sheet.put(readOnlySet);
        
        try{
            
            Property<String> firstNameProp = new PropertySupport.Reflection<>(person,String.class,"firstName");
            Property<String> middleNameProp = new PropertySupport.Reflection<>(person,String.class,"middleName");
            Property<String> lastNameProp = new PropertySupport.Reflection<>(person,String.class,"lastName");
            Property<String> suffixProp = new PropertySupport.Reflection<>(person,String.class,"suffix");
            
            setNames.put(firstNameProp);
            setNames.put(middleNameProp);
            setNames.put(lastNameProp);
            setNames.put(suffixProp);
            
            Property<Person.Gender> genderProp = new PropertySupport.Reflection<>(person,Person.Gender.class,"gender");
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
