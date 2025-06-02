package br.com.olik.asigntest.entity;

import br.com.olik.asigntest.exception.InsufficientBalanceException;
import br.com.olik.asigntest.exception.NegativeBalanceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WalletTest {

    @Test
    public void processTransactionCreditPositiveBalance() {
        BigDecimal balance = new BigDecimal("100.00");
        BigDecimal amount = new BigDecimal("-25.50");

        Wallet wallet = new Wallet.Builder()
                .id(123L)
                .userId(1L)
                .amount(balance)
                .type(Wallet.WalletType.CREDIT)
                .build();

        wallet.processTransaction(amount);

        assertEquals(balance.add(amount), wallet.getAmount());
    }

    @Test
    public void processTransactionDebitPositiveBalance() {
        BigDecimal balance = new BigDecimal("100.00");
        BigDecimal amount = new BigDecimal("-25.50");

        Wallet wallet = new Wallet.Builder()
                .id(123L)
                .userId(1L)
                .amount(balance)
                .type(Wallet.WalletType.DEBIT)
                .build();

        wallet.processTransaction(amount);

        assertEquals(balance.add(amount), wallet.getAmount());
    }

    @Test
    public void processTransactionCreditNegativeBalance() {
        BigDecimal balance = new BigDecimal("100.00");
        BigDecimal amount = new BigDecimal("-120.00");

        Wallet wallet = new Wallet.Builder()
                .id(123L)
                .userId(1L)
                .amount(balance)
                .type(Wallet.WalletType.CREDIT)
                .build();

        wallet.processTransaction(amount);

        assertEquals(balance.add(amount), wallet.getAmount());
    }

    @Test
    public void processTransactionDebitNegativeBalance() {
        BigDecimal balance = new BigDecimal("100.00");
        BigDecimal amount = new BigDecimal("-120.00");

        Wallet wallet = new Wallet.Builder()
                .id(123L)
                .userId(1L)
                .amount(balance)
                .type(Wallet.WalletType.DEBIT)
                .build();

        try {
            wallet.processTransaction(amount);
            fail("Should have thrown exception");
        } catch (InsufficientBalanceException e) {
            assertEquals("Insufficient balance for this transaction", e.getMessage());
        }
    }

    @Test
    public void processTransactionCreditAlreadyNegativeBalance() {
        BigDecimal balance = new BigDecimal("-10.00");
        BigDecimal amount = new BigDecimal("-20.00");

        Wallet wallet = new Wallet.Builder()
                .id(123L)
                .userId(1L)
                .amount(balance)
                .type(Wallet.WalletType.CREDIT)
                .build();

        try {
            wallet.processTransaction(amount);
            fail("Should have thrown exception");
        } catch (NegativeBalanceException e) {
            assertEquals("Balance is already negative, cannot do transactions anymore", e.getMessage());
        }
    }

    @Test
    public void processTransactionCreditNegativeBalanceWithPositiveAmount() {
        BigDecimal balance = new BigDecimal("-10.00");
        BigDecimal amount = new BigDecimal("5.00");

        Wallet wallet = new Wallet.Builder()
                .id(123L)
                .userId(1L)
                .amount(balance)
                .type(Wallet.WalletType.CREDIT)
                .build();

        wallet.processTransaction(amount);

        assertEquals(balance.add(amount), wallet.getAmount());
    }
}