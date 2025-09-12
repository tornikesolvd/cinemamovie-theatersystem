package cinema;

import identity.Person;
import product.Product;
import showpiece.Showpiece;
import identity.Customer;
import identity.Staff;
import theaterhall.TheaterHall;

public class Cinema extends Showpiece {

    private String name;
    private String location;
    private TheaterHall[] halls;
    private Staff[] staffMembers;
    private Customer[] customers;
    private Product[] products; //super type field

    // cinema should have name, location and halls , but not staffmembers or customers.
    public Cinema(String name, String location, TheaterHall[] halls) {
        this.name = name;
        this.location = location;
        this.halls = halls;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public TheaterHall[] getHalls() {
        return halls;
    }

    public Staff[] getStaffMembers() {
        return staffMembers;
    }

    public void setStaffMembers(Staff[] staffMembers) {
        this.staffMembers = staffMembers;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    public void setCustomers(Customer[] customers) {
        this.customers = customers;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }


}
