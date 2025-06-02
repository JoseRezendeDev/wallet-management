package br.com.olik.asigntest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, InsufficientBalanceException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(Throwable t) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), t.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiErrorResponse);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleWalletNotFoundException(Throwable t) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), t.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Throwable t) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), t.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiErrorResponse);
    }
}
