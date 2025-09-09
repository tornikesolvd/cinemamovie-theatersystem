package voucher;

import product.Product;

import java.math.BigDecimal;

public class Voucher extends Product {
    public Voucher(String name) {
        super(name);
    }

    @Override
    public BigDecimal getFinalPrice() {
        return price.multiply(new BigDecimal("0.9"));
    }
}
