package com.eteration.simplebanking;

import com.eteration.simplebanking.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration
class DemoApplicationTests {

    @Autowired
    private TransactionService transactionService;
	@Test
	void contextLoads() {
	}

    @Test
    void testTransactionServiceIsLoaded() {
        assertThat(transactionService).isNotNull();
    }
}
