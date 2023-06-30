package org.example.client;

import org.example.entity.Group;

import javax.swing.*;
import java.awt.*;

public class GroupPanel {

    public static JPanel createGroupPanel() {

        JPanel groupPanel = new JPanel(new BorderLayout());

        JButton createButton = new JButton("Create");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton storeSum = new JButton("Store Sum");
        JButton groupSum = new JButton("Group Sum");
        JButton findGroup = new JButton("Find Group");

        JButton backToProductsButton = new JButton("Back to Products");

        createButton.addActionListener(e -> createGroup(Client.httpAccessor));

        deleteButton.addActionListener(e -> deleteGroup(Client.httpAccessor));

        updateButton.addActionListener(e -> updateGroup(Client.httpAccessor));
        findGroup.addActionListener(e -> findGroup(Client.httpAccessor));

        storeSum.addActionListener(e -> {
            try {
                double value = Client.httpAccessor.value(null);
                JOptionPane.showMessageDialog(null, "Total value in store: " + value);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }});

        groupSum.addActionListener(e ->groupSum(Client.httpAccessor));

        backToProductsButton.addActionListener(e -> Client.cardLayout.show(Client.cardPanel, "product"));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(createButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(storeSum);
        buttonsPanel.add(groupSum);
        buttonsPanel.add(findGroup);

        Client.setFont(new JComponent[]{createButton, deleteButton, updateButton
                , backToProductsButton, storeSum, groupSum, findGroup});
        groupPanel.add(buttonsPanel, BorderLayout.CENTER);
        groupPanel.add(backToProductsButton, BorderLayout.PAGE_END);

        return groupPanel;
    }

    private static void createGroup(HttpAccessor accessor) {
        JFrame frame = new JFrame("Create Group");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JButton createButton = new JButton("Create");

        Client.setFont(new JComponent[]{nameLabel, nameField, descriptionLabel, descriptionField, createButton});
        createButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all values");
                return;
            }
            String name = nameField.getText();
            String description = descriptionField.getText();
            try {
                accessor.createGroup(name, description);
                JOptionPane.showMessageDialog(null, "Successfully created");
                nameField.setText("");
                descriptionField.setText("");
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel nameLabel = new JLabel("Group Name:");
        JTextField nameField = new JTextField();

        JButton deleteButton = new JButton("Delete");

        Client.setFont(new JComponent[]{nameLabel, nameField, deleteButton});

        deleteButton.addActionListener(e -> {
            if (nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all values");
                return;
            }
            String name = nameField.getText();
            try {
                accessor.deleteGroup(name);
                JOptionPane.showMessageDialog(null, "Successfully deleted");
                nameField.setText("");
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel idLabel = new JLabel("Old name:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("New name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JButton createButton = new JButton("Create");

        Client.setFont(new JComponent[]{idLabel, idField, nameLabel, nameField
                , descriptionLabel, descriptionField, createButton});
        createButton.addActionListener(e -> {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty()
                    || descriptionField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all values");
                return;
            }
            String id = idField.getText();
            String name = nameField.getText();
            String description = descriptionField.getText();
            try {
                accessor.updateGroup(id, name, description);
                JOptionPane.showMessageDialog(null, "Successfully updated");
                idField.setText("");
                nameField.setText("");
                descriptionField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(createButton);

        frame.getContentPane().add(panel);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    private static void groupSum(HttpAccessor accessor) {
        JFrame frame = new JFrame("Group Sum");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel idLabel = new JLabel("Group name:");
        JTextField idField = new JTextField();

        JButton createButton = new JButton("Calculate");

        Client.setFont(new JComponent[]{idLabel, idField, createButton});
        createButton.addActionListener(e -> {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all values");
                return;
            }
            String id = idField.getText();
            try {
                double value = accessor.value(id);
                JOptionPane.showMessageDialog(null, "Total price: " + value);
                idField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(createButton);

        frame.getContentPane().add(panel);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    public static void findGroup(HttpAccessor accessor) {
        try {
            String name = JOptionPane.showInputDialog("Enter group name:");
            Group group = accessor.findGroupId(name);
            if (group != null) {
                JFrame frame = new JFrame("Group Information");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel panel = new JPanel(new GridLayout(0, 2));

                JLabel nameLabel = new JLabel("Name:");
                JLabel nameValueLabel = new JLabel(group.getName());
                JLabel descriptionLabel = new JLabel("Description:");
                JLabel descriptionValueLabel = new JLabel(group.getDescription());

                panel.add(nameLabel);
                panel.add(nameValueLabel);
                panel.add(descriptionLabel);
                panel.add(descriptionValueLabel);

                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Group not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error finding group: " + e.getMessage());
        }
    }

}