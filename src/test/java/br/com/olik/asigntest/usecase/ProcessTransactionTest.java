package br.com.olik.asigntest.usecase;

import br.com.olik.asigntest.dto.TransactionDto;
import br.com.olik.asigntest.dto.WalletDto;
import br.com.olik.asigntest.entity.Wallet;
import br.com.olik.asigntest.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessTransactionTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private ProcessTransaction processTransaction;

    @Test
    void processHappyFlow() {
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("-25.20");
        TransactionDto transactionDto = new TransactionDto(userId, amount);

        Wallet wallet = new Wallet.Builder()
                .id(123L)
                .userId(userId)
                .amount(new BigDecimal("100.00"))
                .type(Wallet.WalletType.CREDIT)
                .build();

        Wallet updatedWallet = new Wallet.Builder()
                .id(123L)
                .userId(userId)
                .amount(new BigDecimal("74.80"))
                .type(Wallet.WalletType.CREDIT)
                .build();

        when(walletRepository.findByUserId(userId)).thenReturn(wallet);
        when(walletRepository.save(wallet)).thenReturn(updatedWallet);

        WalletDto actualWallet = processTransaction.process(transactionDto);

        verify(walletRepository).findByUserId(userId);
        verify(walletRepository).save(wallet);
        verifyNoMoreInteractions(walletRepository);

        assertEquals(updatedWallet.getUserId(), actualWallet.userId());
        assertEquals(updatedWallet.getAmount(), actualWallet.amount());
        assertEquals(updatedWallet.getType(), actualWallet.type());
    }

    @Test
    void processWithUserIdNull() {
        TransactionDto transactionDto = new TransactionDto(null, new BigDecimal("-20.00"));

        try {
            processTransaction.process(transactionDto);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(walletRepository);

            assertEquals("Field 'userId' is missing", e.getMessage());
        }
    }

    @Test
    void processWithAmountNull() {
        TransactionDto transactionDto = new TransactionDto(2L, null);

        try {
            processTransaction.process(transactionDto);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(walletRepository);

            assertEquals("Field 'amount' is missing", e.getMessage());
        }
    }

    @Test
    void processWithAmountZero() {
        TransactionDto transactionDto = new TransactionDto(3L, BigDecimal.ZERO);

        try {
            processTransaction.process(transactionDto);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(walletRepository);

            assertEquals("Amount cannot be 0", e.getMessage());
        }
    }
}