/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.viewstudentcourse;

import java.io.Serializable;

/**
 *
 * @author Phiwa
 */
public class ViewStudentCourses implements Serializable {
    private static final long serialVersionUID = 1L;
   private String description;
   private String courseCode;
    public ViewStudentCourses(String description,String courseCode) {
        
        this.description= description;
        this.courseCode = courseCode;
    }

    public String getDescription() {
        return description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    

    @Override
    public String toString() {
        return "ViewStudentCourses{" + "studentNumber=" + description + '}';
    }

    
 
    
    
}
    

