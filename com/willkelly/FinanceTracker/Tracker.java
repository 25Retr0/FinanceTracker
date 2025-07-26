package com.willkelly.FinanceTracker;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tracker {
    //private double balance;
    private final DoubleProperty balance = new SimpleDoubleProperty();

    private final List<Transaction> transactions = new ArrayList<>();
    private final UUID userId; // User id for the user currently using this tracker

    public Tracker(UUID userId, double startingBalance) {
        this.balance.set(startingBalance);
        this.userId = userId;
    }

    public UUID getUserId() {
        return this.userId;
    }

    private void addToBalance(double amount) {
        balance.set(balance.get() + amount);
    }

    private void removeFromBalance(double amount) {
        balance.set(balance.get() - amount);
    }

    public Transaction createTransaction(double amount, TransactionType type) {
        return new Transaction(this.userId, amount, type);

    }

    public Transaction createTransaction(double amount, TransactionType type, String category) {
        Transaction t = new Transaction(this.userId, amount, type);
        t.setCategory(category);
        return t;
    }

    public void addTransaction(Transaction t) {
        this.transactions.add(t);
        DatabaseHelper.insertTransaction(t);

        // Handle balance
        if (t.getType() == TransactionType.INCOME) {
            addToBalance(t.getAmount());
        } else if (t.getType() == TransactionType.EXPENSE) {
            removeFromBalance(t.getAmount());
        }

        DatabaseHelper.updateUserBalance(this.userId, this.balance.get());
    }

    public boolean removeTransaction(UUID id) {
        // Loop through the transactions and see if id matches.
        // if matches remove

        for (int i = 0; i < transactions.size(); i++) {
            if (this.transactions.get(i).getId() == id) {
                DatabaseHelper.deleteTransaction(String.valueOf(this.transactions.get(i).getId()));
                this.transactions.remove(i);
                return true;
            }
        }

        return false;
    }

    public List<Transaction> getTransactions() {
        return DatabaseHelper.getAllTransactions(this.userId);
    }

    public double getBalance() {
        return this.balance.get();
    }

    public DoubleProperty getBalanceProperty() {
        return this.balance;
    }

    public List<Transaction> getTransactionsByCategory(String category) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : this.transactions) {
            if (t.getCategory().equalsIgnoreCase(category)) {
                result.add(t);
            }
        }
        return result;
    }

    public String getTransactionsString() {
        StringBuilder sb = new StringBuilder();
        for (Transaction t : this.transactions) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }

    public String getTransactionsString(TransactionType type) {
        StringBuilder sb = new StringBuilder();
        for (Transaction t : this.transactions) {
            if (t.getType() == type) {
                sb.append(t.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
