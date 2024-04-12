package products;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
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


    public String calculateInventoryPrice() {
        StringBuilder builder = new StringBuilder();
        double total = 0.0;
        for (ProductGroup group : productGroups) {
            for (Product product : group.getProducts()) {
                total += product.getPrice() * product.getQuantity();
            }
        }
        builder.append(total).append(" грн");
        return builder.toString();
    }

    /*
    має бути зазначена к-сть товарів на складі та списком всі товари на складі
     */
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        builder.append("Кількість товарів на складі: ").append(productGroups.stream().mapToInt(group -> group.getProducts().size()).sum()).append("\n");
        builder.append("----------------------------\n");
        builder.append("Список товарів на складі: \n");
        builder.append("----------------------------\n");
        for (ProductGroup group : productGroups) {
            for (Product product : group.getProducts()) {
                builder.append(product).append("\n");
                double totalPrice = product.getPrice() * product.getQuantity();
                builder.append("Загальна вартість: ").append(totalPrice).append(" грн\n");
                builder.append("----------------------------\n");
            }
        }
        return builder.toString();
    }

    public Collection<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        for (ProductGroup group : productGroups) {
            allProducts.addAll(group.getProducts());
        }
        return allProducts;
    }
}

// Класи products.Product та products.ProductGroup вже визначені, як було припущено раніше.
