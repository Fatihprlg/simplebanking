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

    public BillPaymentTransaction(BillTypes type, double amount) throws IllegalArgumentException {
        super(amount);
        try{
            this.billType = BillTypes.valueOf(type.name()); // check isValid
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Bill type is not supported");
        }
        this.type = billType.name() + "_" + this.getClass().getSimpleName();
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

    @Override
    public String toString() {
        return "BillPaymentTransaction [billType=" + billType + ", amount=" + amount + ", account=" +
                account + ", status=" + status + "]";
    }
}
