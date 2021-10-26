package shop_api;

import java.util.List;

public class Customer {
    private Address address;
    private int customerStatus;

    private String id;
    private String password;

    private Person person;
    private ShoppingCart shoppingCart;

    public Customer() {
    }

    public Address getAddress() {
        return address;
    }

    public int getCustomerStatus() {
        return customerStatus;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Person getPerson() {
        return person;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

}
