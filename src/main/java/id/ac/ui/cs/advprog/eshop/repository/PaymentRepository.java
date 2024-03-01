package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment addPayment(Payment payment) {
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment savedPayment: paymentData) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }

    public Payment setStatus(Payment payment, String status) {
        if (! PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        payment.updateStatus(status);

        if ( payment.getStatus().equals(PaymentStatus.SUCCESS.getValue()) ) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        }
        else if ( payment.getStatus().equals(PaymentStatus.REJECTED.getValue()) ) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }

        return payment;
    }

    public List<Payment> getAllPayments() {
        return paymentData;
    }
}
