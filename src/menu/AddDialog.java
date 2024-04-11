package menu;

import products.Product;
import products.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddDialog extends JDialog {
    private JTextField nameField, manufacturerField, quantityField, priceField;
    private JTextArea descriptionField;
    private JComboBox<ProductGroup> groupComboBox;
    private JButton saveButton, cancelButton, newGroupButton;

    private List<ProductGroup> productGroups;

    public AddDialog(Frame owner, String title, boolean modal, List<ProductGroup> productGroups) {
        super(owner, title, modal);
        this.productGroups = productGroups;
        initComponents();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        descriptionField = new JTextArea(5, 20);
        manufacturerField = new JTextField(20);
        quantityField = new JTextField(20);
        priceField = new JTextField(20);

        groupComboBox = new JComboBox<>(new DefaultComboBoxModel<>(productGroups.toArray(new ProductGroup[0])));
        saveButton = new JButton("Зберегти");
        cancelButton = new JButton("Скасувати");
        newGroupButton = new JButton("Нова група");

        JPanel panel = setupPanel();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(setupButtonsPanel(), BorderLayout.SOUTH);

        pack();

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> setVisible(false));
        newGroupButton.addActionListener(e -> onNewGroup());
    }

    private JPanel setupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(2, 2, 2, 2);

        panel.add(new JLabel("Назва:"), c);
        panel.add(nameField, c);
        panel.add(new JLabel("Опис:"), c);
        panel.add(new JScrollPane(descriptionField), c);
        panel.add(new JLabel("Виробник:"), c);
        panel.add(manufacturerField, c);
        panel.add(new JLabel("Кількість:"), c);
        panel.add(quantityField, c);
        panel.add(new JLabel("Ціна:"), c);
        panel.add(priceField, c);
        panel.add(new JLabel("Група продуктів:"), c);
        panel.add(groupComboBox, c);

        return panel;
    }

    private JPanel setupButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(newGroupButton);
        return buttonsPanel;
    }

    private void onSave() {
        // Перевірка, чи поля не порожні
        if (nameField.getText().trim().isEmpty() || descriptionField.getText().trim().isEmpty() ||
                manufacturerField.getText().trim().isEmpty() || quantityField.getText().trim().isEmpty() ||
                priceField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Усі поля повинні бути заповнені.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            if (quantity <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(this, "Кількість та ціна повинні бути позитивними числами.", "Помилка", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Кількість та ціна повинні бути числами.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            String manufacturer = manufacturerField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            ProductGroup selectedGroup = (ProductGroup) groupComboBox.getSelectedItem();

            // Створення нового продукту
            Product newProduct = new Product(name, description, manufacturer, quantity, price, selectedGroup);

            // Додавання продукту до вибраної групи
            if (selectedGroup != null) {
                JOptionPane.showMessageDialog(this, "Продукт успішно збережено.", "Збереження", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false); // Закриваємо діалог
            } else {
                JOptionPane.showMessageDialog(this, "Виберіть групу продукту.", "Група не вибрана", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Кількість та ціна повинні бути числами.", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onNewGroup() {
        String groupName = JOptionPane.showInputDialog(this, "Назва нової групи:");
        if (groupName != null && !groupName.trim().isEmpty()) {
            String groupDescription = JOptionPane.showInputDialog(this, "Опис нової групи:");
            if (groupDescription != null && !groupDescription.trim().isEmpty()) {
                ProductGroup newGroup = new ProductGroup(groupName.trim(), groupDescription.trim());
                if (newGroup != null) {
                    productGroups.add(newGroup);
                    groupComboBox.addItem(newGroup);
                    groupComboBox.setSelectedItem(newGroup);
                }
            }
        }
    }
}