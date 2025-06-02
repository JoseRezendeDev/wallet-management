package br.com.olik.asigntest.controller;

import br.com.olik.asigntest.dto.TransactionDto;
import br.com.olik.asigntest.dto.WalletDto;
import br.com.olik.asigntest.usecase.GetAmount;
import br.com.olik.asigntest.usecase.ProcessTransaction;
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

    private final GetAmount getAmount;
    private final ProcessTransaction processTransaction;

    public WalletController(GetAmount getAmount, ProcessTransaction processTransaction) {
        this.getAmount = getAmount;
        this.processTransaction = processTransaction;
    }

    @GetMapping("/amount")
    public ResponseEntity<BigDecimal> getAmount(Long userId) {
        return ResponseEntity.ok(getAmount.get(userId));
    }

    @PostMapping("/transaction")
    public ResponseEntity<BigDecimal> transaction(@RequestBody TransactionDto transactionDto) {
        logger.info("Start Transaction {}", transactionDto);
        WalletDto walletDto = processTransaction.process(transactionDto);
        logger.info("Wallet {}", walletDto);
        return ResponseEntity.ok(walletDto.amount());
    }
}
