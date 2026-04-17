/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.SystemGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.courseenrolmentsystem.System.Course;
import za.ac.cput.courseenrolmentsystem.SystemDAO.ServerDAO;
import za.ac.cput.courseenrolmentsystem.SystemGUI.*;
/**
 *
 * @author manga
 */
public class StudentEnrol extends JPanel {
    private JLabel lblWelcome;
    private JButton btnRead;
    private JLabel lblStudentNo;
    private JTextField txtStudentNo;
    private JComboBox comCourse;
    private JButton btnEnrol;
    private JButton btnMyEnrol;
    private JButton btnLogout;
    private JButton btnExit;
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

    public StudentEnrol(String studentID) {
        this.studentID = studentID;
    }
        
    public StudentEnrol(){
        main = new JFrame("STUDENT PORTAL");
       
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(600,400);
        main.setLocationRelativeTo(null);
        main.setLayout(new GridBagLayout());
        main.setResizable(true );
        main.setVisible(true);
        
        JPanel pnlMain = new JPanel();
//        pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.Y_AXIS));
        pnlMain.setLayout(new BorderLayout(10,10));
        pnlMain.setBackground(Color.LIGHT_GRAY);
        pnlMain.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        pnlMain.setPreferredSize(new Dimension(200, 400));
       
        
//        pnlMain.setSize(450,400;);
        
        lblWelcome =new JLabel("Welcome: "+"");
        btnRead = new JButton("Show available course");
        btnEnrol = new JButton("Enrol");
        btnMyEnrol = new    JButton("View my Enrolled Courses");
        btnLogout = new JButton("Log out");
        btnExit = new JButton("Exit");
        lblStudentNo = new JLabel("Student Number");
        txtStudentNo = new JTextField(20);
        String [] studentEnroll = {
          "Tittle", "Course code"  
        };
        String[] columns = {
            "Tittle",  "Course code"
        };
        tabModel = new DefaultTableModel(columns,0);
        
        tabModel.addRow(new Object[]{"Python","ICE262S"});
        tabModel.addRow(new Object[]{"Info Management","INM262S"});
        tabModel.addRow(new Object[]{" Management","INM25S"});
        tabModel.addRow(new Object[]{" MATHS","MATH152S"});
        tabEnrol = new JTable(tabModel);
        tabEnrol.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabEnrol.setFillsViewportHeight(true);
        scrl = new JScrollPane(tabEnrol);
        scrl.setBorder(BorderFactory.createTitledBorder("Available Courses"));
        scrl.setPreferredSize(new Dimension(600,400));
       
        table = new DefaultTableModel(studentEnroll,0);
        JTable studentEnrol = new JTable(table);
        studentEnrol.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scroll = new JScrollPane(studentEnrol);
        scroll.setBorder(BorderFactory.createTitledBorder("Enrolled courses"));
        scroll.setPreferredSize(new Dimension(600,400));
//        scrl.setSize(400,400);
        
//        String []comCourse = { "Courses available :",
//            "-Click course to enrol-"
//        };
//        JComboBox<String> comCode = new JComboBox<>(comCourse);
//        
//        btnRead.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                tableModel.setRowCount(0);
//                  ArrayList<Course> subjects = new ServerDAO().readStudent();
//                for (Course s : courses) {
//                tableModel.addRow(new Object[]{s.getSubCode(), s.getSubjectDescription()});
//                
//                comCode.addItem(s.getSubCode());}
//            
//        });
        btnRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                new Thread(() ->{
                 fetchCourses();
            
                }).start();
            }
         
        });
        btnEnrol.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                enrollInSelectedCourse();
            }
            
        });
       btnMyEnrol.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              String studentNo = txtStudentNo.getText();
              if(!studentNo.isEmpty()){
                   fetchStudentCourses();
            }else{
                  JOptionPane.showMessageDialog(null, "Enter student number first");
              }
            }
       });    

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
       int choice = JOptionPane.showConfirmDialog(
        null,
        "Are you sure you want to Log out?",
        "Confirm Sign out",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);
       if(choice == JOptionPane.YES_OPTION){
           ServerDAO server = new ServerDAO();
           server.signOut();
       }
           
//           server.setVisible(false);
            }
            
        });
        btnExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
           int choice = JOptionPane.showConfirmDialog(
        null,
        "Are you sure you want to exit?",
        "Confirm Exit",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);
    if(choice == JOptionPane.YES_OPTION){
     System.exit(0);
    }
            }
            
        });
        
        
        
         
         
//         pnlButtons.add(scrl);
//         pnlButtons.add(pnlCombo);
         
         
        
//        JPanel pnlBox = new JPanel();
//        pnlBox.add(comCode);
//        pnlTable.add(pnlBox);
        
        
         JPanel pnlButtons = new JPanel();
//        pnlDash.setLayout(new GridLayout(6,1,10,10));
        pnlButtons.setLayout(new BoxLayout(pnlButtons,BoxLayout.Y_AXIS));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(30,20,30,30));
        pnlButtons.setPreferredSize(new Dimension(250,400));
        
        
        Dimension size = new Dimension(190,30);
        btnRead.setMaximumSize(size);
        btnEnrol.setMaximumSize(size);
        btnMyEnrol.setMaximumSize(size);
        btnLogout.setMaximumSize(size);
        btnExit.setMaximumSize(size);
        
