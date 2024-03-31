import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GroupDialog extends JDialog {
    private JTextField nameField;
    private JTextArea descriptionField;
    private JButton saveButton;
    private JButton cancelButton;

    public GroupDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initComponents();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        descriptionField = new JTextArea(5, 20);
        saveButton = new JButton("Зберегти");
        cancelButton = new JButton("Скасувати");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("Назва:"), c);

        c.gridx = 1; c.gridy = 0;
        panel.add(nameField, c);

        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Опис:"), c);

        c.gridx = 1; c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel.add(new JScrollPane(descriptionField), c);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> setVisible(false));
    }

    private void onSave() {
        // Тут можна додати логіку обробки збереження
        System.out.println("Збереження: " + nameField.getText() + ", Опис: " + descriptionField.getText());
        setVisible(false);
    }
}
