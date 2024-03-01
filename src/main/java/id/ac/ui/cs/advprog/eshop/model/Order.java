package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;

    public Order(String id, List<Product> products, Long orderTime, String author){
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = OrderStatus.WAITING_PAYMENT.getValue();

        if (products.isEmpty()) {
            throw new IllegalArgumentException("Invalid products");
        }
        this.products = products;
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status){
        this(id, products, orderTime, author);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) {
            this.status = status;
        }
        else {
            throw new IllegalArgumentException("Invalid status");
        }
    }

    public Order updateOrder(Order order) {
        assert this.getId().equals(order.getId());

        this.products = order.getProducts();
        this.orderTime = order.getOrderTime();
        this.author = order.getAuthor();
        this.setStatus(order.getStatus());

        return this;
    }
}
