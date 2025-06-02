package br.com.olik.asigntest.usecase;

import br.com.olik.asigntest.dto.TransactionDto;
import br.com.olik.asigntest.dto.WalletDto;
import br.com.olik.asigntest.entity.Wallet;
import br.com.olik.asigntest.exception.InsufficientBalanceException;
import br.com.olik.asigntest.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Objects;

@Service
public class ProcessTransaction {

    Logger logger = LoggerFactory.getLogger(ProcessTransaction.class);

    private final WalletRepository walletRepository;

    public ProcessTransaction(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletDto process(TransactionDto transactionDto) {
        validateRequest(transactionDto);

        Wallet wallet = walletRepository.findByUserId(transactionDto.userId());

        try {
            wallet.processTransaction(transactionDto.amount());
        } catch (InsufficientBalanceException e) {
            // Send text message (SMS) to customer with user ID transactionDto.userId()
            logger.info("Transaction denied due to insufficient balance, sending text message to customer {}", transactionDto.userId());
            throw e;
        }

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
