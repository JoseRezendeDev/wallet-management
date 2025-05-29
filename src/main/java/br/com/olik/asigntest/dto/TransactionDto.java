package br.com.olik.asigntest.dto;

import java.math.BigDecimal;

public record TransactionDto(Long userId, BigDecimal amount) {
}
