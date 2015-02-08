/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.trashcan;

import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.Index;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public class PersonNodeContainer extends Index.ArrayChildren {
    private final List<Node> personList = new ArrayList<Node>();

    @Override
    protected List<Node> initCollection() {
        return personList;
    }
}
