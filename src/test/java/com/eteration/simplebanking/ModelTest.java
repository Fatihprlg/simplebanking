package com.eteration.simplebanking;



import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
	
	@Test
	public void testCreateAccountAndSetBalance0() {
		Account account = new Account("Kerem Karaca", "17892");
        assertEquals("Kerem Karaca", account.getOwner());
        assertEquals("17892", account.getAccountNumber());
        assertEquals(0, account.getBalance());
	}

	@Test
	public void testDepositIntoBankAccount() {
		Account account = new Account("Demet Demircan", "9834");
		account.credit(100);
        assertEquals(100, account.getBalance());
	}

	@Test
	public void testWithdrawFromBankAccount() throws InsufficientBalanceException {
		Account account = new Account("Demet Demircan", "9834");
		account.credit(100);
        assertEquals(100, account.getBalance());
		account.debit(50);
        assertEquals(50, account.getBalance());
	}

	@Test
	public void testWithdrawException() {
		Assertions.assertThrows( InsufficientBalanceException.class, () -> {
			Account account = new Account("Demet Demircan", "9834");
			account.credit(100);
			account.debit(500);
		  });

	}
	
	@Test
	public void testTransactions() {
		// Create account
		Account account = new Account("Canan Kaya", "1234");
        assertEquals(0, account.getTransactions().size());

		// Deposit Transaction
		DepositTransaction depositTrx = new DepositTransaction(100);
        assertNotNull(depositTrx.getDate());
		account.post(depositTrx);
        assertEquals(100, account.getBalance());
        assertEquals(1, account.getTransactions().size());

		// Withdrawal Transaction
		WithdrawalTransaction withdrawalTrx = new WithdrawalTransaction(60);
        assertNotNull(withdrawalTrx.getDate());
		account.post(withdrawalTrx);
        assertEquals(40, account.getBalance());
        assertEquals(2, account.getTransactions().size());
	}
}
