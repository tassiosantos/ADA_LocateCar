package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Services.ClienteService;
import com.example.ada_locatecar.Utils.ObjetoAtualizadoListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListarClienteController implements Initializable, ObjetoAtualizadoListener<Pessoa> {

    @FXML
    private TextField nomeBusca;
    
    @FXML
    private TableColumn id;
    
    @FXML
    private TableColumn nome;

    @FXML
    private TableColumn documento;
    
    @FXML
    
    private TableColumn habilitacao;
    
    @FXML
    private TableColumn endereco;
    
    @FXML
    private TableView<Pessoa> clientTable;


    @FXML
    public Button buscarClientesButton;
    @FXML
    private Button atualizarCliente;
    @FXML
    private Button cancelarCliente;


    Pessoa clienteSelecionado;
    List<Pessoa> clientes;

    ClienteService clienteService = new ClienteService();

    AtualizarClienteController atualizarClienteController = new AtualizarClienteController();


    public ListarClienteController(){

    }



    public void listarClienteView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/list-client.fxml"));
        Parent root = loader.load();
        ListarClienteController controller = loader.getController();
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
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            documento.setCellValueFactory(new PropertyValueFactory<>("documento"));
            habilitacao.setCellValueFactory(new PropertyValueFactory<>("habilitacao"));
            endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            clientTable.setItems(buscarClientes());

            setClient();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public ObservableList<Pessoa> buscarClientes() throws SQLException {
        clientes = this.clienteService.findAll();
        return FXCollections.observableList(clientes);
    }

    public void buscarClientesFiltrados() throws SQLException {
        String nome = nomeBusca.getText();
        if(!"".equalsIgnoreCase(nome)){
            clientes = this.clienteService.findByName(nome);
            atualizarTabela(FXCollections.observableList(clientes));
        }else{
            atualizarTabela(buscarClientes());
        }

    }


    public void setClient() {
        clientTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Pessoa cliente = clientTable.getSelectionModel().getSelectedItem();
                if (cliente != null) {
                    atualizarCliente.setDisable(false);
                    clienteSelecionado = cliente;
                }
            }
        });
    }


    public void atualizarClienteView() throws SQLException, IOException, ClassNotFoundException {
        if (clienteSelecionado != null) {
            Long clienteId = clienteSelecionado.getId();
            atualizarClienteController.atualizarClienteView(clienteId, this);
        }

    }

    public void atualizarTabela(ObservableList<Pessoa> lista) throws SQLException {
        clientTable.setItems(lista);
        clientTable.refresh();
    }




    public void cancelListClient(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelarCliente.getScene().getWindow();
        stage.close();
    }

    @Override
    public void onObjetoAtualizado(Pessoa objeto) throws SQLException {
        atualizarTabela(buscarClientes());
    }
}
