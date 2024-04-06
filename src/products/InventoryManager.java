package products;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private List<ProductGroup> productGroups = new ArrayList<>();

    public InventoryManager() {
        initializeProductGroups();
    }

    private void initializeProductGroups() {
        // Створення продуктів для першої групи
        Product flour = new Product("Борошно", "Вищий сорт", "Київмлин", 120, 15.50);
        Product buckwheat = new Product("Гречка", "Ядриця", "Харківзернопродукт", 80, 32.00);
        // Створення та додавання першої групи
        ProductGroup grains = new ProductGroup("Зернові", "Зернові та зернопродукти");
        grains.addProduct(flour);
        grains.addProduct(buckwheat);

        // Створення продуктів для другої групи
        Product tea = new Product("Чай", "Зелений чай", "Morison's", 50, 60.00);
        Product coffee = new Product("Кава", "Арабіка", "Brazil Beans", 40, 120.00);
        // Створення та додавання другої групи
        ProductGroup beverages = new ProductGroup("Напої", "Чай та кава");
        beverages.addProduct(tea);
        beverages.addProduct(coffee);

        // Додавання груп до списку
        productGroups.add(grains);
        productGroups.add(beverages);
    }

    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    // Інші методи, як-от відображення інформації про групи та їх товари...
}

// Класи products.Product та products.ProductGroup вже визначені, як було припущено раніше.
