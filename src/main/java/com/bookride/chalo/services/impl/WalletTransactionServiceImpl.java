package com.bookride.chalo.services.impl;

import com.bookride.chalo.entities.WalletTransaction;
import com.bookride.chalo.repositories.WalletTransactionRepository;
import com.bookride.chalo.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
