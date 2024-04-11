package products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private List<Product> products;

    public ProductGroup(String name, String description) {
        this.name = name;
        this.description = description;
        this.products = new ArrayList<>();
    }

    // Methods to add, edit, and delete products
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Product added: " + product);
    }

    public void removeProduct(Product product) {
        boolean removed = products.remove(product);
        System.out.println("Trying to remove: " + product);
        System.out.println("Product removed: " + removed);
    }



    public void editProduct(int index, Product product) {
        products.set(index, product);
    }

    public List<Product> getProducts() {
        return products;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }

    public String calculateGroupPrice() {
        StringBuilder sb = new StringBuilder();
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        sb.append(total).append(" грн");
        return sb.toString();
    }

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
