/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.loginNetwork;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L; // important!

    private String action;
    private String username;
    private String password;
    private String role;
    // Constructor
    public LoginRequest(String action, String username, String password, String role) {
        this.action = action;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    // Getters
    public String getAction() { return action; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    // Setters (optional)
    public void setAction(String action) { this.action = action; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}