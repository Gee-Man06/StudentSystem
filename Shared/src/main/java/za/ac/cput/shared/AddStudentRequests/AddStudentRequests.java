/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.AddStudentRequests;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class AddStudentRequests implements Serializable{
    
    
    
     private static final long serialVersionUID = 1L; // important!

    private String action;
    private Long studentNumber;
    private String name;
    private String password;
    private String surname;
    private String cell;
    private String email;
     
    //private String role;

    // Constructor
    public AddStudentRequests(String action, Long studentNumber, String name, String surname,String cell, String email,String password) {
        this.action = action;
        this.studentNumber = studentNumber;
        this.name = name;
        this.surname = surname;
        this.cell = cell;       
        this.email = email;
        this.password = password;
        
      
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCell() {
        return cell;
    }

    public String getEmail() {
        return email;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAction() {
        return action;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "AddStudentRequest{" + "action=" + action + ", studentNumber=" + studentNumber + ", name=" + name + ", password=" + password + ", surname=" + surname + '}';
    }
      
    
       
     
       
           
 

    
    
    
    
}