package shop_api;

import java.util.List;

public class ShoppingCart {
    private String id;
    private Customer customer;

    private OrderItem[] items;

    public ShoppingCart() {
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.items = orderItems;
    }
}
