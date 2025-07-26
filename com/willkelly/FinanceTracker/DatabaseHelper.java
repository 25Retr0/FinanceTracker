package com.willkelly.FinanceTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:src/data/finance.db";
    private static final int idIndex = 1;
    private static final int userIdIndex = 2;
    private static final int amountIndex = 3;
    private static final int typeIndex = 4;
    private static final int categoryIndex = 5;
    private static final int dateIndex = 6;

    /**
     * Establish a conncetion to the SQLite database
     * @return Connection to the SQLite database
     * @throws SQLException
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Initialise the database 'transactions' table if it doesn't exist.
     */
    public static void initialiseDatabase() {
        initialiseUsersTable();
        initialiseTransactionsTable();
    }

    private static void initialiseTransactionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions ("
                + "id TEXT PRIMARY KEY,"
                + "user_id TEXT NOT NULL,"
                + "amount REAL NOT NULL,"
                + "type TEXT NOT NULL,"
                + "category TEXT,"
                + "date TEXT NOT NULL,"
                + "FOREIGN KEY(user_id) REFERENCES users(id)"
                + ");";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initialiseUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id TEXT PRIMARY KEY,"
                + "username TEXT NOT NULL UNIQUE,"
                + "password TEXT NOT NULL,"
                + "balance REAL"
                + ");";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertUser(UUID id, String username, String password, double balance) {
        String sql = "INSERT INTO users (id, username, password, balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(id));
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setDouble(4, balance);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add a new transaction into the database
     * @param transaction
     */
    public static void insertTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, user_id, amount, type, category, date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(idIndex, String.valueOf(transaction.getId()));
            pstmt.setString(userIdIndex, String.valueOf(transaction.getUserId()));
            pstmt.setDouble(amountIndex, transaction.getAmount());
            pstmt.setString(typeIndex, String.valueOf(transaction.getType()));
            pstmt.setString(categoryIndex, transaction.getCategory());
            pstmt.setString(dateIndex, String.valueOf(transaction.getDate()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static double getUserBalance(UUID userId) {
        double balance = 0;
        String sql = "SELECT balance FROM users WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(userId));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                balance = Double.parseDouble(rs.getString(1));
            } else {
                System.out.println("Could not find balance of user: " + userId);
                return 0;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return balance;
    }

    public static void updateUserBalance(UUID userId, double balance) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, balance);
            pstmt.setString(2, String.valueOf(userId));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieve all transactions from the database
     * @return List of all transactions
     */
    public static List<Transaction> getAllTransactions(UUID userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";

        try (Connection conn = connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, String.valueOf(userId));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String user = rs.getString("user_id");
                double amount = rs.getDouble("amount");
                String type = rs.getString("type");
                String category = rs.getString("category");
                String date = rs.getString("date");

                Transaction t = new Transaction(id, user, amount, type, category, date);
                transactions.add(t);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return transactions;
    }

    /**
     * Retrieved all transactions from the database, filtering by category
     * @param category
     * @return List of filtered transactions
     */
    public static List<Transaction> getTransactionsByCategory(String category) {
        return new ArrayList<>();
    }

    /**
     * Delete a transaction by its ID from the database
     * @param transactionId
     */
    public static void deleteTransaction(String transactionId) {

    }

    /**
     * Modify an existing transaction
     * @param transaction
     */
    public static void updateTransaction(Transaction transaction) {

    }

    /**
     * Get the total of all transactions of a given type
     * @param type
     * @return Total of all transactions of given type
     */
    public static double getTotalByType(String type) {
        return 0;
    }
}
