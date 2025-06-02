package br.com.olik.asigntest.entity;

import br.com.olik.asigntest.exception.InsufficientBalanceException;
import br.com.olik.asigntest.exception.NegativeBalanceException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private BigDecimal amount;

    private WalletType type;

    public enum WalletType {
        DEBIT, CREDIT
    }

    public Wallet() {
    }

    public void processTransaction(BigDecimal amount) {
        if (BigDecimal.ZERO.compareTo(amount) > 0) {
            if (BigDecimal.ZERO.compareTo(this.amount) > 0) {
                throw new NegativeBalanceException("Balance is already negative, cannot do transactions anymore");
            }

            if (WalletType.DEBIT.equals(this.type)
                    && BigDecimal.ZERO.compareTo(this.amount.add(amount)) > 0) {
                throw new InsufficientBalanceException("Insufficient balance for this transaction");
            }
        }

        this.amount = this.amount.add(amount);
    }

    public static Builder builder() {
        return new Builder();
    }

    private Wallet(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.amount = builder.amount;
        this.type = builder.type;
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private BigDecimal amount;
        private WalletType type;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder type(WalletType type) {
            this.type = type;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }

    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public WalletType getType() {
        return type;
    }
}
