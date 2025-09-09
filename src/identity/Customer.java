package identity;

import product.snackorder.SnackOrder;

import java.time.LocalDate;

public class Customer extends Person {

    private LocalDate registrationDate;
    private SnackOrder[] orders;

    //as customer is person and extends Person class, therefore customer should have name as obligatory field, but not registration date or orders because customer can not be registered and does not have any orders but stil attend cinema.
    public Customer(String name) {
        super(name);
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public SnackOrder[] getOrders() {
        return orders;
    }

    public void setOrders(SnackOrder[] orders) {
        this.orders = orders;
    }

    @Override
    public String getRoleDescription() {
        return "Cinema customer";
    }
}

