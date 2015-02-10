/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

/**
 *
 * @author gail
 */
public class Adoption extends ParentChildEvent {    
    public Adoption() {
        setEventName("Adoption");
        setBioBirth(false);
    }    
    
    public Adoption(Long id) {
        super(id);
        setEventName("Adoption");
        setBioBirth(false);        
    }

    
}
