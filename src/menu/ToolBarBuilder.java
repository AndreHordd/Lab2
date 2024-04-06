package menu;

import action.DialogManager;
import products.InventoryManager;
import products.Product;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class ToolBarBuilder {

    private static DialogManager dialogManager = new DialogManager();
    public static void createToolBar(MainFrame frame, InventoryManager inventoryManager) {
        JToolBar toolBar = new JToolBar();

        JButton addButton = new JButton("Додати");
        addButton.addActionListener(e ->
                dialogManager.showAddProductDialog(frame, inventoryManager)
            );

        JButton editButton = new JButton("Редагувати");
        // Додаємо логіку для редагування вибраного елементу

        JButton deleteButton = new JButton("Видалити");
        // Додаємо логіку для видалення вибраного елементу

        JButton searchButton = new JButton("Пошук");
        searchButton.addActionListener(e -> {
            showSearchDialog(frame, inventoryManager);
        });

        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);
        toolBar.add(searchButton);

        frame.add(toolBar, BorderLayout.NORTH);
    }

    private static void showSearchDialog(MainFrame frame, InventoryManager inventoryManager) {
        // Метод для відображення діалогу пошуку товару
        // Ви можете використовувати JOptionPane для простого вводу або створити власний діалог, як у прикладі з додаванням товару
        // У методі showSearchDialog або подібному

        String productName = JOptionPane.showInputDialog(frame, "Введіть назву товару для пошуку:");
        List<Product> foundProducts = inventoryManager.searchProductsByName(productName);
        ContentViewPanel.updateTableWithSearchResults(foundProducts);

    }
}
