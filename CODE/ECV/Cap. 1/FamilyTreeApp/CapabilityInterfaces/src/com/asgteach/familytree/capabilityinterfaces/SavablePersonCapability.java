/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.capabilityinterfaces;

import com.asgteach.familytree.model.Person;

/**
 *
 * @author gail
 */
public interface SavablePersonCapability {
    
    public void save(Person person) throws Exception;
}
