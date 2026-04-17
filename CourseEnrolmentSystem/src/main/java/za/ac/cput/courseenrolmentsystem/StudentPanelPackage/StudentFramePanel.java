/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.StudentPanelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.courseenrolmentsystem.MainAppFramePackage.MainAppFrame;
import za.ac.cput.courseenrolmentsystem.RoundButtonPackage.RoundedButton;
import za.ac.cput.shared.CoursePackage.Course;
import za.ac.cput.shared.EnroleStudentsPackage.EnroleStudents;
import za.ac.cput.shared.ShowAvailableCoursesPackage.ShowAvailableCourses;
import za.ac.cput.shared.UnenrolStudent.UnenrolStudent;
import za.ac.cput.shared.showMyEnroledCoursePackage.ShowMyEnroledCourse;
import za.ac.cput.shared.viewstudentcourse.ViewStudentCourses;

/**
 *
 * @author Phiwa
 */

@SuppressWarnings("serial")
public class StudentFramePanel extends JPanel{
    
private JLabel lblWelcome;
    private RoundedButton btnRead;

    private JComboBox comCourse;
     private RoundedButton btnEnrol;
    private RoundedButton btnMyEnrol;
    private RoundedButton btnLogout;
    private RoundedButton btnExit;
    private JTable tabEnrol;
    private JFrame main;
    private JScrollPane scrl;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private DefaultListModel<String> listModel;
    private DefaultTableModel tabModel;
    private  DefaultTableModel table;
    private Socket client;
    private String studentID;
    private JTable studentEnrol;
    public StudentFramePanel(String studentName, Socket soc, ObjectOutputStream oos, ObjectInputStream ois) {
        setLayout(new BorderLayout());
       

        JPanel pnlMain = new JPanel();

        pnlMain.setLayout(new BorderLayout(10,10));

        pnlMain.setPreferredSize(new Dimension(200, 400));

      lblWelcome =new JLabel("Welcome: "+"");


       btnRead = new RoundedButton("Show Courses", 16, new Color(123, 237, 76), Color.BLACK);
        
       RoundedButton btnDeregister = new RoundedButton("Deregister", 16, new Color(123, 237, 76), Color.BLACK);
        btnEnrol = new RoundedButton("Enrol in course", 16, new Color(123, 237, 76), Color.BLACK);
    
        btnMyEnrol = new RoundedButton("View my Enrolled Courses", 16, new Color(123, 237, 76), Color.BLACK);
  btnLogout = new RoundedButton("Log  out", 16, new Color(123, 237, 76), Color.BLACK);
       btnExit = new RoundedButton("Exit", 16, new Color(123, 237, 76), Color.BLACK);


       String []availableCourse = {
          "Tittle", "Course code"  
        };
        tabModel = new DefaultTableModel(availableCourse,0);
        tabEnrol = new JTable(tabModel);
        scrl = new JScrollPane(tabEnrol);

       String[] enrolledCourse = {
            "Student Number",  "Course code"
        };
     table = new DefaultTableModel(enrolledCourse,0);
      studentEnrol = new JTable(table);
      JScrollPane scroll = new JScrollPane(studentEnrol);

      
    
      
      
       btnRead.addActionListener(e -> new Thread(this::showAvailableCourses).start());
      btnEnrol.addActionListener(e -> {
          
        
           new Thread(() -> Enrol(studentName)).start();
                  });
      //btnLogout.addActionListener(e -> new Thread(this:: logout).start());
       btnExit.addActionListener(e -> new Thread(this:: Exit).start());
      btnMyEnrol.addActionListener(e -> {
          
       
           new Thread(() -> ShowMyCourses(studentName)).start();
                  });
       btnDeregister.addActionListener(e -> {
          
        
           new Thread(() -> unenrol(studentName)).start();
                  });
      
     JPanel pnlButtons = new JPanel();

        pnlButtons.setLayout(new BoxLayout(pnlButtons,BoxLayout.Y_AXIS));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(30,20,30,30));
        pnlButtons.setPreferredSize(new Dimension(250,400));
        
        pnlButtons.add(lblWelcome);

        pnlButtons.add(btnRead);
         pnlButtons.add(Box.createVerticalStrut(15));
        pnlButtons.add(btnEnrol);
        pnlButtons.add(Box.createVerticalStrut(15));
         pnlButtons.add(btnMyEnrol);
        pnlButtons.add(Box.createVerticalStrut(15));
     
