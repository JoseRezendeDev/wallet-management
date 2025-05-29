package br.com.olik.asigntest.controller;

import br.com.olik.asigntest.dto.TransactionDto;
import br.com.olik.asigntest.entity.Wallet;
import br.com.olik.asigntest.repository.WalletRepository;
import br.com.olik.asigntest.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class WalletController {

    Logger logger = LoggerFactory.getLogger(WalletController.class);

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/amount")
    public ResponseEntity<BigDecimal> getAmount(Long userId) {
        return ResponseEntity.ok(walletService.getAmount(userId));
    }

    @PostMapping("/transaction")
    public BigDecimal transaction(@RequestBody TransactionDto transactionDto) {
        logger.info("Start Transaction {}", transactionDto);
//        Wallet wallet = walletRepository.findByUserId(transactionDto.getUserId());
//        wallet.setAmount(wallet.getAmount().add(transactionDto.getAmount()));
//        walletRepository.save(wallet);
//        logger.info("Wallet {}", wallet);
//        return wallet.getAmount();
        return null;
    }
}
