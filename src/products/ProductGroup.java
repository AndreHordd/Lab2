package products;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create a product group
 */
public class ProductGroup {

    /**
     * Name of the product group
     */
    private String name;
    /**
     * Description of the product group
     */
    private String description;
    /**
     * List of products in the group
     */
    private List<Product> products;

    /**
     * Constructor to create a product group
     * @param name name of the product group
     * @param description  description of the product group
     */
    public ProductGroup(String name, String description) {
        this.name = name;
        this.description = description;
        this.products = new ArrayList<>();
    }

    /**
     * Method to add a product to the group
     * @param product product to add
     */
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Product added: " + product);
    }

    /**
     * Method to remove a product from the group
     * @param product product to remove
     */
    public void removeProduct(Product product) {
        boolean removed = products.remove(product);
        System.out.println("Trying to remove: " + product);
        System.out.println("Product removed: " + removed);
    }

    /**
     * Method to edit a product in the group
     * @param index index of the product to edit
     * @param product new product to replace the old one
     */
    public void editProduct(int index, Product product) {
        products.set(index, product);
    }

    /**
     * Method to get the list of products in the group
     * @return list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Method to get the name of the product group
     * @return name of the product group
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name of the product group
     * @param name name of the product group
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the description of the product group
     * @return description of the product group
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set the description of the product group
     * @param description description of the product group
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to get the string representation of the product group
     * @return string representation of the product group
     */
    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }

    /**
     * Method to calculate the total price of the products in the group
     * @return total price of the products in the group
     */
    public String calculateGroupPrice() {
        StringBuilder sb = new StringBuilder();
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        sb.append(total).append(" грн");
        return sb.toString();
    }

    /**
     * Method to get the statistics of the products in the group
     * @return statistics of the products in the group
     */
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("Кількість товарів у групі: ").append(products.size()).append("\n");
        sb.append("----------------------------\n");
        sb.append("Список товарів у групі: \n");
        sb.append("----------------------------\n");
        for (Product product : products) {
            sb.append(product).append("\n");
            double totalPrice = product.getPrice() * product.getQuantity();
            sb.append("Загальна вартість: ").append(totalPrice).append(" грн\n");
            sb.append("----------------------------\n");
        }
        return sb.toString();
    }
}
