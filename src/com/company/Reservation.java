package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private ReservationTarget reservationTarget;
    private Customer customer;
    private int reservationNumber;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;


    public Reservation(ReservationTarget reservationTarget, Customer customer, int reservationNumber, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        this.reservationTarget = reservationTarget;
        this.customer = customer;
        this.reservationNumber = reservationNumber;
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


    public int getReservationNumber() {
        return reservationNumber;
    }
    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }


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
