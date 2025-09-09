package product;

import java.math.BigDecimal;

public class Snack extends Product {

    public Snack(String name) {
        super(name);
    }

    @Override
    public BigDecimal getFinalPrice() {
        return price;
    }
}
