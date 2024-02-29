package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("ca8badb9-b79c-a26f-edc3-3010705ec774");
        product1.setProductName("Sampo Mantap Bang");
        product1.setProductQuantity(5);
        this.products.add(product1);
        Product product2 = new Product();
        product2.setProductId("ca8badb9-b79c-a26f-c10c-420f6bd6f739");
        product2.setProductName("Es Krim Esdea");
        product2.setProductQuantity(1);
        this.products.add(product2);
        Product product3 = new Product();
        product3.setProductId("ae2b2d7a-4962-9e4f-dbd2-a17d1244c53f");
        product3.setProductName("ristler juice");
        product3.setProductQuantity(2);
        this.products.add(product3);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");
    }

    @Test
    void testPaymentValid() {
        // Valid payment data
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertEquals(order.getProducts().size(), payment.getOrder().getProducts().size());
        assertEquals(this.products.get(1).getProductId(),
                payment.getOrder().getProducts().get(1).getProductId());
        assertEquals(this.products.get(2).getProductId(),
                payment.getOrder().getProducts().get(2).getProductId());

        assertEquals("21a11451-cc2c-4033-bd88-48fbb04032e7", payment.getId());
        assertSame(paymentData, payment.getPaymentData());
        assertEquals("VOUCHER_CODE", payment.getMethod());
    }

    @Test
    void testPaymentFailIfOrderAlreadyCompleted() {
        Payment payment;
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        order.setStatus(OrderStatus.SUCCESS.getValue());
        payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
            "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());

        order.setStatus(OrderStatus.CANCELLED.getValue());
        payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
            "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());

        order.setStatus(OrderStatus.FAILED.getValue());
        payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
            "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testPaymentInvalidPaymentMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "MEONG", order, paymentData);
        });
    }

    @Test
    void testPaymentEmptyData() {
        Map<String, String> paymentData = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
            "VOUCHER_CODE", order, paymentData);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
            "BANK_TRANSFER", order, paymentData);
        });
    }

    @Test
    void testValidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
            "VOUCHER_CODE", order, paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testVoucherCodeWrongLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPaa89012345");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
            "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPabcd01234567");

        payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeWrongPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "PHOSE1234ABC5678");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeWrongNumberCount() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "PHOSE1234ABC567P");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "PHOSE1234AB56789");

        payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testBankTransferValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "STM Bank");
        paymentData.put("referenceCode", "82un3rc0");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "BANK_TRANSFER", order, paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testBankTransferEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "e80fuv32");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());

        paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "vhf2308s");

        payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testBankTransferEmptyRefCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Mandiri");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());

        paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Pak Esdea");
        paymentData.put("referenceCode", null);

        payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }
}
