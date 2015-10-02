
package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.capabilities.CalendarCapability;
import com.asgteach.familytree.model.Person.Gender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;

/**
 * Clase GenderNode. Extiende de node. Sirve como clase tipo "Wrapper" y envuelve
 * a un bean para poder ser utilizada por el nodes api.
 * @author ernesto
 */
@Messages({
    "HINT_GenderNode=Gender Node"
})
public class GenderNode extends AbstractNode{

    private final InstanceContent instanceContent;
    
    public GenderNode(Gender gender){
        this(gender,new InstanceContent());
    }
    
    private GenderNode(Gender gender,InstanceContent ic) {
        super(Children.create(new PersonChildFactory(gender), false),new AbstractLookup(ic));
        this.instanceContent=ic;
        instanceContent.add(gender);
        setGenderStuff(gender);
        setShortDescription(Bundle.HINT_GenderNode());
                
        instanceContent.add(new CalendarCapability() {

            @Override
            public void doCalendar() {
                CalendarPanel panel = new CalendarPanel();
                panel.setText("<html>Calendar Action applied to<p/>" + getDisplayName() + "</html>");
                DialogDescriptor dd = new DialogDescriptor(panel, Bundle.CTL_CalendarTitle());
                DialogDisplayer.getDefault().notify(dd);
            }
        });
    }

    @Override
    public Action[] getActions(boolean context) {
        List<Action> nodeActions = new ArrayList<>(Arrays.asList(super.getActions(context)));
        
        nodeActions.add(null);
        nodeActions.addAll(Utilities.actionsForPath("/Actions/Nodes"));
        return nodeActions.toArray(new Action[nodeActions.size()]);
    }
    
    
    
    private void setGenderStuff(Gender gender){
        StringBuilder sb = new StringBuilder();
        StringBuilder iconString = new StringBuilder("com/asgteach/familytree/genderviewer/resources/");
        
        switch(gender){
        
            case MALE:
                sb.append("MALE");
                iconString.append("maleIcon.png");
                break;
            case FEMALE:
                sb.append("FEMALE");
                iconString.append("femaleIcon.png");
                break;
            case UNKNOWN:
                sb.append("UNKNOWN");
                iconString.append("unknownIcon.png");
                break;
        }
        setName(sb.toString());
        setDisplayName(sb.toString());
        setIconBaseWithExtension(iconString.toString());
    }
    
}