        pnlButtons.add(Box.createVerticalStrut(15));
        pnlButtons.add(btnLogout);
        pnlButtons.add(Box.createVerticalStrut(15));
        pnlButtons.add(btnExit);
       
       

       
         JPanel pnlTables =new JPanel();
         pnlTables.setLayout(new BorderLayout());
         pnlTables.add(scrl,BorderLayout.NORTH);
         pnlTables.add(scroll,BorderLayout.CENTER);
         
        pnlMain.add(lblWelcome,BorderLayout.NORTH);
        
        pnlMain.add(pnlButtons,BorderLayout.WEST);
        pnlMain.add(pnlTables,BorderLayout.CENTER);

        
        GridBagConstraints gbc = new  GridBagConstraints();
         gbc.fill =  GridBagConstraints.BOTH;
         gbc.weightx = 1;
         gbc.weighty = 1;
         
         this.add(pnlMain,BorderLayout.CENTER);


         
         
    }
    
    public void showAvailableCourses(){
        new Thread(() -> {
        final String SERVER_HOST = "127.0.0.1"; 
        final int SERVER_PORT = 12349;          

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

           
            ShowAvailableCourses req = new ShowAvailableCourses();
            localOos.writeObject(req);
            localOos.flush();

            Object obj = localOis.readObject();

            List<Course> courses = new ArrayList<>();

            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o == null) continue;
                    try {
                   
                       
                        Class<?> cls = o.getClass();
                        String code = (String) cls.getMethod("getCourseDesc").invoke(o);
                        String desc = (String) cls.getMethod("getCourseCode").invoke(o);

                        Course course = new Course(desc, code);
                        courses.add(course);
                    } catch (NoSuchMethodException nsme) {
                        System.err.println("showCourses: missing getter on server object: " + nsme.getMessage());
                        nsme.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (obj != null) {
                System.err.println("showCourses - unexpected response type: " + obj.getClass().getName());
            }

            System.out.println("Client: extracted " + courses.size() + " courses from server");

            // Update table on EDT
            SwingUtilities.invokeLater(() -> updateCourseTable(tabEnrol, courses));

        } catch (EOFException eof) {
            System.err.println("showCourses: Connection closed by server (EOF).");
            eof.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Connection closed by server. Check server logs."));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Error loading courses: " + ex.getMessage()));
        }
    }, "ShowCourses-Thread").start();
}
    
    
    
    
    
    
    

public void updateCourseTable(JTable courseTable, List<Course> courses) {
        if (courseTable == null) return;

        DefaultTableModel model;
        if (courseTable.getModel() instanceof DefaultTableModel) {
            model = (DefaultTableModel) courseTable.getModel();
        } else {
            String[] cols = {"Student Number", "Name", "Surname", "Cell", "Email", "Password"};
            model = new DefaultTableModel(cols, 0);
            courseTable.setModel(model);
        }

        model.setRowCount(0);

        for (Course course : courses) {
            model.addRow(new Object[]{
                    course.getCourseDesc(),//changed swap
                    course.getCourseCode(),
                   
                   
            });
        }
}
        
