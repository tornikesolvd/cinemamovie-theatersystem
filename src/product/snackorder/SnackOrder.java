package product.snackorder;

import showpiece.Showpiece;
import product.Snack;

import java.math.BigDecimal;

public class SnackOrder extends
        Showpiece {

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
        if (snacks != null) {
            for (Snack snack : snacks) {
                total = total.add(snack.getPrice());
            }
        }
        return total;
    }
}
