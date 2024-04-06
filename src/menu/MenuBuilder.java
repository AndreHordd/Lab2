package menu;

import action.DialogManager;
import products.InventoryManager;

import javax.swing.*;

public class MenuBuilder {
    private static DialogManager dialogManager;

    public static void createMenuBar(MainFrame frame, InventoryManager inventoryManager) {
        JMenuBar menuBar = new JMenuBar();

        // Створення меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem newItem = new JMenuItem("Новий");
        newItem.addActionListener(e -> {
            // Тут має бути логіка для додавання нового елементу
        });
        JMenuItem openItem = new JMenuItem("Відкрити");
        openItem.addActionListener(e -> {
            // Тут має бути логіка для відкриття файлу
        });
        JMenuItem saveItem = new JMenuItem("Зберегти");
        saveItem.addActionListener(e -> {
            // Тут має бути логіка для збереження файлу
        });
        JMenuItem exitItem = new JMenuItem("Вихід");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // Додаємо роздільник
        fileMenu.add(exitItem);

        // Створення меню "Редагувати"
        JMenu editMenu = new JMenu("Редагувати");
        JMenuItem addProductItem = new JMenuItem("Додати товар");
        addProductItem.addActionListener(e -> {
            dialogManager.showAddProductDialog(frame, inventoryManager);
            // Тут має бути логіка для додавання товару
        });
        JMenuItem editProductItem = new JMenuItem("Редагувати товар");
        editProductItem.addActionListener(e -> {
            // Тут має бути логіка для редагування товару
        });
        JMenuItem deleteProductItem = new JMenuItem("Видалити товар");
        deleteProductItem.addActionListener(e -> {
            // Тут має бути логіка для видалення товару
        });

        editMenu.add(addProductItem);
        editMenu.add(editProductItem);
        editMenu.add(deleteProductItem);

        // Додавання меню на панель меню
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        frame.setJMenuBar(menuBar);
    }

}
