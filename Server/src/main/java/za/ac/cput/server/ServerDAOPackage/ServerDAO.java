/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.server.ServerDAOPackage;


import java.sql.Connection;
  import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import za.ac.cput.server.DBConnectionPackage.DBConnection;
import za.ac.cput.shared.CoursePackage.Course;

//import za.ac.cput.shared.StudentPackage.Student;
import za.ac.cput.shared.StudentsPackage.Students;
import za.ac.cput.shared.viewstudentcourse.ViewStudentCourses;

/**
 *
 * @author Phiwa
 */

public class ServerDAO {

    private static Connection con;
       private static PreparedStatement pstmt;

       private boolean tableExists(Connection con, String tableName) throws SQLException {
    try (ResultSet rs = con.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
        return rs.next();
    }
}

 public ServerDAO() {
    try {
        con = DBConnection.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Database connection is null or closed");
        }
        
        /*
//        =========== DROP ENROLLMENTS ===============
        if (tableExists(con, "ENROLLMENTS")) {
            try (PreparedStatement dropEnrollments = con.prepareStatement("DROP TABLE enrollments")) {
                dropEnrollments.executeUpdate();
    }
}
        /*
        //        =========== DROP COURSES ===============
        if (tableExists(con, "COURSES")) {
            try (PreparedStatement dropCourses = con.prepareStatement("DROP TABLE courses")) {
                 dropCourses.executeUpdate();
    }
}
        
        //        =========== DROP STUDENTS ===============
        if (tableExists(con, "STUDENTS")) {
            try (PreparedStatement dropStudents = con.prepareStatement("DROP TABLE students")) {
                 dropStudents.executeUpdate();
    }
}
        
        //        =========== DROP ADMINS ===============
        if (tableExists(con, "ADMINS")) {
            try (PreparedStatement dropAdmins = con.prepareStatement("DROP TABLE admins")) {
                 dropAdmins.executeUpdate();
    }
}

//    ============= CREATE COURSES ================
        try {
                PreparedStatement createCourses = con.prepareStatement(
                "CREATE TABLE courses (" +
                "course_code VARCHAR(50) PRIMARY KEY, " +
                "course_description VARCHAR(255)" +
                ")");
                createCourses.executeUpdate();
            }catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) { 
                    
                    try {
                    // ignore "already exists"
                    throw e;
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }} 
           
// =================== CREATE ENROLMENTS ==========
        try {
                PreparedStatement createEnrollments = con.prepareStatement(
                 "CREATE TABLE enrollments (" +
                "student_number VARCHAR(50), " +
                "course_code VARCHAR(50), " +
                "PRIMARY KEY (student_number, course_code)" +
                ")");
                createEnrollments.executeUpdate();
        }catch (SQLException ex) {
                if (!"X0Y32".equals(ex.getSQLState())) { // ignore "already exists"
                    throw ex;
                }
        }     
//  ================== CREATE STUDENTS ==============
       try {
                PreparedStatement createStudents = con.prepareStatement(
                 "CREATE TABLE students (" +
                "student_number BIGINT PRIMARY KEY, " +
                "first_name VARCHAR(100), " +
                "last_name VARCHAR(100), " +
                "cell VARCHAR(50), " +
                "email VARCHAR(150), " +
                "password VARCHAR(100)" +
                ")");
                createStudents.executeUpdate();
        }catch (SQLException exp) {
                if (!"X0Y32".equals(exp.getSQLState())) { // ignore "already exists"
                    try {
                    // ignore "already exists"
                    throw exp;
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }
       
//       ================== CREATE ADMIN ==============
       try {
                PreparedStatement createStudents = con.prepareStatement(
                 "CREATE TABLE admin (" +
                "student_number BIGINT PRIMARY KEY, " +
                "first_name VARCHAR(100), " +
                "last_name VARCHAR(100), " +
                "cell VARCHAR(50), " +
                "email VARCHAR(150), " +
                "password VARCHAR(100)" +
                ")");
                createStudents.executeUpdate();
        }catch (SQLException exp) {
                if (!"X0Y32".equals(exp.getSQLState())) { // ignore "already exists"
                    try {
                    // ignore "already exists"
                    throw exp;
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }
 
       }catch (SQLException ex) {
            Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }}
        **/
    }   catch (SQLException ex) {
            Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }}
        
    /**
     * Verify student login.
     * Only checks STUDENT role.
     */
    public boolean loginVerifyStudent(String username, String password, String role) {
        // Only process STUDENT role
        if (!"STUDENT".equalsIgnoreCase(role)) {
            return false;
        }

        if (con == null) {
            JOptionPane.showMessageDialog(null, "DB connection is null — cannot verify login");
            return false;
        }

        String sql = "SELECT 1 FROM STUDENTS WHERE STUDENT_NUMBER = ? AND PASSWORD = ?";

        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, username);
            pstm.setString(2, password);

            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next(); // true if user exists
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            return false;
        }
    
    }
    
    
    public boolean loginVerifyAdmin(String username,String password,String role){
        if(!"admin".equalsIgnoreCase(role)){
            return false;
        }if(con==null){
            JOptionPane.showMessageDialog(null,"dbc connection is null");
            return false;
        }
     String sql ="select * from ADMIN where STUDENT_NUMBER = ? AND PASSWORD =? ";
     try(
         PreparedStatement pst = con.prepareStatement(sql)){
         pst.setString(1, username);
         pst.setString(2, password);
         
         try(ResultSet rs = pst.executeQuery()){
            return rs.next();// return true if found
          }
          }catch(SQLException ex){
              ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false; 
         }
       }
    
    
    public boolean SaveStudent(Students student) {
    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot save student");
        return false;
    }

    
    
    
    
    String sql = "INSERT INTO STUDENTS (student_number, first_name, last_name, cell, email, password) VALUES (?, ?, ?, ?, ?, ?)";

    // Use try-with-resources so PreparedStatement is always closed
    try (PreparedStatement pst = con.prepareStatement(sql)) {
       
        
        System.out.println("Saving student: " +
    student.getStudentNumber() + ", " +
    student.getName() + ", " +
    student.getSurname() + ", " +
    student.getCell() + ", " +
    student.getEmail() + ", " +
    student.getPassword());

        
        pst.setLong(1,student.getStudentNumber());

     
        
         pst.setString(2, student.getName());
        pst.setString(3, student.getSurname());
        pst.setString(4, student.getCell());
        pst.setString(5, student.getEmail());
        pst.setString(6, student.getPassword());

        int affected = pst.executeUpdate();
        return affected > 0;

    } catch (SQLIntegrityConstraintViolationException dupEx) {
     
        System.err.println("SaveStudent: duplicate key for student number: " + student.getStudentNumber());
        JOptionPane.showMessageDialog(null, "Student number already exists: " + student.getStudentNumber());
        return false;

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error saving student: " + ex.getMessage());
        return false;
    }
}

   
        
