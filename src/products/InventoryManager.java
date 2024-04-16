package products;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to manage the inventory
 */
public class InventoryManager {
    /**
     * List of product groups
     */
    private List<ProductGroup> productGroups = new ArrayList<>();


    public InventoryManager() {
        // initializeProductGroups();
    }

    /**
     * Method to initialize the product groups
     */
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

    /**
     * Method to add a product group
     *
     * @param group product group to add
     */
    public void importInventory(ProductGroup group) {
        productGroups.add(group);
    }

    /**
     * Method to get the product groups
     *
     * @return list of product groups
     */
    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    /**
     * Method to get selected product from the table
     *
     * @param table table to get the selected product from
     * @return selected product
     */
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

    /**
     * Method to calculate the total price of the inventory
     *
     * @return total price of the inventory
     */
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

    /**
     * Method to get the statistics of the inventory
     *
     * @return statistics of the inventory
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

    /**
     * Method to get all products
     *
     * @return all products
     */
    public Collection<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        for (ProductGroup group : productGroups) {
            allProducts.addAll(group.getProducts());
        }
        return allProducts;
    }

    /**
     * Method to check if a group exists
     *
     * @param groupName name of the group
     * @return true if the group exists, false otherwise
     */
    public boolean isGroupExists(String groupName) {
        return productGroups.stream().anyMatch(group -> group.getName().equalsIgnoreCase(groupName));
    }


    /**
     * Method to check if a product exists
     *
     * @param productName name of the product
     * @return true if the product exists, false otherwise
     */
    public boolean isProductExists(String productName) {
        return productGroups.stream().anyMatch(group -> group.getProducts().stream().anyMatch(product -> product.getName().equalsIgnoreCase(productName)));
    }
}
