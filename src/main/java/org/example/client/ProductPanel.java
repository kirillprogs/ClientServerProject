package org.example.client;


import javax.swing.*;
import java.awt.*;


public class ProductPanel {

    public static JPanel createProductPanel() {

        JPanel productPanel = new JPanel(new BorderLayout());


        JButton createButton = new JButton("Create");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton increaseButton = new JButton("Increase");
        JButton decreaseButton = new JButton("Decrease");
        JButton backToGroupsButton = new JButton("Back to Groups");


        //create product
        createButton.addActionListener(e -> {


        });

        //delete product
        deleteButton.addActionListener(e -> {
            // Logic for deleting a product

        });

        //update something in product
        updateButton.addActionListener(e -> {
            // Logic for updating a product

        });

        //increase quantity in stock
        increaseButton.addActionListener(e -> {
            // Logic for increasing product quantity

        });

        //decrease quantity in stock
        decreaseButton.addActionListener(e -> {
            // Logic for decreasing product quantity

        });

        //return to the page with groups interaction
        backToGroupsButton.addActionListener(e -> Client.cardLayout.show(Client.cardPanel, "group"));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);


        productPanel.add(buttonPanel, BorderLayout.CENTER);
        productPanel.add(backToGroupsButton, BorderLayout.PAGE_END);

        return productPanel;
    }
}
