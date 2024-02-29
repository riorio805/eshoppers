package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    Order order;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, String method, Order order, Map<String, String> paymentData) {
        this.id = id;

        String[] methodList = {"VOUCHER_CODE", "BANK_TRANSFER"};
        if (Arrays.stream(methodList).noneMatch(item -> item.equals(method))) {
            throw new IllegalArgumentException();
        }
        this.method = method;

        if (! order.getStatus().equals(OrderStatus.WAITING_PAYMENT.getValue()) ) {
            throw new IllegalArgumentException();
        }
        this.order = order;

        this.paymentData = paymentData;
        if (method.equals("VOUCHER_CODE")) {
            if (! this.paymentData.containsKey("voucherCode")) {
                throw new IllegalArgumentException("No voucher code");
            }
            this.status = verifyVoucherCode();
        }
    }

    private String verifyVoucherCode() {
        String voucherCode = this.paymentData.get("voucherCode");
        if (voucherCode.length() != 16) {
            return "REJECTED";
        }

        if (! voucherCode.startsWith("ESHOP")) {
            return "REJECTED";
        }

        int numCount = 0;
        for (char character: voucherCode.toCharArray()) {
            if (Character.isDigit(character)) {
                numCount += 1;
            }
        }
        if (numCount != 8) {
            return "REJECTED";
        }

        return "SUCCESS";
    }
}
