package com.company.UIWindows;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.*;
import java.time.format.DateTimeParseException;

public class UserInfoWindow {
    private JFrame userInfoWindow;
    private JButton userInfoSubmit;

    private JTextField userBirthDateField;
    private JTextField userNameField;

    public UserInfoWindow(boolean visibleOnStart) {
        //WINDOW
        userInfoWindow = new JFrame("user info");
        userInfoWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInfoWindow.setSize(400, 150);
        userInfoWindow.setLocationRelativeTo(null);
        if(visibleOnStart) userInfoWindow.setVisible(true);

        //PANEL
        JPanel innerPanel = new JPanel();

        //NAME
        JLabel userNameLabel = new JLabel("Name:");
        userNameField = new JTextField();
        userNameField.setPreferredSize(new Dimension(200, 20));

        //BIRTHDAY
        JLabel userBirthDateLabel = new JLabel("Birthday: (format: DD-MM-YYYY");
        userBirthDateField = new JTextField("04-03-1998");
        userBirthDateField.setPreferredSize(new Dimension(150, 20));

        //SUBMIT
        userInfoSubmit = new JButton("Show reservations");

        //ADD COMPONENTS
        userInfoWindow.add(innerPanel);
        innerPanel.add(userNameLabel);
        innerPanel.add(userNameField);
        innerPanel.add(userBirthDateLabel);
        innerPanel.add(userBirthDateField);
        innerPanel.add(userInfoSubmit);
    }

    public JFrame getFrame() {
        return userInfoWindow;
    }
    public JButton getUserInfoButton() {
        return userInfoSubmit;
    }

    public String getUserName() {
        return userNameField.getText();
    }
    public LocalDateTime getBirthDay() {
        try {
            LocalDate parsedBday = LocalDate.parse(userBirthDateField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return parsedBday.atStartOfDay();
        } catch(DateTimeParseException e) {
            return null;
        }
    }
}
