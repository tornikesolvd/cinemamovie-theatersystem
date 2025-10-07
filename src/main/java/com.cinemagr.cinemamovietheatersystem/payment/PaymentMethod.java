package com.cinemagr.cinemamovietheatersystem.payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;

public enum PaymentMethod {
    CASH("Cash", 0.0, true, "Physical currency"),
    CREDIT_CARD("Credit Card", 0.02, true, "Credit card payment"),
    DEBIT_CARD("Debit Card", 0.01, true, "Debit card payment"),
    MOBILE_PAY("Mobile Pay", 0.015, true, "Mobile payment app"),
    VOUCHER("Voucher", 0.0, false, "Gift voucher"),
    BANK_TRANSFER("Bank Transfer", 0.005, true, "Direct bank transfer"),
    CRYPTOCURRENCY("Cryptocurrency", 0.03, true, "Digital currency");

    private static int totalMethods = 0;
    private static final Logger LOGGER = LogManager.getLogger(PaymentMethod.class);

    static {
        totalMethods = values().length;
        LOGGER.debug("PaymentMethod enum initialized with " + totalMethods + " methods");
    }

    private final String methodName;
    private final double feePercentage;
    private final boolean requiresProcessing;
    private final String description;

    PaymentMethod(String methodName, double feePercentage, boolean requiresProcessing, String description) {
        this.methodName = methodName;
        this.feePercentage = feePercentage;
        this.requiresProcessing = requiresProcessing;
        this.description = description;
    }

    public static int getTotalMethods() {
        return totalMethods;
    }

    public static PaymentMethod getCheapestMethod() {
        return Arrays.stream(values())
                .min((m1, m2) -> Double.compare(m1.feePercentage, m2.feePercentage))
                .orElse(CASH);
    }

    public static PaymentMethod getMostExpensiveMethod() {
        return Arrays.stream(values())
                .max((m1, m2) -> Double.compare(m1.feePercentage, m2.feePercentage))
                .orElse(CASH);
    }

    public String getMethodName() {
        return methodName;
    }

    public double getFeePercentage() {
        return feePercentage;
    }

    public boolean requiresProcessing() {
        return requiresProcessing;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(feePercentage));
    }

    public BigDecimal getTotalAmount(BigDecimal baseAmount) {
        return baseAmount.add(calculateFee(baseAmount));
    }

    public boolean digital() {
        return this == MOBILE_PAY || this == CRYPTOCURRENCY || this == BANK_TRANSFER;
    }

    public boolean traditional() {
        return this == CASH || this == CREDIT_CARD || this == DEBIT_CARD;
    }

    public String getPaymentInfo() {
        return methodName + " (" + (feePercentage * 100) + "% fee) - " + description;
    }

    public boolean instantPayment() {
        return this == CASH || this == VOUCHER;
    }
}
