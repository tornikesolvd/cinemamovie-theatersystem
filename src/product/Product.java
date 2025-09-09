package product;

import java.math.BigDecimal;

public abstract class Product {

    protected String name;
    protected BigDecimal price;

    public Product(String name) {
        this.name = name;
        this.price = BigDecimal.ZERO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public abstract BigDecimal getFinalPrice();
}
