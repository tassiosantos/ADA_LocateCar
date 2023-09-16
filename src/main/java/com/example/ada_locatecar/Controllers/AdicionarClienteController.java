package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Entities.ClienteCNPJ;
import com.example.ada_locatecar.Entities.ClienteCPF;
import com.example.ada_locatecar.Services.ClienteService;
import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;

public class AdicionarClienteController implements Initializable{

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
    private Button adicionarNovoCliente;
    @FXML
    private Button cancelarNovoCliente;


    ClienteService clienteService = new ClienteService();

    public AdicionarClienteController(){

    }

    @FXML
    public void adicionarClienteView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/new-client.fxml"));
        Parent root = loader.load();
        AdicionarClienteController controller = loader.getController();

        Stage clientStage = new Stage();

        Scene scene = new Scene(root);


        clientStage.setTitle("Novo Cliente");
        clientStage.initModality(Modality.APPLICATION_MODAL);
        clientStage.setScene(scene);
        clientStage.show();

    }

    public void adicionarCliente() throws SQLException, ClassNotFoundException {
        boolean hasError = false;
        if("".equalsIgnoreCase(nome.getText())){
            nomeError.setText("*");
            hasError = true;
        }else {
            nomeError.setText("");
        }
        if ("".equalsIgnoreCase(documento.getText())){
            documentError.setText("*");
            hasError = true;
        }else{
            documentError.setText("");
        }
        if ("".equalsIgnoreCase(endereco.getText())) {
            enderecoError.setText("*");
            hasError = true;
        }else{
            enderecoError.setText("");
        }
        if ("".equalsIgnoreCase(habilitacao.getText())){
            driverLicenceError.setText("*");
            hasError = true;
        }else{
            driverLicenceError.setText("");
        }
        System.out.println(tipoDocumento.getValue());
        if (tipoDocumento.getValue() == null){
            tipoError.setText("*");
            hasError = true;
        }else{
            tipoError.setText("");
        }

        if(hasError){
            errors.setText("Campos Obrigatórios");
        }else{
            Pessoa cliente = clienteService.adicionarCliente(nome.getText(), tipoDocumento.getValue(), documento.getText(), habilitacao.getText(), endereco.getText());
            Stage stage = (Stage) adicionarNovoCliente.getScene().getWindow();
            stage.close();


        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] tipo = {"CPF","CNPJ"};
        tipoDocumento.getItems().addAll(tipo);
    }

    public void cancelNewClient(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelarNovoCliente.getScene().getWindow();
        stage.close();
    }


}
