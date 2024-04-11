package action;

import menu.AddDialog;
import menu.ContentViewPanel;
import menu.EditDialog;
import products.InventoryManager;
import products.Product;
import products.ProductGroup;

import javax.swing.*;
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
            int response = JOptionPane.showConfirmDialog(parentFrame, "Ви впевнені, що хочете видалити вибраний продукт?", "Підтвердження видалення", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                // Видаляємо продукт з його групи
                selectedProduct.getProductGroup().removeProduct(selectedProduct);
                // Оновлюємо список продуктів у групі та оновлюємо відображення таблиці
                ContentViewPanel.refreshTableData(inventoryManager.getProductGroups());
                JOptionPane.showMessageDialog(parentFrame, "Продукт успішно видалений.", "Видалення продукту", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Будь ласка, виберіть продукт для видалення.", "Продукт не вибрано", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void onNewGroup(JFrame parentFrame, InventoryManager inventoryManager) {
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
            JOptionPane.showMessageDialog(parentFrame, "Немає груп для видалення.", "Увага", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(parentFrame, "Групу успішно видалено.", "Група видалена", JOptionPane.INFORMATION_MESSAGE);
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
            String newGroupName = JOptionPane.showInputDialog(parentFrame, "Введіть нову назву групи:", group.getName());
            String newGroupDescription = JOptionPane.showInputDialog(parentFrame, "Введіть новий опис групи:", group.getDescription());

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

    // Other methods for showing different dialogs...
}
