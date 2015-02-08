/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.genderviewer;

import com.asgteach.familytree.model.Person.Gender;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public class GenderChildFactory extends ChildFactory<Gender> {

    private static final Logger logger = Logger.getLogger(GenderChildFactory.class.getName());

    @Override
    protected boolean createKeys(List<Gender> list) {
        list.addAll(Arrays.asList(Gender.values()));
        logger.log(Level.INFO, "createKeys called: {0}", list);
        return true;
    }

    @Override
    protected Node createNodeForKey(Gender key) {
        logger.log(Level.INFO, "createNodeForKey: {0}", key);
        return new GenderNode(key);
    }
}
