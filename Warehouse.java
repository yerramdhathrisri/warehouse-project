
import java.io.*;
import java.util.ArrayList;

public class Warehouse {
    private ArrayList<StockItem> items;
    private final String fileName = "stockdata.ser";

    public Warehouse() {
        items = new ArrayList<>();

        File file = new File(fileName);

        if (file.exists()) {
            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(file))) {
                items = (ArrayList<StockItem>) in.readObject();
                System.out.println("Previous stock loaded.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Could not load saved stock.");
                items = new ArrayList<>();
            }
        }
    }

    public ArrayList<StockItem> getItems() {
        return items;
    }

    public StockItem findItem(int code) {
        for (StockItem item : items) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    public void addItem(StockItem item) {
        if (findItem(item.getCode()) != null) {
            throw new IllegalArgumentException(
                    "This product code already exists.");
        }

        for (StockItem existing : items) {
            if (existing.getID() == item.getID()) {
                throw new IllegalArgumentException(
                        "This product ID already exists.");
            }
        }

        items.add(item);
        saveData();
    }

    public void addStock(int code, int quantity) {
        StockItem item = findItem(code);

        if (item == null) {
            throw new IllegalArgumentException("Product not found.");
        }

        item.addStock(quantity);
        saveData();
    }

    public void dispatchStock(int code, int quantity) {
        StockItem item = findItem(code);

        if (item == null) {
            throw new IllegalArgumentException("Product not found.");
        }

        item.dispatchStock(quantity);
        saveData();
    }

    public void updateItem(int code, String name, int reorderLevel,
                           String category, String location) {
        StockItem item = findItem(code);

        if (item == null) {
            throw new IllegalArgumentException("Product not found.");
        }

        item.updateDetails(name, reorderLevel, category, location);
        saveData();
    }

    public void removeEmptyItem(int code) {
        StockItem item = findItem(code);

        if (item == null) {
            throw new IllegalArgumentException("Product not found.");
        }

        if (item.getQuantity() != 0) {
            throw new IllegalArgumentException(
                    "Dispatch all stock before removing this product.");
        }

        items.remove(item);
        saveData();
    }

    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("No products in the warehouse.");
            return;
        }

        for (StockItem item : items) {
            item.display();
            System.out.println("------------------------");
        }
    }

    public void expiryReport() {
        boolean found = false;

        for (StockItem item : items) {
            if (item instanceof PerishableItem) {
                PerishableItem p = (PerishableItem) item;

                if (p.isExpired()) {
                    System.out.println("EXPIRED: " + p.getPname()
                            + " | Code: " + p.getCode());
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No expired perishable items found.");
        }
    }

    public void lowStockReport() {
        boolean found = false;

        for (StockItem item : items) {
            if (item.getQuantity() <= item.getRelevel()) {
                System.out.println("Low stock: " + item.getPname()
                        + " | Quantity: " + item.getQuantity());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No low-stock products.");
        }
    }

    public void saveData() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(items);
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }
}
