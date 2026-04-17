/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.AdminPanelPackage;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.courseenrolmentsystem.RoundButtonPackage.RoundedButton;
import za.ac.cput.courseenrolmentsystem.StudentNumberGenerators.StudentNumberGenerators;
import za.ac.cput.shared.AddCourseRequestPackage.AddCourseRequest;
import za.ac.cput.shared.AddStudentRequests.AddStudentRequests;
import za.ac.cput.shared.CoursePackage.Course;
import za.ac.cput.shared.DeleteCoursePackage.DeleteCourse;
import za.ac.cput.shared.DeleteStudentPackage.DeleteStudent;
import za.ac.cput.shared.ShowCoursePackage.ShowCourse;
import za.ac.cput.shared.ShowStudentPackage.ShowStudent;
//import za.ac.cput.shared.StudentPackage.Student;
import za.ac.cput.shared.StudentsPackage.Students;
//import za.ac.cput.shared.StudentPackage.Student;
import za.ac.cput.shared.UpdateStudentPackage.UpdateStudent;
import za.ac.cput.shared.updateCoursePackage.UpdateCourse;


/**
 *
 * @author Phiwa
 */





@SuppressWarnings("serial")
public final class AdminFramePanel extends JPanel {

    // UI fields (kept similar to your original)
    private JPanel westForm;
    private JPanel eastForm;
    private JPanel centerForm;
    private JPanel northForm; // for searching
    public  JTable northTable;
    public  JTable southTable;
    private DefaultTableModel northModel, southModel;
    private JScrollPane northPane, southPane;
    private RoundedSearchField searchField;

    // components
    private JButton manageBtn, manageBtnCourse;
    private JLabel nameLbl, surnameLbl, phoneLbl, gmailLbl;
    private JTextField nameFld, surnameFld, phoneFld, gmailFld;
    private JLabel maleLbl, femaleLbl;
    private JRadioButton maleBtn, femaleBtn;
    private ButtonGroup radioGroup;

    
    
    // Labels
private JLabel courseDescLbl;
private JLabel courseCodeLbl;

// Text fields
private JTextField courseDescFld;
private JTextField courseCodeFld;

// Buttons
private RoundedButton viewRowBtnCourse;
private RoundedButton saveBtnCourse;
private RoundedButton cancelBtnCourse;
private RoundedButton updateBtnCourse;
private RoundedButton deleteBtnCourse,showCourseBtn;
    
    
   // public static Long number = 200000L;
    
    private RoundedButton saveBtn;
    private RoundedButton cancelBtn;
    private RoundedButton updateBtn, showStudents;
    private RoundedButton deleteBtn, viewRowBtn;

    private JLabel passwordLbl;
    private JTextField passwordFld;

   // private static StudentNumberGenerator generator =
           // new StudentNumberGenerator(270000000L, 280000000L);

    // network — injected via constructor (persistent)
    private final Socket soc;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    /**
     * New constructor: receive the already-open socket + streams created by the login/main code.
     */






