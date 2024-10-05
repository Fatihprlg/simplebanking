package com.eteration.simplebanking.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Long id;
    protected Date date;
    protected String type;
    protected double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonBackReference
    protected Account account;

    @Embedded
    protected TransactionStatus status;

    public Transaction(double amount) {
        date = new Date();
        type = this.getClass().getSimpleName();
        this.amount = amount;
    }

    public abstract TransactionStatus apply(Account account);

    @Override
    public String toString() {
        return type + " [ date=" + date + ", amount=" + amount + ", account=" +
                account + ", status=" + status + "]";
    }
}
