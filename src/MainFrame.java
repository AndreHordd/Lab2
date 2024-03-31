import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private JButton addButton;
    private List<ProductGroup> productGroups = new ArrayList<>();
    private InventoryManager inventoryManager = new InventoryManager();


    public MainFrame() {
        setTitle("Автоматизоване Робоче Місце");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu editMenu = new JMenu("Редагувати");

        JMenuItem newItem = new JMenuItem("Новий");
        JMenuItem openItem = new JMenuItem("Відкрити");
        JMenuItem saveItem = new JMenuItem("Зберегти");
        JMenuItem exitItem = new JMenuItem("Вихід");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar();
        addButton = new JButton("Додати");
        JButton editButton = new JButton("Редагувати");
        JButton deleteButton = new JButton("Видалити");

        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);

        add(toolBar, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        initializeUI();
        createViewPanel(inventoryManager.getProductGroups());
        List<Product> products = DataPersistence.loadProductsFromFile("products.dat");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataPersistence.saveProductsToFile(products, "products.dat");
            }
        });

        exitItem.addActionListener(e -> System.exit(0));

        addButton.addActionListener(e -> textArea.append("Додавання нового елементу\n"));
        editButton.addActionListener(e -> textArea.append("Редагування елементу\n"));
        deleteButton.addActionListener(e -> textArea.append("Видалення елементу\n"));
    }

    private void initializeUI() {
        setTitle("Автоматизоване Робоче Місце");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenuBar();
        createToolBar();
        createMainContent();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        JMenuItem newItem = new JMenuItem("Новий");
        newItem.addActionListener(e -> showAddGroupDialog());
        JMenuItem openItem = new JMenuItem("Відкрити");
        openItem.addActionListener(e -> {
        });
        JMenuItem saveItem = new JMenuItem("Зберегти");
        saveItem.addActionListener(e -> {
        });
        JMenuItem exitItem = new JMenuItem("Вихід");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("Редагувати");
        JMenuItem addProductItem = new JMenuItem("Додати товар");
        addProductItem.addActionListener(e -> {
        });
        JMenuItem editProductItem = new JMenuItem("Редагувати товар");
        editProductItem.addActionListener(e -> {
        });
        JMenuItem deleteProductItem = new JMenuItem("Видалити товар");
        deleteProductItem.addActionListener(e -> {
        });

        editMenu.add(addProductItem);
        editMenu.add(editProductItem);
        editMenu.add(deleteProductItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
    }

    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        addButton = new JButton("Додати");
        JButton editButton = new JButton("Редагувати");
        JButton deleteButton = new JButton("Видалити");

        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);

        add(toolBar, BorderLayout.NORTH);

        addButton.addActionListener(e -> showAddGroupDialog());
    }

    private void createViewPanel(List<ProductGroup> groups) {
        String[] columnNames = {"Група", "Опис групи", "Назва товару", "Опис товару", "Виробник", "Кількість на складі", "Ціна за одиницю"};

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

        // Припускаємо, що метод add() існує в контексті, де цей метод викликається
        add(scrollPane, BorderLayout.CENTER);
    }



    private void createMainContent() {
        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void showAddGroupDialog() {
        GroupDialog dialog = new GroupDialog(this, "Додати групу товарів", true);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
