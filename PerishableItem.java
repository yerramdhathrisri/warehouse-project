
public class PerishableItem extends StockItem {
    private static final long serialVersionUID = 1L;

    private int sld;
    private static double rpupd = 2.0;

    public PerishableItem(int code, int days, int ID, String pname,
                          int quantity, double uc,
                          double coractdiscper, int relevel,
                          String category, String suppliername,
                          String loc, int sld) {

        super(code, days, ID, pname, quantity, uc,
              coractdiscper, relevel, category,
              suppliername, loc);

        if (sld <= 0)
            throw new IllegalArgumentException(
                    "Shelf life must be positive.");

        this.sld = sld;
    }

    public boolean isExpired() {
        return days > sld;
    }

    @Override
    public double calculateStorageCharge(int days) {
        if (days < 0)
            throw new IllegalArgumentException(
                    "Days cannot be negative.");

        return days * quantity * rpupd;
    }

    @Override
    public double calculateChargeForDispatch(
            int days, int boxes, double discountPercent) {

        if (days < 0 || boxes <= 0
                || discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Invalid billing details.");
        }

        double charge = days * boxes * rpupd;
        return charge - charge * discountPercent / 100.0;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Shelf life: " + sld + " days");
        System.out.println("Expired: " + isExpired());
    }
}