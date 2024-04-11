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
        JMenuItem importItem = new JMenuItem("Імпорт");
        JMenuItem exportItem = new JMenuItem("Експорт");
        JMenuItem exitItem = new JMenuItem("Вихід");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(importItem);
        fileMenu.add(exportItem);
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

        JMenu infoItem = new JMenu("Інформація");
        JMenuItem statItem = new JMenuItem("Статистика");
        statItem.addActionListener(e -> {
            // Тут має бути логіка для відображення статистики
        });

        JMenuItem searchItem = new JMenuItem("Пошук");
        searchItem.addActionListener(e -> {
            // Тут має бути логіка для пошуку
        });

        infoItem.add(statItem);
        infoItem.add(searchItem);

        JMenu manageMenu = new JMenu("Управління");
        JMenuItem writeOffItem = new JMenuItem("Списати товар");
        writeOffItem.addActionListener(e -> {
            // Тут має бути логіка для списання товару
        });

        JMenuItem additionItem = new JMenuItem("Поповнення товару");
        additionItem.addActionListener(e -> {
            // Тут має бути логіка для поповнення товару
        });

        manageMenu.add(writeOffItem);
        manageMenu.add(additionItem);

        // Додавання меню на панель меню
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(editGroupMenu);
        menuBar.add(infoItem);
        menuBar.add(manageMenu);

        frame.setJMenuBar(menuBar);
    }

}
