/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.rolemodel;

import com.asgteach.rolemodel.api.User;
import com.asgteach.rolemodel.api.UserRole;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@ServiceProvider(service = UserRole.class)
public class SimpleUserRole implements UserRole {

    private final Map<String, User> userMap = new HashMap<>();
    private final List<String> roles = new ArrayList<>();

    public SimpleUserRole() {
        initialize();
    }
    
    private void initialize() {
        User user1 = new User();
        user1.setUsername("Joe");
        user1.setPassword("Joe");
        user1.setRole("User");
        storeUser(user1);
        storeRole("Admin");
        User user2 = new User();
        user2.setUsername("Meg");
        user2.setPassword("Meg");
        user2.setRole("Admin");
        storeUser(user2); 
    }
    
    @Override
    public User findUser(String username, String password) {
        User user = userMap.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public User storeUser(User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("User");
        }
        storeRole(user.getRole());
        return userMap.put(user.getUsername(), user);
    }

    @Override
    public String[] getRoles() {
        return roles.toArray(new String[roles.size()]);       
    }

    @Override
    public boolean storeRole(String role) {
        if (!roles.contains(role)) {
            return roles.add(role);
        } 
        else return false;
    }
}
