package br.com.olik.asigntest.dto;

import br.com.olik.asigntest.entity.Wallet;

import java.math.BigDecimal;

public record WalletDto(Long userId, BigDecimal amount, Wallet.WalletType type) {
}
