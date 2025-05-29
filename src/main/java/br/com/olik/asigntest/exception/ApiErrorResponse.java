package br.com.olik.asigntest.exception;

import java.time.LocalDateTime;

public record ApiErrorResponse(Integer status, String message, LocalDateTime timestamp) {
}
