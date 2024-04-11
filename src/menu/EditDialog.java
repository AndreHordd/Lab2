package menu;

import products.Product;
import products.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditDialog extends JDialog {
    private JTextField nameField, manufacturerField, quantityField, priceField;
    private JTextArea descriptionField;
    private JComboBox<ProductGroup> groupComboBox;
    private JButton saveButton, cancelButton;
    private Product product;
    private List<ProductGroup> productGroups;

    public EditDialog(Frame owner, String title, boolean modal, Product product, List<ProductGroup> productGroups) {
        super(owner, title, modal);
        this.product = product;
        this.productGroups = productGroups;
        initComponents();
        fillData();
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

        JPanel panel = setupPanel();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(setupButtonsPanel(), BorderLayout.SOUTH);

        pack();

        saveButton.addActionListener(e -> onSave(productGroups));
        cancelButton.addActionListener(e -> setVisible(false));
    }

    private void fillData() {
        nameField.setText(product.getName());
        descriptionField.setText(product.getDescription());
        manufacturerField.setText(product.getManufacturer());
        quantityField.setText(String.valueOf(product.getQuantity()));
        priceField.setText(String.valueOf(product.getPrice()));
        groupComboBox.setSelectedItem(productGroups.stream().filter(pg -> pg.getProducts().contains(product)).findFirst().orElse(null));
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
        return buttonsPanel;
    }

    private void onSave(List<ProductGroup> groups) { // Припускаємо, що groups передається як параметр
        // Перевірка, чи поля не порожні
        ProductGroup selectedGroup = (ProductGroup) groupComboBox.getSelectedItem();
        product.setProductGroup(selectedGroup);
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

            product.setName(nameField.getText().trim());
            product.setDescription(descriptionField.getText().trim());
            product.setManufacturer(manufacturerField.getText().trim());
            product.setQuantity(quantity);
            product.setPrice(price);

            JOptionPane.showMessageDialog(this, "Продукт успішно збережено.", "Збереження", JOptionPane.INFORMATION_MESSAGE);

            setVisible(false); // Закриваємо діалог або оновлюємо інтерфейс

            // Оновлюємо дані в таблиці
            ContentViewPanel.refreshTableData(groups); // Передаємо оновлений список груп товарів

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Кількість та ціна повинні бути числами.", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}

