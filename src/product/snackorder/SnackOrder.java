package product.snackorder;

import showpiece.Showpiece;
import product.Snack;

import java.math.BigDecimal;
import java.util.List;

public class SnackOrder extends Showpiece {

    {
        System.out.println("SnackOrder instance created (instance initializer)");
    }

    private List<Snack> snacks;

    public SnackOrder(List<Snack> snacks) {
        this.snacks = snacks;
    }

    public List<Snack> getSnacks() {
        return snacks;
    }

    public void setSnacks(List<Snack> snacks) {
        this.snacks = snacks;
    }

    public int getSnackCount() {
        return snacks == null ? 0 : snacks.size();
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