//        pnlButtons.add(lblWelcome);
//        pnlButtons.add(Box.createVerticalStrut(15));
        pnlButtons.add(btnRead);
        pnlButtons.add(Box.createVerticalStrut(15));
        pnlButtons.add(btnEnrol);
        pnlButtons.add(Box.createVerticalStrut(15));
       pnlButtons.add(btnMyEnrol);
        pnlButtons.add(Box.createVerticalStrut(15));
       pnlButtons.add(btnLogout);
        pnlButtons.add(Box.createVerticalStrut(15));
       pnlButtons.add(btnExit);
       
       JPanel pnlStudentNo = new JPanel();
       pnlStudentNo.setLayout(new FlowLayout());
       pnlStudentNo.add(lblStudentNo);
       pnlStudentNo.add(txtStudentNo);
        
//        pnlDash.setLocationRelativeTo(null);
        
         
         
         
         JPanel pnlTables =new JPanel();
         pnlTables.setLayout(new BorderLayout());
         pnlTables.add(scrl,BorderLayout.NORTH);
         pnlTables.add(scroll,BorderLayout.CENTER);
//         pnlMain.add(pnlDash,BorderLayout.EAST);
        
         pnlMain.add(lblWelcome,BorderLayout.NORTH);
        
          pnlMain.add(pnlButtons,BorderLayout.WEST);
//          pnlMain.add(scrl,BorderLayout.NORTH);
          pnlMain.add(pnlTables,BorderLayout.CENTER);
          pnlMain.add(pnlStudentNo,BorderLayout.SOUTH);
          
          GridBagConstraints gbc = new  GridBagConstraints();
          gbc.fill =  GridBagConstraints.BOTH;
          gbc.weightx = 1;
          gbc.weighty = 1;
//          main.add(lblWelcome,gbc);
          main.add(pnlMain,gbc);
         
//        main.add(pnlMain,new GridBagConstraints());
         
         main.setVisible(true);
        
    }
    private  void fetchCourses() {
     try (
             Socket client = new Socket("127.0.0.1", 6666);
             ObjectOutputStream  out = new ObjectOutputStream(client.getOutputStream());
//               out.flush();
               ObjectInputStream in = new ObjectInputStream(client.getInputStream());
             ){
            out.writeObject("GET_COURSES");
            out.flush();
            
            Object response = in.readObject();
            Object resp = in.readObject();
           
            if( "VIEW_COURSES".equals(response) || resp instanceof ArrayList){
                 ArrayList<Course> courses = (ArrayList<Course>) resp;
                 tabModel.setRowCount(0);
              if(courses.isEmpty()){
                  JOptionPane.showMessageDialog(null,"No courses found");
             }else{
                  for (Course c : courses) {
               System.out.println("Course recieved :"+c.getCourseCode()+"-"+c.getDescription());
                tabModel.addRow(new Object[]{
                    c.getCourseCode(),c.getDescription()
                });
                 }
            } 
//              client.close();
          }else{
               JOptionPane.showMessageDialog(null,"Invalid response from the server"); 
            }
    }   catch (IOException | ClassNotFoundException ex) {
        System.out.println("Error fetching courses");
            ex.printStackTrace();
        JOptionPane.showMessageDialog(null,"Error fetching courses :"+ex.getMessage()); 
        }}
    private void enrollInSelectedCourse() {
    int selectedRow = tabEnrol.getSelectedRow();
    String studentNo = txtStudentNo.getText();
    if (selectedRow == -1 || studentNo.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please select a course and enter student number to enroll.");
        return;
    }

    String courseCode = (String) tabModel.getValueAt(selectedRow, 1);
    try (Socket socket = new Socket("127.0.0.1", 6666);
         ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
         ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

        out.writeObject("ENROLL_STUDENT");
        out.writeObject(studentNo);
        out.writeObject(courseCode);
        out.flush();
   
        Object respond = in.readObject();
        Object response = in.readObject();
        if ("ENROL_STUDENT".equals(respond) && response instanceof Boolean success) {
            JOptionPane.showMessageDialog(null, "Enrolled successfully in ");
            if(success){
                JOptionPane.showMessageDialog(null, "Enrolled successfully in ");
            }else {
            JOptionPane.showMessageDialog(this, "Enrollment failed.");
        }
        } 

    } catch (IOException | ClassNotFoundException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error during enrollment.");
    }
}

    private  void fetchStudentCourses() {
       String studentNo = txtStudentNo.getText();
        try (
             Socket client = new Socket("127.0.0.1", 6666);
               ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
//               out.flush();
            ObjectInputStream   in = new ObjectInputStream(client.getInputStream());
               ){
            out.writeObject("GET_ENROLLED_COURSES");
            out.writeObject(studentNo);
            out.flush();
            
            Object response = in.readObject();
            Object respond  = in.readObject();
           
            if("ENROLLED_COURSERS".equals(response) || respond instanceof ArrayList){
                 ArrayList<Course> courses = (ArrayList<Course>) respond;
                 System.out.println("Courses received: " +courses.size());
               
                 if(courses.isEmpty()){
             JOptionPane.showMessageDialog(null,"no courses enrolled courses found"); 
//              client.close();
          }
                 table.setRowCount(0);
              for (Course c : courses) {
                table.addRow(new Object[]{
                    c.getCourseCode(),c.getDescription()
                });
                
   }}else{
                JOptionPane.showMessageDialog(null,"Invalid response from the server"); 
           
            }
    }   catch (IOException | ClassNotFoundException  ex) {
        System.out.println("Error fetching Enrolled courses"+ ex.getMessage());
            ex.printStackTrace();
            
        JOptionPane.showMessageDialog(null,"Error fetching Enrolled courses"+ex.getMessage()); 
        }
    }
    public static void main(String[] args) {
        new StudentEnrol();
    }
    
}
