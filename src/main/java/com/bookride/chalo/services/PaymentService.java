package com.bookride.chalo.services;

import com.bookride.chalo.entities.Payment;
import com.bookride.chalo.entities.Ride;
import com.bookride.chalo.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment , PaymentStatus status);
}
