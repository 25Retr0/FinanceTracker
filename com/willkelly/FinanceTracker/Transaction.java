package com.willkelly.FinanceTracker;

import java.time.LocalDate;
import java.util.UUID;


public class Transaction {
    private final UUID id;
    private final UUID userId;
    private final double amount;
    private String account;
    private final TransactionType type;
    private String category;
    private final LocalDate date;

    public Transaction(UUID userId, double amount, TransactionType type) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.category = "";
        this.date = LocalDate.now();
    }

    public Transaction(
            String id, String userId, double amount, String type, String category, String date) {
        this.id = UUID.fromString(id);
        this.userId = UUID.fromString(userId);
        this.amount = amount;
        this.type = TransactionType.fromString(type);
        this.category = category;
        this.date = LocalDate.parse(date);
    }

    public UUID getId() { return this.id; }

    public UUID getUserId() { return this.userId; }

    public double getAmount() { return this.amount; }

    public TransactionType getType() { return this.type; }

    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDate() { return this.date; }

    public String getAccount() { return this.account; }

    public void setAccount(String account) { this.account = account; }

    @Override
    public String toString() {
        return date + " - " + type + " - $" + amount + " [" + category + "]";
    }
}