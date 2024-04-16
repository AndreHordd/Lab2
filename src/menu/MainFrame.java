package menu;

import products.InventoryManager;
import products.ProductGroup;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to create the main frame
 */
public class MainFrame extends JFrame {
    private InventoryManager inventoryManager = new InventoryManager();
    private List<ProductGroup> productGroups = new ArrayList<>();

    /**
     * Constructor to create the main frame
     */
    public MainFrame() {
        setTitle("Автоматизоване Робоче Місце");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuBuilder menuBuilder = new MenuBuilder();

        MenuBuilder.createMenuBar(this, inventoryManager);
        // ToolBarBuilder.createToolBar(this, inventoryManager);
          ContentViewPanel.createViewPanel(this, inventoryManager.getProductGroups());

        setVisible(true);

    }

    /**
     * Method to get the inventory manager
     * @return inventory manager
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();

        });
    }
}

