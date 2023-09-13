package com.example.ada_locatecar.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
//    ClienteController clienteController = new ClienteController();


    @FXML
    private Button buttonNewClient;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("src/main/resources/com/example/ada_locatecar/new-client.fxml"));
        Stage clientStage = new Stage();

        Scene scene = new Scene(root);
        clientStage.setTitle("Novo Cliente");
        clientStage.initModality(Modality.APPLICATION_MODAL);
        clientStage.initOwner(buttonNewClient.getScene().getWindow());
        clientStage.setScene(scene);
        clientStage.show();

    }
}