    public AdminFramePanel(String studentName, Socket soc, ObjectOutputStream oos, ObjectInputStream ois) {
        this.soc = soc;
        this.oos = oos;
        this.ois = ois;

        setLayout(new BorderLayout());

        // build UI
        westForm = west();
        this.add(westForm, BorderLayout.WEST);

        eastForm = east();
        this.add(eastForm, BorderLayout.EAST);

        centerForm = center();
        this.add(centerForm, BorderLayout.CENTER);

        northForm = north();
        this.add(northForm, BorderLayout.NORTH);

        // wire buttons (they are attached in west())
        // Immediately request students from server (reads from the existing ois)
         //showStudents();
         //showCourses();
    }

    
    
    
    
    
    
 
    /* ---------------------
       ADD STUDENT (uses injected oos/ois)
       --------------------- */
public void addStudent() {
    
    
    
    
    String name = nameFld.getText().trim();
    String password = passwordFld.getText().trim();
    String surname = surnameFld.getText().trim();
    String cell = phoneFld.getText().trim();
    String email = gmailFld.getText().trim();
    String action = "ADD";
  
    


    
       if(!cell.matches("^[0-9]{10}$")|| cell.equals(" ")){
                     JOptionPane.showMessageDialog(null,"Invalid Phone Number","Error", JOptionPane.ERROR_MESSAGE);
                 
                  return;
                 }
       
       
           if(!email.matches("[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")|| email.equals(" ")){
                  
                  JOptionPane.showMessageDialog(null,"Invalid Email","Error",JOptionPane.ERROR_MESSAGE);
             
              return;
              
           }
                if(!surname.matches("^[A-Za-z' ]+$")||surname.equals(" ")){
                  
                  JOptionPane.showMessageDialog(null,"Invalid Surname","Error",JOptionPane.INFORMATION_MESSAGE);
                  return;
              }
              
                if(!name.matches("^[A-Za-z' ]+$")|| name.equals(" ")){
                  
                  JOptionPane.showMessageDialog(null,"Invalid name","Error",JOptionPane.INFORMATION_MESSAGE);
                  return;
              }
           
            
                   if(!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@&!*#?])[A-Za-z\\d@&!*#?]{8,}$"
                             )|| password.equals(" ")){
                     JOptionPane.showMessageDialog(null,"Invalid password","Error", JOptionPane.ERROR_MESSAGE);
                 
                  return;
                 }
    
                 
     Long studentNumber = StudentNumberGenerators.getNextNumber();
    AddStudentRequests req = new AddStudentRequests(action, studentNumber, name, surname, cell, email, password);

    // Replace these with your actual server host/port
    final String SERVER_HOST = "127.0.0.1";
    final int SERVER_PORT = 12349;

    new Thread(() -> {
        // Each click uses a fresh socket + streams which are closed automatically
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

            // Send request (OOS created fresh for this socket)
            localOos.writeObject(req);
            localOos.flush();

            // Read response
            Object response = localOis.readObject();

            // Handle response on EDT
            if (response instanceof String) {
                String recv = (String) response;
                if ("true".equalsIgnoreCase(recv)) {
                    
                    // StudentNumberGenerator.number++;
                    SwingUtilities.invokeLater(() -> {
                        northModel.addRow(new Object[]{studentNumber, name, surname, cell, email, password});
                        nameFld.setText("");
                        surnameFld.setText("");
                        passwordFld.setText("");
                        gmailFld.setText("");
                        phoneFld.setText("");
                        JOptionPane.showMessageDialog(this, "Successfully Registered");
                    });
                } else if ("false".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Unable to register"));
                } else {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Unexpected response from server: " + recv));
                }
            } else {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Response not a string"));
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
    }).start();
}


  


//@SuppressWarnings("unchecked")
public void showStudents() {
   
    new Thread(() -> {
        final String SERVER_HOST = "127.0.0.1"; 
        final int SERVER_PORT = 12349;          

       
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

            // send ShowStudent request
            ShowStudent req = new ShowStudent();
            localOos.writeObject(req);
            localOos.flush();

        
            Object obj = localOis.readObject();

            List<Students> students = new ArrayList<>();

            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o == null) continue;
                    try {
                       
                        Class<?> cls = o.getClass();
                        Long sn = (Long) cls.getMethod("getStudentNumber").invoke(o);
                        String name = (String) cls.getMethod("getName").invoke(o);
                        String surname = (String) cls.getMethod("getSurname").invoke(o);
                        String cell = (String) cls.getMethod("getCell").invoke(o);
                        String email = (String) cls.getMethod("getEmail").invoke(o);
                        String password = (String) cls.getMethod("getPassword").invoke(o);

                        Students s = new Students(sn, name, surname, cell, email, password);
                        students.add(s);
                    } catch (NoSuchMethodException nsme) {
                        System.err.println("showStudents: server object missing expected getter: " + nsme.getMessage());
                        nsme.printStackTrace();
                    } catch (Exception e) {
                   
                        e.printStackTrace();
                    }
                }
            } else if (obj != null) {
                System.err.println("showStudents - unexpected response type: " + obj.getClass().getName());
            }

            System.out.println("Client: extracted " + students.size() + " students from server");

      
            SwingUtilities.invokeLater(() -> {
                updateStudentTable(northTable, students);
            });

        } catch (EOFException eof) {
            System.err.println("showStudents: Connection closed by server (EOF).");
            eof.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Connection closed by server. Check server logs."));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Error loading students: " + ex.getMessage()));
        }
    }, "ShowStudents-Thread").start();
}










