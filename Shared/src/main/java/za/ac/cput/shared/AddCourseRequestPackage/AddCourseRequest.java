/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.AddCourseRequestPackage;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class AddCourseRequest  implements  Serializable{
    private String description;
    private String code;
  private static final long serialVersionUID = 1L;

    public AddCourseRequest(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AddCourseRequest{" + "description=" + description + ", code=" + code + '}';
    }
    
}
