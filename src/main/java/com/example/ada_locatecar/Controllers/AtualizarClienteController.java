package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Entities.ClienteCNPJ;
import com.example.ada_locatecar.Entities.ClienteCPF;
import com.example.ada_locatecar.HelloApplication;
import com.example.ada_locatecar.Services.ClienteService;
import com.example.ada_locatecar.Utils.ObjetoAtualizadoListener;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class AtualizarClienteController implements Initializable{

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



    ClienteService clienteService = new ClienteService();


    private ObjetoAtualizadoListener<Pessoa> listener;

    private Long clienteId;

    public AtualizarClienteController(){

    }


    @FXML
    public void atualizarClienteView(Long id, ListarClienteController listaClienteController) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/update-client.fxml"));
        Parent root = loader.load();
        AtualizarClienteController controller = loader.getController();
        controller.setClienteAtualizadoListener(listaClienteController);


        Stage clientStage = new Stage();
        Scene scene = new Scene(root);

        clienteId = id;
        Pessoa cliente = clienteService.getById(clienteId);

        controller.clienteId = clienteId;
        controller.nome.setText(cliente.getNome());
        controller.tipoDocumento.setValue(cliente.getTipoDocumento());
        controller.documento.setText(cliente.getDocumento());
        controller.habilitacao.setText(cliente.getHabilitacao());
        controller.endereco.setText(cliente.getEndereco());


        clientStage.setTitle("Atualizar Cliente");
        clientStage.initModality(Modality.APPLICATION_MODAL);
        clientStage.setScene(scene);
        clientStage.show();

    }




    public void atualizarCliente() throws SQLException, ClassNotFoundException {
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
        if (tipoDocumento.getValue() == null){
            tipoError.setText("*");
            hasError = true;
        }else{
            tipoError.setText("");
        }
        if(hasError){
            errors.setText("Campos Obrigatórios");
        }else{
            Pessoa cliente;

            if("CPF".equalsIgnoreCase(tipoDocumento.getValue())){
                cliente = new ClienteCPF(nome.getText(), tipoDocumento.getValue(), documento.getText(), habilitacao.getText(), endereco.getText());
                cliente.setId(clienteId);

            }else{
                cliente = new ClienteCNPJ(nome.getText(), tipoDocumento.getValue(), documento.getText(), habilitacao.getText(), endereco.getText());
                cliente.setId(clienteId);
            }



            clienteService.atualizarCliente(cliente);

            if (listener != null) {
                listener.onObjetoAtualizado(cliente);
            }

            Stage stage = (Stage) atualizarCliente.getScene().getWindow();
            stage.close();


        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] tipo = {"CPF","CNPJ"};
        tipoDocumento.getItems().addAll(tipo);
    }

    public void setClienteAtualizadoListener(ObjetoAtualizadoListener<Pessoa> listener) {
        this.listener = listener;
    }

    public void cancelUpdateClient() {
        Stage stage = (Stage) cancelarCliente.getScene().getWindow();
        stage.close();
    }



}
