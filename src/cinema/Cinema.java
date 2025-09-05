package cinema;

import identity.Customer;
import identity.Staff;
import theaterhall.TheaterHall;

public class Cinema {

    private String name;
    private String location;
    private TheaterHall[] halls;

    private Staff[] staffMembers;
    private Customer[] customers;

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
}
