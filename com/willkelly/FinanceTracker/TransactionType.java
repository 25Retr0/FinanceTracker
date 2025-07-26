package com.willkelly.FinanceTracker;
public enum TransactionType {
    INCOME,
    EXPENSE;

    public static TransactionType fromString(String str) {
        return switch (str.trim().toLowerCase()) {
            case "income" -> INCOME;
            case "expense" -> EXPENSE;
            default -> throw new IllegalArgumentException("Invalid TransactionType: " + str);
        };
    }
}
