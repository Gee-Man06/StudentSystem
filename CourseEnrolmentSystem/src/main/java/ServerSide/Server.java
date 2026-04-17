/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerSide;

import java.awt.List;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import za.ac.cput.courseenrolmentsystem.System.Admin;
import za.ac.cput.courseenrolmentsystem.System.Course;
import za.ac.cput.courseenrolmentsystem.System.Student;
import za.ac.cput.courseenrolmentsystem.SystemDAO.ServerDAO;
import za.ac.cput.courseenrolmentsystem.SystemDBConnection.DBConnection;

/**
 *
 * @author manga
 */

public class Server {
    private ServerSocket listener;
    private Socket client;
    private  ObjectOutputStream out;
    private ObjectInputStream in;
    ArrayList<Course> courseList;
     private static Connection con;
    private static PreparedStatement pstmt;

    
    public static void createConnection(){
         try{
         con = DBConnection.derbyConnection();
         JOptionPane.showMessageDialog(null, "Server is connected to the Database");
          ServerDAO dao = new ServerDAO(); 
         }catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Can't connect to the database"+ e.getMessage(),"Warning",JOptionPane.ERROR_MESSAGE);
   }
    
    }
    public  static boolean loginVerifyStudent( String StudentNumber,String password)throws SQLException{
        System.out.println("Starting loginVerifyStudent...");
    System.out.println("Input student number: " + StudentNumber);
    System.out.println("Input password: " + password);
       createConnection();
       String sql = "SELECT * FROM STUDENT WHERE STUDENT_NUMBER = ?";
        if(con == null){
            throw new SQLException("Connection is null.check DB setUp:");
        }
        try
        { 
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,StudentNumber);
          ResultSet rs = pstmt.executeQuery();
//            return rs.next();
            if(rs.next()){
             String pswd = rs.getString("password");
             return password.trim().equals(pswd);
            }else{
                JOptionPane.showMessageDialog( null,"No student found with number :"+StudentNumber);
            }
           
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println("Exception during loginVerifyStudent: " + e.getMessage());
       }
        return false;
}
    public  static boolean loginVerifyAdmin( String adminNo,String password)throws SQLException{
        System.out.println("Starting loginVerifyStudent...");
    System.out.println("Input admin number: " + adminNo);
    System.out.println("Input password: " + password);
       createConnection();
       String sql = "SELECT * FROM ADMIN WHERE ADMIN_NUMBER = ?";
  if(con == null){
            throw new SQLException("Connection is null.check DB setUp:");
        }
          try
        { 
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,adminNo);
          ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
             String pswd = rs.getString("password");
             return password.trim().equals(pswd);
            }else{
                JOptionPane.showMessageDialog( null,"No admin found with number :"+adminNo);
            }
       } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          System.out.println("Exception during loginVerifyStudent: " + e.getMessage());
     }
        return false;
}
    public static ArrayList<Course> getAvailableCourses() {
        try {
            return ServerDAO.getAvailableCourses();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }}
   
      public void insertStudentsDetails(Student student){
          // call a method from DAO to insert students details
        }
      
      
      public void deleteStudentDetails(Student student){
        }
      
      public void insertCourses(Course course){
          // call a DAO method to insert courses
          // get the the course from the gui 
          // this method will insert new course to the db
      }
      
      
      public void deleteCourse(Course course){
          // this method will get  course-code and delete 
          // and delete  that course form the the db called courses
         // method to delete coruse
          
      } 
      
      public void studentSnrollment(String courseCode, int StudentNumber){
         // this method will get student_number , get students courses(only course id )
         //
         // store it in a database called StudentsEnrollement
          
          
      }
      
    public static void main(String[]args) throws SQLException, ClassNotFoundException {
      try (
           ServerSocket listener = new ServerSocket(6666,1)){
           JOptionPane.showMessageDialog(null, "Server is started. Waiting for client to connect");
           while(true){
            try{
           Socket client = listener.accept();
           System.out.println("Client is Connected");
           
//            run(client);
//             listen(client);
            enrollStudent(client);
//           viewStudentCourses(client);
           
           }catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"error accepting client"+ex.getMessage());
            ex.printStackTrace();
             System.out.println(ex);
        }
}
    }catch(IOException e){
     JOptionPane.showMessageDialog(null,"Fatal server error"+""+e.getMessage());
   }}
    
     public static void listen(Socket client) throws SQLException, IOException, ClassNotFoundException {
        try (
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            ){while(true){
             Object request = in.readObject();
        if(request instanceof Student student){
             boolean login = loginVerifyStudent(student.getStudentNumber(),student.getPassword());
             out.writeObject(login);
             out.flush(); 
            }else if(request instanceof Admin admin){
             boolean login = loginVerifyAdmin(admin.getAdminNumber(),admin.getPassword());
             out.writeObject(login);
             out.flush(); 
            }else{
            JOptionPane.showMessageDialog(null,"Invalid request Type"+ request.getClass().getName());
             break;
        }
       }
     }catch(IOException io){
         JOptionPane.showMessageDialog(null,io.getMessage());
     }
       }
     public static void run(Socket client) throws SQLException, IOException, ClassNotFoundException{
         try
         (
             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(client.getInputStream());
//              System.out.print("Client Connected,sending course");
                 ){
        Object request;
         while((request =in.readObject()) != null){
            if(request instanceof String req){
                System.out.println("Recieved requests :"+req);
              if((req.equals("GET_COURSES")) || req.equals("VIEW_COURSE")){
                  ServerDAO dao = new ServerDAO();
                  ArrayList<Course> course = dao.getAvailableCourses();
                  System.out.println("sending"+" "+course.size()+"to clients");
                      
                     out.writeObject("VIEW_COURSES");
                     out.writeObject(course);
                     out.flush();
                 }}
                  
                  }
              }catch(IOException |ClassNotFoundException | SQLException ioc){
             ioc.printStackTrace();
         }
         }
     public static void enrollStudent(Socket client) {
    try {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());

        Object request = in.readObject();
        String courseCode = (String)in.readObject();
        String studentNo = (String)in.readObject();
       ServerDAO dao = new ServerDAO();
       boolean result = dao.enrollStudent(courseCode,studentNo);
        out.writeObject("ENROLL_RESULT");
        out.writeObject(result);
        out.flush();
        
       
    } catch (IOException | SQLException |ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, "Error in enrolling for a course : ");
        ex.printStackTrace();
    }
    try {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());

        out.writeObject("ENROLL_RESULT");
        out.writeObject(false);
        out.flush();
    }catch(IOException ioe){
        System.out.println("Failed to send failure response: " + ioe.getMessage());
    }
}

     public static void viewStudentCourses(Socket client) throws IOException, ClassNotFoundException {
      try (
       ObjectOutputStream   out = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
              ){
          Object request = in.readObject();
        if (  "GET_ENROLLED_COURSES".equals(request)) {
            String studentNo = (String)in.readObject();
           ServerDAO dao = new ServerDAO();
          ArrayList<Course> course = dao.getStudentCourse(studentNo); 
           
          System.out.println("Received GET_ENROLLED_COURSES for student: " + studentNo);
             System.out.print("courses found :"+ course.size());
             
             out.writeObject("ENROLLED_COURSES");
             out.writeObject(course);
             out.flush();
            return; 
        }else{
            out.writeObject("ERROR");
             out.writeObject("Unkownwn request :"+request);
             out.flush();}
       } catch (IOException | ClassNotFoundException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error in viewing Student Courses: " );
        ex.printStackTrace();
        try{
            ObjectOutputStream   out = new ObjectOutputStream(client.getOutputStream());
//         ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            out.writeObject("FAILED");
//            out.writeObject(new ArrayList<Course>());
            out.flush();
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null,"Failed to send fallback response :"+ ioe.getMessage());
        }
    }


     
          }
     
}
     
    

