package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment addPayment(Payment payment) {
        return null;
    }

    public Payment findById(String paymentId) {
        return null;
    }

    public Payment setStatus(String paymentId, String status) {
        return null;
    }

    public List<Payment> getAllPayments() {
        return null;
    }
}
