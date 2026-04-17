/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.SystemGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;

/**
 *
 * @author manga
 */
public class SystemEntry extends JFrame {
    private JLabel lblWelcome;
    private JButton btnStudent;
    private JButton btnAdmin;
    private JPanel pnlTop;
     private JPanel pnlButtons;
    
    public SystemEntry(){
        JFrame mainframe = new JFrame("Main Frame");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(400,450);
       mainframe.setLocationRelativeTo(null);
       mainframe.setLayout(new BorderLayout());
        
       JPanel pnlSystem = new JPanel();
       pnlSystem.setLayout(new BoxLayout(pnlSystem,BoxLayout.Y_AXIS));
       pnlSystem.setBackground(Color.LIGHT_GRAY);
       pnlSystem.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlSystem.setPreferredSize(new Dimension(320, 260));
       
        lblWelcome = new JLabel("<html><u> WELCOME </u></html>");
        lblWelcome.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblWelcome.setFont(new Font("Arial",Font.BOLD,14));
       
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new BoxLayout(pnlButtons,BoxLayout.Y_AXIS));
        pnlButtons.setOpaque(false);
        btnStudent  = new JButton("Login as Student");
        btnAdmin = new JButton("Login as Admin");
       
         btnStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
       
        btnAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
       pnlButtons.add(btnStudent);
       pnlButtons.add(Box.createVerticalStrut(15));
//       pnlButtons.add(Box.createVerticalStrut(5));
       pnlButtons.add(btnAdmin);
       
        
       pnlSystem.add(lblWelcome);
       pnlSystem.add(Box.createRigidArea(new Dimension(10, 50)));
       pnlSystem.add(pnlButtons);
       pnlSystem.add(Box.createRigidArea(new Dimension(0, 5)));
      
       
       btnStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            StudentGUI student = new StudentGUI();  
            student.show();
            dispose();
              }
          });
       
        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            AdminLogin admin = new AdminLogin();  
            admin.show();
            dispose();
              }
          });
        
        JPanel pnlMiddle = new JPanel(new GridBagLayout());
        pnlMiddle.add(pnlSystem);
        
        mainframe.add(pnlMiddle, BorderLayout.CENTER);
        
        
        mainframe.setVisible(true);
          
    }
   
}
