import java.io.Serializable;

public class Product {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String manufacturer;
    private int quantity;
    private double price;

    public Product(String name, String description, String manufacturer, int quantity, double price) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
    }

    // Геттери і сеттери
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getManufacturer() { return manufacturer; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("%s (%s), %s, %d шт, %.2f грн.", name, description, manufacturer, quantity, price);
    }
}
