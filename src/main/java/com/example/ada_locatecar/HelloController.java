package com.example.ada_locatecar;

import com.example.ada_locatecar.Controllers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {

    AdicionarClienteController adicionarClienteController = new AdicionarClienteController();
    ListarClienteController listarClienteController = new ListarClienteController();
    AdicionarCarroController adicionarCarroController = new AdicionarCarroController();

    ListarCarroController listarCarroController = new ListarCarroController();

    AlugarController alugarController = new AlugarController();

    DevolverController devolverController = new DevolverController();


    @FXML
    private Button buttonNewClient;
    @FXML
    private Label welcomeText;

    @FXML
    protected void newClient(ActionEvent event) throws IOException {
        adicionarClienteController.adicionarClienteView(event);
    }


    @FXML
    public void listClient(ActionEvent event) throws IOException {
        listarClienteController.listarClienteView();
    }
    @FXML
    public void newCar(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        adicionarCarroController.adicionarCarroView();
    }

    @FXML
    public void listCar(ActionEvent event) throws IOException {
        listarCarroController.listarCarrosView();
    }


    @FXML
    public void rentCar(ActionEvent event) throws IOException {
        alugarController.rentCarView();
    }

    @FXML
    public void returnCar(ActionEvent event)throws IOException {
        devolverController.returnCarView();
    }
}