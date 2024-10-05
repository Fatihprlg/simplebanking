package com.eteration.simplebanking.services;


import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findAccountById(String accountId) throws EntityNotFoundException {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.orElseThrow(() -> new EntityNotFoundException("Account with id " + accountId + " not found."));
    }

    public Account createAccount(String owner) {
        Account account = new Account(owner);
        return accountRepository.save(account);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }

    public List<Account> getAllAccounts() {
        Iterable<Account> iterable = accountRepository.findAll();
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Account updateAccount(String accountId, Account newAccountData) throws EntityNotFoundException{
        Optional<Account> existingAccountOpt = accountRepository.findById(accountId);

        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            if (newAccountData.getOwner() != null && !newAccountData.getOwner().isEmpty()) {
                existingAccount.setOwner(newAccountData.getOwner());
            }
            if (newAccountData.getAccountNumber() != null && !newAccountData.getAccountNumber().isEmpty()) {
                existingAccount.setAccountNumber(newAccountData.getAccountNumber());
            }
            if (newAccountData.getBalance() >= 0) {
                existingAccount.setBalance(newAccountData.getBalance());
            }
            if (!newAccountData.getTransactions().isEmpty()) {
                existingAccount.setTransactions(newAccountData.getTransactions());
            }
            return accountRepository.save(existingAccount);
        } else {
            throw new EntityNotFoundException("Account with id " + accountId + " not found.");
        }
    }
}
