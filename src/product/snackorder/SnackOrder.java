package product.snackorder;

import showpiece.Showpiece;
import product.Snack;

import java.math.BigDecimal;

public class SnackOrder extends Showpiece {

    {
        System.out.println("SnackOrder instance created (instance initializer)");
    }

    private Snack[] snacks;

    public SnackOrder(Snack[] snacks) {
        this.snacks = snacks;
    }

    public Snack[] getSnacks() {
        return snacks;
    }

    public void setSnacks(Snack[] snacks) {
        this.snacks = snacks;
    }

    public int getSnackCount() {
        return snacks == null ? 0 : snacks.length;
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            if (snacks != null) {
                for (Snack snack : snacks) {
                    if (snack != null && snack.getPrice() != null) {
                        total = total.add(snack.getPrice());
                    }
                }
            }
            return total;
        } finally {
            System.out.println("SnackOrder total calculated: $" + total);
        }
    }


    @Override
    public boolean isValid() {
        return snacks != null;
    }

    @Override
    public String getDisplayname() {
        return "Snacks: " + getSnacks().toString();
    }
}
