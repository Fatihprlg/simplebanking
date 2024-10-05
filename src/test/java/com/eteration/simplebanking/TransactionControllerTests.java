package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.eteration.simplebanking.common.Status;
import com.eteration.simplebanking.controller.TransactionController;
import com.eteration.simplebanking.model.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.dto.TransactionRequest;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.services.AccountService;

import com.eteration.simplebanking.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


class TransactionControllerTests {

    private TransactionController controller;
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService service;

    private Account account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        TransactionService transactionService = new TransactionService(transactionRepository, service);
        controller = new TransactionController(transactionService);
        account = new Account("Kerem Karaca");
        account.setAccountNumber("17892");
    }

    @Test
    public void givenId_Credit_thenReturnJson() {
        TransactionRequest request = new TransactionRequest(account.getAccountNumber(), 100d);
        doReturn(account).when(service).findAccountById( "17892");
        ResponseEntity<TransactionStatus> result = controller.deposit( request );
        verify(service, times(1)).findAccountById("17892");
        assertEquals(Status.OK, result.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson() {
        
        TransactionRequest request = new TransactionRequest(account.getAccountNumber(), 1000d);
        TransactionRequest request2 = new TransactionRequest(account.getAccountNumber(), 50d);
        doReturn(account).when(service).findAccountById( "17892");
        ResponseEntity<TransactionStatus> result = controller.deposit(request);
        ResponseEntity<TransactionStatus> result2 = controller.withdrawal(request2);
        verify(service, times(2)).findAccountById("17892");
        assertEquals(Status.OK, result.getBody().getStatus());
        assertEquals(Status.OK, result2.getBody().getStatus());
        assertEquals(950.0, account.getBalance(),0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() {
            TransactionRequest request = new TransactionRequest(account.getAccountNumber(), 1000d);

            doReturn(account).when(service).findAccountById( "17892");
            ResponseEntity<TransactionStatus> result = controller.deposit(request);
            assertEquals(Status.OK, result.getBody().getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
            verify(service, times(1)).findAccountById("17892");
            TransactionRequest request2 = new TransactionRequest(account.getAccountNumber(), 5000d);

            ResponseEntity<TransactionStatus> result2 = controller.withdrawal(request2);
            assertEquals(Status.FAIL, result2.getBody().getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
    }

}
