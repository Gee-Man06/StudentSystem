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
public class Student implements Serializable{
    private  String name;
    private String surname;
    private  String studentNumber;
    private String password;

    public Student(String name, String surname, String studentNumber,String password) {
        this.name = name;
        this.surname = surname;
        this.studentNumber = studentNumber;
         this.password = password;
    }
    public Student(){
    }

    public Student(String studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }

   

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public  String getStudentNumber() {
        return studentNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", surname=" + surname + ", studentNumber=" + studentNumber + '}';
    }
    
    
    
}
