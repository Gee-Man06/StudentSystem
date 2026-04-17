/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.System;

import java.io.Serializable;

/**
 *
 * @author manga
 */
public class Course implements Serializable{
    private String description;
    private String courseCode;

    @Override
    public String toString() {
        return "Course{" + "description=" + description + ", courseCode=" + courseCode + '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDescription() {
        return description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Course(String description, String courseCode) {
        this.description = description;
        this.courseCode = courseCode;
    }

    public Course() {
    }
    
}

   