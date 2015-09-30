package com.asgteach.rolemodel.api;

/**
 *
 * @author ernesto
 */
public interface UserRole {
    
    public User findUser(String userName,String password);
    public User storeUser(User user);
    public String[] getRoles();
    public boolean storeRole(String role);
}
