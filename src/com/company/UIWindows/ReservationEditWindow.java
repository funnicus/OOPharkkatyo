package com.company.UIWindows;

import com.company.Customer;
import com.company.Reservation;
import com.company.ReservationTarget;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationEditWindow {
    private JFrame reservationEditWindow;
    private Reservation currentReservation;

    private DefaultListModel targetList;

    private JTextField placeField, addrField, startDateField, endDateField;

    public ReservationEditWindow(boolean visibleOnStart) {
        //WINDOW
        reservationEditWindow = new JFrame("Edit reservation");
        reservationEditWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        reservationEditWindow.setSize(340, 300);
        reservationEditWindow.setResizable(false);
        reservationEditWindow.setLocationRelativeTo(null);

        //PANEL
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(5,4));
        reservationEditWindow.add(innerPanel);

        //CONTENTS
        //RESERVATION NAME LABEL
        JLabel placeLabel = new JLabel("Reservation name: ");
        innerPanel.add(placeLabel);

        //RESERVATION PLACE TEXTFIELD
        placeField = new JTextField();
        innerPanel.add(placeField);

        //RESERVATION ADDRESS LABEL
        JLabel reservationAddrLabel = new JLabel("Address: ");
        innerPanel.add(reservationAddrLabel);

        //RESERVATION ADDRESS TEXTFIELD
        addrField = new JTextField();
        innerPanel.add(addrField);

        //RESERVATION NAME LABEL
        JLabel startDateLabel = new JLabel("Start time and date: ");
        innerPanel.add(startDateLabel);

        //RESERVATION START DATE TEXTFIELD
        startDateField = new JTextField();
        innerPanel.add(startDateField);

        //RESERVATION NAME LABEL
        JLabel endDateLabel = new JLabel("End time and date: ");
        innerPanel.add(endDateLabel);

        //RESERVATION END DATE TEXTFIELD
        endDateField = new JTextField();
        innerPanel.add(endDateField);

        //OK BUTTON
        JButton okButton = new JButton("Ok");
        innerPanel.add(okButton);
        //OK BUTTON ACTIONS
        okButton.addActionListener(actionEvent -> {

            //change reservation details and hide window
            ReservationTarget target = currentReservation.getReservationTarget();
            target.setName(placeField.getText());
            target.setAddress(addrField.getText());
            target.setType("reservation");

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                //TODO: parse correct times from user input
                LocalDateTime parsedStartTime = LocalDateTime.parse(startDateField.getText(), formatter);
                LocalDateTime parsedEndTime = LocalDateTime.parse(endDateField.getText(), formatter);
                currentReservation.setReservationStart(parsedStartTime);
                currentReservation.setReservationEnd(parsedEndTime);

                if(targetList != null) {
                    int index = targetList.indexOf(currentReservation);
                    if(index != -1) {
                        targetList.setElementAt(currentReservation, index);
                    } else {
                        targetList.addElement(currentReservation);
                    }
                    currentReservation = null;
                    reservationEditWindow.setVisible(false);
                }
            } catch (DateTimeParseException exc) {
                System.out.println(exc);
                JOptionPane.showMessageDialog(null, "Please type the start and end times in this format:\nDD-MM-YYYY HH:MM");
            }
        });

        //CANCEL BUTTON
        JButton cancelButton = new JButton("Cancel");
        innerPanel.add(cancelButton);
        //CANCEL BUTTON ACTIONS
        cancelButton.addActionListener(actionEvent -> {
            //discard changes and hide window
            reservationEditWindow.setVisible(false);
            currentReservation = null;
            addrField.setText("");
            placeField.setText("");
        });

        if(visibleOnStart) reservationEditWindow.setVisible(true);
    }

    public void setReservationToEdit(Reservation reservation, Customer customer) {
        //UPDATE TEXTFIELDS WITH CORRECT DATA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");

        if(reservation == null) {
            //CREATE NEW RESERVATION TARGET
            ReservationTarget newReservationTarget = new ReservationTarget("", "", "");
            currentReservation = new Reservation(newReservationTarget, customer, (int) (Math.random()*1000000), LocalDateTime.now(), LocalDateTime.now());

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
    public JFrame getFrame() {
        return reservationEditWindow;
    }

    public void setTargetList(DefaultListModel target) {
        this.targetList = target;
    }
}
