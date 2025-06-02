package br.com.olik.asigntest.usecase;

import br.com.olik.asigntest.dto.TransactionDto;
import br.com.olik.asigntest.dto.WalletDto;
import br.com.olik.asigntest.entity.Wallet;
import br.com.olik.asigntest.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class ProcessTransaction {

    private final WalletRepository walletRepository;

    public ProcessTransaction(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletDto process(TransactionDto transactionDto) {
        validateRequest(transactionDto);

        Wallet wallet = walletRepository.findByUserId(transactionDto.userId());

        wallet.processTransaction(transactionDto.amount());

        Wallet updatedWallet = walletRepository.save(wallet);

        return new WalletDto(updatedWallet.getUserId(), updatedWallet.getAmount(), updatedWallet.getType());
    }

    private void validateRequest(TransactionDto transactionDto) {
        if (Objects.isNull(transactionDto.userId())) {
            throw new IllegalArgumentException("Field 'userId' is missing");
        }

        if (Objects.isNull(transactionDto.amount())) {
            throw new IllegalArgumentException("Field 'amount' is missing");
        }

        if (BigDecimal.ZERO.compareTo(transactionDto.amount()) == 0) {
            throw new IllegalArgumentException("Amount cannot be 0");
        }
    }
}
