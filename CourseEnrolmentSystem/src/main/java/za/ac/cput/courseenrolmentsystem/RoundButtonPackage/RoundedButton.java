/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.courseenrolmentsystem.RoundButtonPackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Phiwa
 */
public class RoundedButton extends JButton {
    private final int radius;

    public RoundedButton(String text, int radius, Color bgColor, Color fgColor) {
        super(text);
        this.radius = radius;

        setFocusPainted(false);
        setContentAreaFilled(false);   // we'll paint it ourselves
        setOpaque(false);
        setBorder(new EmptyBorder(6, 12, 6, 12)); // padding
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        setBackground(bgColor);  
        setForeground(fgColor);  
        setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // optional thin border
        g2.setColor(new Color(0,0,0,40));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);

        g2.dispose();

        super.paintComponent(g); // draw text/icon
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}