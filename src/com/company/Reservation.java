package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Reservation {
    private ReservationTarget reservationTarget;
    private Customer customer;
    private String id;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;

    public Reservation(ReservationTarget reservationTarget, Customer customer, String reservationNumber, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        this.reservationTarget = reservationTarget;
        this.customer = customer;
        this.id = UUID.randomUUID().toString();
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    public ReservationTarget getReservationTarget() {
        return reservationTarget;
    }
    public void setReservationTarget(ReservationTarget reservationTarget) {
        this.reservationTarget = reservationTarget;
    }


    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public String getId() {
        return id;
    }
    /*public void setReservationNumber(int reservationNumber) {
        this.id = reservationNumber;
    }*/


    public LocalDateTime getReservationStart() {
        return reservationStart;
    }
    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }


    public LocalDateTime getReservationEnd() {
        return reservationEnd;
    }
    public void setReservationEnd(LocalDateTime reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        String formattedStartTime = this.reservationStart.format(formatter);
        String formattedEndTime = this.reservationEnd.format(formatter);
        return this.reservationTarget.getName() + " @ " + this.reservationTarget.getAddress() + "  |  from: " + formattedStartTime + " until: " + formattedEndTime;
    }
}
