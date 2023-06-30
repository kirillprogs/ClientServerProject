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
            createGroup(Client.httpAccessor);
        });

        //delete group
        deleteButton.addActionListener(e -> {
            deleteGroup(Client.httpAccessor);
        });

        //update some info about group
        updateButton.addActionListener(e -> {
            updateGroup(Client.httpAccessor);

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

    private static void createGroup(HttpAccessor accessor) {
        JFrame frame = new JFrame("Create Group");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JButton createButton = new JButton("Create");

        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            try {
                accessor.createGroup(name, description);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(createButton);

        frame.getContentPane().add(panel);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    private static void deleteGroup(HttpAccessor accessor) {
        JFrame frame = new JFrame("Delete Group");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel nameLabel = new JLabel("Group Name:");
        JTextField nameField = new JTextField();

        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            String name = nameField.getText();
            try {
                accessor.deleteGroup(name);
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

    private static void updateGroup(HttpAccessor accessor) {
        JFrame frame = new JFrame("Update Group");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));


        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {

        });


        panel.add(updateButton);

        frame.getContentPane().add(panel);
        frame.setSize(400, 150);
        frame.setVisible(true);
    }




}