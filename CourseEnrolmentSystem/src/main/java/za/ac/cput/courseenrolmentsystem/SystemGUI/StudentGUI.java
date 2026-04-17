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
import javax.swing.JFrame;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.ac.cput.courseenrolmentsystem.System.Student;





/**
 *
 * @author manga
 */
public class StudentGUI extends JFrame{
    private JLabel lblWelcome;
    private JLabel lblStudentNumber;
    private JTextField txtStudentNumber;
    private JLabel lblPassword;
    private JPasswordField txtPassword;
    private JButton btnLog;
    private JButton btnBack;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public StudentGUI(){
        JFrame main = new JFrame("MAIN");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(400,450);
        main.setLocationRelativeTo(null);
        main.setLayout(new BorderLayout());
        
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.Y_AXIS));
        pnlMain.setBackground(Color.LIGHT_GRAY);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlMain.setPreferredSize(new Dimension(320, 260));
        
        lblWelcome = new JLabel("<html><u> WELCOME STUDENT</u></html>");
//        lblWelcome.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblWelcome.setFont(new Font("Arial",Font.BOLD,14));
        lblWelcome.add(Box.createRigidArea(new Dimension(10, 50)));
        
        JPanel pnlUser = new JPanel();
        pnlUser.setLayout(new FlowLayout(FlowLayout. CENTER,5,5));
        pnlUser.setOpaque(false);
        lblStudentNumber = new JLabel("Student Number");
        txtStudentNumber = new JTextField(15);
        pnlUser.add(lblStudentNumber);
        pnlUser.add(txtStudentNumber);
        pnlUser.add(Box.createRigidArea(new Dimension(5, 20)));
        
        JPanel pnlPassword = new JPanel();
        pnlPassword.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        pnlPassword.setOpaque(false);
//        lblStudentNumber.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblPassword = new JLabel("Password");
        txtPassword = new JPasswordField(15);
        pnlPassword.add(lblPassword);
        pnlPassword.add(txtPassword);
       pnlPassword.add(Box.createRigidArea(new Dimension(5, 20)));
        
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        pnlButtons.setOpaque(false);
        btnLog = new JButton("Login");
        btnBack = new JButton("Back");
        pnlButtons.add(btnLog);
        pnlButtons.add(btnBack);
        
       btnLog.addActionListener(new ActionListener(){
        @Override
            public void actionPerformed(ActionEvent e) {
//                     loginUser();
      String studentNo = txtStudentNumber.getText().trim();
      String password = new String (txtPassword.getPassword()).trim();
      if (studentNo.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(null, 
            "Please fill in all fields before signing up!",
            "Missing Information",
            JOptionPane.WARNING_MESSAGE);
        return;
      }else{
          try {
              loginUser(studentNo,password);
          } catch (IOException ex) {
              Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
          } catch (ClassNotFoundException ex) {
              Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
        }
       });
        btnBack.addActionListener(new ActionListener() {
          @Override
         public void actionPerformed(ActionEvent e) {
             main.dispose();
             SystemEntry entry = new SystemEntry();
//             entry.add(main);
            }
             });

        
      pnlMain.add(lblWelcome);
        pnlMain.add(pnlUser);
        pnlMain.add(pnlPassword);
        pnlMain.add(pnlButtons);
       
        JPanel pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.add(pnlMain);
        
         main.add(pnlCenter, BorderLayout.CENTER);
               main.setVisible(true);
               
          }
         private void loginUser(String studentNo, String password) throws IOException, ClassNotFoundException {
         try {
         Socket server = new Socket("127.0.0.1", 6666);
         ObjectOutputStream   out = new ObjectOutputStream(server.getOutputStream());
         out.flush();
        ObjectInputStream in = new ObjectInputStream(server.getInputStream());
                     
        Student student = new Student(studentNo,password);   
//        String request = "from server";
        out.writeObject(student);
        out.flush();
        
        Object response =in.readObject();
        if(response instanceof Boolean result){
            if(result){
                JOptionPane.showMessageDialog(null,"Login succesfully"+" "+"\nWelcome :"+ response); 
                new StudentEnrol().setVisible(true);
                   dispose();
            }else{
                 JOptionPane.showMessageDialog(null,"Incorrect password. Please try again");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Student number:"+ response+"\tdoes not exist");
        } server.close();
        }catch(Exception io){
             JOptionPane.showMessageDialog(null,"Error connecting to server"+io.getMessage());
             io.printStackTrace();
         }
            }
        
public static void main(String[]args) {
        
        new StudentGUI();
        
}
}
