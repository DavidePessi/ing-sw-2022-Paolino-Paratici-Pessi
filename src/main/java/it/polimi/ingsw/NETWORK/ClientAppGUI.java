package it.polimi.ingsw.NETWORK;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientAppGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("Eriantys");
        stage.setScene(new Scene(root, 500, 500));
        stage.show();

    }

    public static void main(String[] args) {
        launch();

    }
}