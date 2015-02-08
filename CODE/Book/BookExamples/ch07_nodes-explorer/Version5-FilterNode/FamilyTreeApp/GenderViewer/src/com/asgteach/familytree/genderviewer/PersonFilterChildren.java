/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.model.Person;
import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public class PersonFilterChildren extends FilterNode.Children {
    private final String searchString;

    public PersonFilterChildren(Node original, String searchString) {
        super(original);
        this.searchString = searchString;
    }

    @Override
    protected Node[] createNodes(Node key) {
        List<Node> result = new ArrayList<>();
        for (Node node : super.createNodes(key)) {
            if (accept(node)) {
                result.add(node);
            }
        }
        return result.toArray(new Node[0]);
    }

    @Override
    protected Node copyNode(Node original) {
        return new PersonFilterNode(original, searchString);
    }
    
    private boolean accept(Node node) {
        Person p = node.getLookup().lookup(Person.class);
        // make case insensitive
        return (p == null || p.toString().toLowerCase().contains(searchString));
    }   
    
}
