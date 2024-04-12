package action;

import menu.*;
import products.InventoryManager;
import products.Product;
import products.ProductGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class DialogManager {

    public static void showAddProductDialog(JFrame parentFrame, InventoryManager inventoryManager) {
        AddDialog addDialog = new AddDialog(parentFrame, "Додати товар", true, inventoryManager.getProductGroups());
        addDialog.setVisible(true);
        // Assuming AddDialog updates the inventory manager with the new product,
        // you might want to refresh the table here as well if the dialog results in a new product being added.
        ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
    }

    public static void showEditProductDialog(JFrame owner, Product product, List<ProductGroup> productGroups, InventoryManager inventoryManager) {
        EditDialog editDialog = new EditDialog(owner, "Редагування продукту", true, product, productGroups);
        editDialog.setVisible(true);
        // Оновлення таблиці після закриття діалогу
        ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
    }

    public static void showDeleteProductDialog(JFrame parentFrame, InventoryManager inventoryManager, JTable table) {
        Product selectedProduct = inventoryManager.getSelectedProduct(table);
        if (selectedProduct != null) {
            // Підтвердження видалення
            int response = JOptionPane.showConfirmDialog(parentFrame, "Ви впевнені, що хочете видалити вибраний продукт?", "Видалення товару", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                // Видаляємо продукт з його групи
                selectedProduct.getProductGroup().removeProduct(selectedProduct);
                // Оновлюємо список продуктів у групі та оновлюємо відображення таблиці
                ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
                JOptionPane.showMessageDialog(parentFrame, "Продукт успішно видалений", "Результат", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Будь ласка, виберіть продукт для видалення", "Помилка", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void showNewGroupDialog(JFrame parentFrame, InventoryManager inventoryManager) {
        String groupName = JOptionPane.showInputDialog(parentFrame, "Назва нової групи:");
        if (groupName != null && !groupName.trim().isEmpty()) {
            String groupDescription = JOptionPane.showInputDialog(parentFrame, "Опис нової групи:");
            if (groupDescription != null && !groupDescription.trim().isEmpty()) {
                ProductGroup newGroup = new ProductGroup(groupName.trim(), groupDescription.trim());
                inventoryManager.getProductGroups().add(newGroup);
                // Оновлення віджетів, які використовують список груп, якщо вони існують
                // Тут можна додати оновлення інших інтерфейсів користувача, які залежать від списку груп
            }
        }
    }

    public static void showDeleteGroupDialog(JFrame parentFrame, InventoryManager inventoryManager) {
        List<ProductGroup> groups = inventoryManager.getProductGroups();
        if (groups.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Немає груп для видалення", "Помилка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ProductGroup selectedGroup = (ProductGroup) JOptionPane.showInputDialog(parentFrame,
                "Виберіть групу для видалення:", "Видалення групи",
                JOptionPane.QUESTION_MESSAGE, null,
                groups.toArray(), groups.get(0));

        if (selectedGroup != null) {
            int response = JOptionPane.showConfirmDialog(parentFrame,
                    "Ви впевнені, що хочете видалити групу " + selectedGroup.getName() + "?", "Підтвердження видалення",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                inventoryManager.getProductGroups().remove(selectedGroup);
                ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
                // Оновлення UI тут, якщо потрібно
                JOptionPane.showMessageDialog(parentFrame, "Групу успішно видалено", "Результат", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void showEditGroupDialog(JFrame parentFrame, InventoryManager inventoryManager) {
        // Вибір групи для редагування
        Object selectedGroup = JOptionPane.showInputDialog(parentFrame, "Виберіть групу для редагування:",
                "Вибір групи", JOptionPane.QUESTION_MESSAGE, null,
                inventoryManager.getProductGroups().toArray(), null);

        if (selectedGroup instanceof ProductGroup) {
            ProductGroup group = (ProductGroup) selectedGroup;

            // Запит нової назви та опису для групи
            String newGroupName = (String) JOptionPane.showInputDialog(
                    parentFrame,
                    "Введіть нову назву групи:",
                    "Редагування групи",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    group.getName()
            );

            String newGroupDescription = (String) JOptionPane.showInputDialog(
                    parentFrame,
                    "Введіть новий опис групи:",
                    "Редагування групи",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    group.getDescription()
            );

            if (newGroupName != null && !newGroupName.trim().isEmpty() && newGroupDescription != null && !newGroupDescription.trim().isEmpty()) {
                // Оновлення інформації групи
                group.setName(newGroupName.trim());
                group.setDescription(newGroupDescription.trim());

                ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
                // Тут можна додати оновлення віджетів або інтерфейсів, які залежать від списку груп
                // Наприклад, оновлення відображення таблиці або випадаючих списків
            }
        }
    }

    public static void showSearchDialog(MainFrame frame) {
        // Ask the user for the search query
        String searchQuery = JOptionPane.showInputDialog(frame, "Введіть пошуковий запит:", "Пошук товару", JOptionPane.QUESTION_MESSAGE);
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // Get the table and its model
            JTable table = ContentViewPanel.getTable();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Create a TableRowSorter and set it to the table
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            // Create a RowFilter and set it to the table's row sorter
            RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    // Check if the product name contains the search query
                    String productName = ((String) entry.getValue(2)).toLowerCase(); // Assuming the product name is in the third column
                    return productName.contains(searchQuery.trim().toLowerCase());
                }
            };
            table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
            MenuBuilder.resetFilterItem.setEnabled(true); // Enable the reset filter menu item
            sorter.setRowFilter(filter);
        }
    }

    public static void showStorageStatDialog(MainFrame frame, InventoryManager inventoryManager) {
        // Get the statistics from the inventory
        String statistics = inventoryManager.getStatistics();

        // Create a JTextArea to display the statistics
        JTextArea textArea = new JTextArea(statistics);
        textArea.setEditable(false);

        // Create a JScrollPane to make the JTextArea scrollable
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 500)); // Set the preferred size of the scroll pane

        // Create a JOptionPane with the scroll pane
        JOptionPane.showMessageDialog(frame, scrollPane, "Статистика складу", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showGroupStatDialog(MainFrame frame, InventoryManager inventoryManager) {
        Object selectedGroup = JOptionPane.showInputDialog(frame, "Виберіть групу для відображення статистики:",
                "Вибір групи", JOptionPane.QUESTION_MESSAGE, null,
                inventoryManager.getProductGroups().toArray(), null);

        if (selectedGroup instanceof ProductGroup) {
            ProductGroup group = (ProductGroup) selectedGroup;
            String statistics = group.getStatistics();

            JTextArea textArea = new JTextArea(statistics);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(700, 500));

            JOptionPane.showMessageDialog(frame, scrollPane, "Статистика групи \"" + group.getName() + "\"", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void showStoragePriceDialog(MainFrame frame, InventoryManager inventoryManager) {
        JOptionPane.showMessageDialog(frame, "Загальна вартість складу: " + inventoryManager.calculateInventoryPrice(), "Результат", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showGroupPriceDialog(MainFrame frame, InventoryManager inventoryManager) {
        Object selectedGroup = JOptionPane.showInputDialog(frame, "Виберіть групу для розрахунку вартості:",
                "Вибір групи", JOptionPane.QUESTION_MESSAGE, null,
                inventoryManager.getProductGroups().toArray(), null);

        if (selectedGroup instanceof ProductGroup) {
            ProductGroup group = (ProductGroup) selectedGroup;
            JOptionPane.showMessageDialog(frame, "Загальна вартість групи \"" + group.getName() + "\": " + group.calculateGroupPrice(), "Результат", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void showWriteOffDialog(MainFrame frame, InventoryManager inventoryManager, Product selectedProduct) {
        if (selectedProduct != null) {
            // Ask the user to enter the quantity to remain after write-off
            SpinnerNumberModel model = new SpinnerNumberModel(selectedProduct.getQuantity(), Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
            JSpinner spinner = new JSpinner(model);
            int result = JOptionPane.showConfirmDialog(frame, spinner, "К-сть товару \"" + selectedProduct.getName() + "\" після списання", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                int remainingQuantity = (Integer) spinner.getValue();

                if (remainingQuantity < 0) {
                    JOptionPane.showMessageDialog(frame, "Кількість товару після списання не може бути від'ємною", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (remainingQuantity > selectedProduct.getQuantity()) {
                    JOptionPane.showMessageDialog(frame, "Кількість товару після списання не може бути більшою за поточну кількість товару", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantityToWriteOff = selectedProduct.getQuantity() - remainingQuantity;

                // Subtract the quantity from the current quantity of the product
                selectedProduct.setQuantity(remainingQuantity);

                if(quantityToWriteOff == 0) {
                    JOptionPane.showMessageDialog(frame, "Кількість товару не змінилася", "Результат", JOptionPane.INFORMATION_MESSAGE);
                    ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
                    return;
                }

                // If the quantity of the product becomes 0, remove the product from the inventory
                if (selectedProduct.getQuantity() == 0) {
                    selectedProduct.getProductGroup().removeProduct(selectedProduct);
                    JOptionPane.showMessageDialog(frame, "Всі одиниці товару \"" + selectedProduct.getName() + "\" були списані", "Результат", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Списано " + quantityToWriteOff + " одиниць товару \"" + selectedProduct.getName() + "\"", "Результат", JOptionPane.INFORMATION_MESSAGE);
                }

                // Update the table
                ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
            }
        }
    }

    public static void showAdditionDialog(MainFrame frame, InventoryManager inventoryManager, Product selectedProduct) {
        if (selectedProduct != null) {
            // Ask the user to enter the quantity after addition
            SpinnerNumberModel model = new SpinnerNumberModel(selectedProduct.getQuantity(), Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
            JSpinner spinner = new JSpinner(model);
            int result = JOptionPane.showConfirmDialog(frame, spinner, "К-сть товару \"" + selectedProduct.getName() + "\" після додавання", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int newQuantity = (Integer) spinner.getValue();

                if (newQuantity < 0) {
                    JOptionPane.showMessageDialog(frame, "Кількість товару після додавання не може бути від'ємною", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantityToAdd = newQuantity - selectedProduct.getQuantity();

                if (quantityToAdd < 0) {
                    JOptionPane.showMessageDialog(frame, "Кількість товару для додавання повинна бути більшою за поточну кількість товару", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add the quantity to the current quantity of the product
                selectedProduct.setQuantity(newQuantity);

                if (quantityToAdd == 0) {
                    JOptionPane.showMessageDialog(frame, "Кількість товару не змінилася", "Результат", JOptionPane.INFORMATION_MESSAGE);
                    ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
                    return;
                }

                JOptionPane.showMessageDialog(frame, "Додано " + quantityToAdd + " одиниць товару \"" + selectedProduct.getName() + "\"", "Результат", JOptionPane.INFORMATION_MESSAGE);

                // Update the table
                ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
            }
        }
    }

    // Other methods for showing different dialogs...
}
