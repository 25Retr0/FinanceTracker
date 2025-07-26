package com.willkelly.FinanceTracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;


public class AppController {

    private final Tracker tracker;
    private TransactionTableManager transactionTableManager;
    private final TransactionPopupHandler transactionPopupHandler = new TransactionPopupHandler();

    @FXML private TableView<Transaction> transactionTable;

    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;
    @FXML private TableColumn<Transaction, String> accountColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, LocalDate> dateColumn;

    @FXML private Label balanceLabel;

    public AppController(Tracker tracker) {
        this.tracker = tracker;
    }

    @FXML
    private void initialize() {
        transactionTableManager = new TransactionTableManager(transactionTable, categoryColumn, amountColumn, accountColumn, typeColumn, dateColumn, tracker);
        transactionTableManager.refresh();

        bindBalanceLabel();
    }

    private void bindBalanceLabel() {
        balanceLabel.textProperty().bind(tracker.getBalanceProperty().asString("Balance: $%.2f"));
    }

    @FXML
    private void newIncomePressed() {
        try {
            Transaction t = transactionPopupHandler.showAddPopup(TransactionType.INCOME, tracker);
            if (t != null) {
                tracker.addTransaction(t);
                transactionTableManager.refresh();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with adding new income.... Please try again!");
        }
    }


    @FXML
    private void newExpensePressed() {
        try {
            Transaction t = transactionPopupHandler.showAddPopup(TransactionType.EXPENSE, tracker);
            if (t != null) {
                tracker.addTransaction(t);
                transactionTableManager.refresh();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with adding new expense.... Please try again!");
        }
    }

    @FXML
    private void newTransferPressed() {
        System.out.println("New Transfer Button Pressed");
    }
}
