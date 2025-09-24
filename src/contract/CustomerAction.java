package contract;

import identity.Customer;

@FunctionalInterface
public interface CustomerAction {
    void execute(Customer customer);
}