@SuppressWarnings("unchecked")
public void showCourses() {
    new Thread(() -> {
        final String SERVER_HOST = "127.0.0.1"; 
        final int SERVER_PORT = 12349;          

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
           
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

            ShowCourse req = new ShowCourse();
            localOos.writeObject(req);
            localOos.flush();

          
            Object obj = localOis.readObject();

            List<Course> courses = new ArrayList<>();

            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o == null) continue;
                    try {
                       
                        Class<?> cls = o.getClass();
                        String code = (String) cls.getMethod("getCourseCode").invoke(o);
                        String desc = (String) cls.getMethod("getCourseDesc").invoke(o);

                        Course course = new Course(code, desc);
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

    
            SwingUtilities.invokeLater(() -> updateCourseTable(southTable, courses));

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













    

@SuppressWarnings("unchecked")
public void updateStudent() {
    int selectedRow = northTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row.");
        return;
    }

    Object firstColumnValue = northTable.getValueAt(selectedRow, 0);
    if (firstColumnValue == null) {
        JOptionPane.showMessageDialog(this, "Selected row's first column is empty.");
        return;
    }

    final Long studentNumber;
    try {
        if (firstColumnValue instanceof Number) {
            studentNumber = ((Number) firstColumnValue).longValue();
        } else {
            studentNumber = Long.parseLong(firstColumnValue.toString().trim());
        }
    } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(this, "Cannot parse student number from the selected row: " + firstColumnValue);
        return;
    }

    final String updateName = nameFld.getText().trim();
    final String updateSurname = surnameFld.getText().trim();
    final String updateCell = phoneFld.getText().trim();
    final String updateGmail = gmailFld.getText().trim();
    final String updatePassword = passwordFld.getText().trim();
    
    
       if(!updateCell.matches("^[0-9]{10}$")|| updateCell.equals(" ")){
                     JOptionPane.showMessageDialog(null,"Invalid Phone Number","Error", JOptionPane.ERROR_MESSAGE);
                 
                  return;
                 }
       
       
           if(!updateGmail.matches("[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")|| updateGmail.equals(" ")){
                  
                  JOptionPane.showMessageDialog(null,"Invalid Email","Error",JOptionPane.ERROR_MESSAGE);
             
              return;
              
           }
                if(!updateSurname.matches("^[A-Za-z' ]+$")||updateSurname.equals(" ")){
                  
                  JOptionPane.showMessageDialog(null,"Invalid Surname","Error",JOptionPane.INFORMATION_MESSAGE);
                  return;
              }
              
                if(!updateName.matches("^[A-Za-z' ]+$")|| updateName.equals(" ")){
                  
                  JOptionPane.showMessageDialog(null,"Invalid name","Error",JOptionPane.INFORMATION_MESSAGE);
                  return;
              }
    
    
                
                   if(!updatePassword.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@&!*#?])[A-Za-z\\d@&!*#?]{8,}$"
                             )|| updatePassword.equals(" ")){
                     JOptionPane.showMessageDialog(null,"Invalid password","Error", JOptionPane.ERROR_MESSAGE);
                 
                  return;
                 }
       
                
                
                
                
                
                
                
                
                
                
                
                
                

    final UpdateStudent req = new UpdateStudent(studentNumber, updateName, updateSurname, updateCell, updateGmail, updatePassword);

    int option = JOptionPane.showConfirmDialog(this, "Update?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option != JOptionPane.YES_OPTION) return;

  
    new Thread(() -> {
        final String SERVER_HOST = "127.0.0.1"; 

        final int SERVER_PORT = 12349;          
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
           
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

            localOos.writeObject(req);
            localOos.flush();

            Object response = localOis.readObject();

            if (response instanceof String) {
                String recv = (String) response;
                if ("true".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Update successful for student " + studentNumber);
                       
                        nameFld.setText("");
                        surnameFld.setText("");
                        phoneFld.setText("");
                        gmailFld.setText("");
                        passwordFld.setText("");
                        
                    });
                } else if ("false".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Server reported: update failed for student " + studentNumber));
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
                    JOptionPane.showMessageDialog(this, "Error while sending update request: " + ex.getMessage()));
        }
    }, "UpdateStudent-Thread").start();
}

    
    
    
    
    
    


