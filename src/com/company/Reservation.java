package com.company;

import java.util.Date;

public interface Reservation {

    ReservationTarget getReservationTarget();
    void setReservationTarget(ReservationTarget reservationTarget);

    Customer getCustomer();
    void setCustomer(Customer customer);

    int getReservationNumber();
    void setReservationNumber(int number);

    Date getStartDate();
    void setStartDate(Date startDate);

    Date getEndDate();
    void setEndDate(Date endDate);

}
