/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

/**
 *
 * @author gail
 */
public class Birth extends ParentChildEvent {
    
    public Birth() {
        setEventName("Birth");
        setBioBirth(true);
    }
    
    public Birth(Long id) {
        super(id);
        setEventName("Birth");
        setBioBirth(true);        
    }   
}
