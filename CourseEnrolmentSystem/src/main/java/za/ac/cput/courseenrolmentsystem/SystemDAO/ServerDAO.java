/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.SystemDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import za.ac.cput.courseenrolmentsystem.System.Course;
import za.ac.cput.courseenrolmentsystem.System.Student;
import za.ac.cput.courseenrolmentsystem.SystemDBConnection.DBConnection;
import za.ac.cput.courseenrolmentsystem.SystemGUI.SystemEntry;

/**
 *
 * @author manga
 */
public class ServerDAO {
    private static Connection con;
    private  static PreparedStatement pstmt;
   
    public static void createConnection(){
         try{
         con = DBConnection.derbyConnection();
         JOptionPane.showMessageDialog(null, " connected to the Database");
          ServerDAO dao = new ServerDAO(); 
         }catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Can't connect to the database"+ e.getMessage(),"Warning",JOptionPane.ERROR_MESSAGE);
   }
    }
    

    public ServerDAO() {
    }

     
   
       public static void createStudent(JFrame sign,Student student) {
         createConnection();
        int ok;
        String sql = "INSERT INTO PROFILE VALUES  (?,?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,student.getName());
            pstmt.setString(2,student.getSurname());
           pstmt.setString(3,student.getStudentNumber());
            pstmt.setString(4,student.getPassword());
           
            ok = pstmt.executeUpdate();
            if(ok>0) {
             JOptionPane.showMessageDialog(null, "Student Successfully Created");
//            new LoginGUI().setVisible(true);
                   sign.dispose();
            } else
             JOptionPane.showMessageDialog(null, "Could not Create Student profile");
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null,
                    "\nStudent Name Already exists");
        } finally {
            try 
            { 
                if(pstmt!=null)
                pstmt.close();
            }catch(Exception exception){
                JOptionPane.showMessageDialog(null,
                   exception.getMessage(),"Warning",JOptionPane.ERROR_MESSAGE);
         }
            try 
            {
               con.close(); 
            }catch(SQLException e){
                Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE,null,e);
            }
        }
    }
//      
       public static ArrayList<Course> getAvailableCourses() throws SQLException {
        createConnection();
           ArrayList<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM COURSE"; // your table name
        try ( 
//              PreparedStatement pstmt = ServerDAO.con.prepareStatement(sql);
               PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String code = rs.getString("COURSE_CODE");
                String name = rs.getString("Tittle");
                courses.add(new Course(code, name));
            }
        }catch(SQLException ex){
            System.out.println("Error fetching courses from the DB:");
            ex.printStackTrace();
        }
        return courses;
    }
       public static boolean enrollStudent(String studentNo, String courseCode) throws SQLException{
           createConnection();
           String sql = "SELECT COUNT(*) FROM STUDENT WHERE STUDENT_NUMBER = ?";
           try(PreparedStatement pstmt = con.prepareStatement(sql)){
                   pstmt.setString(1,studentNo);
                  
                   ResultSet rs = pstmt.executeQuery();
                   if(rs.next() && rs.getInt(1) ==0){
                       JOptionPane.showMessageDialog(null,"Student Number :"+ " "+studentNo+" "+"Is not found found in the database");
               return false;        
                   }
}       
      
           String sql1 = "SELECT COUNT(*) FROM ENROLL_STUDENT WHERE STUDENT_NUMBER = ? AND COURSE_CODE = ?";
           try (PreparedStatement pstmt = con.prepareStatement(sql1)){
                    pstmt.setString(1,studentNo);
                    pstmt.setString(2,courseCode);
                    ResultSet rs = pstmt.executeQuery();
                    if(rs.next() && rs.getInt(1)>0){
                        JOptionPane.showMessageDialog(null," Student already enrolled in this course");
                     return false;
                    }
           }
           String sqlI = "INSERT INTO ENROLL_STUDENT(STUDENT_NUMBER,COURSE_CODE) VALUES(?,?)";
           try {
                  PreparedStatement pstmt = con.prepareStatement(sqlI);
                   pstmt.setString(1,studentNo);
                   pstmt.setString(2,courseCode);
                   JOptionPane.showMessageDialog(null,"Enrollment succesfull for student :"+studentNo);
                   return pstmt.executeUpdate()>0;
                  
            }catch(SQLException ioe){
               JOptionPane.showMessageDialog(null,"Error enrolling in this course!");
                    
                ioe.printStackTrace();
            }
           return false;
       }
       public static ArrayList<Course> getStudentCourse(String studentNo){
           createConnection();
           System.out.println("Student ID received: '" + studentNo + "'");
           System.out.println("Length: " + studentNo.length());

           String sql = """
                  SELECT * FROM ENROLL_STUDENT WHERE STUDENT_NUMBER = ?""";
//          String sql = """
//                  SELECT COURSE.COURSE_CODE,COURSE.TITTLE
//                  FROM ENROLL_STUDENT
//                  JOIN  COURSE ON ENROLL_STUDENT.COURSE_CODE =  COURSE.COURSE_CODE      
//                  WHERE ENROLL_STUDENT.STUDENT_NUMBER =?""";
//          
         System.out.println("Running query for student: '" + studentNo  + "'");
           System.out.println("SQL: " + sql);
         ArrayList<Course> course = new ArrayList<>(); 
         try {
             PreparedStatement pstmt = con.prepareStatement(sql);
             pstmt.setString(1,studentNo.trim());
             ResultSet rs = pstmt.executeQuery();
             ResultSetMetaData meta = rs.getMetaData();
              System.out.println("Columns returned:");
              for (int i = 1; i <= meta.getColumnCount(); i++) {
              System.out.println("- " + meta.getColumnName(i));
}
             while(rs.next()){
                 course.add(new Course(rs.getString("COURSE_CODE"),rs.getString("STUDENT_NUMBER")));
                 System.out.println("Row found");
                 System.out.println("COURSE_CODE: " + rs.getString("COURSE_CODE"));
                  System.out.println("TITTLE: " + rs.getString("STUDENT_NUMBER"));
             }
         }catch(SQLException sqle){
             JOptionPane.showMessageDialog(null,"Error fetching enrolled course :");
             sqle.printStackTrace();
         }
        return course;
           
       }
       public static void updateStudent(JFrame sign,Student student) {
//         createConnection();
        int ok;
        String sql = "UPDATE PROFILE SET Name =?,SURNAME=?,PASSWORD=? WHERE STUDENT_NUMBER =?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
          
            pstmt.setString(1,student.getName());
            pstmt.setString(2,student.getSurname());
            pstmt.setString(3,student.getStudentNumber());
            pstmt.setString(4,student.getPassword());
            
            
            ok = pstmt.executeUpdate();
            if(ok>0) {
             JOptionPane.showMessageDialog(null, "Student seccessfully Updated");
           }else {
//            JOptionPane.showMessageDialog(null, "No Student profile found with username: "+ Student.getStudentNumber());
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, 
            "SQL Error while updating Student profile:\n" + e.getMessage(), 
            "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    }
//     
        public void signOut() {
        SwingUtilities.invokeLater(() -> {
        new SystemEntry().setVisible(true);
        });

    
        }}

   