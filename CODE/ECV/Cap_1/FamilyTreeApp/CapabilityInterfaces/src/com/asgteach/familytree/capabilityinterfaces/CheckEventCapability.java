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
public interface CheckEventCapability {
    
    public void checkEvent(Event event) throws Exception;
    
}
