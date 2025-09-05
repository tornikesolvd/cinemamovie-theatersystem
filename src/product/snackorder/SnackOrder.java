package product.snackorder;

import identity.Customer;
import product.Snack;

import java.math.BigDecimal;

public class SnackOrder {

    private Customer customer;
    private Snack[] snacks;

    //it is not obligatory to mention who orders snack but essential to have amount of snacks in order to order.
    public SnackOrder(Snack[] snacks) {
        this.snacks = snacks;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Snack[] getSnacks() {
        return snacks;
    }

    public void setSnacks(Snack[] snacks) {
        this.snacks = snacks;
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (snacks != null) {
            for (Snack snack : snacks) {
                if (snack != null) {
                    total = total.add(snack.getPrice());
                }
            }
        }
        return total;
    }

    public void setCustomer(Customer customer1) {
        this.customer = customer;
    }
}
