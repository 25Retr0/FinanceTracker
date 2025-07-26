package com.willkelly.FinanceTracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransactionInputController {

    @FXML private TextField amountInputField;
    @FXML private TextField nameInputField;
    @FXML private Button submitButton;
    @FXML private Button cancelButton;

    private double amount;
    private String name;
    private TransactionType transactionType;

    public boolean submitted = false;

    @FXML
    private void handleSubmit() {
        try {
            String text = amountInputField.getText().trim();
            amount = Double.parseDouble(text);
            name = nameInputField.getText().trim();
            submitted = true;
            ((Stage) submitButton.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            amountInputField.setStyle("-fx-border-color:red;");
            amountInputField.setPromptText("Enter a valid amount...");
            submitted = false;
        }
    }

    @FXML
    private void handleCancel() {
        submitted = false;
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public double getAmount() {
        return this.amount;
    }

    public String getName() {
        return this.name;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(TransactionType type) {
        this.transactionType = type;
    }

    public boolean isSubmitted() {
        return this.submitted;
    }

}
