package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface CustomerInterface {

    String getName();
    void setName(String name);

    LocalDateTime getBirthday();
    void setBirthday(LocalDateTime birthDate);

    ArrayList<Reservation> getReservations();
    void setReservations(ArrayList<Reservation> reservations);
}
