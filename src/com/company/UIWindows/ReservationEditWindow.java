package com.company.UIWindows;

import javax.swing.*;

public class ReservationEditWindow {
    private JFrame reservationEditWindow;
    private JLabel reservationNameLabel;

    public ReservationEditWindow(boolean visibleOnStart) {
        //WINDOW
        reservationEditWindow = new JFrame("Edit reservation");
        reservationEditWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reservationEditWindow.setSize(400, 300);

        //PANEL
        JPanel innerPanel = new JPanel();
        reservationEditWindow.add(innerPanel);

        //CONTENTS
        reservationNameLabel = new JLabel("Name: ");
        innerPanel.add(reservationNameLabel);

        if(visibleOnStart) reservationEditWindow.setVisible(true);
    }

    public JLabel getReservationNameLabel() {
        return reservationNameLabel;
    }
    public JFrame getFrame() {
        return reservationEditWindow;
    }
}
