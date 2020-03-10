package com.company.UIWindows;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.*;
import java.time.format.DateTimeParseException;

public class UserInfoWindow {

    //komponentit joista tarvitaan viittaus
    private JFrame userInfoWindow;
    private JButton userInfoSubmit;
    private JTextField userBirthDateField;
    private JTextField userNameField;

    //luodaan ikkuna konstruktorissa
    public UserInfoWindow(boolean visibleOnStart) {

        //Ikkunan asetukset
        userInfoWindow = new JFrame("user info");
        userInfoWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInfoWindow.setSize(400, 150);
        userInfoWindow.setLocationRelativeTo(null);
        if(visibleOnStart) userInfoWindow.setVisible(true);

        //Paneeli, johon komponentit lisätään
        JPanel innerPanel = new JPanel();

        //Käyttäjänimen teksti ja tekstikenttä
        JLabel userNameLabel = new JLabel("Name:");
        userNameField = new JTextField();
        userNameField.setPreferredSize(new Dimension(200, 20));

        //Syntymäpäivän teksti ja tekstikenttä
        JLabel userBirthDateLabel = new JLabel("Birthday: (format: DD-MM-YYYY)");
        userBirthDateField = new JTextField("10-03-2020");
        userBirthDateField.setPreferredSize(new Dimension(150, 20));

        //Näytä varaukset-nappi
        userInfoSubmit = new JButton("Show reservations");

        //Lisätään komponentit järjestyksessä ikkunaan/paneeliin
        userInfoWindow.add(innerPanel);
        innerPanel.add(userNameLabel);
        innerPanel.add(userNameField);
        innerPanel.add(userBirthDateLabel);
        innerPanel.add(userBirthDateField);
        innerPanel.add(userInfoSubmit);
    }

    /**
     * Palauttaa ikkuna-komponentin
     * @return JFrame
     */
    public JFrame getFrame() {
        return userInfoWindow;
    }

    /**
     * Palauttaa näytä varaukset-napin
     * @return JButton
     */
    public JButton getUserInfoButton() {
        return userInfoSubmit;
    }

    /**
     * Palauttaa käyttäjän nimen
     * @return String
     */
    public String getUserName() { return userNameField.getText(); }

    /**
     * Yrittää parsia syntymäpäivä-tekstikentästä päivämäärän ja palauttaa sen
     * @return LocalDateTime
     */
    public LocalDateTime getBirthDay() {
        try {
            LocalDate parsedBday = LocalDate.parse(userBirthDateField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return parsedBday.atStartOfDay();
        } catch(DateTimeParseException e) {
            return null;
        }
    }
}
