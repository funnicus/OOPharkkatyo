package com.company;

import com.company.UIWindows.ReservationEditWindow;
import com.company.UIWindows.UserInfoWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class UserInterface extends JFrame {
    private Customer currentCustomer;

    public UserInterface() {
        //RESERVATION EDIT WINDOW
        ReservationEditWindow reservationEditWindow = new ReservationEditWindow(false);

        UserInfoWindow uiw = new UserInfoWindow(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Varausjärjestelmä - Juho Ollila, Juhana Kuparinen & Matias Kumpulainen");
        setSize(400,500);
        setVisible(true);

        Container cp = getContentPane();
        JPanel mainPanel = new JPanel(new BorderLayout());
        cp.add(mainPanel);

        //NEW RESERVATION BUTTON
        JButton newReservationButton = new JButton("New");
        newReservationButton.setSize(40, 20);
        mainPanel.add(newReservationButton, BorderLayout.PAGE_START, 0);
        //NEW RESERVATION ACTIONS
        newReservationButton.addActionListener(actionEvent -> {
            reservationEditWindow.getFrame().setVisible(true);
            reservationEditWindow.setReservation(null, currentCustomer);
        });

        //RESERVATION LIST MODEL
        DefaultListModel<Reservation> reservationList = new DefaultListModel<>();

        //GIVE REFERENCE OF LIST TO EDIT WINDOW
        reservationEditWindow.setTargetList(reservationList);

        //RESERVATION LIST PANE
        JList<Reservation> reservationListPane = new JList<>(reservationList);
        reservationListPane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(reservationListPane);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //USER INFO SUBMIT
        JButton userInfoSubmit = uiw.getUserInfoButton();
        //USER INFO ACTIONS
        userInfoSubmit.addActionListener(actionEvent -> {

            String name = uiw.getUserName();
            LocalDateTime birthday = uiw.getBirthDay();

            if(birthday == null) {
                JOptionPane.showMessageDialog(null, "Please type the date in this format:\nDD-MM-YYYY");
            } else {
                currentCustomer = new Customer(name, birthday, null);
                uiw.getFrame().setVisible(false);
            }
            //TODO: fetch from db using this data
        });
    }
}
