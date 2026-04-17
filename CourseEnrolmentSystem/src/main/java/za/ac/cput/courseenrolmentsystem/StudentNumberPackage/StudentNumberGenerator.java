/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.StudentNumberPackage;

/**
 *
 * @author Phiwa
 */





public class StudentNumberGenerator {
    public static Long number = 200000L;

    // synchronized to be thread-safe
    public static synchronized Long getNextNumber() {
        return number++;
    }
    
    
 
}
