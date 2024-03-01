package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Order> orders;
    List<Payment> payments;
    
    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        this.orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        this.orders.add(order1);
        Order order2 = new Order("ca8badb9-b79c-a26f-e914-e65b6fce652a",
                products, 1708570000L, "Safira Sudrajat");
        this.orders.add(order2);

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "STM Bank");
        paymentData2.put("referenceCode", "82un3rc0");

        this.payments = new ArrayList<>();
        Payment payment1 = new Payment("09930585-170c-4ceb-afb5-308b72ef74ac",
                PaymentMethod.VOUCHER_CODE.getValue(), order1, paymentData1);
        this.payments.add(payment1);
        Payment payment2 = new Payment("09930585-170c-4ceb-afb5-308b72ef74ac",
                PaymentMethod.BANK_TRANSFER.getValue(), order2, paymentData2);
        this.payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.getFirst();
        Payment result = paymentRepository.addPayment(payment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
    }

    @Test
    void testGetAllPayments() {
        for (Payment payment: payments) {
            paymentRepository.addPayment(payment);
        }
        List<Payment> allPayments = paymentRepository.getAllPayments();

        assertEquals(payments.size(), allPayments.size());
        assertEquals(payments.get(0).getId(), allPayments.get(0).getId());
        assertEquals(payments.get(1).getId(), allPayments.get(1).getId());
    }

    @Test
    void testGetAllPaymentsIfEmpty() {
        List<Payment> allPayments = paymentRepository.getAllPayments();
        assertEquals(0, allPayments.size());
    }

    @Test
    void testFindPaymentByIdIfIdFound() {
        for (Payment payment: payments) {
            paymentRepository.addPayment(payment);
        }

        Payment findResult = paymentRepository.findById(payments.getFirst().getId());

        assertEquals(payments.getFirst().getId(), findResult.getId());
        assertEquals(payments.getFirst().getMethod(), findResult.getMethod());
        assertEquals(payments.getFirst().getStatus(), findResult.getStatus());
    }

    @Test
    void testFindPaymentByIdIfIdNotFound() {
        for (Payment payment: payments) {
            paymentRepository.addPayment(payment);
        }

        Payment findResult = paymentRepository.findById("this-is-definitely-not-a-real-uuid");
        assertNull(findResult);
    }

    @Test
    void testSetStatusSuccess() {
        for (Payment payment: payments) {
            paymentRepository.addPayment(payment);
        }
        Payment payment = paymentRepository.findById(payments.getFirst().getId());
        Payment setResult = paymentRepository.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(),setResult.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(),setResult.getOrder().getStatus());
    }

    @Test
    void testSetStatusRejected() {
        for (Payment payment: payments) {
            paymentRepository.addPayment(payment);
        }
        Payment payment = paymentRepository.findById(payments.getFirst().getId());
        Payment setResult = paymentRepository.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(),setResult.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(),setResult.getOrder().getStatus());
    }
}
