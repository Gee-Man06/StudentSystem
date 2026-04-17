/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.StudentPackage;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class Student  implements Serializable {
    
    
    
      private static final long serialVersionUID = 1L;
    private String name;
    private String surname;
    private Long studentNumber;
    private String password;
    private String cell;
    private String email;
    public Student(Long studentNumber,String name, String surname, String cell , String email,String password) {
       
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

    public String getName() {
        return name;
    }
    

    public String getSurname() {
        return surname;
    }

    public Long getStudentNumber() {
        return studentNumber;
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

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", surname=" + surname + ", studentNumber=" + studentNumber + '}';
    }
    
    
}
