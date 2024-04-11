package products;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private List<ProductGroup> productGroups = new ArrayList<>();

    public InventoryManager() {
        initializeProductGroups();
    }

    private void initializeProductGroups() {

        // Створення та додавання першої групи
        ProductGroup grains = new ProductGroup("Зернові", "Зернові та зернопродукти");
        // Створення продуктів для першої групи
        Product flour = new Product("Борошно", "Вищий сорт", "Київмлин", 120, 15.50, grains);
        Product buckwheat = new Product("Гречка", "Ядриця", "Харківзернопродукт", 80, 32.00, grains);

        // Створення та додавання другої групи
        ProductGroup beverages = new ProductGroup("Напої", "Чай та кава");
        // Створення продуктів для другої групи
        Product tea = new Product("Чай", "Зелений чай", "Morison's", 50, 60.00, beverages);
        Product coffee = new Product("Кава", "Арабіка", "Brazil Beans", 40, 120.00, beverages);


        // Додавання груп до списку
        productGroups.add(grains);
        productGroups.add(beverages);
    }

    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    // Інші методи, як-от відображення інформації про групи та їх товари...
    public List<Product> searchProductsByName(String productName) {
        List<Product> foundProducts = new ArrayList<>();
        for (ProductGroup group : productGroups) {
            for (Product product : group.getProducts()) {
                if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                    foundProducts.add(product);
                }
            }
        }
        return foundProducts;
    }
    public Product getSelectedProduct(JTable table) {
        int selectedRowIndex = table.getSelectedRow();
        if (selectedRowIndex != -1) {
            // Assuming the product name is in the third column of the table.
            String productName = (String) table.getValueAt(selectedRowIndex, 2);

            for (ProductGroup group : productGroups) {
                for (Product product : group.getProducts()) {
                    if (product.getName().equals(productName)) {
                        return product;
                    }
                }
            }
        }
        return null; // No product selected or found
    }
}

// Класи products.Product та products.ProductGroup вже визначені, як було припущено раніше.
