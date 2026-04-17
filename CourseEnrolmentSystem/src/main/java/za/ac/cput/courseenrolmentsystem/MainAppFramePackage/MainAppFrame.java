package za.ac.cput.courseenrolmentsystem.MainAppFramePackage;
import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import za.ac.cput.courseenrolmentsystem.AdminPanelPackage.AdminFramePanel;
import za.ac.cput.courseenrolmentsystem.GUI.LoginFrame;
import za.ac.cput.courseenrolmentsystem.StudentPanelPackage.StudentFramePanel;

public class MainAppFrame extends JFrame {
    private final JTabbedPane tabbedPane = new JTabbedPane();

    public MainAppFrame() {
        setTitle("UNIVERSITY");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        LoginFrame loginPanel = new LoginFrame(this);
        tabbedPane.addTab("Login", loginPanel);

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Enusring that any tabs except the login tab (index 0).
     */
    private void removeAllUserTabs() {
        // we are removing from right to left to avoid index shifts
        for (int i = tabbedPane.getTabCount() - 1; i >= 1; i--) {
            tabbedPane.remove(i);
        }
    }

    /**
     * Add a single student tab — ensures only login + this tab exist.
     */
   public void addStudentTab(String studentNumber, Socket soc, ObjectOutputStream oos, ObjectInputStream ois) {
    SwingUtilities.invokeLater(() -> {
        removeAllUserTabs();
        StudentFramePanel studentPanel = new StudentFramePanel(studentNumber, soc, oos, ois);
        tabbedPane.addTab("Student: " + studentNumber, studentPanel);
        tabbedPane.setSelectedComponent(studentPanel);
    });
}

public void addAdminTab(String adminUsername, Socket soc, ObjectOutputStream oos, ObjectInputStream ois) {
    SwingUtilities.invokeLater(() -> {
        removeAllUserTabs();
        AdminFramePanel adminPanel = new AdminFramePanel(adminUsername, soc, oos, ois);
        tabbedPane.addTab("Admin: " + adminUsername, adminPanel);
        tabbedPane.setSelectedComponent(adminPanel);
    });
}  



public void removeCurrentTab() {
    int selectedIndex = tabbedPane.getSelectedIndex();
    if (selectedIndex > 0) { // to avoid removing login or base tab
        tabbedPane.removeTabAt(selectedIndex);
        tabbedPane.setSelectedIndex(selectedIndex - 1);
    }
}


}


