/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.rolemodel.api;

/**
 *
 * @author gail
 */
public class User {
    private String username;
    private String password;
    private String role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getUsername()).append("-");
        sb.append(this.getPassword()).append("-");
        sb.append(this.getRole());
        return sb.toString();
    }    
    
}
