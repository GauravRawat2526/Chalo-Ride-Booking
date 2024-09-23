package com.bookride.chalo.strategies;

import com.bookride.chalo.entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);
}
