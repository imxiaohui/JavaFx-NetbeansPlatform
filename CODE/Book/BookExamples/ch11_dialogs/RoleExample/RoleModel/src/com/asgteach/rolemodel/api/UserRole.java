/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.rolemodel.api;

/**
 *
 * @author gail
 */
public interface UserRole {
    
    public User findUser(String username, String password);
    
    public User storeUser(User user);
   
    public String[] getRoles();
    
    public boolean storeRole(String role);
    
    
    
}
