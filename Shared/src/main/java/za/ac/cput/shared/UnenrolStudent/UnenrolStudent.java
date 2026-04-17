/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.UnenrolStudent;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class UnenrolStudent implements Serializable{
    private String number;
     private static final long serialVersionUID = 1L;
    public UnenrolStudent(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "UnenrolStudent{" + "number=" + number + '}';
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
    
}
