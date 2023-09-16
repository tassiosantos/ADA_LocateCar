package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Entities.Aluguel;
import com.example.ada_locatecar.Services.AluguelService;
import com.example.ada_locatecar.Services.CarroService;
import com.example.ada_locatecar.Services.ClienteService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.StringConverter;
import org.h2.table.Table;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class AlugarController implements Initializable {

    @FXML
    private Button cancelarAlugar;
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
    private TableView<Carro> carTable;
    @FXML
    private TableView<Pessoa> clientTable;
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
    private DatePicker dataAluguel;
    @FXML
    private Spinner<Integer> horaAluguel;
    @FXML
    private Spinner<Integer> minutosAluguel;
    @FXML
    private TextField localAluguel;
    @FXML
    private TextField localDevolucao;
    @FXML
    private TextField nomeCliente;
    @FXML
    private TextField marcaCarro;
    @FXML
    private Button filtrarCarro;
    @FXML
    private Button filtrarCliente;
    @FXML
    private Button rentButton;
    @FXML
    private Label dataAluguelError;
    @FXML
    private Label horaError;
    @FXML
    private Label minutosError;
    @FXML
    private Label localAluguelError;
    @FXML
    private Label localDevolucaoError;
    @FXML
    private Label clienteError;
    @FXML
    private Label carroError;

    @FXML
    private Label errors;


    private Pessoa cliente;

    private Carro carro;


    AluguelService aluguelService = new AluguelService();

    CarroService carroService = new CarroService();

    ClienteService clienteService = new ClienteService();

    public void rentCarView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/rent-car.fxml"));
        Parent root = loader.load();
        AlugarController controller = loader.getController();
        Stage clientStage = new Stage();
        Scene scene = new Scene(root);







        clientStage.setTitle("Alugar Carro");
        clientStage.initModality(Modality.APPLICATION_MODAL);
        clientStage.setScene(scene);
        clientStage.show();

    }



    public void rentCar(ActionEvent event)  throws SQLException, ClassNotFoundException {
        Aluguel aluguel;
        boolean hasError = false;

            if ("".equalsIgnoreCase(localAluguel.getText())) {
                localAluguelError.setText("*");
                hasError = true;
            } else {
                localAluguelError.setText("");
            }
            if ("".equalsIgnoreCase(localDevolucao.getText())) {
                localDevolucaoError.setText("*");
                hasError = true;
            } else {
                localDevolucaoError.setText("");
            }
            if (cliente != null) {
                if ("".equalsIgnoreCase(cliente.getNome())) {
                    clienteError.setText("*");
                    hasError = true;
                } else {
                    clienteError.setText("");
                }
            }else {
                clienteError.setText("*");
                hasError = true;
            }
            if(carro != null) {
                if ("".equalsIgnoreCase(carro.getMarca())) {
                    carroError.setText("*");
                    hasError = true;
                } else {
                    carroError.setText("");
                }
            }else{
                carroError.setText("*");
                hasError = true;
            }
            if (dataAluguel.getValue() == null) {
                dataAluguelError.setText("*");
                hasError = true;
            } else {
                dataAluguelError.setText("");
            }
            if ((horaAluguel.getValue() == null) || ("".equalsIgnoreCase(String.valueOf(horaAluguel.getValue())))) {
                horaError.setText("*");
                hasError = true;
            } else {
                horaError.setText("");
            }
            if ((minutosAluguel.getValue() == null) || ("".equalsIgnoreCase(String.valueOf(minutosAluguel.getValue())))) {
                minutosError.setText("*");
                hasError = true;
            } else {
                minutosError.setText("");
            }




        if(hasError){
            errors.setText("Campos Obrigatórios");
        }else{
            LocalDateTime data = LocalDateTime.of(dataAluguel.getValue().getYear(),dataAluguel.getValue().getMonth(),dataAluguel.getValue().getDayOfMonth(),horaAluguel.getValue(),minutosAluguel.getValue());
            aluguel = new Aluguel(data,localAluguel.getText(),localDevolucao.getText(),cliente.getId(), carro.getId());
            this.aluguelService.AlugarCarro(aluguel);
            Stage stage = (Stage) rentButton.getScene().getWindow();
            stage.close();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureSpinner(minutosAluguel, 0, 59);
        configureSpinner(horaAluguel, 0, 24);
        try {
            marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
            modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            placa.setCellValueFactory(new PropertyValueFactory<>("placa"));
            cor.setCellValueFactory(new PropertyValueFactory<>("cor"));
            status.setCellValueFactory(new PropertyValueFactory<>("alugado"));
            carTable.setItems(buscarCarros());

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            documento.setCellValueFactory(new PropertyValueFactory<>("documento"));
            habilitacao.setCellValueFactory(new PropertyValueFactory<>("habilitacao"));
            endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            clientTable.setItems(buscarClientes());

            setCarro();
            setCliente();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void filtrarCarro(ActionEvent event) throws SQLException {
        if(!("".equalsIgnoreCase(marcaCarro.getText()))){
            carTable.setItems(FXCollections.observableList(buscarCarrosFiltrados()));
        }else {
            carTable.setItems(buscarCarros());
        }
    }

    public void filtrarCliente(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(!("".equalsIgnoreCase(nomeCliente.getText()))){
            clientTable.setItems(FXCollections.observableList(buscarPessoasFiltradas()));
        }
        else{
            clientTable.setItems(buscarClientes());
        }
    }

    public void setCarro() {
        carTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                carro = carTable.getSelectionModel().getSelectedItem();
            }
        });
    }

    public void setCliente() {
        clientTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                cliente = clientTable.getSelectionModel().getSelectedItem();
            }
        });
    }

    private void configureSpinner(Spinner<Integer> spinner, int minValue, int maxValue) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, 0, 1));
        spinner.setEditable(true);

        spinner.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String text) {
                return Integer.parseInt(text);
            }
        });


        spinner.getValueFactory().valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> obs, Integer oldValue, Integer newValue) {
                if (newValue > maxValue) {
                    spinner.getValueFactory().setValue(minValue);
                } else if (newValue < minValue) {
                    spinner.getValueFactory().setValue(maxValue);
                }
            }
        });
    }



    public ObservableList<Carro> buscarCarros() throws SQLException {
        List<Carro> carros = this.carroService.getAllFree();
        return FXCollections.observableList(carros);
    }

    public ObservableList<Pessoa> buscarClientes() throws SQLException {
        List<Pessoa> clientes = this.clienteService.findAll();
        return FXCollections.observableList(clientes);
    }

    public List<Carro> buscarCarrosFiltrados() throws SQLException {
            return this.carroService.findByMarca(marcaCarro.getText());
    }

    public List<Pessoa> buscarPessoasFiltradas() throws SQLException, ClassNotFoundException {
        return this.clienteService.findByName(nomeCliente.getText());
    }

    public void cancelarAlugarButton(ActionEvent event) {
        Stage stage = (Stage) cancelarAlugar.getScene().getWindow();
        stage.close();
    }


}
