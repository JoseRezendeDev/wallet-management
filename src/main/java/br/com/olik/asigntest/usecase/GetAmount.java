package br.com.olik.asigntest.usecase;

import br.com.olik.asigntest.entity.Wallet;
import br.com.olik.asigntest.exception.WalletNotFoundException;
import br.com.olik.asigntest.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
public class GetAmount {

    private final WalletRepository walletRepository;

    public GetAmount(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public BigDecimal get(Long userId) {
        validateRequest(userId);

        Wallet wallet = walletRepository.findByUserId(userId);

        return Optional.ofNullable(wallet)
                .map(Wallet::getAmount)
                .orElseThrow(() -> new WalletNotFoundException("Cannot find wallet for user ID " + userId));
    }

    private void validateRequest(Long userId) {
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("Query param 'userId' is missing");
        }
    }
}
