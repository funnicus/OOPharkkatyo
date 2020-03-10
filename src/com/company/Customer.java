package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Customer {
    private String name;
    private LocalDateTime birthday;
    private ArrayList<Reservation> reservations;
    private String id;

    Customer(String name, LocalDateTime birthday, ArrayList<Reservation> reservations) {
        this.name = name;
        this.birthday = birthday;
        this.reservations = reservations;
        id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getId() {
        return id;
    }
}
