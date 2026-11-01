
import java.io.Serializable;

public class StockItem implements Storable, Serializable {
    private static final long serialVersionUID = 1L;

    protected int code;
    protected int days;
    protected int ID;
    protected String pname;
    protected int quantity;
    protected double uc;
    protected double coractdiscper;
    protected int relevel;
    protected String category;
    protected String suppliername;
    protected String loc;

    protected static double srpud = 0.5;
    protected static double hp = 0.02;

    public StockItem(int code, int days, int ID, String pname,
                     int quantity, double uc, double coractdiscper,
                     int relevel, String category,
                     String suppliername, String loc) {

        if (quantity < 0 || uc < 0 || days < 0)
            throw new IllegalArgumentException("Invalid stock details.");

        this.code = code;
        this.days = days;
        this.ID = ID;
        this.pname = pname;
        this.quantity = quantity;
        this.uc = uc;
        this.coractdiscper = coractdiscper;
        this.relevel = relevel;
        this.category = category;
        this.suppliername = suppliername;
        this.loc = loc;
    }

    public int getCode() {
        return code;
    }

    public int getID() {
        return ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPname() {
        return pname;
    }

    public int getDays() {
        return days;
    }

    public int getRelevel() {
        return relevel;
    }

    public double getUc() {
        return uc;
    }

    public String getLoc() {
        return loc;
    }

    public void addStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero.");

        quantity += amount;
    }

    public void dispatchStock(int amount) {
        if (amount <= 0 || amount > quantity)
            throw new IllegalArgumentException(
                    "Invalid dispatch quantity.");

        quantity -= amount;
    }

    public void updateDetails(String pname, int relevel,
                              String category, String loc) {
        if (pname == null || pname.trim().isEmpty())
            throw new IllegalArgumentException(
                    "Product name cannot be empty.");

        if (relevel < 0)
            throw new IllegalArgumentException(
                    "Reorder level cannot be negative.");

        this.pname = pname;
        this.relevel = relevel;
        this.category = category;
        this.loc = loc;
    }

    @Override
    public double calculateStockValue() {
        return quantity * uc;
    }

    @Override
    public double calculateStorageCharge(int days) {
        if (days < 0)
            throw new IllegalArgumentException(
                    "Days cannot be negative.");

        double charge = days * quantity * srpud;
        return charge + charge * hp;
    }

    public double calculateStorageCharge(
            int days, double discountPercent) {

        if (discountPercent < 0 || discountPercent > 100)
            throw new IllegalArgumentException(
                    "Discount must be between 0 and 100.");

        double charge = calculateStorageCharge(days);

        return charge - charge * discountPercent / 100.0;
    }

    public double calculateChargeForDispatch(
            int days, int boxes, double discountPercent) {

        if (days < 0 || boxes <= 0 ||
            discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Invalid billing details.");
        }

        double charge = days * boxes * srpud;
        charge = charge + charge * hp;

        return charge - charge * discountPercent / 100.0;
    }

    public void display() {
        System.out.println("Code: " + code);
        System.out.println("ID: " + ID);
        System.out.println("Product: " + pname);
        System.out.println("Quantity: " + quantity);
        System.out.println("Days stored: " + days);
        System.out.println("Unit cost: Rs " + uc);
        System.out.println("Stock value: Rs " + calculateStockValue());
        System.out.println("Category: " + category);
        System.out.println("Supplier: " + suppliername);
        System.out.println("Location: " + loc);
        System.out.println("Reorder level: " + relevel);
    }

    @Override
    public String toString() {
        return "Code: " + code + ", Product: " + pname
                + ", Quantity: " + quantity
                + ", Category: " + category
                + ", Location: " + loc;
    }
}
