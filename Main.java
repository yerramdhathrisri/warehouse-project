
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Warehouse warehouse = new Warehouse();

        int choice = -1;

        while (choice != 0) {
            System.out.println(" WAREHOUSE MANAGEMENT ");
            System.out.println("1. Receive goods");
            System.out.println("2. Dispatch goods and generate bill");
            System.out.println("3. View all stock");
            System.out.println("4. Search product");
            System.out.println("5. Add stock");
            System.out.println("6. Update product details");
            System.out.println("7. Remove empty product");
            System.out.println("8. Low stock report");
            System.out.println("9. Expiry report");
            System.out.println("0. Save and exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1: {
                        System.out.print("Product type (1-Regular, 2-Perishable, 3-Fragile): ");
                        int type = Integer.parseInt(sc.nextLine());

                        System.out.print("Product code: ");
                        int code = Integer.parseInt(sc.nextLine());

                        System.out.print("Product ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Product name: ");
                        String name = sc.nextLine();

                        System.out.print("Quantity: ");
                        int quantity = Integer.parseInt(sc.nextLine());

                        System.out.print("Unit cost: ");
                        double cost = Double.parseDouble(sc.nextLine());

                        System.out.print("Contract discount percentage: ");
                        double discount = Double.parseDouble(sc.nextLine());

                        System.out.print("Reorder level: ");
                        int reorder = Integer.parseInt(sc.nextLine());

                        System.out.print("Category: ");
                        String category = sc.nextLine();

                        System.out.print("Supplier name: ");
                        String supplier = sc.nextLine();

                        System.out.print("Storage location: ");
                        String location = sc.nextLine();

                        System.out.print("Days already stored: ");
                        int days = Integer.parseInt(sc.nextLine());

                        StockItem item;

                        if (type == 1) {
                            item = new StockItem(
                                    code, days, id, name, quantity,
                                    cost, discount, reorder, category,
                                    supplier, location);

                        } else if (type == 2) {
                            System.out.print("Shelf life in days: ");
                            int shelfLife = Integer.parseInt(sc.nextLine());

                            item = new PerishableItem(
                                    code, days, id, name, quantity,
                                    cost, discount, reorder, category,
                                    supplier, location, shelfLife);

                        } else if (type == 3) {
                            System.out.print("Insurance percentage: ");
                            double insurance = Double.parseDouble(sc.nextLine());

                            System.out.print("Special packaging? (yes/no): ");
                            boolean packaging =
                                    sc.nextLine().equalsIgnoreCase("yes");

                            item = new FragileItem(
                                    code, days, id, name, quantity,
                                    cost, discount, reorder, category,
                                    supplier, location, insurance, packaging);

                        } else {
                            System.out.println("Invalid product type.");
                            break;
                        }

                        warehouse.addItem(item);
                        System.out.println("Goods received successfully.");
                        break;
                    }

                    case 2: {
                        System.out.print("Product code: ");
                        int code = Integer.parseInt(sc.nextLine());

                        StockItem item = warehouse.findItem(code);

                        if (item == null) {
                            System.out.println("Product not found.");
                            break;
                        }

                        System.out.println("Available quantity: "
                                + item.getQuantity());

                        System.out.print("Quantity to dispatch: ");
                        int boxes = Integer.parseInt(sc.nextLine());

                        System.out.print("Storage days to charge: ");
                        int days = Integer.parseInt(sc.nextLine());

                        System.out.print("Discount percentage: ");
                        double discount = Double.parseDouble(sc.nextLine());

                       
                        double total = item.calculateChargeForDispatch(
                                days, boxes, discount);

                        warehouse.dispatchStock(code, boxes);

                       
                        System.out.println("Product: " + item.getPname());
                        System.out.println("Product code: " + code);
                        System.out.println("Boxes dispatched: " + boxes);
                        System.out.println("Storage days: " + days);
                        System.out.printf("Discount: %.2f%%%n", discount);
                        System.out.printf("Final amount: Rs ", total);
                        System.out.println("Remaining stock: "
                                + item.getQuantity());
                        break;
                    }

                    case 3:
                        warehouse.displayAllItems();
                        break;

                    case 4: {
                        System.out.print("Enter product code: ");
                        int code = Integer.parseInt(sc.nextLine());

                        StockItem item = warehouse.findItem(code);

                        if (item == null) {
                            System.out.println("Product not found.");
                        } else {
                            item.display();
                        }
                        break;
                    }

                    case 5: {
                        System.out.print("Product code: ");
                        int code = Integer.parseInt(sc.nextLine());

                        System.out.print("Additional quantity: ");
                        int quantity = Integer.parseInt(sc.nextLine());

                        warehouse.addStock(code, quantity);
                        System.out.println("Stock added successfully.");
                        break;
                    }

                    case 6: {
                        System.out.print("Product code: ");
                        int code = Integer.parseInt(sc.nextLine());

                        System.out.print("New product name: ");
                        String name = sc.nextLine();

                        System.out.print("New reorder level: ");
                        int reorder = Integer.parseInt(sc.nextLine());

                        System.out.print("New category: ");
                        String category = sc.nextLine();

                        System.out.print("New location: ");
                        String location = sc.nextLine();

                        warehouse.updateItem(
                                code, name, reorder, category, location);

                        System.out.println("Product updated successfully.");
                        break;
                    }

                    case 7: {
                        System.out.print("Product code to remove: ");
                        int code = Integer.parseInt(sc.nextLine());

                        warehouse.removeEmptyItem(code);
                        System.out.println("Product removed.");
                        break;
                    }

                    case 8:
                        warehouse.lowStockReport();
                        break;

                    case 9:
                        warehouse.expiryReport();
                        break;

                    case 0:
                        warehouse.saveData();
                        System.out.println("Data saved. Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
    }
}
