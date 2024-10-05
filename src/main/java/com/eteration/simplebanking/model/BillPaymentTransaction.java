package com.eteration.simplebanking.model;


import com.eteration.simplebanking.common.BillTypes;
import com.eteration.simplebanking.common.Constants;
import com.eteration.simplebanking.common.Status;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BILL_PAYMENT")
@NoArgsConstructor
public class BillPaymentTransaction extends Transaction {
    private BillTypes billType;

    public BillPaymentTransaction(BillTypes type, double amount){
        super(amount);
        this.billType = type;
        this.type = billType.name() + "_" + this.getClass().getSimpleName();
    }

    @Override
    public TransactionStatus apply(Account account) {

        try {
            BillTypes.valueOf(billType.name()); // check isValid
            this.account = account;
            account.debit(amount);
        } catch (InsufficientBalanceException | IllegalArgumentException e) {
            status = new TransactionStatus(Status.FAIL, e.getMessage());
            return status;
        }
        status = new TransactionStatus(Status.OK, Constants.TRANSACTION_SUCCESSFUL);
        return status;
    }

    @Override
    public String toString() {
        return "BillPaymentTransaction [billType=" + billType + ", amount=" + amount + ", account=" +
                account + ", status=" + status + "]";
    }
}
