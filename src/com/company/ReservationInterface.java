package com.company;

import java.time.LocalDateTime;

public interface ReservationInterface {

    ReservationTarget getReservationTarget();
    void setReservationTarget(ReservationTarget reservationTarget);

    Customer getCustomer();
    void setCustomer(Customer customer);

    String getId();
    void setId(String id);

    LocalDateTime getReservationStart();
    void setReservationStart(LocalDateTime startDate);

    LocalDateTime getReservationEnd();
    void setReservationEnd(LocalDateTime endDate);

}
