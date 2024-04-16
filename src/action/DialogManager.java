package action;

import menu.*;
import products.InventoryManager;
import products.Product;
import products.ProductGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * Class to manage dialogs
 */
public class DialogManager {

    /**
     * Method to show the add product dialog
     * @param parentFrame parent frame
     * @param inventoryManager inventory manager
     */
    public static void showAddProductDialog(JFrame parentFrame, InventoryManager inventoryManager) {
        AddDialog addDialog = new AddDialog(parentFrame, "Додати товар", true, inventoryManager);
        addDialog.setVisible(true);
        ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
    }

    /**
     * Method to show the edit product dialog
     * @param owner owner frame
     * @param product product to edit
     * @param productGroups list of product groups
     * @param inventoryManager inventory manager
     */
    public static void showEditProductDialog(JFrame owner, Product product, List<ProductGroup> productGroups, InventoryManager inventoryManager) {
        EditDialog editDialog = new EditDialog(owner, "Редагування продукту", true, product, productGroups, inventoryManager);
        editDialog.setVisible(true);
        // Оновлення таблиці після закриття діалогу
        ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
    }

    /**
     * Method to show the delete product dialog
     * @param parentFrame parent frame
     * @param inventoryManager inventory manager
     * @param table table to get the selected product
     */
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

    /**
     * Method to show the add group dialog
     * @param parentFrame parent frame
     * @param inventoryManager inventory manager
     */
    public static void showNewGroupDialog(JFrame parentFrame, InventoryManager inventoryManager) {
        String groupName = JOptionPane.showInputDialog(parentFrame, "Назва нової групи:");
        if (groupName != null && !groupName.trim().isEmpty()) {
            if (inventoryManager.isGroupExists(groupName.trim())) {
                JOptionPane.showMessageDialog(parentFrame, "Група з такою назвою вже існує", "Помилка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String groupDescription = JOptionPane.showInputDialog(parentFrame, "Опис нової групи:");
            if (groupDescription != null && !groupDescription.trim().isEmpty()) {
                ProductGroup newGroup = new ProductGroup(groupName.trim(), groupDescription.trim());
                inventoryManager.getProductGroups().add(newGroup);
                // Оновлення віджетів, які використовують список груп, якщо вони існують
                // Тут можна додати оновлення інших інтерфейсів користувача, які залежать від списку груп
            }
        }
    }

    /**
     * Method to show the delete group dialog
     * @param parentFrame parent frame
     * @param inventoryManager inventory manager
     */
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

    /**
     * Method to show the edit group dialog
     * @param parentFrame parent frame
     * @param inventoryManager inventory manager
     */
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

            if (inventoryManager.isGroupExists(newGroupName.trim()) && !newGroupName.trim().equals(group.getName())) {
                JOptionPane.showMessageDialog(parentFrame, "Група з такою назвою вже існує", "Помилка", JOptionPane.ERROR_MESSAGE);
                return;
            }

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
            }
        }
    }

    /**
     * Method to show the search dialog
     * @param frame parent frame
     */
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

    /**
     * Method to show the storage statistics dialog
     * @param frame parent frame
     * @param inventoryManager inventory manager
     */
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

    /**
     * Method to show the group statistics dialog
     * @param frame parent frame
     * @param inventoryManager inventory manager
     */
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

    /**
     * Method to show the storage price dialog
     * @param frame parent frame
     * @param inventoryManager inventory manager
     */
    public static void showStoragePriceDialog(MainFrame frame, InventoryManager inventoryManager) {
        JOptionPane.showMessageDialog(frame, "Загальна вартість складу: " + inventoryManager.calculateInventoryPrice(), "Результат", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method to show the group price dialog
     * @param frame parent frame
     * @param inventoryManager inventory manager
     */
    public static void showGroupPriceDialog(MainFrame frame, InventoryManager inventoryManager) {
        Object selectedGroup = JOptionPane.showInputDialog(frame, "Виберіть групу для розрахунку вартості:",
                "Вибір групи", JOptionPane.QUESTION_MESSAGE, null,
                inventoryManager.getProductGroups().toArray(), null);

        if (selectedGroup instanceof ProductGroup) {
            ProductGroup group = (ProductGroup) selectedGroup;
            JOptionPane.showMessageDialog(frame, "Загальна вартість групи \"" + group.getName() + "\": " + group.calculateGroupPrice(), "Результат", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Method to show the write-off dialog
     * @param frame parent frame
     * @param inventoryManager inventory manager
     * @param selectedProduct selected product
     */
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

                if (quantityToWriteOff == 0) {
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

    /**
     * Method to show the addition dialog
     * @param frame parent frame
     * @param inventoryManager inventory manager
     * @param selectedProduct selected product
     */
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

    /**
     * Method to show the import dialog
     * @param frame parent frame
     * @param inventoryManager inventory manager
     */
    public void showImportDialog(MainFrame frame, InventoryManager inventoryManager) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                ProductGroup currentGroup = null;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("@")) {
                        if (currentGroup != null) {
                            inventoryManager.importInventory(currentGroup);
                            currentGroup = null;
                        }
                        String[] groupDetails = line.substring(1).split(",");
                        String groupName = groupDetails[0].trim();
                        if (inventoryManager.isGroupExists(groupName)) {
                            throw new IllegalArgumentException("Група " + groupName + " вже існує");
                        }
                        currentGroup = new ProductGroup(groupName, groupDetails[1].trim());
                    } else if (line.startsWith("~")) {
                        if (currentGroup == null) {
                            throw new IllegalArgumentException("Товар знайдено до групи");
                        }
                        String[] productDetails = line.substring(1).split(",");
                        String productName = productDetails[0].trim();
                        if (inventoryManager.isProductExists(productName)) {
                            throw new IllegalArgumentException("Товар " + productName + " вже існує");
                        }
                        new Product(productName, productDetails[1].trim(), productDetails[2].trim(),
                                Integer.parseInt(productDetails[3].trim()), Double.parseDouble(productDetails[4].trim()), currentGroup);
                    } else if (line.startsWith("$")) {
                        if (currentGroup != null) {
                            inventoryManager.importInventory(currentGroup);
                            currentGroup = null;
                        }
                    } else {
                        throw new IllegalArgumentException("Недійсний рядок у файлі: " + line);
                    }
                }
                if (currentGroup != null) {
                    inventoryManager.importInventory(currentGroup);
                }
                JOptionPane.showMessageDialog(frame, "Імпорт успішний", "Імпорт", JOptionPane.INFORMATION_MESSAGE);
                ContentViewPanel.refreshTableData(inventoryManager.getProductGroups()); // Refresh the table
            } catch (IOException | IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, "Помилка при імпорті файлу: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Method to show the export dialog
     * @param frame parent frame
     * @param inventoryManager inventory manager
     */
    public void showExportDialog(MainFrame frame, InventoryManager inventoryManager) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                for (ProductGroup group : inventoryManager.getProductGroups()) {
                    writer.write("@" + group.getName() + "," + group.getDescription() + "\n");
                    for (Product product : group.getProducts()) {
                        writer.write("~" + product.getName() + "," + product.getDescription() + "," + product.getManufacturer() + ","
                                + product.getQuantity() + "," + product.getPrice() + "\n");
                    }
                    writer.write("$\n"); // End of group
                }
                JOptionPane.showMessageDialog(frame, "Експорт успішний", "Експорт", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Помилка при експорті до файлу: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
