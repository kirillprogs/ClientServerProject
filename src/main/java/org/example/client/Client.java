package org.example.client;

import javax.swing.*;
import java.awt.*;

public class Client {
    private final JFrame frame;
    protected static  JPanel cardPanel;
    protected static CardLayout cardLayout;
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
        cardPanel.add(GroupPanel.createGroupPanel(), "group");
        cardPanel.add(ProductPanel.createProductPanel(), "product");

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

    public static void main(String[] args) {
        new Client();
    }
}