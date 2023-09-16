package com.example.ada_locatecar.Controllers;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Entities.CarroMedio;
import com.example.ada_locatecar.Entities.CarroPequeno;
import com.example.ada_locatecar.Entities.CarroSUV;
import com.example.ada_locatecar.Services.CarroService;
import com.example.ada_locatecar.Utils.ObjetoAtualizadoListener;
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

public class AtualizarCarroController implements Initializable{

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


    @FXML
    private Button atualizarCarro;
    @FXML
    private Button cancelarCarro;




    CarroService carroService = new CarroService();


    private ObjetoAtualizadoListener<Carro> listener;

    private Long carroId;

    public AtualizarCarroController(){

    }


    @FXML
    public void atualizarCarroView(Long id, ListarCarroController listaCarroController) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ada_locatecar/update-carro.fxml"));
        Parent root = loader.load();
        AtualizarCarroController controller = loader.getController();
        controller.setCarroAtualizadoListener(listaCarroController);

        Stage clientStage = new Stage();
        Scene scene = new Scene(root);

        carroId = id;

        Carro carro = carroService.getById(carroId);


        controller.carroId = id;
        controller.marca.setText(carro.getMarca());
        controller.modelo.setText(carro.getModelo());
        controller.tipo.setValue(carro.getTipo());
        controller.placa.setText(carro.getPlaca());
        controller.cor.setText(carro.getCor());


        clientStage.setTitle("Atualizar Carro");
        clientStage.initModality(Modality.APPLICATION_MODAL);
        clientStage.setScene(scene);
        clientStage.show();

    }



    public void atualizarCarro() throws SQLException, ClassNotFoundException {
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
            Carro carro;
            if ("PEQUENO".equalsIgnoreCase(tipo.getValue().toString())) {
                carro = new CarroPequeno(marca.getText(), modelo.getText(), tipo.getValue().toString(), placa.getText(), cor.getText());
            } else if("MEDIO".equalsIgnoreCase(tipo.getValue().toString())) {
                carro = new CarroMedio(marca.getText(), modelo.getText(), tipo.getValue().toString(), placa.getText(), cor.getText());
            }else{
                carro = new CarroSUV(marca.getText(), modelo.getText(), tipo.getValue().toString(), placa.getText(), cor.getText());
            }
            carroService.atualizarCarro(carro);

            if (listener != null) {
                listener.onObjetoAtualizado(carro);
            }

            Stage stage = (Stage) atualizarCarro.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] tipoCarros = {"PEQUENO","MEDIO", "GRANDE"};
        tipo.getItems().addAll(tipoCarros);
    }


    public void setCarroAtualizadoListener(ObjetoAtualizadoListener<Carro> listener) {
        this.listener = listener;
    }
    public void cancelarAtualizarCarro(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelarCarro.getScene().getWindow();
        stage.close();
    }



}
