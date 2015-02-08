/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.model.Person.Gender;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gail
 */
@Messages({
    "HINT_GenderNode=Gender Node"
})
public class GenderNode extends AbstractNode {

    public GenderNode(Gender gender) {
        super(Children.create(new PersonChildFactory(gender), false), Lookups.singleton(gender));
        setGenderStuff(gender);
        setShortDescription(Bundle.HINT_GenderNode());
    }
    
    private void setGenderStuff(Gender gender) {
        StringBuilder sb = new StringBuilder();
        StringBuilder iconString = new StringBuilder("com/asgteach/familytree/genderviewer/resources/");
        switch (gender) {
            case MALE:
                sb.append("Male");
                iconString.append("maleIcon.png");
                break;
            case FEMALE:
                sb.append("Female");
                iconString.append("femaleIcon.png");
                break;
            case UNKNOWN:
                sb.append("Unknown");
                iconString.append("unknownIcon.png");
                break;
        }
        setName(sb.toString());
        setDisplayName(sb.toString());
        setIconBaseWithExtension(iconString.toString());        
    }
}
