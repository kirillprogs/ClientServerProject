package org.example.client;

import org.example.entity.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.client.Client.defaultTable;

public class ProductPanel extends JPanel {

    private JScrollPane scrollPane;


    public ProductPanel() {
        super(new BorderLayout());
        JButton createButton = new JButton("Create");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton increaseButton = new JButton("Increase");
        JButton decreaseButton = new JButton("Decrease");
        JButton findProductButton = new JButton("Find");
        JButton backToGroupsButton = new JButton("Back to Groups");

        createButton.addActionListener(e -> createProduct(Client.httpAccessor));

        deleteButton.addActionListener(e -> deleteProduct(Client.httpAccessor));

        updateButton.addActionListener(e -> updateProduct(Client.httpAccessor));

        increaseButton.addActionListener(e -> increaseAmount(Client.httpAccessor));

        decreaseButton.addActionListener(e -> decreaseAmount(Client.httpAccessor));
        findProductButton.addActionListener(e -> findProduct(Client.httpAccessor));

        backToGroupsButton.addActionListener(e -> Client.cardLayout.show(Client.cardPanel, "group"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);
        buttonPanel.add(findProductButton);

        Client.setFont(new JComponent[]{createButton, deleteButton, updateButton
                , increaseButton, decreaseButton, backToGroupsButton, findProductButton});
        add(buttonPanel, BorderLayout.NORTH);
        add(backToGroupsButton, BorderLayout.SOUTH);
    }

    public void retrieveAll() {
        if (scrollPane != null)
            remove(scrollPane);
        List<Product> products;
        try {
            products = Client.httpAccessor.allProducts(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving products: " + e.getMessage());
            products = new ArrayList<>();
        }
        String[] column = {"Name", "Group", "Description", "Amount", "Price"};
        String[][] info = new String[products.size()][5];
        int i = 0;
        for (Product product : products) {
            info[i++] = new String[] {product.getName(), product.getGroup_name(),
                    product.getDescription(), "" + product.getAmount(), "" + product.getPrice()};
        }
        JTable table = defaultTable(info,column);
        this.scrollPane = new JScrollPane(table);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
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
                JOptionPane.showMessageDialog(null, "Please fill in all values");
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
                    JOptionPane.showMessageDialog(null, "Successfully created");
                    nameField.setText("");
                    descriptionField.setText("");
                    groupField.setText("");
                    amountField.setText("");
                    priceField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Incorrect numeric format");
            }
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

    private static void updateProduct(HttpAccessor accessor) {
        JFrame frame = new JFrame("Update Product");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel idLabel = new JLabel("Old name:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("New name:");
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

        Client.setFont(new JComponent[]{idLabel, idField, nameLabel, nameField, descriptionLabel
                , descriptionField, amountLabel, amountField, priceLabel, priceField, groupLabel
                , groupField, createButton});
        createButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || idField.getText().isEmpty()
                    || descriptionField.getText().isEmpty()
                    || amountField.getText().isEmpty()
                    || priceField.getText().isEmpty()
                    || groupField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all values");
                return;
            }
            try {
                String id = idField.getText();
                String name = nameField.getText();
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                double price = Double.parseDouble(priceField.getText());
                String group = groupField.getText();
                try {
                    accessor.updateProduct(id, name, group, description, amount, price);
                    JOptionPane.showMessageDialog(null, "Successfully updated");
                    idField.setText("");
                    nameField.setText("");
                    descriptionField.setText("");
                    groupField.setText("");
                    amountField.setText("");
                    priceField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Incorrect numeric format");
            }
        });
        panel.add(idLabel);
        panel.add(idField);
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
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
                httpAccessor.deleteProduct(name);
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

    public static void increaseAmount(HttpAccessor accessor) {
        JFrame frame = new JFrame("Increase Amount");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel amountLabel = new JLabel("Amount (must be greater than 0):");
        JTextField amountField = new JTextField();

        JButton increaseButton = new JButton("Increase");

        increaseButton.addActionListener(e -> {
            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            try {
                accessor.change(name, amount);
                JOptionPane.showMessageDialog(null, "Successfully increased");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        Client.setFont(new JComponent[]{nameLabel, nameField, amountLabel, amountField, increaseButton});

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(increaseButton);

        frame.getContentPane().add(panel);
        frame.setSize(400, 100);
        frame.setVisible(true);
    }

    public static void decreaseAmount(HttpAccessor accessor) {
        JFrame frame = new JFrame("Decrease Amount");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();

        JButton decreaseButton = new JButton("Decrease");


        Client.setFont(new JComponent[]{nameLabel, nameField, amountLabel, amountField, decreaseButton});

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(decreaseButton);

        decreaseButton.addActionListener(e -> {
            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            try {
                accessor.change(name, -Math.abs(amount));
                JOptionPane.showMessageDialog(null, "Successfully decreased");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }

    public static void findProduct(HttpAccessor accessor) {
        try {
            String name = JOptionPane.showInputDialog("Enter product name:");
            Product product = accessor.findProductId(name);
            if (product != null) {

                JFrame frame = new JFrame("Product Info");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));


                panel.add(new JLabel("Name:"));
                panel.add(new JLabel(product.getName()));
                panel.add(new JLabel("Description:"));
                panel.add(new JLabel(product.getDescription()));
                panel.add(new JLabel("Amount:"));
                panel.add(new JLabel(String.valueOf(product.getAmount())));
                panel.add(new JLabel("Price:"));
                panel.add(new JLabel(String.valueOf(product.getPrice())));
                panel.add(new JLabel("Group:"));
                panel.add(new JLabel(product.getGroup_name()));

                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
            } else {

                JOptionPane.showMessageDialog(null, "Product not found.");
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error finding product: " + e.getMessage());
        }
    }
}