package br.com.olik.asigntest.service;

import br.com.olik.asigntest.entity.Wallet;
import br.com.olik.asigntest.exception.WalletNotFoundException;
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
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @Test
    public void getAmountHappyFlow() {
        Long userId = 1L;

        Wallet wallet = Wallet.builder()
                .id(123L)
                .userId(1L)
                .amount(new BigDecimal("75.40"))
                .type(Wallet.WalletType.CREDIT)
                .build();

        when(walletRepository.findByUserId(userId)).thenReturn(wallet);

        BigDecimal amount = walletService.getAmount(userId);

        verify(walletRepository).findByUserId(userId);
        verifyNoMoreInteractions(walletRepository);

        assertEquals(wallet.getAmount(), amount);
    }

    @Test
    public void getAmountWalletNotFound() {
        Long userId = 1L;

        Wallet wallet = Wallet.builder()
                .id(123L)
                .userId(1L)
                .amount(new BigDecimal("75.40"))
                .type(Wallet.WalletType.CREDIT)
                .build();

        when(walletRepository.findByUserId(userId)).thenReturn(null);

        try {
            BigDecimal amount = walletService.getAmount(userId);
            fail("Should have thrown exception");
        } catch (WalletNotFoundException e) {
            verify(walletRepository).findByUserId(userId);
            verifyNoMoreInteractions(walletRepository);

            assertEquals("Cannot find wallet for user ID " + userId, e.getMessage());
        }
    }

    @Test
    public void getAmountUserIdMissing() {
        try {
            BigDecimal amount = walletService.getAmount(null);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            verifyNoMoreInteractions(walletRepository);

            assertEquals("Query param 'userId' is missing", e.getMessage());
        }
    }
}