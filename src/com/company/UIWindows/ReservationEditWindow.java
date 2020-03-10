package com.company.UIWindows;

import com.company.Customer;
import com.company.Reservation;
import com.company.ReservationTarget;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationEditWindow {
    private JFrame reservationEditWindow;
    private Reservation currentReservation;

    private JTextField placeField, addrField, startDateField, endDateField;
    private JButton okButton, cancelButton;

    public ReservationEditWindow(boolean visibleOnStart) {
        //WINDOW
        reservationEditWindow = new JFrame("Edit reservation");
        reservationEditWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        reservationEditWindow.setSize(340, 300);
        reservationEditWindow.setResizable(false);
        reservationEditWindow.setLocationRelativeTo(null);
        if(visibleOnStart) reservationEditWindow.setVisible(true);

        //PANEL
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(5,4));

        //RESERVATION NAME
        JLabel placeLabel = new JLabel("Reservation name: ");
        placeField = new JTextField();

        //RESERVATION ADDRESS
        JLabel reservationAddrLabel = new JLabel("Address: ");
        addrField = new JTextField();

        //RESERVATION START DATE
        JLabel startDateLabel = new JLabel("Start time and date: ");
        startDateField = new JTextField();

        //RESERVATION NAME LABEL
        JLabel endDateLabel = new JLabel("End time and date: ");
        endDateField = new JTextField();

        //OK BUTTON
        okButton = new JButton("Ok");
        //CANCEL BUTTON
        cancelButton = new JButton("Cancel");
        //CANCEL BUTTON ACTIONS
        cancelButton.addActionListener(actionEvent -> resetWindow());

        //ADD COMPONENTS
        reservationEditWindow.add(innerPanel);
        innerPanel.add(placeLabel);
        innerPanel.add(placeField);
        innerPanel.add(reservationAddrLabel);
        innerPanel.add(addrField);
        innerPanel.add(startDateLabel);
        innerPanel.add(startDateField);
        innerPanel.add(endDateLabel);
        innerPanel.add(endDateField);
        innerPanel.add(okButton);
        innerPanel.add(cancelButton);
    }

    private void resetWindow() {
        //discard changes and hide window
        reservationEditWindow.setVisible(false);
        currentReservation = null;
        addrField.setText("");
        placeField.setText("");
    }

    public void setReservationToEdit(Reservation reservation, Customer customer) {
        //UPDATE TEXTFIELDS WITH CORRECT DATA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");

        if(reservation == null) {
            //CREATE NEW RESERVATION TARGET
            ReservationTarget newReservationTarget = new ReservationTarget("", "", "");
            currentReservation = new Reservation(newReservationTarget, customer, placeField.getText(), LocalDateTime.now(), LocalDateTime.now());

            //SET DEFAULT VALUES
            placeField.setText("");
            addrField.setText("");
            startDateField.setText(LocalDateTime.now().format(formatter));
            endDateField.setText(LocalDateTime.now().format(formatter));

        //IF EDITING AN EXISTING RESERVATION
        } else {
            currentReservation = reservation;

            //Set values according to reservation details
            placeField.setText(currentReservation.getReservationTarget().getName());
            addrField.setText(currentReservation.getReservationTarget().getAddress());
            startDateField.setText(currentReservation.getReservationStart().format(formatter));
            endDateField.setText(currentReservation.getReservationEnd().format(formatter));
        }
    }
    public Reservation getCurrentReservation() { return currentReservation; }
    public void resetForm() {
        placeField.setText("");
        addrField.setText("");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        startDateField.setText(LocalDateTime.now().format(formatter));
        endDateField.setText(LocalDateTime.now().format(formatter));
    }
    public JFrame getFrame() { return reservationEditWindow; }
    public JButton getOkButton() { return okButton; }
    public JButton getCancelButton() { return cancelButton; }

    public String getName() { return placeField.getText(); }
    public String getAddress() { return addrField.getText(); }
    public LocalDateTime getStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(startDateField.getText(), formatter);
    }
    public LocalDateTime getEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(endDateField.getText(), formatter);
    }
}