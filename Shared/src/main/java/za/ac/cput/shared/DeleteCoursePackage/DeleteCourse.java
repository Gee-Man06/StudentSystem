/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.DeleteCoursePackage;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class DeleteCourse  implements Serializable {
      private String code;
  private static final long serialVersionUID = 1L;

    public DeleteCourse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DeleteCourse{" + "code=" + code + '}';
    }
  
  
}
