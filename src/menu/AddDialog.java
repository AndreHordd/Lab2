package menu;

import products.ProductGroup;

import javax.swing.*;
import java.util.List;
import java.awt.*;

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