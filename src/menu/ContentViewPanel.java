package menu;

import products.Product;
import products.ProductGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContentViewPanel {

    private static JTable table;
    private static JScrollPane scrollPane;

    public static void createViewPanel(MainFrame frame, List<ProductGroup> groups) {
        String[] columnNames = {
                "Група",
                "Опис групи",
                "Назва товару",
                "Опис товару",
                "Виробник",
                "Кількість на складі",
                "Ціна за одиницю"
        };

        Object[][] data = prepareTableData(groups);

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(700, 70));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    // Винесемо підготовку даних для таблиці в окремий метод
    private static Object[][] prepareTableData(List<ProductGroup> groups) {
        List<Object[]> dataList = new ArrayList<>();
        for (ProductGroup group : groups) {
            for (Product product : group.getProducts()) {
                dataList.add(new Object[]{
                        group.getName(),
                        group.getDescription(),
                        product.getName(),
                        product.getDescription(),
                        product.getManufacturer(),
                        product.getQuantity(),
                        product.getPrice()
                });
            }
        }

        return dataList.toArray(new Object[0][]);
    }

    // Метод для оновлення таблиці з результатами пошуку
    public static void updateTableWithSearchResults(List<Product> searchResults) {
        // Цей метод припускає, що кожен продукт належить до якоїсь групи, тому ми можемо ігнорувати групу в цьому контексті
        List<Object[]> dataList = new ArrayList<>();
        for (Product product : searchResults) {
            dataList.add(new Object[]{
                    "Пошуковий результат", // Це поле може бути замінено на реальну назву групи, якщо потрібно
                    "", // Опис групи може бути порожнім або заповненим за необхідності
                    product.getName(),
                    product.getDescription(),
                    product.getManufacturer(),
                    product.getQuantity(),
                    product.getPrice()
            });
        }

        Object[][] data = dataList.toArray(new Object[0][]);

        // Встановлюємо нові дані для таблиці
        table.setModel(new DefaultTableModel(data, new String[]{
                "Група",
                "Опис групи",
                "Назва товару",
                "Опис товару",
                "Виробник",
                "Кількість на складі",
                "Ціна за одиницю"
        }));

        // Оновлення скролл-панелі необхідно, якщо розміри таблиці суттєво змінилися
        scrollPane.setViewportView(table);
    }
}