public void updateMyCourseTable(JTable courseTable, List<ViewStudentCourses> courses) {
        if (courseTable == null) return;

        DefaultTableModel model;
        if (courseTable.getModel() instanceof DefaultTableModel) {
            model = (DefaultTableModel) courseTable.getModel();
        } else {
            String[] cols = {"Student Number", "Name", "Surname", "Cell", "Email", "Password"};
            model = new DefaultTableModel(cols, 0);
            courseTable.setModel(model);
        }

        model.setRowCount(0);

        for (ViewStudentCourses course : courses) {
            model.addRow(new Object[]{
                    course.getCourseCode(),
                    course.getDescription(),
                   
            });
        }
}
        
        
    
    
    
    public void Enrol(String studentName){
        
          int selectedRow = tabEnrol.getSelectedRow();
          
          if(selectedRow== -1){
              JOptionPane.showMessageDialog(null, "Please select Course");
              return;
          }
        

          
          String code = (String)tabEnrol.getValueAt(selectedRow,1); // change 

    final EnroleStudents req = new EnroleStudents(studentName, code);

    new Thread(() -> {
        final String SERVER_HOST = "127.0.0.1";
        final int SERVER_PORT = 12349;         

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             // create ObjectOutputStream first to avoid handshake deadlocks
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

            // send request
            localOos.writeObject(req);
            localOos.flush();

            // read response
            Object response = localOis.readObject();

            if (response instanceof String) {
                String recv = (String) response;
                if ("true".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() -> {
                        table.addRow(new Object[]{studentName, code});
                        
                        JOptionPane.showMessageDialog(this, "Successfully Added");
                    });
                } else if ("false".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Unable to Enrol"));
                } else {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Unexpected response from server: " + recv));
                }
            } else {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Response not a string (unexpected)."));
            }

        } catch (EOFException eof) {
            eof.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Connection closed by server before response (EOF)."));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Error while sending request: " + ex.getMessage()));
        }
    }, "AddCourse-Thread").start();
        
    }
    
 
    public void ShowMyCourses(final String  Studentnumber){
        new Thread(() -> {
        final String SERVER_HOST = "127.0.0.1"; 
        final int SERVER_PORT = 12349;          

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             // create OOS first to avoid handshake deadlocks
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

            // send ShowCourse request
            ShowMyEnroledCourse req = new ShowMyEnroledCourse(Studentnumber);
            localOos.writeObject(req);
            localOos.flush();

            // read response
            Object obj = localOis.readObject();

            List<ViewStudentCourses> courses = new ArrayList<>();

            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o == null) continue;
                    try {
                      
                        Class<?> cls = o.getClass();
                        String courseDescription = (String) cls.getMethod("getDescription").invoke(o);
                        String  code = (String) cls.getMethod("getCourseCode").invoke(o);

                        ViewStudentCourses course= new ViewStudentCourses(courseDescription, code);
                        courses.add(course);
                    } catch (NoSuchMethodException nsme) {
                        System.err.println("showCourses: missing getter on server object: " + nsme.getMessage());
                        nsme.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (obj != null) {
                System.err.println("showCourses - no courses enroled: " + obj.getClass().getName());
                JOptionPane.showMessageDialog(null,"No courses enroled ");
            }

            System.out.println("Client: extracted " + courses.size() + " courses from server");

         
            SwingUtilities.invokeLater(() -> updateMyCourseTable(studentEnrol,courses));

        } catch (EOFException eof) {
            System.err.println("showCourses: Connection closed by server (EOF).");
            eof.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Connection closed by server. Check server logs."));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Error loading courses: " + ex.getMessage()));
        }
    }, "ShowStudentCourses-Thread").start();
        
    }
    
    
    
    
    
    
    
    public void unenrol(String studentNumber){
       

 
        

    final UnenrolStudent req = new UnenrolStudent(studentNumber);

    int option = JOptionPane.showConfirmDialog(this, "Delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option != JOptionPane.YES_OPTION) return;

    new Thread(() -> {
        final String SERVER_HOST = "127.0.0.1";
        final int SERVER_PORT = 12349;          

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
           
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

            // send request
            localOos.writeObject(req);
            localOos.flush();

            // read response
            Object response = localOis.readObject();

            if (response instanceof String) {
                String recv = (String) response;
                if ("true".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Delete successful for student " + studentNumber);
                   
                    });
                } else if ("false".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Server reported: unenroll failed" + studentNumber));
                } else {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Unexpected response from server: " + recv));
                }
            } else {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Response not a string (unexpected)."));
            }

        } catch (EOFException eof) {
            eof.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Connection closed by server before response (EOF)."));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Error while sending delete request: " + ex.getMessage()));
        }
    }, "DeleteStudent-Thread").start();
}



   

    
    
    
    
    public void logout(){
        int choice = JOptionPane.showConfirmDialog(null,"Logout?"+JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION);
          
         

    if (choice == JOptionPane.YES_OPTION) {
        
        MainAppFrame mainappframe=new MainAppFrame();
        
         mainappframe.removeCurrentTab();
        
    }


    }
    public void Exit(){
        int choice = JOptionPane.showConfirmDialog(null,"Exit?"+JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION);
          
         if (choice == JOptionPane.YES_OPTION) {
        System.exit(0);
        
    }
    
}
}