package com.willkelly.FinanceTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.UUID;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseHelper.initialiseDatabase();

        // Will experiment with a login system soon... For now
        UUID user = UUID.fromString("8c925391-0069-4db9-8ddd-51da9691aab6");
        double userBalance = DatabaseHelper.getUserBalance(user);
        Tracker tracker = new Tracker(user, userBalance);

        String baseLayout = "/ViewLayouts/BasicTableView3.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(baseLayout));
        AppController controller = new AppController(tracker);
        loader.setController(controller);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Finance Tracker");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}