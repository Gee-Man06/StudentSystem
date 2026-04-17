
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.GUI;





import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import za.ac.cput.shared.loginNetwork.LoginRequest;
//import za.ac.cput.shared.loginNetwork.LoginRequest;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import za.ac.cput.courseenrolmentsystem.AdminPanelPackage.AdminFramePanel;
import za.ac.cput.courseenrolmentsystem.MainAppFramePackage.MainAppFrame;

    
    
    

public class LoginFrame extends JPanel {

   private JPanel login;
    private JPanel label;
    private JPanel label2;
    private String btnAction; 
    JRadioButton studentButton, adminButton;
    private ButtonGroup roleGroup;
    JPanel rolePanel;
    JTextField usernameField;
    JPasswordField passwordField;
    JPanel usernamePanel, passwordPanel;

   
    private MainAppFrame mainApp; // reference to main app

    public LoginFrame() {
    }

    
    
    public LoginFrame(MainAppFrame mainApp) {
        this.mainApp = mainApp;
        initGui();
    }

    private void initGui() {
       
        this.setLayout(new GridBagLayout());

        login = new JPanel();
        login.setBackground(Color.LIGHT_GRAY);
        login.setOpaque(true);
        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
        login.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        login.setPreferredSize(new Dimension(320, 260));

        JLabel welcomeLabel = new JLabel("Welcome.");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.add(welcomeLabel);
        login.add(Box.createRigidArea(new Dimension(0, 10)));

        usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernamePanel.add(new JLabel("Username"));
        usernameField = new JTextField(15);
        usernamePanel.add(usernameField);
        login.add(usernamePanel);

        login.add(Box.createRigidArea(new Dimension(0, 8)));

        passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.add(new JLabel("Password"));
        passwordField = new JPasswordField(15);
        passwordPanel.add(passwordField);
        login.add(passwordPanel);

        login.add(Box.createRigidArea(new Dimension(0, 8)));

        rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rolePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminButton = new JRadioButton("Admin");
        studentButton = new JRadioButton("Student");
        roleGroup = new ButtonGroup();
        roleGroup.add(adminButton);
        roleGroup.add(studentButton);
        rolePanel.add(adminButton);
        rolePanel.add(studentButton);
        login.add(rolePanel);

        login.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton logButton = new JButton("Log");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(logButton);
        buttonPanel.add(cancelButton);
        login.add(buttonPanel);

        label = new JPanel();
        label.setPreferredSize(new Dimension(0, 0));
        label2 = new JPanel();
        label2.setPreferredSize(new Dimension(0, 0));

        add(label, new GridBagConstraints());
        add(label2, new GridBagConstraints());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(login, gbc);

      
        logButton.addActionListener(e -> new Thread(this::login).start());
        cancelButton.addActionListener(e -> System.exit(0));
    }
    
    
public void login() {
    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword());
    String role;

    if (studentButton.isSelected()) {
        role = "STUDENT";
    } else if (adminButton.isSelected()) {
        role = "ADMIN";
    } else {
        JOptionPane.showMessageDialog(null, "Select a role");
        return;
    }

    LoginRequest req = new LoginRequest("LOGIN", username, password, role);

    new Thread(() -> {
        Socket soc = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            // Connect to server
            soc = new Socket("127.0.0.1", 12349);
            oos = new ObjectOutputStream(soc.getOutputStream());
            ois = new ObjectInputStream(soc.getInputStream());

            // Send login request
            synchronized (oos) {
                oos.writeObject(req);
                oos.flush();
            }

            // Read login response
            Object loginResponse;
            synchronized (ois) {
                loginResponse = ois.readObject(); // "true" or "false"
            }

            if ("true".equals(loginResponse)) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null, "Successfully logged in as " + role)
                );

                if ("ADMIN".equalsIgnoreCase(role)) {
                    // Initialize admin panel and add tab
                    AdminFramePanel adminPanel = new AdminFramePanel(username, soc, oos, ois);
                    mainApp.addAdminTab(username, soc, oos, ois);

                   

                } else { 
                    mainApp.addStudentTab(username, soc, oos, ois);
                }

               
                soc = null;
                oos = null;
                ois = null;

            } else {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null, "Invalid username/password")
                );
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null, "Login error: " + ex.getMessage())
            );
        } finally {
         
            if (soc != null) {
                try { if (oos != null) oos.close(); } catch (Exception ignored) {}
                try { if (ois != null) ois.close(); } catch (Exception ignored) {}
                try { soc.close(); } catch (Exception ignored) {}
            }
        }
    }).start();
}

}





