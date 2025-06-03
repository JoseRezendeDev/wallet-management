package br.com.olik.asigntest.usecase;

import br.com.olik.asigntest.dto.TransactionDto;
import br.com.olik.asigntest.dto.WalletDto;
import br.com.olik.asigntest.entity.Wallet;
import br.com.olik.asigntest.exception.InsufficientBalanceException;
import br.com.olik.asigntest.exception.WalletNotFoundException;
import br.com.olik.asigntest.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class ProcessTransaction {

    Logger logger = LoggerFactory.getLogger(ProcessTransaction.class);

    private final WalletRepository walletRepository;

    public ProcessTransaction(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public WalletDto process(TransactionDto transactionDto) {
        validateRequest(transactionDto);

        Wallet wallet = walletRepository.findByUserId(transactionDto.userId());

        if (Objects.isNull(wallet)) {
            throw new WalletNotFoundException("Cannot find wallet for user ID " + transactionDto.userId());
        }

        try {
            wallet.processTransaction(transactionDto.amount());
        } catch (InsufficientBalanceException e) {
            logger.info("Transaction denied due to insufficient balance, sending text message to customer {}", transactionDto.userId());
            sendNotification(transactionDto, e);
            throw e;
        }

        try {
            Wallet updatedWallet = walletRepository.save(wallet);
            return new WalletDto(updatedWallet.getUserId(), updatedWallet.getAmount(), updatedWallet.getType());
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.info("Concurrent transactions, try again");
            throw e;
        }
    }

    @Async
    private void sendNotification(TransactionDto transactionDto, InsufficientBalanceException e) {
//         smsService.sendSms(transactionDto.userId());
//         monitoringService.error(transactionDto, e);
//         queueService.sendMessage(transactionDto, e);
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
