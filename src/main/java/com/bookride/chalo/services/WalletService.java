package com.bookride.chalo.services;

import com.bookride.chalo.entities.Ride;
import com.bookride.chalo.entities.User;
import com.bookride.chalo.entities.Wallet;
import com.bookride.chalo.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount , String transactionId , Ride ride , TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);

    Wallet deductMoneyFromWallet(User user, Double amount , String transactionId , Ride ride , TransactionMethod transactionMethod);
}
