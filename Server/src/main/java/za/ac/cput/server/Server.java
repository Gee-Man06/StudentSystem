/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package za.ac.cput.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;
import za.ac.cput.server.ServerDAOPackage.ServerDAO;
import za.ac.cput.shared.AddCourseRequestPackage.AddCourseRequest;

import za.ac.cput.shared.AddStudentRequests.AddStudentRequests;
import za.ac.cput.shared.CoursePackage.Course;
import za.ac.cput.shared.DeleteCoursePackage.DeleteCourse;
import za.ac.cput.shared.DeleteStudentPackage.DeleteStudent;

import za.ac.cput.shared.EnroleStudentsPackage.EnroleStudents;
import za.ac.cput.shared.ShowAvailableCoursesPackage.ShowAvailableCourses;
import za.ac.cput.shared.ShowCoursePackage.ShowCourse;
import za.ac.cput.shared.ShowStudentPackage.ShowStudent;
//import za.ac.cput.shared.StudentPackage.Student;
import za.ac.cput.shared.StudentsPackage.Students;
import za.ac.cput.shared.UnenrolStudent.UnenrolStudent;
import za.ac.cput.shared.UpdateStudentPackage.UpdateStudent;
import za.ac.cput.shared.loginNetwork.LoginRequest;
import za.ac.cput.shared.showMyEnroledCoursePackage.ShowMyEnroledCourse;
import za.ac.cput.shared.updateCoursePackage.UpdateCourse;
import za.ac.cput.shared.viewstudentcourse.ViewStudentCourses;









public class Server {

    private final ServerSocket listener;
    private final ExecutorService executor;

    public Server(int port) throws IOException {
        this.listener = new ServerSocket(port);
        this.listener.setReuseAddress(true);
        this.executor = Executors.newCachedThreadPool();
        System.out.println("Server created and listening on port " + port);
    }

    
    
