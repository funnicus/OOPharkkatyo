package com.company;

import com.company.UIWindows.ReservationEditWindow;
import com.company.UIWindows.UserInfoWindow;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class UserInterface extends JFrame {
    private Customer currentCustomer;

    private JList<Reservation> reservationListPane;
    private DefaultListModel<Reservation> reservationList;

    private ReservationEditWindow reservationEditWindow;
    private UserInfoWindow userInfoWindow;
    Backend backend = new Backend();
    public UserInterface() {
        reservationEditWindow = new ReservationEditWindow(false);
        userInfoWindow = new UserInfoWindow(true);

        //RESERVATION VIEW WINDOW SETTINGS
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Varausjärjestelmä - Juho Ollila, Juhana Kuparinen & Matias Kumpulainen");
        setSize(650, 300);
        setResizable(false);

        //CONTAINER
        Container cp = getContentPane();
        JPanel mainPanel = new JPanel(new FlowLayout());

        //NEW RESERVATION BUTTON
        JButton newReservationButton = new JButton("New");
        newReservationButton.setSize(40, 20);
        newReservationButton.addActionListener(actionEvent -> newReservation());

        //EDIT RESERVATION BUTTON
        JButton editReservationButton = new JButton("Edit");
        editReservationButton.setSize(40, 20);
        editReservationButton.addActionListener(actionEvent -> openEditWindow());

        //DELETE RESERVATION BUTTON
        JButton deleteReservationButton = new JButton("Delete");
        deleteReservationButton.setSize(40, 20);
        deleteReservationButton.addActionListener(actionEvent -> deleteReservation());

        //RESERVATION LIST MODEL
        reservationList = new DefaultListModel<>();
        //RESERVATION LIST PANE
        reservationListPane = new JList<>(reservationList);
        reservationListPane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationListPane.setPreferredSize(new Dimension(600, 200));

        //SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(reservationListPane);
        scrollPane.setPreferredSize(new Dimension(620, 220));

        //USER INFO SUBMIT
        JButton userInfoSubmit = userInfoWindow.getUserInfoButton();
        userInfoSubmit.addActionListener(actionEvent -> showReservations());

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

                String place = reservation.getReservationTarget().getName();
                String address = reservation.getReservationTarget().getAddress();
                String startTime = parsedStartTime.format(formatter);
                String endTime = parsedEndTime.format(formatter);

                if(index == -1) {
                    //NEW RESERVATION
                    reservationList.addElement(reservation);

                    backend.createReservation(reservation.getId(), currentCustomer.getId(), place, address, startTime, endTime);
                } else {
                    //EXISTING RESERVATIONS
                    reservationList.setElementAt(reservation, index);

                    backend.updateReservation(reservation.getId(), currentCustomer.getId(), place, address, startTime, endTime);
                }

                reservationEditWindow.getFrame().setVisible(false);
                reservationEditWindow.resetForm();
                //TODO: update kysely

            } catch(DateTimeParseException timeException) {
                System.out.println(timeException);
                JOptionPane.showMessageDialog(null, "Please type the start and end times in this format:\nDD-MM-YYYY HH:MM");
            }
        });

        //ADD COMPONENTS
        cp.add(mainPanel);
        mainPanel.add(newReservationButton);
        mainPanel.add(editReservationButton);
        mainPanel.add(deleteReservationButton);
        mainPanel.add(scrollPane);
        pack();
        setLocationRelativeTo(null);
    }

    private void newReservation() {
        reservationEditWindow.getFrame().setVisible(true);
        reservationEditWindow.setReservationToEdit(null, currentCustomer);
    }
    private void showReservations() {
        String name = userInfoWindow.getUserName();
        LocalDateTime birthday = userInfoWindow.getBirthDay();

        if(birthday == null) {
            JOptionPane.showMessageDialog(null, "Please type the date in this format:\nDD-MM-YYYY");
        } else {
            currentCustomer = new Customer(name, birthday, null);
            String bday = birthday.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            backend.createCustomer(currentCustomer.getId(), name, bday);

            ArrayList<Reservation> list = backend.selectReservation(currentCustomer);
            for(Reservation r : list) {
                if(r != null) reservationList.addElement(r);
            }
            userInfoWindow.getFrame().setVisible(false);
            setVisible(true);
            setTitle("Reservations for " + name);
        }
    }
    private void openEditWindow() {
        int index = reservationListPane.getSelectedIndex();
        if(index != -1) {
            reservationEditWindow.getFrame().setVisible(true);
            reservationEditWindow.setReservationToEdit(reservationList.getElementAt(index), currentCustomer);
            //TODO: insert kysely
        }
    }
    private void deleteReservation() {
        int index = reservationListPane.getSelectedIndex();
        if(index != -1) {
            reservationList.remove(index);
            //TODO: delete kysely
        }
    }
}