public boolean SaveCourse(Course course) {
    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot save course");
        return false;
    }

   
    String sql = "INSERT INTO COURSES (COURSE_CODE, COURSE_DESCRIPTION) VALUES (?, ?)";

    try (PreparedStatement pst = con.prepareStatement(sql)) {
 
        pst.setString(1, course.getCourseCode());
        pst.setString(2, course.getCourseDesc());

        int affected = pst.executeUpdate();
        return affected > 0;

    } catch (SQLIntegrityConstraintViolationException dupEx) {
        System.err.println("SaveCourse: duplicate key for course code: " + course.getCourseCode());
        JOptionPane.showMessageDialog(null, "Course code already exists: " + course.getCourseCode());
        return false;

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error saving course: " + ex.getMessage());
        return false;
    }
}

    

    
   //== UPDATING STUDENTS===
/**
 * Update a student record using the existing connection 'con'.
 * Returns true if at least one row was updated.
 */
public boolean updateStudent(long studentNumber,String name,  String surname, String cell, String email,String password) {
    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot update student");
        return false;
    }

    final String sql = "UPDATE STUDENTS "
                     + "SET first_name = ?, last_name = ?, cell = ?, email = ?, password = ? "
                     + "WHERE student_number = ?";

    try (PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, name);
        pst.setString(2, surname);
        pst.setString(3, cell);
        pst.setString(4, email);
        pst.setString(5, password); // consider hashing the password before calling DAO
        pst.setLong(6, studentNumber);

        int rows = pst.executeUpdate();
        return rows > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error updating student: " + ex.getMessage());
        return false;
    }
}


//===== UPDATING COURSES====
 public boolean updateCourse(String code,String desc) {
    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot update  courses");
        return false;
    }

    final String sql = "UPDATE COURSES "
                     + "SET  COURSE_DESCRIPTION = ? "
                     + "WHERE COURSE_CODE = ?";

    try (PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, code);
        pst.setString(2, desc);
       // pst.setString(3,code);
       

        int rows = pst.executeUpdate();
        System.out.println("course updated" +code+"  " + desc);
        return rows > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
       
        JOptionPane.showMessageDialog(null, "Database error updating course: " + ex.getMessage());
        return false;
    }
}









//====== GATTING ALL STUDENT =======

public ArrayList<Students> getAllStudents() throws SQLException {
    ArrayList<Students> students = new ArrayList<>();

    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot retrieve students");
        return students;
    }

    String sql = "SELECT student_number, first_name, last_name, cell, email, password FROM STUDENTS";

    try (PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            long studentNumber = rs.getLong("student_number");
            String name = rs.getString("first_name");
            String surname = rs.getString("last_name");
            String cell = rs.getString("cell");
            String email = rs.getString("email");
            String password = rs.getString("password");

            Students student = new Students(studentNumber, name, surname, cell, email, password);
            students.add(student);
        }

        System.out.println("ServerDAO: Retrieved " + students.size() + " students from database.");

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error retrieving students: " + ex.getMessage());
    }

    return students;

}




