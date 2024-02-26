package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Mantap Bang");
        product1.setProductQuantity(5);
        this.products.add(product1);
        Product product2 = new Product();
        product2.setProductId("dd57b860-7612-4b75-86f7-f625f53907bd");
        product2.setProductName("Es Krim Esdea");
        product2.setProductQuantity(1);
        this.products.add(product2);
        Product product3 = new Product();
        product3.setProductId("0c1ce873-59e6-461e-9741-7bee2cd95228");
        product3.setProductName("Algoritma Convolusi");
        product3.setProductQuantity(2);
        this.products.add(product3);
    }

    @Test
    void testPaymentValid() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
            this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        assertSame(order.getProducts(), payment.getOrder().getProducts());
        assertEquals(order.getProducts().size(), payment.getOrder().getProducts().size());
        assertEquals(this.products.get(0).getProductName(),
                payment.getOrder().getProducts().get(0).getProductName());
        assertEquals(this.products.get(1).getProductName(),
                payment.getOrder().getProducts().get(1).getProductName());

        assertEquals("21a11451-cc2c-4033-bd88-48fbb04032e7", payment.getId());
        assertSame(paymentData, payment.getPaymentData());
        assertEquals("VOUCHER_CODE", payment.getStatus());
    }

    @Test
    void testPaymentOrderAlreadyCompleted() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
            this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        order.setStatus(OrderStatus.SUCCESS.getValue());
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        });

        order.setStatus(OrderStatus.CANCELLED.getValue());
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        });

        order.setStatus(OrderStatus.FAILED.getValue());
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        });
    }

    @Test
    void testPaymentInvalidPaymentMethod() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "MEONG", order, paymentData);
        });
    }

    @Test
    void testValidVoucherCode() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
            "VOUCHER_CODE", order, paymentData);
    }

    @Test
    void testVoucherCodeWrongLength() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP26022024");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "VOUCHER_CODE", order, paymentData);
        });
    }

    @Test
    void testVoucherCodeWrongPrefix() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "PHOSE1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                    "VOUCHER_CODE", order, paymentData);
        });
    }

    @Test
    void testVoucherCodeWrongNumberCount() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "PHOSE1234ABC567P");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                    "VOUCHER_CODE", order, paymentData1);
        });

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "PHOSE1234AB56789");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                    "VOUCHER_CODE", order, paymentData2);
        });
    }

    @Test
    void testBankTransferValid() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "STM Bank");
        paymentData.put("referenceCode", "82un3rc0");

        Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "BANK_TRANSFER", order, paymentData);
    }

    @Test
    void testBankTransferInvalidBankName() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "");
        paymentData1.put("referenceCode", "e80fuv32");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                "BANK_TRANSFER", order, paymentData1);
        });

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", null);
        paymentData2.put("referenceCode", "vhf2308s");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                    "BANK_TRANSFER", order, paymentData2);
        });
    }

    @Test
    void testBankTransferInvalidRefCode() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708690000L, "Dek Depe");

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "Bank Mandiri");
        paymentData1.put("referenceCode", "");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                    "BANK_TRANSFER", order, paymentData1);
        });

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "Bank Pak Esdea");
        paymentData2.put("referenceCode", null);
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("21a11451-cc2c-4033-bd88-48fbb04032e7",
                    "BANK_TRANSFER", order, paymentData2);
        });
    }
}
