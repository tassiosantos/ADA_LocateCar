package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Entities.Aluguel;
import com.example.ada_locatecar.Services.AluguelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class DevolverController implements Initializable {
    @FXML
    private DatePicker returnDate;
    @FXML
    private TableView<Aluguel> carrosAlugados;
    @FXML
    private TableColumn id;
    @FXML
    private TableColumn dataAluguel;
    @FXML
    private TableColumn dataDevolucao;
    @FXML
    private TableColumn localAluguel;
    @FXML
    private TableColumn localDevolucao;
    @FXML
    private TableColumn clienteId;
    @FXML
    private TableColumn carroId;
    @FXML
    private TableColumn valorLocacao;
    @FXML
    private Button devolverCarroButton;
    @FXML
    private Button cancelarButton;


    Aluguel aluguel;
    List<Aluguel> alugueis;
    AluguelService aluguelService = new AluguelService();

    public void returnCarView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/return-car.fxml"));
        Parent root = loader.load();
        DevolverController controller = loader.getController();
        Stage clientStage = new Stage();
        Scene scene = new Scene(root);



        clientStage.setTitle("Alugar Carro");
        clientStage.initModality(Modality.APPLICATION_MODAL);
        clientStage.setScene(scene);
        clientStage.show();

    }


    public void devolverCarro(ActionEvent event) throws SQLException, ClassNotFoundException {
        System.out.println(returnDate.getValue());
        if(returnDate.getValue() == null){
            if(aluguel.getDataHoraDevolucao() == null) {
                LocalDateTime data = LocalDateTime.now();
                aluguel.setDataHoraDevolucao(data);
                this.aluguelService.DevolverCarro(aluguel);
                returnDate.setValue(data.toLocalDate());
                atualizarTabela();
            }
        }else {
            if(aluguel.getDataHoraDevolucao() == null) {
                aluguel.setDataHoraDevolucao(returnDate.getValue().atStartOfDay());
                this.aluguelService.DevolverCarro(aluguel);
                atualizarTabela();
            }
        }

    }

    
    
    


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            dataAluguel.setCellValueFactory(new PropertyValueFactory<>("dataHoraAlguel"));
            dataDevolucao.setCellValueFactory(new PropertyValueFactory<>("dataHoraDevolucao"));
            localAluguel.setCellValueFactory(new PropertyValueFactory<>("localAluguel"));
            localDevolucao.setCellValueFactory(new PropertyValueFactory<>("localDevolucao"));
            clienteId.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
            carroId.setCellValueFactory(new PropertyValueFactory<>("carroId"));
            valorLocacao.setCellValueFactory(new PropertyValueFactory<>("valorLocacao"));

            carrosAlugados.setItems(buscarAlugueis());

            setAluguel();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        
    }

    private ObservableList<Aluguel> buscarAlugueis() throws SQLException {
        alugueis = this.aluguelService.findAll();
        return FXCollections.observableList(alugueis);
    }

    public void setAluguel() {
        carrosAlugados.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                aluguel = carrosAlugados.getSelectionModel().getSelectedItem();
                if (aluguel != null) {
                    devolverCarroButton.setDisable(false);
                }
            }
        });
    }


    public void atualizarTabela() throws SQLException {
        carrosAlugados.refresh();
    }


    public void cancelar(ActionEvent event) {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}
