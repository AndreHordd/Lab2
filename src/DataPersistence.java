import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataPersistence {

    public static void saveProductsToFile(List<Product> products, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> loadProductsFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Product>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
