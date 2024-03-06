package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        Order order2 = new Order("ca8badb9-b79c-a26f-e914-e65b6fce652a",
                products, 1708570000L, "Safira Sudrajat");

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "STM Bank");
        paymentData2.put("referenceCode", "82un3rc0");

        this.payments = new ArrayList<>();
        Payment payment1 = new Payment("09930585-170c-4ceb-afb5-308b72ef74ac",
                PaymentMethod.VOUCHER_CODE.getValue(), order1, paymentData1);
        this.payments.add(payment1);
        Payment payment2 = new Payment("ca8badb9-b79c-a26f-c240-a2a834bb2fae",
                PaymentMethod.BANK_TRANSFER.getValue(), order2, paymentData2);
        this.payments.add(payment2);
        Payment payment3 = new Payment("ca8badb9-b79c-a26f-fed2-2bb2f0148207",
                PaymentMethod.VOUCHER_CODE.getValue(), order2, paymentData1);
        this.payments.add(payment3);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).addPayment(any(Payment.class));

        Payment result = paymentService.addPayment(
            payment.getOrder(), payment.getMethod(), payment.getPaymentData()
        );

        verify(paymentRepository, times(1)).addPayment(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testAddPaymentIfOrderAlreadyComplete() {
        Payment payment = payments.get(1);
        payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        payment = new Payment( payment.getId(), payment.getMethod(),
                payment.getOrder(), payment.getPaymentData());
        doReturn(payment).when(paymentRepository).addPayment(any(Payment.class));

        Payment result = paymentService.addPayment(
                payment.getOrder(), payment.getMethod(), payment.getPaymentData()
        );

        verify(paymentRepository, times(1)).addPayment(any(Payment.class));
        assertEquals(PaymentStatus.FAILED.getValue(), result.getStatus());
    }

    @Test
    void testGetPaymentWithId() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());

        verify(paymentRepository, times(1)).findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getOrder(), result.getOrder());
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(3, result.size());
    }

    @Test
    void testSetStatusToCompleted() {
        doReturn(payments.getFirst()).when(paymentRepository).findById(payments.getFirst().getId());

        Payment payment = paymentService.getPayment(payments.getFirst().getId());

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = payments.get(2);
        payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        payment = new Payment( payment.getId(), payment.getMethod(),
                payment.getOrder(), payment.getPaymentData());
        doReturn(payment).when(paymentRepository).addPayment(any(Payment.class));

        payment = paymentService.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }
}
