/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.EnroleStudentsPackage;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class EnroleStudents implements Serializable {
    private static final long serialVersionUID = 1L;
            private String Code;
            private String Studentnumber;

    public EnroleStudents(String  Studentnumber, String Code) {
      
        this.Studentnumber = Studentnumber;
        this.Code = Code;
    }
            


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return Code;
    }

    public String getStudentnumber() {
        return Studentnumber;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public void setStudentnumber(String Studentnumber) {
        this.Studentnumber = Studentnumber;
    }

    @Override
    public String toString() {
        return "EnroleStudent{" + "Code=" + Code + ", Studentnumber=" + Studentnumber + '}';
    }

    
    


}