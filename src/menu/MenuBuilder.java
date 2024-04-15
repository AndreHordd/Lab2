package menu;

import action.DialogManager;
import products.InventoryManager;
import products.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class MenuBuilder {
    public static JMenuItem resetFilterItem = new JMenuItem("Скинути фільтр");
    private static DialogManager dialogManager;

    public MenuBuilder() {
        dialogManager = new DialogManager();
        // Other initialization code...
    }

    public static void createMenuBar(MainFrame frame, InventoryManager inventoryManager) {
        JMenuBar menuBar = new JMenuBar();

        // Створення меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem importItem = new JMenuItem("Імпорт");
        JMenuItem exportItem = new JMenuItem("Експорт");
        JMenuItem exitItem = new JMenuItem("Вихід");
        exitItem.addActionListener(e -> System.exit(0));

        importItem.addActionListener(e -> {
            dialogManager.showImportDialog(frame, inventoryManager);
        });

        exportItem.addActionListener(e -> {
            dialogManager.showExportDialog(frame, inventoryManager);
        });

        fileMenu.add(importItem);
        fileMenu.add(exportItem);
        fileMenu.addSeparator(); // Додаємо роздільник
        fileMenu.add(exitItem);

        JMenu editGroupMenu = new JMenu("Група товарів");
        JMenuItem addGroupItem = new JMenuItem("Додати групу");
        addGroupItem.addActionListener(e -> DialogManager.showNewGroupDialog(frame, inventoryManager));

        JMenuItem editGroupItem = new JMenuItem("Редагувати групу");
        editGroupItem.addActionListener(e -> DialogManager.showEditGroupDialog(frame, inventoryManager));


        JMenuItem deleteGroupItem = new JMenuItem("Видалити групу");
        deleteGroupItem.addActionListener(e -> {
            DialogManager.showDeleteGroupDialog(frame, inventoryManager);
        });

        // Створення меню "Редагувати"
        JMenu editMenu = new JMenu("Товари");
        JMenuItem addProductItem = new JMenuItem("Додати товар");
        addProductItem.addActionListener(e -> {
            dialogManager.showAddProductDialog(frame, inventoryManager);
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
        JMenuItem storageStatItem = new JMenuItem("Статистика складу");
        storageStatItem.addActionListener(e -> {
            DialogManager.showStorageStatDialog(frame, inventoryManager);
        });

        JMenuItem groupStatItem = new JMenuItem("Статистика групи");
        groupStatItem.addActionListener(e -> {
            DialogManager.showGroupStatDialog(frame, inventoryManager);
        });

        JMenuItem storagePriceItem = new JMenuItem("Загальна вартість складу");
        storagePriceItem.addActionListener(e -> {
            DialogManager.showStoragePriceDialog(frame, inventoryManager);
        });

        JMenuItem groupPriceItem = new JMenuItem("Загальна вартість групи");
        groupPriceItem.addActionListener(e -> {
            DialogManager.showGroupPriceDialog(frame, inventoryManager);
        });


        JMenuItem searchItem = new JMenuItem("Пошук");
        searchItem.addActionListener(e -> {
            searchItem.setEnabled(false);
            DialogManager.showSearchDialog(frame);
        });

        resetFilterItem.addActionListener(e -> {
            JTable table = ContentViewPanel.getTable();
            if (table.getRowSorter() != null) {
                ((TableRowSorter<DefaultTableModel>) table.getRowSorter()).setRowFilter(null);
            }
            resetFilterItem.setEnabled(false);
            searchItem.setEnabled(true);
        });

        infoItem.add(storageStatItem);
        infoItem.add(groupStatItem);
        infoItem.addSeparator();
        infoItem.add(storagePriceItem);
        infoItem.add(groupPriceItem);
        infoItem.addSeparator();
        infoItem.add(searchItem);
        infoItem.add(resetFilterItem); // Додайте цей елемент до відповідного меню
        resetFilterItem.setEnabled(false);

        JMenu manageMenu = new JMenu("Управління");
        JMenuItem writeOffItem = new JMenuItem("Списати товар");
        writeOffItem.addActionListener(e -> {
            Product selectedProduct = inventoryManager.getSelectedProduct(ContentViewPanel.getTable());
            if (selectedProduct != null) {
                DialogManager.showWriteOffDialog(frame, inventoryManager, selectedProduct);
            } else {
                JOptionPane.showMessageDialog(frame, "Будь ласка, виберіть продукт для списання", "Продукт не вибрано", JOptionPane.WARNING_MESSAGE);
            }
        });

        JMenuItem additionItem = new JMenuItem("Поповнення товару");
        additionItem.addActionListener(e -> {
            Product selectedProduct = inventoryManager.getSelectedProduct(ContentViewPanel.getTable());
            if (selectedProduct != null) {
                DialogManager.showAdditionDialog(frame, inventoryManager, selectedProduct);
            } else {
                JOptionPane.showMessageDialog(frame, "Будь ласка, виберіть продукт для поповнення", "Продукт не вибрано", JOptionPane.WARNING_MESSAGE);
            }
        });

        manageMenu.add(writeOffItem);
        manageMenu.add(additionItem);

        // Додавання меню на панель меню
        menuBar.add(fileMenu);
        menuBar.add(editGroupMenu);
        menuBar.add(editMenu);
        menuBar.add(infoItem);
        menuBar.add(manageMenu);

        frame.setJMenuBar(menuBar);
    }

}
