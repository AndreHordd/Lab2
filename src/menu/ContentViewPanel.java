package menu;

import products.Product;
import products.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContentViewPanel {

    public static void createViewPanel(MainFrame frame, List<ProductGroup> groups) {
        // Визначення назв колонок у таблиці
        String[] columnNames = {
                "Група",
                "Опис групи",
                "Назва товару",
                "Опис товару",
                "Виробник",
                "Кількість на складі",
                "Ціна за одиницю"
        };

        // Підготовка даних для таблиці
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

        Object[][] data = dataList.toArray(new Object[0][]);

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(700, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
    }
}
