package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Services.CarroService;
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

public class ListarCarroController implements Initializable, ObjetoAtualizadoListener<Carro> {
    @FXML
    private TextField marcaBusca;
    @FXML
    private TextField modeloBusca;
    @FXML
    private TableColumn marca;

    @FXML
    private TableColumn modelo;

    @FXML
    private TableColumn placa;

    @FXML

    private TableColumn cor;

    @FXML
    private TableColumn status;

    @FXML
    private TableView<Carro> carroTable;



    @FXML
    public Button buscarCarrosButton;
    @FXML
    private Button atualizarCarro;
    @FXML
    private Button cancelarCarro;


    Carro carroSelecionado;
    List<Carro> carros;

    CarroService carroService = new CarroService();

    AtualizarCarroController atualizarCarroController = new AtualizarCarroController();


    public ListarCarroController(){

    }



    public void listarCarrosView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/list-car.fxml"));
        Parent root = loader.load();
        ListarCarroController controller = loader.getController();
        Stage carroStage = new Stage();
        Scene scene = new Scene(root);





        carroStage.setTitle("Lista de Carros");
        carroStage.initModality(Modality.APPLICATION_MODAL);
        carroStage.setScene(scene);
        carroStage.show();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
            modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            placa.setCellValueFactory(new PropertyValueFactory<>("placa"));
            cor.setCellValueFactory(new PropertyValueFactory<>("cor"));
            status.setCellValueFactory(new PropertyValueFactory<>("alugado"));
            carroTable.setItems(buscarCarros());

            setCarro();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public ObservableList<Carro> buscarCarros() throws SQLException {
        carros = this.carroService.findAll();
        return FXCollections.observableList(carros);
    }

    public void buscarCarrosFiltrados() throws SQLException {
        String marca = marcaBusca.getText();
        String modelo = modeloBusca.getText();
        if((!"".equalsIgnoreCase(marca))&&(!"".equalsIgnoreCase(marca))){
            carros = this.carroService.findByMarcaAndModelo(marca, modelo);
            atualizarTabela(FXCollections.observableList(carros));
        }else if((!"".equalsIgnoreCase(marca))&&("".equalsIgnoreCase(modelo))){
            carros = this.carroService.findByMarca(marca);
            atualizarTabela(FXCollections.observableList(carros));
        }else if(("".equalsIgnoreCase(marca))&&(!"".equalsIgnoreCase(modelo))){
            carros = this.carroService.findByModelo(modelo);
            atualizarTabela(FXCollections.observableList(carros));
        }else{
            atualizarTabela(buscarCarros());
        }

    }

    public void setCarro() {
        carroTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Carro carro = carroTable.getSelectionModel().getSelectedItem();
                if (carro != null) {
                    atualizarCarro.setDisable(false);
                    carroSelecionado = carro;
                }
            }
        });
    }


    public void atualizarCarroView() throws SQLException, IOException, ClassNotFoundException {
        if (carroSelecionado != null) {
            Long carroId = carroSelecionado.getId();
            atualizarCarroController.atualizarCarroView(carroId, this);
        }

    }

    public void atualizarTabela(ObservableList<Carro> lista) throws SQLException {
        carroTable.setItems(lista);
        carroTable.refresh();
    }




    public void cancelListCarro(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelarCarro.getScene().getWindow();
        stage.close();
    }

    @Override
    public void onObjetoAtualizado(Carro objeto) throws SQLException {
        atualizarTabela(buscarCarros());
    }
}
