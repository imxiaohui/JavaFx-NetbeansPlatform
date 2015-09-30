/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.capabilityinterfaces;

import com.asgteach.familytree.model.Event;

/**
 *
 * @author gail
 */
public interface CreatableEventCapability {
    
    public void create(Event event) throws Exception;
    
}
