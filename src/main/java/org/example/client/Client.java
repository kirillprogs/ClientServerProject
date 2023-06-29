package org.example.client;

import javax.swing.*;
import java.awt.*;

public class Client {
    private final JFrame frame;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private final Controller controller;

    public Client() {
        controller = new Controller();
        frame = new JFrame("Warehouse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        cardPanel.add(createLoginPanel(), "login");
        cardPanel.add(createGroupPanel(), "group");
        cardPanel.add(createProductPanel(), "product");

        frame.add(cardPanel);
        cardLayout.show(cardPanel, "login");
        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(10);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(10);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            if (controller.login(username, password) == 0)
                cardLayout.show(cardPanel, "group");
            else
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        });

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel());
        inputPanel.add(loginButton);

        loginPanel.add(inputPanel, BorderLayout.CENTER);

        return loginPanel;
    }

    private JPanel createGroupPanel() {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout());

        JLabel groupLabel = new JLabel("Welcome to the Group Window");
        groupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton switchToProductButton = new JButton("Switch to Product Window");
        switchToProductButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "product"); // Switch to the product window
        });

        groupPanel.add(groupLabel, BorderLayout.CENTER);
        groupPanel.add(switchToProductButton, BorderLayout.SOUTH);

        return groupPanel;
    }

    private JPanel createProductPanel() {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());

        JLabel productLabel = new JLabel("Welcome to the Product Window");
        productLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton switchToGroupButton = new JButton("Switch to Group Window");
        switchToGroupButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "group"); // Switch to the group window
        });

        productPanel.add(productLabel, BorderLayout.CENTER);
        productPanel.add(switchToGroupButton, BorderLayout.SOUTH);

        return productPanel;
    }

    public static void main(String[] args) {
        new Client();
    }
}