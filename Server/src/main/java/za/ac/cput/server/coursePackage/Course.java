/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.server.coursePackage;

/**
 *
 * @author Phiwa
 */
public class Course {
    
     private String courseCode;
    private String courseDesc;

    public Course(String courseCode, String courseDesc) {
        this.courseCode = courseCode;
        this.courseDesc = courseDesc;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    @Override
    public String toString() {
        return "Course{" + "courseCode=" + courseCode + ", courseDesc=" + courseDesc + '}';
    }
    
    


    
    
}
