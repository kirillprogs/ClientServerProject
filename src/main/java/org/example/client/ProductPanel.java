package org.example.client;


import org.example.entity.Product;

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
            createProduct(Client.httpAccessor);
        });

        //delete product
        deleteButton.addActionListener(e -> {
            deleteProduct(Client.httpAccessor);

        });

        //update something in product

       /* updateButton.addActionListener(e -> {
           updateButton(Client.httpAccessor, );

        });*/

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

    private static void createProduct(HttpAccessor accessor) {
        JFrame frame = new JFrame("Create Product");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        JLabel groupLabel = new JLabel("Group:");
        JTextField groupField = new JTextField();

        JButton createButton = new JButton("Create");

        Client.setFont(new JComponent[]{nameLabel, nameField, descriptionLabel, descriptionField,
                        amountLabel, amountField, priceLabel, priceField, groupLabel, groupField, createButton});
        createButton.addActionListener(e -> {
            if (nameField.getText().isEmpty()
                    || descriptionField.getText().isEmpty()
                    || amountField.getText().isEmpty()
                    || priceField.getText().isEmpty()
                    || groupField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all data");
                return;
            }
            try {
                String name = nameField.getText();
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                double price = Double.parseDouble(priceField.getText());
                String group = groupField.getText();
                try {
                    accessor.createProduct(name, group, description, amount, price);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Incorrect numeric format");
            }
            nameField.setText("");
            descriptionField.setText("");
            groupField.setText("");
            amountField.setText("");
            priceField.setText("");
        });
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(groupLabel);
        panel.add(groupField);
        panel.add(createButton);
        frame.getContentPane().add(panel);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private static void deleteProduct(HttpAccessor httpAccessor) {
        JFrame frame = new JFrame("Delete Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            String name = nameField.getText();
            try {
                httpAccessor.deleteProduct(name);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(deleteButton);

        frame.getContentPane().add(panel);
        frame.setSize(400, 150);
        frame.setVisible(true);
    }


    private static void updateProduct(HttpAccessor accessor) {
        JFrame frame = new JFrame("Update Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));


        JButton updateButton = new JButton("Update");

        updateButton.addActionListener(e -> {

        });

        panel.add(updateButton);
        frame.getContentPane().add(panel);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}