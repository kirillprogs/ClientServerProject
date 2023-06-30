package org.example.client;

import javax.swing.*;
import java.awt.*;


public class GroupPanel {
    public static JPanel createGroupPanel() {

        JPanel groupPanel = new JPanel(new BorderLayout());


        JButton createButton = new JButton("Create");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton backToProductsButton = new JButton("Back to Products");

        //create group
        createButton.addActionListener(e -> {

        });

        //delete group
        deleteButton.addActionListener(e -> {


        });

        //update some info about group
        updateButton.addActionListener(e -> {


        });

        //return to the page with products interaction
        backToProductsButton.addActionListener(e -> Client.cardLayout.show(Client.cardPanel, "product"));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(createButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(updateButton);
        groupPanel.add(buttonsPanel, BorderLayout.CENTER);
        groupPanel.add(backToProductsButton, BorderLayout.PAGE_END);

        return groupPanel;
    }
}