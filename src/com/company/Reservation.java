package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

//Luokka pitää sisällään yksittäisen varauksen kaikki tiedot sekä varauskohteen,
// ja metodit joilla näitä tietoja voidaan lukea ja muokata
public class Reservation implements ReservationInterface{
    private ReservationTarget reservationTarget;
    private Customer customer;
    private String id;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;

    public Reservation(String id,ReservationTarget reservationTarget, Customer customer, String reservationNumber, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        this.reservationTarget = reservationTarget;
        this.customer = customer;
        if(id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }

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
    public void setId(String id) {
        this.id = id;
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

    //Palauttaa varauksen tiedot siinä muodossa, jossa ne näkyvät varauksessa
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        String formattedStartTime = this.reservationStart.format(formatter);
        String formattedEndTime = this.reservationEnd.format(formatter);
        return this.reservationTarget.getName() + " @ " + this.reservationTarget.getAddress() + "  |  from: " + formattedStartTime + " until: " + formattedEndTime;
    }
}
