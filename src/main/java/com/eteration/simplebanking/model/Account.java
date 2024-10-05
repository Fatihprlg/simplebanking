package com.eteration.simplebanking.model;


import com.eteration.simplebanking.common.Constants;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    private String accountNumber;
    private String owner;
    private double balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
    }
    public Account(String owner) {
        this.owner = owner;
    }

    @PrePersist
    public void generateId() {
        this.accountNumber = UUID.randomUUID().toString();
    }

    public TransactionStatus post(Transaction transaction) {
        TransactionStatus status = transaction.apply(this);
        transactions.add(transaction);
        return status;
    }

    public void credit(double amount) {
        balance += amount;
    }

    public void debit(double amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException(Constants.INSUFFICIENT_FUNDS);
        }
        balance -= amount;
    }
}