public void deleteStudent() {
    int selectedRow = northTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row.");
        return;
    }

    Object firstColumnValue = northTable.getValueAt(selectedRow, 0);
    if (firstColumnValue == null) {
        JOptionPane.showMessageDialog(this, "Selected row's first column is empty.");
        return;
    }

    final Long studentNumber;
    try {
        if (firstColumnValue instanceof Number) {
            studentNumber = ((Number) firstColumnValue).longValue();
        } else {
            studentNumber = Long.parseLong(firstColumnValue.toString().trim());
        }
    } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(this, "Cannot parse student number from the selected row: " + firstColumnValue);
        return;
    }

    final DeleteStudent req = new DeleteStudent(studentNumber);

    int option = JOptionPane.showConfirmDialog(this, "Delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option != JOptionPane.YES_OPTION) return;

    new Thread(() -> {
        final String SERVER_HOST = "127.0.0.1"; 
        final int SERVER_PORT = 12349;          

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
           
             ObjectOutputStream localOos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream localOis = new ObjectInputStream(socket.getInputStream())) {

        
            localOos.writeObject(req);
            localOos.flush();

         
            Object response = localOis.readObject();

            if (response instanceof String) {
                String recv = (String) response;
                if ("true".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Delete successful for student " + studentNumber);
                    
                        nameFld.setText("");
                        surnameFld.setText("");
                        phoneFld.setText("");
                        gmailFld.setText("");
                        passwordFld.setText("");
                      
                    });
                } else if ("false".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Server reported: delete failed for student " + studentNumber));
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





    public void getSelectedRow() {
        int selectedRow = northTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row.");
            return;
        }

        String name = String.valueOf(northTable.getValueAt(selectedRow, 1));
        String surname = String.valueOf(northTable.getValueAt(selectedRow, 2));
        String cell = String.valueOf(northTable.getValueAt(selectedRow, 3));
        String gmail = String.valueOf(northTable.getValueAt(selectedRow, 4));
        String password = String.valueOf(northTable.getValueAt(selectedRow, 5));

        nameFld.setText(name);
        surnameFld.setText(surname);
        phoneFld.setText(cell);
        gmailFld.setText(gmail);
        passwordFld.setText(password);
    }
    
    
    
      public void getSelectedRowCourse() {
          
          courseCodeFld.setEditable(false);
        int selectedRow = southTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row.");
            return;
        }
        

        String courseCode = String.valueOf(southTable.getValueAt(selectedRow, 0));
        String courseDescription = String.valueOf(southTable.getValueAt(selectedRow, 1));
       
        courseCodeFld.setText(courseCode);
        courseDescFld.setText(courseDescription);
        
       
    }
    
    
    
    

    /* ---------------------
       Table helpers
       --------------------- 
                        */



    private void clearStudentTable(JTable studentTable) {
        if (studentTable == null) return;
        if (studentTable.getModel() instanceof DefaultTableModel) {
            ((DefaultTableModel) studentTable.getModel()).setRowCount(0);
        } else {
            studentTable.setModel(new DefaultTableModel(
                    new String[]{"Student Number", "Name", "Surname", "Cell", "Email", "Password"}, 0
            ));
        }
    }

    public void updateStudentTable(JTable studentTable, List<Students> students) {
        if (studentTable == null) return;

        DefaultTableModel model;
        if (studentTable.getModel() instanceof DefaultTableModel) {
            model = (DefaultTableModel) studentTable.getModel();
        } else {
            String[] cols = {"Student Number", "Name", "Surname", "Cell", "Email", "Password"};
            model = new DefaultTableModel(cols, 0);
            studentTable.setModel(model);
        }

        model.setRowCount(0);

        for (Students s : students) {
            model.addRow(new Object[]{
                    s.getStudentNumber(),
                    s.getName(),
                    s.getSurname(),
                    s.getCell(),
                    s.getEmail(),
                    s.getPassword()
            });
        }
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
                    course.getCourseCode(),
                    course.getCourseDesc(),
                   
            });
        }
    }
    
    
    
    
    
    
    
    
    
    
    private void Cancel(){
    // clear fields
        nameFld.setText("");
        surnameFld.setText("");
        phoneFld.setText("");
        gmailFld.setText("");
        passwordFld.setText("");
    
    }
    
    
     private void CancelCourse(){
    // clear fields
    
         courseCodeFld.setEditable(true);
        courseCodeFld.setText("");
        courseDescFld.setText("");
       
    
    }
    

    /* ---------------------
       UI building methods (north, west, center, east)
       Buttons wired to the class methods.
       --------------------- 
            */




    public final JPanel north() {
        JPanel panel = new JPanel(null);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1400, 50));
        JPanel searchPanel = createSearchBarPanel();
        searchPanel.setBounds(650, 20, 300, 30);
        panel.add(searchPanel);
        return panel;
    }

    public JPanel west() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(345, 700));
        TitledBorder border = BorderFactory.createTitledBorder("Add New Student");
        border.setTitleColor(Color.GREEN);
        panel.setBorder(border);

        if (manageBtn == null) manageBtn = new JButton("Student Registration");
        manageBtn.setFocusPainted(false);
        manageBtn.setContentAreaFilled(false);
        manageBtn.setBorder(new javax.swing.border.LineBorder(Color.MAGENTA, 2, true));
        manageBtn.setBounds(63, 160, 250, 30);
        panel.add(manageBtn);

        if (nameLbl == null) nameLbl = new JLabel("FirstName");
        nameLbl.setBounds(10, 215, 100, 25);
        panel.add(nameLbl);

        if (nameFld == null) nameFld = new JTextField();
        nameFld.setBounds(72, 215, 210, 25);
        panel.add(nameFld);



        
        if (surnameLbl == null) surnameLbl = new JLabel("Surname");
        surnameLbl.setBounds(10,250,100,25);
        panel.add(surnameLbl);
        panel.add(surnameLbl);
        if (surnameFld == null) surnameFld = new JTextField();
        surnameFld.setBounds(72, 250, 210, 25);
        panel.add(surnameFld);

        
        if(phoneLbl == null) phoneLbl = new JLabel("Cell");
        phoneLbl.setBounds(10,285,100,25);
        panel.add(phoneLbl);
        if (phoneFld == null) phoneFld = new JTextField();
        phoneFld.setBounds(72, 285, 210, 25);
        panel.add(phoneFld);

        
        
          if(gmailLbl == null) gmailLbl= new JLabel("Email");
        gmailLbl.setBounds(10,320,100,25);
        panel.add(gmailLbl);
        
        
        if (gmailFld == null) gmailFld = new JTextField();
        gmailFld.setBounds(72, 320, 210, 25);
        panel.add(gmailFld);

        if (passwordLbl == null) passwordLbl = new JLabel("Password");
        passwordLbl.setBounds(10, 365, 100, 25);
        panel.add(passwordLbl);

        if (passwordFld == null) passwordFld = new JTextField();
        passwordFld.setBounds(72, 365, 210, 25);
        panel.add(passwordFld);

        
        
        
        
        // -- Gender radio buttons (original coords you provided) --
