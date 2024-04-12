package action;

import menu.AddDialog;
import menu.ContentViewPanel;
import menu.EditDialog;
import menu.MainFrame;
import products.InventoryManager;
import products.Product;
import products.ProductGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
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

    public static void showSearchDialog(MainFrame frame, InventoryManager inventoryManager) {
        // Ask the user for the search query
        String searchQuery = JOptionPane.showInputDialog(frame, "Введіть пошуковий запит:", "Пошук товару", JOptionPane.QUESTION_MESSAGE);
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // Filter the products based on the search query
            List<Product> filteredProducts = inventoryManager.searchProducts(searchQuery.trim());
            if (filteredProducts.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Товар не знайдено", "Результат", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // Create a new JTable and populate it with the filtered products
            String[] columnNames = {"Група", "Опис групи", "Назва товару", "Опис товару", "Виробник", "Кількість на складі", "Ціна за одиницю"};
            Object[][] data = new Object[filteredProducts.size()][7];
            for (int i = 0; i < filteredProducts.size(); i++) {
                Product product = filteredProducts.get(i);
                data[i][0] = product.getProductGroup().getName();
                data[i][1] = product.getProductGroup().getDescription();
                data[i][2] = product.getName();
                data[i][3] = product.getDescription();
                data[i][4] = product.getManufacturer();
                data[i][5] = product.getQuantity();
                data[i][6] = product.getPrice();
            }
            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable table = new JTable(model);

            // Create a JScrollPane to make the JTable scrollable
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(700, 500)); // Set the preferred size of the scroll pane

            // Create a JOptionPane with the scroll pane
            JOptionPane.showMessageDialog(frame, scrollPane, "Результат", JOptionPane.INFORMATION_MESSAGE);
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

    public static void showWriteOffDialog(MainFrame frame, InventoryManager inventoryManager) {
        // Prepare the list of product names for the dropdown
        List<String> productNames = new ArrayList<>();
        for (Product product : inventoryManager.getAllProducts()) {
            productNames.add(product.getProductGroup().getName() + " - " + product.getName());
        }

        // Ask the user to select a product for write-off
        String selectedProductName = (String) JOptionPane.showInputDialog(frame, "Виберіть товар для списання:",
                "Вибір товару", JOptionPane.QUESTION_MESSAGE, null,
                productNames.toArray(), null);

        if (selectedProductName != null) {
            // Find the selected product
            Product selectedProduct = null;
            for (Product product : inventoryManager.getAllProducts()) {
                if ((product.getProductGroup().getName() + " - " + product.getName()).equals(selectedProductName)) {
                    selectedProduct = product;
                    break;
                }
            }

            if (selectedProduct != null) {
                // Ask the user to enter the quantity to remain after write-off
                SpinnerNumberModel model = new SpinnerNumberModel(selectedProduct.getQuantity(), 0, selectedProduct.getQuantity(), 1);
                JSpinner spinner = new JSpinner(model);
                int result = JOptionPane.showConfirmDialog(frame, spinner, "К-сть товару \"" + selectedProductName + "\"", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                    if (result > selectedProduct.getQuantity()) {
                        JOptionPane.showMessageDialog(frame, "Кількість товару для списання не може перевищувати кількість товару на складі", "Помилка списання товару", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int remainingQuantity = (Integer) spinner.getValue();
                    int quantityToWriteOff = selectedProduct.getQuantity() - remainingQuantity;

                    // Subtract the quantity from the current quantity of the product
                    selectedProduct.setQuantity(remainingQuantity);

                    // If the quantity of the product becomes 0, remove the product from the inventory
                    if (selectedProduct.getQuantity() == 0) {
                        selectedProduct.getProductGroup().removeProduct(selectedProduct);
                        JOptionPane.showMessageDialog(frame, "Всі одиниці товару \"" + selectedProduct.getName() + "\" були продані", "Результат", JOptionPane.INFORMATION_MESSAGE);
                    } else if (quantityToWriteOff == 0) {
                        JOptionPane.showMessageDialog(frame, "Кількість товару \"" + selectedProduct.getName() + "\" не змінилася", "Результат", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Продано " + quantityToWriteOff + " одиниць товару \"" + selectedProduct.getName() + "\"", "Результат", JOptionPane.INFORMATION_MESSAGE);
                    }

                    // Update the table
                    ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
                }
            }
        }
    }

    public static void showAdditionDialog(MainFrame frame, InventoryManager inventoryManager) {
        // Prepare the list of product names for the dropdown
        List<String> productNames = new ArrayList<>();
        for (Product product : inventoryManager.getAllProducts()) {
            productNames.add(product.getProductGroup().getName() + " - " + product.getName());
        }

        // Ask the user to select a product for addition
        String selectedProductName = (String) JOptionPane.showInputDialog(frame, "Виберіть товар для поповнення:",
                "Вибір товару", JOptionPane.QUESTION_MESSAGE, null,
                productNames.toArray(), null);

        if (selectedProductName != null) {
            // Find the selected product
            Product selectedProduct = null;
            for (Product product : inventoryManager.getAllProducts()) {
                if ((product.getProductGroup().getName() + " - " + product.getName()).equals(selectedProductName)) {
                    selectedProduct = product;
                    break;
                }
            }

            if (selectedProduct != null) {
                // Ask the user to enter the quantity after addition
                SpinnerNumberModel model = new SpinnerNumberModel(selectedProduct.getQuantity(), selectedProduct.getQuantity(), Integer.MAX_VALUE, 1);
                JSpinner spinner = new JSpinner(model);
                int result = JOptionPane.showConfirmDialog(frame, spinner, "К-сть товару \"" + selectedProductName + "\"", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    int newQuantity = (Integer) spinner.getValue();
                    int quantityToAdd = newQuantity - selectedProduct.getQuantity();

                    if (quantityToAdd == 0) {
                        JOptionPane.showMessageDialog(frame, "Кількість товару \"" + selectedProduct.getName() + "\" не змінилася", "Результат", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    if (quantityToAdd < 0) {
                        JOptionPane.showMessageDialog(frame, "Кількість товару для поповнення повинна бути більшою за поточну кількість товару", "Результат", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Add the quantity to the current quantity of the product
                    selectedProduct.setQuantity(newQuantity);

                    JOptionPane.showMessageDialog(frame, "На склад прийшло " + quantityToAdd + " шт. товару \"" + selectedProduct.getName() + "\"", "Результат", JOptionPane.INFORMATION_MESSAGE);
                    // Update the table
                    ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
                }
            }
        }
    }

    // Other methods for showing different dialogs...
}
