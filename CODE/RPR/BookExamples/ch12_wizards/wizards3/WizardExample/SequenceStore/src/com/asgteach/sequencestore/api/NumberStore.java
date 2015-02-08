/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.sequencestore.api;

/**
 *
 * @author gail
 */
public interface NumberStore {
    
    public boolean isUnique(Integer num);
    
    public boolean store(Integer num);
    
}
