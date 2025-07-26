package com.willkelly.FinanceTracker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionPopupHandler {
    private final String addPopupFXML = "/ViewLayouts/NewIncomeExpensePopup.fxml";

    public Transaction showAddPopup(TransactionType type, Tracker tracker) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(addPopupFXML));
        Parent root = loader.load();

        TransactionInputController transactionInputController = loader.getController();
        transactionInputController.setTransactionType(type);

        Stage popupStage = new Stage();
        popupStage.setScene(new Scene(root));
        String str = "Enter " + capitalize(type.toString().toLowerCase()) + " Information";
        popupStage.setTitle(str);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();

        if (!transactionInputController.isSubmitted()) {
            return null;
        }

        double amount = transactionInputController.getAmount();
        String description = transactionInputController.getName();
        return tracker.createTransaction(amount, type, description);
    }

    private String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
