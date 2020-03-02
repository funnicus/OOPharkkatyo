package com.company;

import java.util.ArrayList;
import java.util.Date;

public interface Customer {

    String getName();
    void setName(String name);

    Date getBirthDate();
    void setBirthDate(Date birthDate);

    ArrayList<Reservation> getAllReservations();
    void setAllReservations(ArrayList<Reservation> reservations);

    Reservation getReservation(int reservationNumber);
    void setReservation(Reservation reservation);

}
