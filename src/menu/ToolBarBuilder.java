package menu;

import products.InventoryManager;

import javax.swing.*;
import java.awt.*;

public class ToolBarBuilder {

    public static void createToolBar(MainFrame frame, InventoryManager inventoryManager) {
        JToolBar toolBar = new JToolBar();

        JButton addButton = new JButton("Додати");
        addButton.addActionListener(e -> {
            // Тут має бути логіка для додавання нового елементу
            showAddProductDialog(frame, inventoryManager);
        });

        JButton editButton = new JButton("Редагувати");
        editButton.addActionListener(e -> {
            // Тут має бути логіка для редагування вибраного елементу
        });

        JButton deleteButton = new JButton("Видалити");
        deleteButton.addActionListener(e -> {
            // Тут має бути логіка для видалення вибраного елементу
        });

        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);

        frame.add(toolBar, BorderLayout.NORTH);
    }

    private static void showAddProductDialog(MainFrame frame, InventoryManager inventoryManager) {
        // Метод для відображення діалогу додавання нового товару
        // Приклад:
        AddDialog dialog = new AddDialog(frame, "Додати товар", true, inventoryManager.getProductGroups());
        dialog.setVisible(true);
    }
}
