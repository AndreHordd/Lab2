package menu;

import action.DialogManager;
import products.InventoryManager;
import products.Product;

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
        JMenu editMenu = new JMenu("Товари");
        JMenuItem addProductItem = new JMenuItem("Додати товар");
        addProductItem.addActionListener(e -> {
            dialogManager.showAddProductDialog(frame, inventoryManager);
            // Тут має бути логіка для додавання товару
        });
        JMenuItem editProductItem = new JMenuItem("Редагувати товар");
        editProductItem.addActionListener(e -> {
            Product selectedProduct = inventoryManager.getSelectedProduct(ContentViewPanel.getTable());
            if (selectedProduct != null) {
                DialogManager.showEditProductDialog(frame, selectedProduct, inventoryManager.getProductGroups(), inventoryManager);
            } else {
                JOptionPane.showMessageDialog(frame, "Будь ласка, виберіть продукт для редагування.", "Продукт не вибрано", JOptionPane.WARNING_MESSAGE);
            }
        });

        JMenu editGroupMenu = new JMenu("Група товарів");
        JMenuItem addGroupItem = new JMenuItem("Додати групу");
        addGroupItem.addActionListener(e -> DialogManager.onNewGroup(frame, inventoryManager));

        JMenuItem editGroupItem = new JMenuItem("Редагувати групу");
        editGroupItem.addActionListener(e -> DialogManager.showEditGroupDialog(frame, inventoryManager));


        JMenuItem deleteGroupItem = new JMenuItem("Видалити групу");
        deleteGroupItem.addActionListener(e -> {
            DialogManager.showDeleteGroupDialog(frame, inventoryManager);
        });


        JMenuItem deleteProductItem = new JMenuItem("Видалити товар");
        deleteProductItem.addActionListener(e -> {
            DialogManager.showDeleteProductDialog(frame, inventoryManager, ContentViewPanel.getTable());
        });


        editMenu.add(addProductItem);
        editMenu.add(editProductItem);
        editMenu.add(deleteProductItem);

        editGroupMenu.add(addGroupItem);
        editGroupMenu.add(editGroupItem);
        editGroupMenu.add(deleteGroupItem);

        // Додавання меню на панель меню
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(editGroupMenu);

        frame.setJMenuBar(menuBar);
    }

}
