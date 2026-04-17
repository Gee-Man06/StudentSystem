/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.ShowCoursePackage;

/**
 *
 * @author Phiwa
 */

import java.io.Serializable;

/**
 * Marker request used by client to ask server for the full students list.
 */
public class ShowCourse implements Serializable {
    private static final long serialVersionUID = 1L;

    public ShowCourse() {
    }

    
    @Override
    public String toString() {
        return "ShowStudent{}";
    }
}
