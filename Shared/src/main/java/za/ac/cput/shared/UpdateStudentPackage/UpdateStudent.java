/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.UpdateStudentPackage;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class UpdateStudent  implements Serializable {
    
    private Long StudentNumber;
    private String updateName,updateSurname,updateCell,updateGmail,updatePassword;

    public UpdateStudent(Long StudentNumber, String updateName, String updateSurname, String updateCell, String updateGmail, String updatePassword) {
        this.StudentNumber = StudentNumber;
        this.updateName = updateName;
        this.updateSurname = updateSurname;
        this.updateCell = updateCell;
        this.updateGmail = updateGmail;
        this.updatePassword = updatePassword;
    }

    public Long getStudentNumber() {
        return StudentNumber;
    }

    public String getUpdateName() {
        return updateName;
    }

    public String getUpdateSurname() {
        return updateSurname;
    }

    public String getUpdateCell() {
        return updateCell;
    }

    public String getUpdateGmail() {
        return updateGmail;
    }

    public String getUpdatePassword() {
        return updatePassword;
    }

    public void setStudentNumber(Long StudentNumber) {
        this.StudentNumber = StudentNumber;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public void setUpdateSurname(String updateSurname) {
        this.updateSurname = updateSurname;
    }

    public void setUpdateCell(String updateCell) {
        this.updateCell = updateCell;
    }

    public void setUpdateGmail(String updateGmail) {
        this.updateGmail = updateGmail;
    }

    public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }

    @Override
    public String toString() {
        return "UpdateStudent{" + "StudentNumber=" + StudentNumber + ", updateName=" + updateName + ", updateSurname=" + updateSurname + ", updateCell=" + updateCell + ", updateGmail=" + updateGmail + ", updatePassword=" + updatePassword + '}';
    }
    
   

 
    
}
