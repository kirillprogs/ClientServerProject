package org.example.client;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Client {
    private final JFrame frame;
    protected static JPanel cardPanel;
    protected static CardLayout cardLayout;
    protected static HttpAccessor httpAccessor;

    public Client() {
        httpAccessor = new HttpAccessor();
        frame = new JFrame("Warehouse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        cardPanel = new JPanel();
        GroupPanel groupPanel = new GroupPanel();
        ProductPanel productPanel = new ProductPanel();
        cardLayout = new PanelSwitcher(productPanel, groupPanel);
        cardPanel.setLayout(cardLayout);

        cardPanel.add(createLoginPanel(), "login");
        cardPanel.add(groupPanel, "group");
        cardPanel.add(productPanel, "product");

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
        setFont(new JComponent[]{usernameLabel, usernameField, passwordLabel, passwordField, loginButton});
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            try {
                httpAccessor.login(username, new String(password));
                usernameField.setText("");
                passwordField.setText("");
                cardLayout.show(cardPanel, "product");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
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

    public static void setFont(JComponent[] array) {
        for (JComponent component : array)
            component.setFont(new Font("Arial", Font.PLAIN, 18));
    }

    public static JTable defaultTable(String[][] info, String[] column){
        JTable table = new JTable(info, column);
        DefaultTableModel tableModel = new DefaultTableModel(info, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        return table;
    }

    public static void main(String[] args) throws Exception {
        new Client();
    }
}