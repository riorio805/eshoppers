package id.ac.ui.cs.advprog.eshop.model;

import java.util.List;
import java.util.Map;

public class Payment {
    String id;
    String method;
    Order order;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, String method, Order order, Map<String, String> paymentData) {
        // empty skeleton
    }

    public Payment(String id, String method, Order order, Map<String, String> paymentData, String status) {
        // empty skeleton
    }

    public void setStatus(String status) {
        // empty skeleton
    }

    public Order getOrder() {
        // empty skeleton
        return null;
    }
}
