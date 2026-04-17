/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.DeleteStudentPackage;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class DeleteStudent  implements Serializable {
    private static final long serialVersionUID = 1L; 
    private Long StudentNumber;

    public DeleteStudent(Long StudentNumber) {
        this.StudentNumber = StudentNumber;
    }

    public Long getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(Long StudentNumber) {
        this.StudentNumber = StudentNumber;
    }

    @Override
    public String toString() {
        return "DeleteStudent{" + "StudentNumber=" + StudentNumber + '}';
    }

  
    
    
    
}
