/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.System;

import java.io.Serializable;

/**
 *
 * @author manga
 */
public class Admin implements Serializable{
    private String name;
    private String surname;
    private String adminNumber;
    private String password;

    public Admin(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public Admin(String adminNumber, String password) {
        this.adminNumber = adminNumber;
        this.password = password;
    }

    public Admin() {
    }

    public Admin(String name, String surname, String adminNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.adminNumber = adminNumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAdminNumber() {
        return adminNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" + "name=" + name + ", surname=" + surname + ", adminNumber=" + adminNumber + ", password=" + password + '}';
    }
}
