package com.bookride.chalo.dto;

import com.bookride.chalo.entities.enums.TransactionMethod;
import com.bookride.chalo.entities.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WalletTransactionDto {

    private Long id;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto ride;

    private String transactionId;

    private LocalDateTime timeStamp;

    private WalletDto wallet;
}
