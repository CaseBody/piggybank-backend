package com.testing.piggybank;

import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.helper.CurrencyConverterService;
import com.testing.piggybank.model.*;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.TransactionRepository;
import com.testing.piggybank.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CurrencyConverterService converterService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTransactions() {
        long accountId = 1L;
        List<Transaction> mockTransactions = createMockTransactions(accountId);
        when(transactionRepository.findAll()).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.getTransactions(5, accountId);

        assertEquals(3, result.size());
    }

    @Test
    void testFilterAndLimitTransactions() {
        // Arrange
        long accountId = 1L;
        List<Transaction> mockTransactions = createMockTransactions(accountId);

        // Act
        List<Transaction> filteredTransactions = transactionService.filterAndLimitTransactions(mockTransactions, accountId, 2);

        // Assert
        assertEquals(2, filteredTransactions.size()); // Assuming 3 transactions belong to accountId 2
    }

    @Test
    void createTransactionTest() {
        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
        createTransactionRequest.setCurrency(Currency.GBP);
        createTransactionRequest.setDescription("Test");
        createTransactionRequest.setAmount(BigDecimal.TEN);
        createTransactionRequest.setSenderAccountId(1L);
        createTransactionRequest.setReceiverAccountId(2L);

        Account senderAccount = new Account();
        senderAccount.setId(1L);
        senderAccount.setName("Afzender");
        senderAccount.setBalance(BigDecimal.valueOf(100));

        Account receiverAccount = new Account();
        receiverAccount.setId(2L);
        receiverAccount.setName("Ontvanger");
        receiverAccount.setBalance(BigDecimal.valueOf(50));

        when(accountService.getAccount(1L)).thenReturn(Optional.of(senderAccount));
        when(accountService.getAccount(2L)).thenReturn(Optional.of(receiverAccount));
        when(transactionRepository.save(any())).thenReturn(null);


        transactionService.createTransaction(createTransactionRequest);
        verify(transactionRepository).save(any());
    }

    private List<Transaction> createMockTransactions(long accountId) {
        Account senderAccount = new Account();
        senderAccount.setId(accountId);
        senderAccount.setName("Afzender");
        senderAccount.setBalance(BigDecimal.valueOf(100));

        Account receiverAccount = new Account();
        receiverAccount.setId(2L);
        receiverAccount.setName("Ontvanger");
        receiverAccount.setBalance(BigDecimal.valueOf(50));

        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        transaction.setAmount(BigDecimal.TEN);
        transaction.setCurrency(Currency.EURO);
        transaction.setDescription("Voorbeeldtransactie");
        transaction.setDateTime(Instant.now());
        transaction.setStatus(Status.SUCCESS);
        transactions.add(transaction);

        Transaction transaction2 = new Transaction();
        transaction2.setSenderAccount(senderAccount);
        transaction2.setReceiverAccount(receiverAccount);
        transaction2.setAmount(BigDecimal.TEN);
        transaction2.setCurrency(Currency.EURO);
        transaction2.setDescription("Voorbeeldtransactie");
        transaction2.setDateTime(Instant.now());
        transaction2.setStatus(Status.SUCCESS);
        transactions.add(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setSenderAccount(senderAccount);
        transaction3.setReceiverAccount(receiverAccount);
        transaction3.setAmount(BigDecimal.TEN);
        transaction3.setCurrency(Currency.EURO);
        transaction3.setDescription("Voorbeeldtransactie");
        transaction3.setDateTime(Instant.now());
        transaction3.setStatus(Status.SUCCESS);
        transactions.add(transaction3);

        return transactions;
    }
}
