/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.model.Person;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public class PersonFilterNode extends FilterNode {

    public PersonFilterNode(Node original, String searchString) {
        super(original, new PersonFilterChildren(original, searchString));
    }
    
    @Override
    public Node getOriginal() {
        return super.getOriginal();
    }

    @Override
    public String getHtmlDisplayName() {
        Person person = getOriginal().getLookup().lookup(Person.class);
        if (person == null) {
            return null;
        } 
        StringBuilder sb = new StringBuilder();
        switch (person.getGender()) {
            case MALE:
                sb.append("<font color='#5588FF'><i>| ");
                break;
            case FEMALE:
                sb.append("<font color='#FF8855'><i>* ");
                break;
            case UNKNOWN:
                sb.append("<i>? ");
                break;
        }
        sb.append(person.toString()).append("</i></font>");
        return sb.toString();
    }
    
    
}
