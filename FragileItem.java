
public class FragileItem extends StockItem {
    private static final long serialVersionUID = 1L;

    private double insurancePercent;
    private boolean specialPackaging;

    private static double Charge_per_unit = 3.0;

    public FragileItem(int code, int days, int ID, String pname,
                       int quantity, double uc,
                       double coractdiscper, int relevel,
                       String category, String suppliername,
                       String loc, double insurancePercent,
                       boolean specialPackaging) {

        super(code, days, ID, pname, quantity, uc,
              coractdiscper, relevel, category,
              suppliername, loc);

        if (insurancePercent < 0 || insurancePercent > 100)
            throw new IllegalArgumentException(
                    "Insurance must be between 0 and 100.");

        this.insurancePercent = insurancePercent;
        this.specialPackaging = specialPackaging;
    }

    @Override
    public double calculateStorageCharge(int days) {
        double charge = super.calculateStorageCharge(days);

        double insurance =
                calculateStockValue() * insurancePercent / 100.0;

        double packaging = specialPackaging
                ? quantity * Charge_per_unit : 0;

        return charge + insurance + packaging;
    }

    @Override
    public double calculateChargeForDispatch(
            int days, int boxes, double discountPercent) {

        if (discountPercent < 0 || discountPercent > 100)
            throw new IllegalArgumentException(
                    "Discount must be between 0 and 100.");

        double storage = super.calculateChargeForDispatch(days, boxes, 0);
        double insurance = boxes * uc * insurancePercent / 100.0;
        double packaging = specialPackaging ? boxes * Charge_per_unit : 0;
        double total = storage + insurance + packaging;

        return total - total * discountPercent / 100.0;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Insurance: " + insurancePercent + "%");
        System.out.println("Special packaging: " + specialPackaging);
    }
}