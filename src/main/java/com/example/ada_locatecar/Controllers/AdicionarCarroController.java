package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Entities.CarroPequeno;
import com.example.ada_locatecar.Entities.CarroMedio;
import com.example.ada_locatecar.Entities.CarroSUV;
import com.example.ada_locatecar.Services.CarroService;
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

public class AdicionarCarroController implements Initializable{

    @FXML
    private Button cancelarCarro;

    @FXML
    private Button adicionarCarro;

    @FXML
    private TextField marca;
    @FXML
    private TextField modelo;
    @FXML
    private ChoiceBox tipo;
    @FXML
    private TextField placa;
    @FXML
    private TextField cor;


    @FXML
    private Label marcaError;
    @FXML
    private Label modeloError;
    @FXML
    private Label tipoError;
    @FXML
    private Label placaError;
    @FXML
    private Label corError;

    @FXML
    private Label errors;

    CarroService carroService = new CarroService();

    public AdicionarCarroController(){

    }

    @FXML
    public void adicionarCarroView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/new-car.fxml"));
        Parent root = loader.load();
        AdicionarCarroController controller = loader.getController();
        Stage carroStage = new Stage();

        Scene scene = new Scene(root);


        carroStage.setTitle("Novo Carro");
        carroStage.initModality(Modality.APPLICATION_MODAL);
        carroStage.setScene(scene);
        carroStage.show();

    }

    public void adicionarCarro() throws SQLException, ClassNotFoundException {
        boolean hasError = false;
        if("".equalsIgnoreCase(marca.getText())){
            marcaError.setText("*");
            hasError = true;
        }else {
            marcaError.setText("");
        }
        if ("".equalsIgnoreCase(modelo.getText())){
            modeloError.setText("*");
            hasError = true;
        }else{
            modeloError.setText("");
        }
        if ("".equalsIgnoreCase(placa.getText())) {
            placaError.setText("*");
            hasError = true;
        }else{
            placaError.setText("");
        }
        if ("".equalsIgnoreCase(cor.getText())){
            corError.setText("*");
            hasError = true;
        }else{
            corError.setText("");
        }
        if (tipo.getValue() == null){
            tipoError.setText("*");
            hasError = true;
        }else{
            tipoError.setText("");
        }

        if(hasError){
            errors.setText("Campos Obrigatórios");
        }else{
            Carro carro = carroService.adicionarCarro(marca.getText(), modelo.getText(), tipo.getValue().toString(), placa.getText(), cor.getText(), false);
            Stage stage = (Stage) adicionarCarro.getScene().getWindow();
            stage.close();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] tipoCarros = {"PEQUENO","MEDIO", "GRANDE"};
        tipo.getItems().addAll(tipoCarros);
    }

    public void cancelNewCarro(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelarCarro.getScene().getWindow();
        stage.close();
    }


}
