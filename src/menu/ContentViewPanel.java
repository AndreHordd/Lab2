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

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Жодна комірка не може бути відредагована
                return false;
            }
        };
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(700, 70));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);


        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
    }


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

    public static void refreshTableData(List<ProductGroup> groups) {
        Object[][] data = prepareTableData(groups);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(data, new String[]{
                "Група",
                "Опис групи",
                "Назва товару",
                "Опис товару",
                "Виробник",
                "Кількість на складі",
                "Ціна за одиницю"
        });
    }


    public static JTable getTable() {
        return table;
    }
}
