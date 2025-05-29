package br.com.olik.asigntest.entity;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(WalletType type) {
        this.type = type;
    }
}
