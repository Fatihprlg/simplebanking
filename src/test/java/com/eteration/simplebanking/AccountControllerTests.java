package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountControllerTests {


    private AccountController accountController;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        AccountService accountService = new AccountService(accountRepository);
        accountController = new AccountController(accountService);
        account = new Account("Fatih Parlagi");
        account.setAccountNumber("12345");
        account.setBalance(1000.0);
    }

    @Test
    public void testGetAccount_Success() {
        when(accountRepository.findById("12345")).thenReturn(Optional.of(account));

        ResponseEntity<?> response = accountController.getAccount("12345");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(account, response.getBody());
    }

    @Test
    public void testGetAccount_NotFound() {
        when(accountRepository.findById("67890")).thenThrow(new EntityNotFoundException());

        ResponseEntity<?> response = accountController.getAccount("67890");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetAllAccounts_Success() {
        when(accountRepository.findAll()).thenReturn(Collections.singletonList(account));

        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testDeleteAccount_Success() {
        doNothing().when(accountRepository).deleteById("12345");

        ResponseEntity<?> response = accountController.deleteAccount("12345");

        assertEquals(200, response.getStatusCodeValue());
        verify(accountRepository, times(1)).deleteById("12345");
    }

    @Test
    public void testCreateAccount_Success() {
        var newAccount = new Account("Kerem Karaca");
        newAccount.setAccountNumber("12345");
        newAccount.setBalance(1000.0);
        when(accountRepository.save(any())).thenReturn(newAccount);
        ResponseEntity<?> response = accountController.createAccount("Kerem Karaca");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(newAccount, response.getBody());
    }

    @Test
    public void testUpdateAccount_Success() {
        when(accountRepository.save(account)).thenReturn(account);
        when(accountRepository.findById(account.getAccountNumber())).thenReturn(Optional.of(account));
        ResponseEntity<?> response = accountController.updateAccount(account);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(account, response.getBody());
    }

    @Test
    public void testUpdateAccount_NotFound() {
        when(accountRepository.save(account)).thenThrow(new EntityNotFoundException());

        ResponseEntity<?> response = accountController.updateAccount(account);

        assertEquals(404, response.getStatusCodeValue());
    }
}

