package br.com.olik.asigntest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class WalletController {

    Logger logger = LoggerFactory.getLogger(WalletController.class);

    private final WalletRepository walletRepository;

    @GetMapping("/amount")
    public BigDecimal getAmount(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        return wallet.getAmount();
    }

    @PostMapping("/transaction")
    public BigDecimal transaction(@RequestBody TransactionDto transactionDto) {
        logger.info("Start Transaction {}", transactionDto);
        Wallet wallet = walletRepository.findByUserId(transactionDto.getUserId());
        wallet.setAmount(wallet.getAmount().add(transactionDto.getAmount()));
        walletRepository.save(wallet);
        logger.info("Wallet {}", wallet);
        return wallet.getAmount();
    }
}
