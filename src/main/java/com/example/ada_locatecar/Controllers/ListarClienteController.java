package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Entities.ClienteCNPJ;
import com.example.ada_locatecar.Entities.ClienteCPF;
import com.example.ada_locatecar.HelloApplication;
import com.example.ada_locatecar.Services.ClienteService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClienteListController implements Initializable{
    @FXML
    private TextField nome;
    @FXML
    private TextField documento;
    @FXML
    private ChoiceBox<String> tipoDocumento;
    @FXML
    private TextField endereco;
    @FXML
    private TextField habilitacao;

    @FXML
    private Label nomeError;
    @FXML
    private Label documentError;
    @FXML
    private Label tipoError;
    @FXML
    private Label driverLicenceError;
    @FXML
    private Label enderecoError;


    @FXML
    private Label errors;

    @FXML
    private Button atualizarCliente;
    @FXML
    private Button cancelarCliente;


    List<Pessoa> clientes;

    ClienteService clienteService = new ClienteService();


    public ClienteListController(){

    }



    public void listarClienteView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/list-client.fxml"));
        Parent root = loader.load();
        ClienteController controller = loader.getController();
        Stage clientStage = new Stage();
        Scene scene = new Scene(root);







        clientStage.setTitle("Lista de Clientes");
        clientStage.initModality(Modality.APPLICATION_MODAL);
        clientStage.setScene(scene);
        clientStage.show();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            clientes = this.clienteService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void cancelSearchClient(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelarCliente.getScene().getWindow();
        stage.close();
    }


    public void buscarClientes(ActionEvent actionEvent) {

    }



    public void setClient(SortEvent<TableView> tableViewSortEvent) {
    }
}
