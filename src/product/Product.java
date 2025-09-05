package product;

import java.math.BigDecimal;

public class Product {

    private String name;
    private BigDecimal price;

    //product should have it's own name - what kind of product is it. the price should with default value 0.
    public Product(String name) {
        this.name = name;
        this.price = new BigDecimal("0.00");
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