if (maleLbl == null) maleLbl = new JLabel("Male");
maleLbl.setBounds(10, 447, 50, 25);
panel.add(maleLbl);
if (maleBtn == null) maleBtn = new JRadioButton();
maleBtn.setBounds(62, 447, 20, 25);
maleBtn.setBackground(panel.getBackground());
maleBtn.setBorderPainted(false);
panel.add(maleBtn);
if (femaleLbl == null) femaleLbl = new JLabel("Female");
femaleLbl.setBounds(100, 447, 50, 25);
panel.add(femaleLbl);
if (femaleBtn == null) femaleBtn = new JRadioButton();
femaleBtn.setBounds(160, 447, 20, 25);
femaleBtn.setBackground(panel.getBackground());
femaleBtn.setBorderPainted(false);
panel.add(femaleBtn);
// radio buttons are grouped
if (radioGroup == null) radioGroup = new ButtonGroup();
radioGroup.add(maleBtn);
radioGroup.add(femaleBtn);

        
        
        
        
        
        
        
        
        
          if (showStudents== null) showStudents = new RoundedButton("Show Students", 16, new Color(123, 237, 76), Color.BLACK);
        showStudents.setBounds(170, 100, 120, 35);
       panel.add(showStudents);

        if (viewRowBtn == null) viewRowBtn = new RoundedButton("View Details", 16, new Color(123, 237, 76), Color.BLACK);
        viewRowBtn.setBounds(150, 399, 120, 35);
        panel.add(viewRowBtn);

        if (saveBtn == null) saveBtn = new RoundedButton("SAVE", 16, new Color(123, 237, 76), Color.BLACK);
        saveBtn.setBounds(1, 500, 120, 35);
        panel.add(saveBtn);

        if (cancelBtn == null) cancelBtn = new RoundedButton("CANCEL", 16, new Color(123, 237, 76), Color.BLACK);
        cancelBtn.setBounds(147, 500, 120, 35);
        panel.add(cancelBtn);

        if (updateBtn == null) updateBtn = new RoundedButton("UPDATE", 16, new Color(123, 237, 76), Color.BLACK);
        updateBtn.setBounds(1, 560, 120, 35);
        panel.add(updateBtn);

        if (deleteBtn == null) deleteBtn = new RoundedButton("DELETE", 16, new Color(123, 237, 76), Color.BLACK);
        deleteBtn.setBounds(147, 560, 120, 35);
        panel.add(deleteBtn);

        // listeners
        saveBtn.addActionListener(e -> new Thread(this::addStudent).start());
        updateBtn.addActionListener(e -> new Thread(this::updateStudent).start());
        deleteBtn.addActionListener(e -> new Thread(this::deleteStudent).start());
        viewRowBtn.addActionListener(e -> new Thread(this::getSelectedRow).start());
        cancelBtn.addActionListener(e -> new Thread(this::Cancel).start());
        showStudents.addActionListener(e -> new Thread(this::showStudents).start());

        return panel;
    }

    public final JPanel center() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        JPanel centerNorthChild = new JPanel();
        TitledBorder northBorder = BorderFactory.createTitledBorder("Student Table");
        northBorder.setTitleColor(Color.GREEN);
        centerNorthChild.setBorder(northBorder);
        centerNorthChild.setPreferredSize(new Dimension(0, 350));
        northModel = new DefaultTableModel(new String[]{"Student Number", "Name", "Surname", "cell", "email", "password"}, 0);
        northTable = new JTable(northModel);
        northTable.setDefaultEditor(Object.class, null);

        northPane = new JScrollPane(northTable);
        centerNorthChild.setLayout(new BorderLayout());
        centerNorthChild.add(northPane);

        panel.add(centerNorthChild, BorderLayout.NORTH);

        JPanel centerSouthChild = new JPanel();
        centerSouthChild.setPreferredSize(new Dimension(0, 350));
        TitledBorder southBorder = BorderFactory.createTitledBorder("Course Table");
        southBorder.setTitleColor(Color.green);
        centerSouthChild.setBorder(southBorder);
        southModel = new DefaultTableModel(new String[]{"Course Code", "Description"}, 0);
        southTable = new JTable(southModel);
        southTable.setDefaultEditor(Object.class, null);

        southPane = new JScrollPane(southTable);
        centerSouthChild.setLayout(new BorderLayout());
        centerSouthChild.add(southPane, BorderLayout.CENTER);

        panel.add(centerSouthChild, BorderLayout.SOUTH);
        return panel;
    }
    
    
    
    
    
    
    
    
    
    
