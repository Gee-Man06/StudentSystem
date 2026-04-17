/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.showMyEnroledCoursePackage;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class ShowMyEnroledCourse implements Serializable {
    private static final long serialVersionUID = 1L;
private String Studentnumber;
    public ShowMyEnroledCourse(String Studentnumber) {
        this.Studentnumber=Studentnumber;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStudentnumber() {
        return Studentnumber;
    }

    public void setStudentnumber(String Studentnumber) {
        this.Studentnumber = Studentnumber;
    }

    @Override
    public String toString() {
        return "ShowMyEnroledCourse{" + "Studentnumber=" + Studentnumber + '}';
    }

    
}
