/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.SystemGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import za.ac.cput.courseenrolmentsystem.System.Admin;

/**
 *
 * @author manga
 */
public class AdminLogin extends JFrame {
    private JLabel lblWelcome;
    private JLabel lblAdminNo;
    private JTextField txtAdminNo;
    private JLabel lblPassword;
    private JPasswordField txtPassword;
    private JButton btnLog;
    private JButton btnBack;
   
    public AdminLogin(){
         JFrame main = new JFrame("Admin Logiin");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(400,450);
        main.setLocationRelativeTo(null);
//        main.setLayout(new BorderLayout());
        
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.Y_AXIS));
        pnlMain.setBackground(Color.LIGHT_GRAY);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                
        pnlMain.setPreferredSize(new Dimension(320, 260));
        
        JPanel pnlWelcome = new JPanel();
       pnlWelcome.setLayout(new BorderLayout());
        lblWelcome = new JLabel("<html><u> WELCOME ADMIN</u></html>");
        lblWelcome.setFont(new Font("Arial",Font.BOLD,14));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
//        lblWelcome.add(Box.createRigidArea(new Dimension(10, 50)));
        pnlWelcome.add(lblWelcome,BorderLayout.NORTH);
        
        JPanel pnlUser = new JPanel();
//         pnlUser.setLayout(new GridLayout(1,2));
        pnlUser.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
//        pnlUser.setOpaque(false);
        lblAdminNo = new JLabel("Admin Number");
        txtAdminNo = new JTextField(10);
        pnlUser.add(lblAdminNo);
        pnlUser.add(txtAdminNo);
//        pnlUser.add(Box.createRigidArea(new Dimension(5, 20)));
//        
        JPanel pnlPassword = new JPanel();
//        pnlPassword.setLayout(new GridLayout(1,2));
        pnlPassword.setLayout(new FlowLayout(FlowLayout.CENTER,60,0));
//        pnlPassword.setOpaque(false);
//        lblStudentNumber.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblPassword = new JLabel("Password");
        txtPassword = new JPasswordField(10);
        pnlPassword.add(lblPassword);
        pnlPassword.add(txtPassword);
//       pnlPassword.add(Box.createRigidArea(new Dimension(5, 20)));
//        
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
//        pnlButtons.setOpaque(false);
        btnLog = new JButton("Login");
        btnBack = new JButton("Back");
        pnlButtons.add(btnLog);
        pnlButtons.add(btnBack);
//        
        btnLog.addActionListener(new ActionListener(){
             @Override
       public void actionPerformed(ActionEvent e) {
          String studentNo = txtAdminNo.getText().trim();
          String password = new String(txtPassword.getPassword()).trim();
//          String password = new String (txtPassword.getPassword()).trim();
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
                      Logger.getLogger(AdminLogin.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (ClassNotFoundException ex) {
                      Logger.getLogger(AdminLogin.class.getName()).log(Level.SEVERE, null, ex);
                  }
         
      }} 
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
        pnlMain.add(Box.createVerticalStrut(20));
        pnlMain.add(pnlUser);
//        pnlMain.add(txtAdminNo);
//        pnlMain.add(Box.createVerticalStrut(5));
//       pnlMain.add(Box.createVerticalStrut(5));
        pnlMain.add(pnlPassword);
//        pnlMain.add(txtPassword);
//        pnlMain.add(Box.createVerticalStrut(5));
        pnlMain.add(pnlButtons);
        
        JPanel pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.add(pnlMain);
        
//         main.add(pnlCenter, BorderLayout.CENTER);
        
        main.setContentPane(pnlCenter);
        main.setVisible(true);
    }
// private Socket connect() throws IOException{
//        return new Socket("127.0.0.1", 6666);
//    }
 private void loginUser(String adminNo, String password) throws IOException, ClassNotFoundException {
     try (
         Socket server = new Socket("127.0.0.1", 6666);
        ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
//        out.flush();
        ObjectInputStream in = new ObjectInputStream(server.getInputStream());
             ){        
             
        Admin admin = new Admin(adminNo,password);   
//        String request = "from server";
        out.writeObject(admin);
        out.flush();
        
        Object response =in.readObject();
        if(response instanceof Boolean result){
            if(result){
                JOptionPane.showMessageDialog(null,"Login succesfully"+" "+"Welcome :"+ response); 
            }else{
                 JOptionPane.showMessageDialog(null,"Incorrect password. Please try again");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Admin number:"+ response+"\tdoes not exist");
        }
        }catch(Exception io){
             JOptionPane.showMessageDialog(null,"Error connecting to server"+io.getMessage());
         }
           
    }
            public static void main(String[] args) {
        new AdminLogin();
    }
   
   
    
}
