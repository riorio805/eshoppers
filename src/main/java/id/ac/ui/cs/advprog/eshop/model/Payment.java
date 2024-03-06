package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

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

        if (! PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid method");
        }
        this.method = method;

        if (! order.getStatus().equals(OrderStatus.WAITING_PAYMENT.getValue()) ) {
            this.status = PaymentStatus.FAILED.getValue();
        }
        this.order = order;
        this.paymentData = paymentData;

        if (status == null) {
            updateStatusFromMethod();
        }
    }

    public void updateStatus(String status) {
        if (! PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;

        if (this.status.equals(PaymentStatus.SUCCESS.getValue())) {
            this.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        }
        else if (this.status.equals(PaymentStatus.REJECTED.getValue())) {
            this.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
    }

    public void updateStatus(PaymentStatus status) {
        updateStatus(status.getValue());
    }

    private void updateStatusFromMethod() {
        if (this.method.equals(PaymentMethod.VOUCHER_CODE.getValue())) {
            if (! this.paymentData.containsKey("voucherCode")) {
                throw new IllegalArgumentException("Invalid payment data for current method");
            }
            this.status = verifyVoucherCode();
        }
        else if (this.method.equals(PaymentMethod.BANK_TRANSFER.getValue())) {
            if (! this.paymentData.containsKey("bankName") ||
                    ! this.paymentData.containsKey("referenceCode")) {
                throw new IllegalArgumentException("Invalid payment data for current method");
            }
            this.status = verifyBankTransfer();
        }
    }

    private String verifyVoucherCode() {
        String voucherCode = this.paymentData.get("voucherCode");
        if (voucherCode.length() != 16) {
            return PaymentStatus.REJECTED.getValue();
        }

        if (! voucherCode.startsWith("ESHOP")) {
            return PaymentStatus.REJECTED.getValue();
        }

        int numCount = 0;
        for (char character: voucherCode.toCharArray()) {
            if (Character.isDigit(character)) {
                numCount += 1;
            }
        }
        if (numCount != 8) {
            return PaymentStatus.REJECTED.getValue();
        }

        return PaymentStatus.SUCCESS.getValue();
    }

    private String verifyBankTransfer() {
        String bankName = this.paymentData.get("bankName");
        String referenceCode = this.paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty()) {
            return PaymentStatus.REJECTED.getValue();
        }

        if (referenceCode == null || referenceCode.isEmpty()) {
            return PaymentStatus.REJECTED.getValue();
        }

        return PaymentStatus.WAITING.getValue();
    }
}
