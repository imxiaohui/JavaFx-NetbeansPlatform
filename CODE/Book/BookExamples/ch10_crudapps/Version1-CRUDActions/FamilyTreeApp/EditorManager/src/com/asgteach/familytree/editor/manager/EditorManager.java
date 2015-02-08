/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.editor.manager;


import com.asgteach.familytree.model.Person;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public interface EditorManager {
    public void openEditor(Node node);
    public void unRegisterEditor(Person person);    
}
