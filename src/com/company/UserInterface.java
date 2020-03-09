package com.company;

import com.company.Customer;
import com.company.Reservation;
import com.company.UIWindows.ReservationEditWindow;
import com.company.UIWindows.UserInfoWindow;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserInterface extends JFrame {
    private Customer currentCustomer;

    public UserInterface() {
        //RESERVATION EDIT WINDOW
        ReservationEditWindow reservationEditWindow = new ReservationEditWindow(false);
        //USER INFO WINDOW
        UserInfoWindow uiw = new UserInfoWindow(true);

        //RESERVATION VIEW WINDOW SETTINGS
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Varausjärjestelmä - Juho Ollila, Juhana Kuparinen & Matias Kumpulainen");
        setSize(650, 300);

        //CONTAINER
        Container cp = getContentPane();
        JPanel mainPanel = new JPanel(new FlowLayout());
        cp.add(mainPanel);

        //NEW RESERVATION BUTTON
        JButton newReservationButton = new JButton("New");
        newReservationButton.setSize(40, 20);
        mainPanel.add(newReservationButton);
        //NEW RESERVATION ACTIONS
        newReservationButton.addActionListener(actionEvent -> {
            reservationEditWindow.getFrame().setVisible(true);
            reservationEditWindow.setReservationToEdit(null, currentCustomer);
        });

        //EDIT RESERVATION BUTTON
        JButton editReservationButton = new JButton("Edit");
        editReservationButton.setSize(40, 20);
        mainPanel.add(editReservationButton);

        //DELETE RESERVATION BUTTON
        JButton deleteReservationButton = new JButton("Delete");
        deleteReservationButton.setSize(40, 20);
        mainPanel.add(deleteReservationButton);

        //RESERVATION LIST MODEL
        DefaultListModel<Reservation> reservationList = new DefaultListModel<>();
        //GIVE REFERENCE OF LIST TO EDIT WINDOW
        reservationEditWindow.setTargetList(reservationList);

        //RESERVATION LIST PANE
        JList<Reservation> reservationListPane = new JList<>(reservationList);
        reservationListPane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationListPane.setPreferredSize(new Dimension(600, 200));

        //NEW RESERVATION ACTIONS
        editReservationButton.addActionListener(actionEvent -> {
            int index = reservationListPane.getSelectedIndex();
            if(index != -1) {
                reservationEditWindow.getFrame().setVisible(true);
                reservationEditWindow.setReservationToEdit(reservationList.getElementAt(index), currentCustomer);
                //TODO: insert kysely
            }

        });

        //DELETE RESERVATION ACTIONS
        deleteReservationButton.addActionListener(actionEvent -> {
            int index = reservationListPane.getSelectedIndex();
            if(index != -1) {
                reservationList.remove(index);
                //TODO: delete kysely
            }
        });

        //SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(reservationListPane);
        scrollPane.setPreferredSize(new Dimension(620, 220));
        mainPanel.add(scrollPane);
        pack();
        setLocationRelativeTo(null);

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
                setVisible(true);
                setTitle("Reservations for " + name);
                //TODO: select kysely

            }
        });

        //RESERVATION EDIT SUBMIT
        JButton editOkButton = reservationEditWindow.getOkButton();
        //EDIT SUBMISSION ACTION
        editOkButton.addActionListener(e -> {
            Reservation reservation = reservationEditWindow.getCurrentReservation();

            ReservationTarget reservationTarget = reservation.getReservationTarget();

            reservationTarget.setName(reservationEditWindow.getName());
            reservationTarget.setAddress(reservationEditWindow.getAddress());
            reservationTarget.setType("reservation");

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                LocalDateTime parsedStartTime = reservationEditWindow.getStartDate();
                LocalDateTime parsedEndTime = reservationEditWindow.getEndDate();

                reservation.setReservationStart(parsedStartTime);
                reservation.setReservationEnd(parsedEndTime);

                int index = reservationList.indexOf(reservation);

                //NEW RESERVATION
                if(index == -1) {
                    reservationList.addElement(reservation);
                } else {
                    reservationList.setElementAt(reservation, index);
                }

                reservationEditWindow.getFrame().setVisible(false);
                reservationEditWindow.resetForm();
                //TODO: update kysely

            } catch(DateTimeParseException timeException) {
                System.out.println(timeException);
                JOptionPane.showMessageDialog(null, "Please type the start and end times in this format:\nDD-MM-YYYY HH:MM");
            }


        });
    }
}
