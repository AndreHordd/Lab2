package products;

public class Product {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String manufacturer;
    private int quantity;
    private double price;

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


    // Геттери і сеттери
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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
