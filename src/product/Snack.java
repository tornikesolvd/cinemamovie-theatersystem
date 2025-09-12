package product;

import contract.Consumable;

import java.math.BigDecimal;

public class Snack extends Product implements Consumable {

    public Snack(String name) {
        super(name);
    }

    @Override
    public BigDecimal getFinalPrice() {
        return price;
    }

    @Override
    public void consume() {
        System.out.println("Enjoy your " + name + "!");
    }
}
