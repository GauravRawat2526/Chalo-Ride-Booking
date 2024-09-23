package com.bookride.chalo.repositories;

import com.bookride.chalo.entities.User;
import com.bookride.chalo.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet , Long> {

    Optional<Wallet> findByUser(User user);
}