public final JPanel east() {
    JPanel panel2 = new JPanel();
    panel2.setLayout(null); // Using absolute positioning like your original code
    panel2.setPreferredSize(new Dimension(345, 0));

    TitledBorder border = BorderFactory.createTitledBorder("Add Course");
    border.setTitleColor(Color.green);
    panel2.setBorder(border);

    // Manage/Add New Course Button
    if (manageBtnCourse == null) manageBtnCourse = new JButton("Add New Course");
    manageBtnCourse.setFocusPainted(false);
    manageBtnCourse.setContentAreaFilled(false);
    manageBtnCourse.setBorder(new javax.swing.border.LineBorder(Color.MAGENTA, 2, true));
    manageBtnCourse.setBounds(63, 160, 250, 30);
    panel2.add(manageBtnCourse);

    // Course Description Label and Field
    if (courseDescLbl == null) courseDescLbl = new JLabel("Course Description");
    courseDescLbl.setBounds(10, 215, 100, 25);
    panel2.add(courseDescLbl);

    if (courseDescFld == null) courseDescFld = new JTextField();
    courseDescFld.setBounds(120, 215, 162, 25); // Adjusted width to fit panel nicely
    panel2.add(courseDescFld);

    // Course Code Label and Field
    if (courseCodeLbl == null) courseCodeLbl = new JLabel("Course Code");
    courseCodeLbl.setBounds(10, 250, 100, 25);
    panel2.add(courseCodeLbl);

    if (courseCodeFld == null) courseCodeFld = new JTextField();
    courseCodeFld.setBounds(120, 250, 162, 25);
    panel2.add(courseCodeFld);

    // Buttons
    
       if (showCourseBtn== null) showCourseBtn = new RoundedButton("Show Courses", 16, new Color(123, 237, 76), Color.BLACK);
        showCourseBtn.setBounds(170, 100, 120, 35);
        panel2.add(showCourseBtn);
    
    if (viewRowBtnCourse == null) viewRowBtnCourse = new RoundedButton("View Details", 16, new Color(123, 237, 76), Color.BLACK);
    viewRowBtnCourse.setBounds(150, 399, 120, 35);
    panel2.add(viewRowBtnCourse);

    if (saveBtnCourse == null) saveBtnCourse = new RoundedButton("SAVE", 16, new Color(123, 237, 76), Color.BLACK);
    saveBtnCourse.setBounds(4, 500, 120, 35);
    panel2.add(saveBtnCourse);

    if (cancelBtnCourse == null) cancelBtnCourse = new RoundedButton("CANCEL", 16, new Color(123, 237, 76), Color.BLACK);
    cancelBtnCourse.setBounds(147, 500, 120, 35);
    panel2.add(cancelBtnCourse);

    if (updateBtnCourse == null) updateBtnCourse = new RoundedButton("UPDATE", 16, new Color(123, 237, 76), Color.BLACK);
    updateBtnCourse.setBounds(4, 560, 120, 35);
    panel2.add(updateBtnCourse);

    if (deleteBtnCourse == null) deleteBtnCourse = new RoundedButton("DELETE", 16, new Color(123, 237, 76), Color.BLACK);
    deleteBtnCourse.setBounds(147, 560, 120, 35);
    panel2.add(deleteBtnCourse);

            // listeners
        saveBtnCourse.addActionListener(e -> new Thread(this::addCourse).start());
        updateBtnCourse.addActionListener(e -> new Thread(this::updateCourse).start());
       
        deleteBtnCourse.addActionListener(e -> new Thread(this::deleteCourse).start());
        viewRowBtnCourse.addActionListener(e -> new Thread(this::getSelectedRowCourse).start());
        cancelBtnCourse.addActionListener(e -> new Thread(this::CancelCourse).start());
        
        showCourseBtn.addActionListener(e -> new Thread(this::showCourses).start());
    
    return panel2;
}





  


