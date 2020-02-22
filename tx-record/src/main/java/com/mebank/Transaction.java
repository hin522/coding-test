package com.mebank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    public enum Type {PAYMENT, REVERSAL}

    private String id;
    private String fromAccountId;
    private String toAccountId;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private Type type;
    private String relatedTransaction;

    public Transaction(String id, String fromAccountId, String toAccountId, LocalDateTime createdAt, BigDecimal amount, Type type, String relatedTransaction) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.createdAt = createdAt;
        this.amount = amount;
        this.type = type;
        this.relatedTransaction = relatedTransaction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRelatedTransaction() {
        return relatedTransaction;
    }

    public void setRelatedTransaction(String relatedTransaction) {
        this.relatedTransaction = relatedTransaction;
    }
}
