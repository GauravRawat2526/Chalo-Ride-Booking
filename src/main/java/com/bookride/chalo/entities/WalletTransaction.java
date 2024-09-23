package com.bookride.chalo.entities;

import com.bookride.chalo.entities.enums.TransactionMethod;
import com.bookride.chalo.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(indexes = {
        @Index(name = "idx_wallet_transaction_wallet" , columnList = "wallet_id"),
        @Index(name = "idx_wallet_transaction_ride" , columnList = "ride_id")

})
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @ManyToOne
    private Ride ride;

    private String transactionId;

    @CreationTimestamp
    private LocalDateTime timeStamp;

    @ManyToOne
    private Wallet wallet;

    private Double amount;
}
