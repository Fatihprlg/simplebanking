package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.model.dto.BillPaymentTransactionRequest;
import com.eteration.simplebanking.model.dto.TransactionRequest;
import com.eteration.simplebanking.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("deposit")
    public ResponseEntity<TransactionStatus> deposit(@RequestBody TransactionRequest request) {
        DepositTransaction transaction = new DepositTransaction(request.amount);
        return processTransaction(request.accountNumber, transaction);
    }

    @PostMapping("withdrawal")
    public ResponseEntity<TransactionStatus> withdrawal(@RequestBody TransactionRequest request) {
        WithdrawalTransaction transaction = new WithdrawalTransaction(request.amount);
        return processTransaction(request.accountNumber, transaction);
    }

    @PostMapping("billPayment")
    public ResponseEntity<TransactionStatus> billPayment(@RequestBody BillPaymentTransactionRequest request) {
        BillPaymentTransaction transaction = new BillPaymentTransaction(request.billType, request.amount);
        return processTransaction(request.accountNumber, transaction);
    }

    private ResponseEntity<TransactionStatus> processTransaction(String accountNumber, Transaction transaction) {
        try {
            var status = transactionService.applyTransaction(accountNumber, transaction);
            return ResponseEntity.ok(status);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
