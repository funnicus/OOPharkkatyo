package com.company.UIWindows;

import com.company.Customer;
import com.company.Reservation;
import com.company.ReservationTarget;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class ReservationEditWindow {
    private JFrame reservationEditWindow;
    private JLabel reservationNameLabel;
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
        JLabel placeLabel = new JLabel("Reservation place: ");
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
            if(targetList != null) {
                targetList.addElement(currentReservation);
                currentReservation = null;
                reservationEditWindow.setVisible(false);
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

    public void setReservation(Reservation reservation, Customer customer) {
        if(reservation == null) {
            //CREATE NEW RESERVATION
            ReservationTarget newReservationTarget = new ReservationTarget("", "", "");
            currentReservation = new Reservation(newReservationTarget, customer, (int) (Math.random()*1000000), LocalDateTime.now(), LocalDateTime.now());
        } else {
            currentReservation = reservation;
        }
        //update frame data
        placeField.setText(currentReservation.getReservationTarget().getName());
        addrField.setText(currentReservation.getReservationTarget().getAddress());
    }

    public JLabel getReservationNameLabel() {
        return reservationNameLabel;
    }
    public JFrame getFrame() {
        return reservationEditWindow;
    }

    public void setTargetList(DefaultListModel target) {
        this.targetList = target;
    }
}
