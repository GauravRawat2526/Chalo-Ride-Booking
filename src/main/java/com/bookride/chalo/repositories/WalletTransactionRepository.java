package com.bookride.chalo.repositories;

import com.bookride.chalo.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction , Long> {
}
