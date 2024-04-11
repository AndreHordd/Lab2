package menu;

import products.InventoryManager;
import products.Product;

import javax.swing.*;
import java.awt.*;

public class ToolBarBuilder {

    public static void createToolBar(JFrame frame, InventoryManager inventoryManager) {
        JToolBar toolBar = new JToolBar();

        JButton addButton = new JButton("Додати");
        addButton.addActionListener(e ->
                System.out.println("Додавання продукту (діалог ще не реалізовано)")
        );

        JButton editButton = new JButton("Редагувати");
        editButton.addActionListener(e -> {
            JTable table = ContentViewPanel.getTable();
            Product selectedProduct = inventoryManager.getSelectedProduct(table);
            if (selectedProduct != null) {
                System.out.println("Редагування продукту: " + selectedProduct.getName() + " (діалог ще не реалізовано)");
            } else {
                JOptionPane.showMessageDialog(frame, "Будь ласка, виберіть продукт для редагування.", "Вибір продукту", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton deleteButton = new JButton("Видалити");
        deleteButton.addActionListener(e -> {
            Product selectedProduct = inventoryManager.getSelectedProduct(ContentViewPanel.getTable());
            if (selectedProduct != null) {
                // Видаляємо продукт з його групи
                selectedProduct.getProductGroup().removeProduct(selectedProduct);

                // Оновлюємо список продуктів у групі та оновлюємо відображення таблиці
                ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());

                JOptionPane.showMessageDialog(null, "Продукт успішно видалений.", "Видалення продукту", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Будь ласка, виберіть продукт для видалення.", "Продукт не вибрано", JOptionPane.WARNING_MESSAGE);
            }
        });


        JButton searchButton = new JButton("Пошук");
        searchButton.addActionListener(e ->
                System.out.println("Пошук продукту (діалог ще не реалізовано)")
        );

        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);
        toolBar.add(searchButton);

        frame.add(toolBar, BorderLayout.NORTH);
    }
}
