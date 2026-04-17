/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.shared.ShowStudentPackage;



import java.io.Serializable;

/**
 * Marker request used by client to ask server for the full students list.
 */
public class ShowStudent implements Serializable {
    private static final long serialVersionUID = 1L;

    public ShowStudent() {
        // no fields required — this is a simple marker request
    }

    @Override
    public String toString() {
        return "ShowStudent{}";
    }
}
