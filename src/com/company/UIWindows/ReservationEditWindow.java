package com.company.UIWindows;

import com.company.Customer;
import com.company.Reservation;
import com.company.ReservationTarget;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationEditWindow {

    //komponentit joista tarvitaan viittaus
    private JFrame reservationEditWindow;
    private Reservation currentReservation;
    private JTextField placeField, addrField, startDateField, endDateField;
    private JButton okButton, cancelButton;

    //luodaan ikkuna konstruktorissa
    public ReservationEditWindow(boolean visibleOnStart) {
        //ikkunan asetukset
        reservationEditWindow = new JFrame("Edit reservation");
        reservationEditWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        reservationEditWindow.setSize(340, 300);
        reservationEditWindow.setResizable(false);
        reservationEditWindow.setLocationRelativeTo(null);
        if(visibleOnStart) reservationEditWindow.setVisible(true);

        //paneeli, johon komponenti lisätään
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(5,4));

        //varauksen nimen otsikko ja tekstikenttä
        JLabel placeLabel = new JLabel("Reservation name: ");
        placeField = new JTextField();

        //varauksen osoitteen otsikko ja tekstikenttä
        JLabel reservationAddrLabel = new JLabel("Address: ");
        addrField = new JTextField();

        //varauksen alkupvm otsikko ja tekstikenttä
        JLabel startDateLabel = new JLabel("Start time and date: ");
        startDateField = new JTextField();

        //varauksen loppupvm otsikko ja tekstikenttä
        JLabel endDateLabel = new JLabel("End time and date: ");
        endDateField = new JTextField();

        //ok-nappi
        okButton = new JButton("Ok");

        //peruuta-nappi
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(actionEvent -> resetWindow());

        //lisätään komponentit järjestyksessä ikkunaan/paneeliin
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

    /**
     * Nollaa ikkunan kentät ja asettaa muokattavan varauksen tyhjäksi
     */
    private void resetWindow() {
        //discard changes and hide window
        reservationEditWindow.setVisible(false);
        currentReservation = null;
        addrField.setText("");
        placeField.setText("");
    }

    /**
     * Asettaa ikkunan varauksen, jonka perusteella tekstikenttien tiedot täytetään.
     * Ikkunassa tehdyt muutokset tullaan päivittämään tähän kyseiseen varaukseen.
     * @param reservation varaus jota muokataan
     * @param customer asiakas jolla varaus on
     */
    public void setReservationToEdit(Reservation reservation, Customer customer) {
        //UPDATE TEXTFIELDS WITH CORRECT DATA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");

        if(reservation == null) {
            //CREATE NEW RESERVATION TARGET
            ReservationTarget newReservationTarget = new ReservationTarget("", "", "");
            currentReservation = new Reservation( null, newReservationTarget, customer, placeField.getText(), LocalDateTime.now(), LocalDateTime.now());

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

    /**
     * Palauttaa nykyisen varauksen
     * @return varaus
     */
    public Reservation getCurrentReservation() { return currentReservation; }

    /**
     * Nollaa ikkunan tiedot
     */
    public void resetForm() {
        placeField.setText("");
        addrField.setText("");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        startDateField.setText(LocalDateTime.now().format(formatter));
        endDateField.setText(LocalDateTime.now().format(formatter));
    }

    /**
     * Yrittää parsia aloituspvm:n tekstikentästä päivämäärän, ja palauttaa sen
     * @return aloituspvm
     */
    public LocalDateTime getStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(startDateField.getText(), formatter);
    }

    /**
     * Yrittää parsia lopetuspvm:n tekstikentästä päivämäärän, ja palauttaa sen
     * @return lopetuspvm
     */
    public LocalDateTime getEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(endDateField.getText(), formatter);
    }


    public JFrame getFrame() { return reservationEditWindow; }
    public JButton getOkButton() { return okButton; }
    public String getName() { return placeField.getText(); }
    public String getAddress() { return addrField.getText(); }
}