// ===GETTING ALL AVAILABLE COURSES=======
public ArrayList<Course> getAllCourses() throws SQLException {
    ArrayList<Course> courses = new ArrayList<>();

    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot retrieve courses");
        return courses;
    }

    String sql = "SELECT COURSE_CODE, COURSE_DESCRIPTION  FROM COURSES";

    try (PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            String code = rs.getString("COURSE_CODE");
            String desc = rs.getString("COURSE_DESCRIPTION");
           

           Course course = new Course( code, desc );
            courses.add(course);
        }

        System.out.println("ServerDAO: Retrieved " + courses.size() + " courses from database.");

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error retrieving students: " + ex.getMessage());
    }

    return courses;

}
 



//=======GETTING STUDENT COURSE======

public ArrayList<ViewStudentCourses> getMyCourse(String studentNumber)throws SQLException {
    ArrayList<ViewStudentCourses> myCourses = new ArrayList<>();

    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot retrieve courses");
        return myCourses;
    }

    String sql = "SELECT * from enrollments where student_number =?";

    try (PreparedStatement pst = con.prepareStatement(sql)) {
    
        pst.setString(1, studentNumber);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                // query selects COURSE_CODE so read that column
                String courseCode = rs.getString("Course_code");
                 String student_number = rs.getString("Student_number");
                 
                if (courseCode != null) {
                    ViewStudentCourses myCourse = new ViewStudentCourses(courseCode,student_number);
                    myCourses.add(myCourse);
                }
            }
        }

        System.out.println("ServerDAO: Retrieved " + myCourses.size() + " courses from database for student " + studentNumber);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error retrieving courses: " + ex.getMessage());
    }

    return myCourses;
}






///// DELETING a Student====

/**
 * Delete a student record by student number.
 * Returns true if a row was deleted.
 */
public boolean deleteStudents(long studentNumber) {
    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot delete student");
        return false;
    }

    final String sql = "DELETE FROM STUDENTS WHERE student_number = ?";

    try (PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setLong(1, studentNumber);

        int affectedRows = pst.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("ServerDAO: Student deleted successfully (student_number=" + studentNumber + ")");
            return true;
        } else {
            System.out.println("ServerDAO: No student found with student_number=" + studentNumber);
            return false;
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error deleting student: " + ex.getMessage());
        return false;
    }
    
} 
    
    public boolean deleteCourse(String code) {
    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot delete course");
        return false;
    }

    final String sql = "DELETE FROM COURSES WHERE COURSE_CODE = ?";

    try (PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, code);

        int affectedRows = pst.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("ServerDAO: Student deleted successfully (course Code=" + code + ")");
            return true;
        } else {
            System.out.println("ServerDAO: No student found with course found with=" + code);
            return false;
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error deleting student: " + ex.getMessage());
        return false;
    }
}
    
    
    
    
    
    public boolean unEnrol(String studentNumber){
 
    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot unerol student");
        return false;
    }

    final String sql = "DELETE FROM ENROLLMENTS WHERE STUDENT_NUMBER = ?";

    try (PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1,studentNumber);

        int affectedRows = pst.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("ServerDAO: Student  unEnrolled successfully=" + studentNumber + ")");
            return true;
        } else {
            System.out.println("ServerDAO: No Course found with student=" + studentNumber);
            return false;
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error unEnrolling in a Course: " + ex.getMessage());
        return false;
    }

    }
    
    
    
    
    
    
    
public boolean enroleStudent(String studentNumber, String code) throws SQLException {
    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB connection is null — cannot enroll student");
        return false;
    }

   
    final String sql = "INSERT INTO enrollments (student_number, course_code) VALUES (?, ?)";

    try (PreparedStatement pst = con.prepareStatement(sql)) {
   
        pst.setString(1, studentNumber);
        pst.setString(2, code);

        int affectedRows = pst.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("ServerDAO: Student enrolled successfully (student=" + studentNumber + ", course=" + code + ")");
            return true;
        } else {
            System.out.println("ServerDAO: No student enrolled (student=" + studentNumber + ", course=" + code + ")");
            return false;
        }
    } catch (SQLIntegrityConstraintViolationException icve) {
       
        JOptionPane.showMessageDialog(null, "Enrollment failed: student may already be enrolled or invalid data. " + icve.getMessage());
        return false;
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error enrolling student: " + ex.getMessage());
        return false;
    }
}

    public static void main(String[] args) {
        new ServerDAO();
    }
  
     
}
    
    
    
    
    
    

















