package products;

/**
 * Class to create a product
 */
public class Product {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String manufacturer;
    private int quantity;
    private double price;

    /**
     * Constructor to create a product
     * @param name name of the product
     * @param description description of the product
     * @param manufacturer manufacturer of the product
     * @param quantity quantity of the product
     * @param price price of the product
     * @param group group of the product
     */
    public Product(String name, String description, String manufacturer, int quantity, double price, ProductGroup group) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
        setProductGroup(group); // Встановлюємо групу на етапі створення продукту
    }


    private ProductGroup productGroup;

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    /**
     * Method to set the product group
     * @param newGroup new group of the product
     */
    public void setProductGroup(ProductGroup newGroup) {
        // Отримуємо поточну групу продукту
        ProductGroup currentGroup = getProductGroup();

        // Якщо продукт належить до якоїсь групи, спробуємо його видалити
        if (currentGroup != null) {
            currentGroup.removeProduct(this);
            System.out.println("Removed from old group: " + currentGroup.getName());
        }

        // Оновлюємо групу продукту на нову
        this.productGroup = newGroup;

        // Додаємо продукт до нової групи, якщо він там вже не присутній
        if (newGroup != null && !newGroup.getProducts().contains(this)) {
            newGroup.addProduct(this);
            System.out.println("Added to new group: " + newGroup.getName());
        }
    }


    /**
     * Method to get the name of the product
     * @return name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the description of the product
     * @return description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to get the manufacturer of the product
     * @return manufacturer of the product
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Method to get the quantity of the product
     * @return quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Method to get the price of the product
     * @return price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method to set the name of the product
     * @param name name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to set the description of the product
     * @param description description of the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to set the manufacturer of the product
     * @param manufacturer manufacturer of the product
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Method to set the quantity of the product
     * @param quantity quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Method to set the price of the product
     * @param price price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method to decrease the quantity of the product
     * @param amount amount to decrease
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Назва: ").append(name).append("\n");
        sb.append("Опис: ").append(description).append("\n");
        sb.append("Виробник: ").append(manufacturer).append("\n");
        sb.append("Кількість: ").append(quantity).append("\n");
        sb.append("Ціна: ").append(price).append(" грн");
        return sb.toString();
    }


}
