package com.company;

import com.company.UIWindows.ReservationEditWindow;
import com.company.UIWindows.UserInfoWindow;
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserInterface extends JFrame {
    private Customer customer;

    public UserInterface() {
        UserInfoWindow uiw = new UserInfoWindow(true);
        ReservationEditWindow rew = new ReservationEditWindow(false);

        Container cp = getContentPane();
        JPanel mainPanel = new JPanel(new FlowLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Varausjärjestelmä - Juho Ollila, Juhana Kuparinen & Matias Kumpulainen");
        setSize(300,200);
        setVisible(true);

        cp.add(mainPanel);

        //RESERVATION LIST MODEL
        DefaultListModel<String> reservationList = new DefaultListModel<String>();
        //RESERVATION LIST PANE
        JList reservationListPane = new JList(reservationList);
        reservationListPane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(reservationListPane);
        cp.add(scrollPane);

        //USER INFO SUBMIT
        JButton userInfoSubmit = uiw.getUserInfoSubmit();
        userInfoSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = uiw.getUserName();
                LocalDateTime birthday = uiw.getBirthDay();
                System.out.println(name + " : " + birthday);
                //TODO: fetch from db using this data
            }
        });

        //
    }
}
