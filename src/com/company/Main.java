package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	    Backend backend = new Backend();
	    backend.connect();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserInterface();
            }
        });
    }
}