package com.willkelly.FinanceTracker;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TransactionTableManager {

    private final TableView<Transaction> transactionTable;
    private final TableColumn<Transaction, String> categoryColumn;
    private final TableColumn<Transaction, Double> amountColumn;
    private final TableColumn<Transaction, String> accountColumn;
    private final TableColumn<Transaction, String> typeColumn;
    private final TableColumn<Transaction, LocalDate> dateColumn;
    private final Tracker tracker;

    public TransactionTableManager(TableView<Transaction> transactionTable,
                                   TableColumn<Transaction, String> categoryColumn,
                                   TableColumn<Transaction, Double> amountColumn,
                                   TableColumn<Transaction, String> accountColumn,
                                   TableColumn<Transaction, String> typeColumn,
                                   TableColumn<Transaction, LocalDate> dateColumn,
                                   Tracker tracker) {
        this.transactionTable = transactionTable;
        this.categoryColumn = categoryColumn;
        this.amountColumn = amountColumn;
        this.accountColumn = accountColumn;
        this.typeColumn = typeColumn;
        this.dateColumn = dateColumn;
        this.tracker = tracker;
        setupColumns();
    }

    private void setupColumns() {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public void refresh() {
        List<Transaction> transactionList = DatabaseHelper.getAllTransactions(tracker.getUserId());
        Collections.reverse(transactionList);
        transactionTable.setItems(FXCollections.observableArrayList(transactionList));
    }
}
