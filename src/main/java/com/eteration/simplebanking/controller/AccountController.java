package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("get_by_id/{id}")
    public ResponseEntity<?> getAccount(@PathVariable("id") String accountNumber) {
        try {
            var response = accountService.findAccountById(accountNumber);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("get_all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        try {
            var accounts = accountService.getAllAccounts();
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") String accountNumber) {
        try {
            accountService.deleteAccount(accountNumber);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("create")
    public ResponseEntity<?> createAccount(@RequestParam String owner) {
        try {
            var response = accountService.createAccount(owner);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("update")
    public ResponseEntity<?> updateAccount(@RequestBody Account account) {
        try {
            var response = accountService.updateAccount(account.getAccountNumber(), account);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}