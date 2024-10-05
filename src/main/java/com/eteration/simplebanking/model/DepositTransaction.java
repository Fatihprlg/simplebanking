package com.eteration.simplebanking.model;


import com.eteration.simplebanking.common.Constants;
import com.eteration.simplebanking.common.Status;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DEPOSIT")
@NoArgsConstructor
public class DepositTransaction extends Transaction {

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    public TransactionStatus apply(Account account) {
        this.account = account;
        account.credit(amount);
        status = new TransactionStatus(Status.OK, Constants.TRANSACTION_SUCCESSFUL);
        return status;
    }
}