public void addCourse() {
    final String courseDesc = courseDescFld.getText().trim();
    final String courseCode = courseCodeFld.getText().trim();

    if (courseDesc.isEmpty() || courseCode.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Course description and code are required.");
        return;
    }
    
     if (courseCode.isEmpty() || courseCode.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Course description and code are required.");
        return;
    }
    
    
      if(!courseCode.matches("^[A-Za-z0-9]+$")){
                  
                  JOptionPane.showMessageDialog(null,"Course Description must contain[letters&numbers]","Error",JOptionPane.INFORMATION_MESSAGE);
                  return;
              }
              
    
    
    

    final AddCourseRequest req = new AddCourseRequest(courseCode, courseDesc);

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
                        southModel.addRow(new Object[]{courseCode,courseDesc});
                        courseDescFld.setText("");
                        courseCodeFld.setText("");
                        JOptionPane.showMessageDialog(this, "Successfully Added");
                    });
                } else if ("false".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Unable to Add Course"));
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





    

@SuppressWarnings("unchecked")
public void updateCourse() {
    int selectedRow = southTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row.");
        return;
    }

    Object firstColumnValue = southTable.getValueAt(selectedRow, 0);
    if (firstColumnValue == null) {
        JOptionPane.showMessageDialog(this, "Selected row's first column is empty.");
        return;
    }

    final String originalCourseCode;
    try {
        if (firstColumnValue instanceof String) {
            originalCourseCode = ((String) firstColumnValue).trim();
        } else {
            originalCourseCode = firstColumnValue.toString().trim();
        }
        if (originalCourseCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selected course code is empty.");
            return;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Cannot read course code from selected row: " + firstColumnValue);
        return;
    }

    final String updateCourseDsc = courseDescFld.getText().trim();
    final String updateCourseCode = courseCodeFld.getText().trim();

    
    if (updateCourseCode.isEmpty()) {
      
        JOptionPane.showMessageDialog(this, "Please enter a course code (or fill the field if you want to change it).");
        return;
    }

    final UpdateCourse req = new UpdateCourse(updateCourseCode, updateCourseDsc);

    int option = JOptionPane.showConfirmDialog(this, "Update?", "Confirmation", JOptionPane.YES_NO_OPTION);
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
                        JOptionPane.showMessageDialog(this, "Update successful for Course " + updateCourseCode);
                       
                        courseDescFld.setText("");
                        courseCodeFld.setText("");
                      
                    });
                } else if ("false".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Server reported: update failed for course " + updateCourseCode));
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
                    JOptionPane.showMessageDialog(this, "Error while sending update request: " + ex.getMessage()));
        }
    }, "UpdateCourse-Thread").start();
}




    
    