    public void listenAndAccept() {
        System.out.println("Server waiting for connections...");
        while (!listener.isClosed()) {
            try {
                Socket client = listener.accept();
                System.out.println("Accepted connection from " + client.getRemoteSocketAddress());
                // Hand off to thread pool
                executor.submit(() -> processClient(client));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        shutdownExecutor();
    }

  
public void processClient(Socket client) {
    Socket sock = client;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;

    try {
        out = new ObjectOutputStream(sock.getOutputStream());
        in = new ObjectInputStream(sock.getInputStream());

        // Loop until client disconnects
        while (!sock.isClosed()) {
            Object received;
            try {
                received = in.readObject(); // blocking read
            } catch (EOFException eof) {
                System.err.println("Server: client closed connection (EOF).");
                break;
            } catch (Exception ex) {
                System.err.println("Server: error reading object");
                ex.printStackTrace();
                break;
            }

            if (received == null) {
                System.out.println("Server: received null object, ignoring...");
                continue;
            }

            System.out.println("Server: received object type: " + received.getClass().getName());

            try {
                // === LOGIN ===
                if (received instanceof LoginRequest) {
                    LoginRequest req = (LoginRequest) received;
                    boolean success = false;
                    try {
                        success = verifyLogin(req.getUsername(), req.getPassword(), req.getRole());
                    } catch (Exception ex) {
                        System.err.println("Server: error verifying login");
                        ex.printStackTrace();
                        success = false;
                    }

                    String reply = success ? "true" : "false";
                    out.writeObject(reply);
                    out.flush();
                    System.out.println("Server: sent response (login) -> " + reply);

               
                    continue;
                }

                
                
                
                
                // === ADD STUDENT ===
                if (received instanceof AddStudentRequests) {
                    AddStudentRequests req = (AddStudentRequests) received;
                    Students student = new Students(
                            req.getStudentNumber(),
                            req.getName(),
                            req.getSurname(),
                            req.getCell(),
                            req.getEmail(),
                            req.getPassword()
                    );

                    String reply = "false";
                    try {
                        boolean inserted = insertStudentsDetails(student);
                        reply = inserted ? "true" : "false";
                        System.out.println("Server: insertStudentsDetails returned: " + inserted);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        reply = "false";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        reply = "false";
                    }

                    out.writeObject(reply);
                    out.flush();
                    continue;
                }

                //=====ADD COURSE ====
                if(received instanceof AddCourseRequest){
          try {
              
                 System.out.println("instance recieved");
                 AddCourseRequest req = (AddCourseRequest) received;
                    System.out.println("Server: AddCourseRequest received -> " + req.getCode() + ", " + req.getDescription());

                   Course course = new Course(req.getDescription(), req.getCode());
        boolean inserted = insertCourseDetails(course);

        String reply = inserted ? "true" : "false";
        out.writeObject(reply);
        out.flush();
        System.out.println("Server: insertCourseDetails returned: " + inserted);

    } catch (Exception e) {
        System.err.println("Server: Error handling AddCourseRequest");
        e.printStackTrace();
        try { 
            out.writeObject("false"); 
            out.flush(); 
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

                
                
                
                
                
                
                // === UPDATE STUDENT ===
                if (received instanceof UpdateStudent) {
                    UpdateStudent req = (UpdateStudent) received;
                    String reply = "false";
                    try {
                        boolean updated = updateStudent(
                                req.getStudentNumber(),
                                req.getUpdateName(),
                                req.getUpdateSurname(),
                                req.getUpdateCell(),
                                req.getUpdateGmail(),
                                req.getUpdatePassword()
                        );
                        reply = updated ? "true" : "false";
                        System.out.println("Server: updateStudentInDB returned: " + updated);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        reply = "false";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        reply = "false";
                    }

                    out.writeObject(reply);
                    out.flush();
                    continue;
                }

                //===UPDATE COURSE=====
                if (received instanceof UpdateCourse) {
                    UpdateCourse req = (UpdateCourse) received;
                    String reply = "false";
                    try {
                        boolean updatedCourse = updateCourse(
                                req.getCode(),
                                req.getDescription()
                                
                        );
                        reply = updatedCourse? "true" : "false";
                        System.out.println("Server: updateStudentInDB returned: " + updatedCourse);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        reply = "false";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        reply = "false";
                    }

                    out.writeObject(reply);
                    out.flush();
                    continue;
                }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                // === DELETE STUDENT ===
                if (received instanceof DeleteStudent) {
                    DeleteStudent req = (DeleteStudent) received;
                    String reply = "false";
                    try {
                        boolean deleted = deleteStudents(req.getStudentNumber());
                        reply = deleted ? "true" : "false";
                        System.out.println("Server: deleteStudents returned: " + deleted);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        reply = "false";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        reply = "false";
                    }

                    out.writeObject(reply);
                    out.flush();
                    continue;
                }

                
                
                //===DELETE COURSE======
                if (received instanceof DeleteCourse){
                    DeleteCourse req = (DeleteCourse) received;
                    
                    
                    
                     String reply = "false";
                    try {
                        boolean deletedCourse= deleteCourse(req.getCode());
                        reply = deletedCourse ? "true" : "false";
                        System.out.println("Server: deleteCourse returned: " + deletedCourse);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        reply = "false";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        reply = "false";
                    }

                    out.writeObject(reply);
                    out.flush();
                    continue;
                    
                    
                    
                    
                    
                    
                }
                
                
                
                
                
                
                
                
                
                // === SHOW STUDENTS (explicit request from client) ===
                if (received instanceof ShowStudent) {
                    try {
                        sendAllStudentsToClient(out);
                        System.out.println("Server: handled ShowStudent request and sent list");
                    } catch (Exception e) {
                        System.err.println("Server: failed to send students list on ShowStudent request");
                        e.printStackTrace();
                        // send an empty list to keep client-side reads consistent
                        try {
                            out.writeObject(new ArrayList<>());
                            out.flush();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    continue;
                }
                
                // SHOW COURSE====
                  if (received instanceof ShowCourse) {
                    try {
                        sendAllCourseToClient(out);
                        System.out.println("Server: handled ShowCorse request and sent list");
                    } catch (Exception e) {
                        System.err.println("Server: failed to send course list on ShowStudent request");
                        e.printStackTrace();
                        // send an empty list to keep client-side reads consistent
                        try {
                            out.writeObject(new ArrayList<>());
                            out.flush();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    continue;
                }
                
                
           
                  
                  
                  // === showAvailablecouraes===
                  if(received instanceof ShowAvailableCourses){
                      
                      try {
                        sendAllCoursesTOStudentdashboard(out);
                        System.out.println("Server: handled ShowCorse request and sent list");
                    } catch (Exception e) {
                        System.err.println("Server: failed to send course list on ShowStudent request");
                        e.printStackTrace();
                        // send an empty list to keep client-side reads consistent
                        try {
                            out.writeObject(new ArrayList<>());
                            out.flush();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    continue;
                      
                      
                      
                      
                  }
                  
                 
              //===ENROL STUDENT======
                if (received instanceof EnroleStudents){
                    EnroleStudents req = (EnroleStudents) received;
                    
                    
                    
                     String reply = "false";
                    try {
                        boolean EnroleStudent=  enroleStudent(req.getStudentnumber(),req.getCode());
                        reply = EnroleStudent ? "true" : "false";
                        System.out.println("Server: EnroleStudent returned: " + EnroleStudent);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        reply = "false";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        reply = "false";
                    }

                    out.writeObject(reply);
                    out.flush();
                    continue;
                }

                //====== SHOW MYENROLED COURSE =======
                
              
                    // I WILL CALL  AMETHOD THAT CALLS THE METHOD THAT INTERACT WITH THE DAO
                   if(received instanceof ShowMyEnroledCourse){
                       ShowMyEnroledCourse req = (ShowMyEnroledCourse) received;
                      try {
                          
                          String studentnumber= req.getStudentnumber();
                        ShowMyEnroleCourses(out,studentnumber);
                        System.out.println("Server: handled ShowCorse request and sent list");
                    } catch (Exception e) {
                        System.err.println("Server: failed to send course list on ShowStudent request");
                        e.printStackTrace();
                        // send an empty list to keep client-side reads consistent
                        try {
                            out.writeObject(new ArrayList<>());
                            out.flush();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    continue;
                      
                      
                      
                      
                  }
                
                
                //====== UNEROL STUDENTS =======
                  if (received instanceof UnenrolStudent){
                    UnenrolStudent req = (UnenrolStudent) received;
                    
                    
                    
                     String reply = "false";
                    try {
                        boolean deletedCourse= unEnrolStudent(req.getNumber());
                        reply = deletedCourse ? "true" : "false";
                        System.out.println("Server: deleteCourse returned: " + deletedCourse);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        reply = "false";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        reply = "false";
                    }

                    out.writeObject(reply);
                    out.flush();
                    continue;
                
                  }
                
                  
                  
                  
                  
                  
                // === UNEXPECTED OBJECT ===
                System.err.println("Server: Unexpected object type from client: " + received.getClass().getName());
                out.writeObject("false");
                out.flush();

            } catch (IOException ex) {
                System.err.println("Server: IOException during request processing");
                ex.printStackTrace();
                break;
            }
        }

    } catch (IOException ex) {
        System.err.println("Server: Error initializing client streams");
        ex.printStackTrace();
    } finally {
        try { if (in != null) in.close(); } catch (Exception ignored) {}
        try { if (out != null) out.close(); } catch (Exception ignored) {}
        try { if (sock != null && !sock.isClosed()) sock.close(); } catch (Exception ignored) {}
        System.out.println("Server: connection closed for client.");
    }
}




    
    
    public boolean enroleStudent(String Studentnumber,String Code)throws SQLException{
        boolean enroleStudent = false;
        
        try {
            
           System.out.println("studentName/number"+Studentnumber+" ==="+ "received");
        ServerDAO dao = new ServerDAO();
         enroleStudent = dao.enroleStudent(Studentnumber,Code);
        
        
        }catch(SQLException e){
        
    }


return enroleStudent;


}   
        
        
        
   
    
    
    
    
    
    


   










    
    
    
    
    
    private void sendAllStudentsToClient(ObjectOutputStream out) throws SQLException, IOException {
    List<Students> students = new ArrayList<>();
    try {
        ServerDAO dao = new ServerDAO();
        
        students = dao.getAllStudents();
        System.out.println("Server: fetched " + (students == null ? 0 : students.size()) + " students from DB");
    } catch (SQLException sqle) {
        System.err.println("Server: SQLException while fetching students");
        sqle.printStackTrace();
        throw sqle; 
    } catch (Exception ex) {
        System.err.println("Server: unexpected error while fetching students");
        ex.printStackTrace();
    
        throw new IOException("Error fetching students: " + ex.getMessage(), ex);
    }

    // Send the list to the client
    try {
        out.writeObject(students); // send List<Student>
        out.flush();
        System.out.println("Server: sent students list to client");
    } catch (IOException io) {
        System.err.println("Server: failed to write students list to client");
        io.printStackTrace();
        throw io;
    }
}
    
    
    
    
    
    private void sendAllCourseToClient(ObjectOutputStream out) throws SQLException, IOException {
    List<Course> course = new ArrayList<>();
    try {
        ServerDAO dao = new ServerDAO();
  
        course = dao.getAllCourses();
        System.out.println("Server: fetched " + (course == null ? 0 : course.size()) + " students from DB");
    } catch (SQLException sqle) {
        System.err.println("Server: SQLException while fetching courses");
        sqle.printStackTrace();
        throw sqle;
    } catch (Exception ex) {
        System.err.println("Server: unexpected error while fetching courses");
        ex.printStackTrace();
        
        throw new IOException("Error fetching courses: " + ex.getMessage(), ex);
    }

    // Send the list to the client
    try {
        out.writeObject(course); // send List<Student>
        out.flush();
        System.out.println("Server: sent courses list to client");
    } catch (IOException io) {
        System.err.println("Server: failed to write courses list to client");
        io.printStackTrace();
        throw io;
    }
}
    
    
    private void sendAllCoursesTOStudentdashboard(ObjectOutputStream out) throws SQLException, IOException {
    List<Course> course = new ArrayList<>();
    try {
        ServerDAO dao = new ServerDAO();
     
        course = dao.getAllCourses();
        System.out.println("Server: fetched " + (course == null ? 0 : course.size()) + " students from DB");
    } catch (SQLException sqle) {
        System.err.println("Server: SQLException while fetching courses");
        sqle.printStackTrace();
        throw sqle;
    } catch (Exception ex) {
        System.err.println("Server: unexpected error while fetching courses");
        ex.printStackTrace();
     
        throw new IOException("Error fetching courses: " + ex.getMessage(), ex);
    }

    // Send the list to the client
    try {
        out.writeObject(course); // send List<Student>
        out.flush();
        System.out.println("Server: sent courses list to client");
    } catch (IOException io) {
        System.err.println("Server: failed to write courses list to client");
        io.printStackTrace();
        throw io;
    }
}
    
    
    
    
    
   private void ShowMyEnroleCourses(ObjectOutputStream out,String StudentNumber) throws SQLException, IOException {
    List<ViewStudentCourses> course = new ArrayList<>();
    try {
        ServerDAO dao = new ServerDAO();
        // Assumes ServerDAO has a method getAllStudents() that returns List<Student>
        course = dao.getMyCourse(StudentNumber);
        System.out.println("Server: fetched " + (course == null ? 0 : course.size()) + " students from DB");
    } catch (SQLException sqle) {
        System.err.println("Server: SQLException while fetching courses");
        sqle.printStackTrace();
        throw sqle; 
    } catch (Exception ex) {
        System.err.println("Server: unexpected error while fetching courses");
        ex.printStackTrace();
       
        throw new IOException("Error fetching courses: " + ex.getMessage(), ex);
    }

    // Send the list to the client
    try {
        out.writeObject(course); 
        out.flush();
        System.out.println("Server: sent courses list to client");
    } catch (IOException io) {
        System.err.println("Server: failed to write courses list to client");
        io.printStackTrace();
        throw io;
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    

    /**
     * Verify login credentials using the DAO.
     * DAO returns boolean; this method just delegates.
     */
    public boolean verifyLogin(String userName, String password, String role) {
        boolean isValid = false;
        try {
            
            ServerDAO dao = new ServerDAO();
            if(role.equalsIgnoreCase("Student")){
            isValid = dao.loginVerifyStudent(userName, password, role);
            
            }else if(role.equalsIgnoreCase("admin")){
                isValid =dao.loginVerifyAdmin(userName,password,role);
            }
        } catch (Exception e) {
            e.printStackTrace();
           
            JOptionPane.showMessageDialog(null, "Server verifyLogin error: " + e.getMessage());
        }
        return isValid;
    }

    
    
    
      public boolean insertStudentsDetails(Students student)throws SQLException{
           boolean inserted ;
          
          ServerDAO dao = new ServerDAO(); 
          inserted = dao.SaveStudent(student);
          // call a method from DAO to insert students details
          
          return inserted;
      } 
      
      public boolean insertCourseDetails(Course course)throws SQLException{
          boolean insertedCourse;
          ServerDAO dao = new ServerDAO();
          insertedCourse = dao.SaveCourse(course);
          
         return insertedCourse; 
      }
      
      
      
      
     /**
 * Wrapper in the server class that calls ServerDAO to update a student.
 * Returns true if update succeeded.
 */
public boolean updateStudent(Long studentNumber, String name, String surname, String cell, String gmail, String password) throws SQLException{
    boolean updated = false;
    try {
        ServerDAO dao = new ServerDAO();  
        updated = dao.updateStudent(studentNumber, name, surname, cell, gmail, password);
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Server: updateStudent error: " + ex.getMessage());
        updated = false;
    }
    return updated;
}

public boolean updateCourse(String code, String desc)throws SQLException{
    boolean updated = false;
    try{
        ServerDAO dao = new ServerDAO();  
        updated = dao.updateCourse(code,desc);
    }
    
     catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Server: updateStudent error: " + ex.getMessage());
        updated = false;
    }
    return updated;
    
}





   public boolean deleteStudents(Long studentNumber) throws SQLException {
    if (studentNumber == null) {
        System.err.println("deleteStudents called with null studentNumber");
        return false;
    }
   
    ServerDAO dao = new ServerDAO(); 
    return dao.deleteStudents(studentNumber);
}

   
      public boolean deleteCourse(String code) throws SQLException {
    if (code == null) {
        System.err.println("deleteCourse called with null Code");
        return false;
    }
   
    ServerDAO dao = new ServerDAO(); 
    return dao.deleteCourse(code);
}

   public boolean unEnrolStudent(String studentNumber) throws SQLException{
       if (studentNumber == null) {
        System.err.println("deleteCourse called with null Code");
        return false;
    }
   
    ServerDAO dao = new ServerDAO(); 
    return dao.unEnrol(studentNumber);
   }
   
   
   
   
   
  
    
    
    
    
    
    
    
    private void shutdownExecutor() {
        try {
            executor.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            listener.close();
            shutdownExecutor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    
    
    
    
  
    
    public static void main(String[] args) {
        final int port = 12349; 
        try {
            Server server = new Server(port);
            server.listenAndAccept();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Server failed to start: " + e.getMessage());
        }
    }
}