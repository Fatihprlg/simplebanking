package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.TransactionStatus;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public TransactionStatus applyTransaction(String accountId, Transaction transaction) throws EntityNotFoundException {
        var account = accountService.findAccountById(accountId);
        var status = account.post(transaction);
        transactionRepository.save(transaction);
        accountService.updateAccount(accountId, account);
        return status;
    }
}
