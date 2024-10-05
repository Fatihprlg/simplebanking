package com.eteration.simplebanking.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    public String accountNumber;
    public double amount;
}