public void deleteCourse() {
    int selectedRow = southTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row.");
        return;
    }

    Object firstColumnValue = southTable.getValueAt(selectedRow, 0);
    if (firstColumnValue == null) {
        JOptionPane.showMessageDialog(this, "Selected row's first column is empty.");
        return;
    }

    final String code;
    try {
        if (firstColumnValue instanceof String) {
            code = ((String) firstColumnValue).trim();
        } else {
      
            code = firstColumnValue.toString().trim();
        }
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selected course code is empty.");
            return;
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error reading course code from row: " + ex.getMessage());
        return;
    }

    int option = JOptionPane.showConfirmDialog(this, "Delete course " + code + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option != JOptionPane.YES_OPTION) return;

    final DeleteCourse req = new DeleteCourse(code);

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
                        JOptionPane.showMessageDialog(this, "Deleted successfully: " + code);
                        // clear input fields
                        courseDescFld.setText("");
                        courseCodeFld.setText("");
                      
                    });
                } else if ("false".equalsIgnoreCase(recv)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Delete failed for course: " + code));
                } else {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "Unexpected server response: " + recv));
                }
            } else {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Invalid response type from server."));
            }

        } catch (EOFException eof) {
            eof.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Connection closed by server before response."));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Error during delete request: " + ex.getMessage()));
        }
    }, "DeleteCourse-Thread").start();
}










    // RoundedSearchField (keeps your custom class behavior)
    class RoundedSearchField extends JTextField {
        private final String placeholder;
        private final int radius;

        public RoundedSearchField(int radius, String placeholder) {
            super();
            this.placeholder = placeholder;
            this.radius = radius;
            setOpaque(false);
            setBorder(new EmptyBorder(5, 10, 5, 10));

            getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
                @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
                @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
            });
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            if (getText().isEmpty()) {
                g2.setColor(Color.GRAY);
                FontMetrics fm = g2.getFontMetrics();
                int textHeight = fm.getAscent();
                g2.drawString(placeholder, 10, (getHeight() + textHeight) / 2 - 2);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    
    }
    
     private JPanel createSearchBarPanel() {
        searchField = new RoundedSearchField(20, "Search");
        searchField.setPreferredSize(new Dimension(400, 35));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(searchField, BorderLayout.CENTER);
        return panel;
    
}
    
   /* static class StudentNumberGenerator {
        private long next;
        private final long max;
        StudentNumberGenerator(long start, long max) { this.next = start; this.max = max; }
        public synchronized long getNextStudentNumber() {
            if (next >= max) next = 251000000L;
            return next++;
        }
    }*/
}



