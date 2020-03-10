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
    //viittaus asiakkaaseen jolle varaus tehdään
    private Customer currentCustomer;

    //komponentit joista tarvitaan viittaus
    private JList<Reservation> reservationListPane;
    private DefaultListModel<Reservation> reservationList;
    private ReservationEditWindow reservationEditWindow;
    private UserInfoWindow userInfoWindow;

    //tietokantaan yhdistävä luokka
    private Backend backend = new Backend();

    //luodaan käyttöliittymä konstruktorissa
    public UserInterface() {

        //Luodaan uudet muokkaus- ja käyttäjätiedot -ikkunat
        reservationEditWindow = new ReservationEditWindow(false);
        userInfoWindow = new UserInfoWindow(true);

        //Varausnäkymän ikkunan asetukset
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Varausjärjestelmä");
        setSize(620, 300);
        setResizable(false);

        //Paneeli, johon komponentit lisätään
        Container cp = getContentPane();
        JPanel mainPanel = new JPanel(new FlowLayout());

        //Uusi varaus -nappi
        JButton newReservationButton = new JButton("New");
        newReservationButton.setSize(40, 20);
        newReservationButton.addActionListener(actionEvent -> newReservation());

        //Muokkaa varausta -nappi
        JButton editReservationButton = new JButton("Edit");
        editReservationButton.setSize(40, 20);
        editReservationButton.addActionListener(actionEvent -> openEditWindow());

        //Poista varaus -nappi
        JButton deleteReservationButton = new JButton("Delete");
        deleteReservationButton.setSize(40, 20);
        deleteReservationButton.addActionListener(actionEvent -> deleteReservation());

        //ArrayList, joka sisältää kaikki varaukset
        reservationList = new DefaultListModel<>();
        //Lista-komponentti, joka näyttää kaikki varaukset listana ruudulla
        reservationListPane = new JList<>(reservationList);
        reservationListPane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationListPane.setPreferredSize(new Dimension(600, 200));

        //Vierityspaneeli johon lista-komponentti sisällytetään
        JScrollPane scrollPane = new JScrollPane(reservationListPane);
        scrollPane.setPreferredSize(new Dimension(620, 220));

        //Käyttäjätiedot-ikkunan ok-nappi
        JButton userInfoSubmit = userInfoWindow.getUserInfoButton();
        userInfoSubmit.addActionListener(actionEvent -> showReservations());

        //Varausikkunan ok-nappi
        JButton editOkButton = reservationEditWindow.getOkButton();
        editOkButton.addActionListener(e -> updateReservations());

        //Lisätään komponentit järjestyksessä ikkunaan/paneeliin
        cp.add(mainPanel);
        mainPanel.add(newReservationButton);
        mainPanel.add(editReservationButton);
        mainPanel.add(deleteReservationButton);
        mainPanel.add(scrollPane);
        pack();
        setLocationRelativeTo(null); //Keskittää ikkunan ruudulle
    }

    /**
     * Avaa muokkausikkunan ja asettaa muokattavan varauksen tyhjäksi,
     * jolloin luodaan uusi varaus
     */
    private void newReservation() {
        reservationEditWindow.getFrame().setVisible(true);
        reservationEditWindow.setReservationToEdit(null, currentCustomer);
    }

    /**
     * Ottaa muokkausikkunan tiedot, ja asettaa varauksen tiedot niiden perusteella.
     * Mikäli muokattava varaus on uusi, luodaan uusi varaus ja lisätään se ikkunaan ja tietokantaan.
     * Mikäli muokattava varaus on olemassaoleva, muokataan kyseisen varauksen tiedot ikkunassa ja tietokannassa.
     */
    private void updateReservations() {
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
        } catch(DateTimeParseException timeException) {
            System.out.println(timeException);
            JOptionPane.showMessageDialog(null, "Please type the start and end times in this format:\nDD-MM-YYYY HH:MM");
        }
    }

    /**
     * Poistaa valitun varauksen sekä ikkunasta että tietokannasta
     */
    private void deleteReservation() {
        int index = reservationListPane.getSelectedIndex();
        Reservation reservation =  reservationList.elementAt(index);
        if(index != -1) {
            reservationList.remove(index);
            backend.deleteReservation(reservation.getId());
        }
    }

    /**
     * Hakee tietokannasta käyttäjän id:llä varustetut varaukset ja näyttää ne ikkunassa.
     * Sulkee käyttäjätiedot-ikkunan ja avaa varausnäkymä-ikkunan.
     */
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

    /**
     * Avaa varauksenmuokkaus-ikkunan, ja asettaa valitun varauksen muokattavaksi varaukseksi
     */
    private void openEditWindow() {
        int index = reservationListPane.getSelectedIndex();
        if(index != -1) {
            reservationEditWindow.getFrame().setVisible(true);
            reservationEditWindow.setReservationToEdit(reservationList.getElementAt(index), currentCustomer);
        }
    }


}