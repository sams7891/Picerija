package menuItems;

import java.util.List;

public class PizzaOrder {
    public String customerName;
    public String phone;
    public String address;
    public Item crust;
    public Item sauce;
    public List<Item> toppings;
    public String size;
    public List<OrderDetail> extras; // Items + Quantities
    public double totalPrice;

    // Helper class for snacks/drinks with counts
    public static class OrderDetail {
        public Item item;
        public int quantity;
        public OrderDetail(Item i, int q) { this.item = i; this.quantity = q; }
    }
}