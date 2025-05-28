package br.com.olik.asigntest;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InputDataSource {

    private final WalletRepository walletRepository;

    @PostConstruct
    public void init() {
        List<Wallet> wallets = List.of(
                buildWallet(1L, BigDecimal.valueOf(7), Wallet.WalletType.CREDIT),
                buildWallet(2L, BigDecimal.valueOf(10), Wallet.WalletType.DEBIT)
        );
        walletRepository.saveAll(wallets);
    }

    private Wallet buildWallet(Long userId, BigDecimal amount, Wallet.WalletType type) {
        return Wallet.builder()
                .amount(amount)
                .userId(userId)
                .type(type)
                .build();
    }
}
