package com.company;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
	    //Backend backend = new Backend();
	    //id, customer_id, place, address, start_date, end_date
	    //backend.createReservation(id, "JANNNE030200", "Hilton Hotel", "Matsku st. 10", "9.3.2020", "19.3.2020");
        //backend.createCustomer("JANNE030200", "Janne", "01.04.2003");
        //backend.selectCustomer();
        //backend.selectReservation();

        SwingUtilities.invokeLater(UserInterface::new);
    }
}