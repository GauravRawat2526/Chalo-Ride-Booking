package com.bookride.chalo.strategies.Impl;

import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.Payment;
import com.bookride.chalo.entities.Wallet;
import com.bookride.chalo.entities.enums.PaymentStatus;
import com.bookride.chalo.entities.enums.TransactionMethod;
import com.bookride.chalo.repositories.PaymentRepository;
import com.bookride.chalo.services.WalletService;
import com.bookride.chalo.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Wallet wallet = walletService.findByUser(driver.getUser());
        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;
        walletService.deductMoneyFromWallet(driver.getUser() , platformCommission , null , payment.getRide() ,  TransactionMethod.Ride);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
