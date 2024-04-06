package action;

import menu.AddDialog;

import javax.swing.*;
import products.InventoryManager;

public class DialogManager {

    // Метод для показу діалогу додавання нового товару
    public static void showAddProductDialog(JFrame parentFrame, InventoryManager inventoryManager) {
        // Створення екземпляра діалогового вікна для додавання нового товару
        AddDialog addDialog = new AddDialog(parentFrame, "Додати товар", true, inventoryManager.getProductGroups());
        addDialog.setVisible(true);
        // Після того, як діалог стає видимим, код тут може обробляти результати діалогу
    }

    // Тут можуть бути інші методи для показу різних діалогових вікон, наприклад, для редагування або видалення товарів
}
