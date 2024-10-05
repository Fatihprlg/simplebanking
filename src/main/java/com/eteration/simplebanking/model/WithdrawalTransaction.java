package com.eteration.simplebanking.model;


import com.eteration.simplebanking.common.Constants;
import com.eteration.simplebanking.common.Status;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WITHDRAWAL")
@NoArgsConstructor
public class WithdrawalTransaction extends Transaction {


    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    public TransactionStatus apply(Account account) {
        try {
            this.account = account;
            account.debit(amount);
        } catch (InsufficientBalanceException e) {
            status = new TransactionStatus(Status.FAIL, e.getMessage());
            return status;
        }
        status = new TransactionStatus(Status.OK, Constants.TRANSACTION_SUCCESSFUL);
        return status;
    }
}


