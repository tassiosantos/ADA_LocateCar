package com.example.ada_locatecar.Entities;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Services.CarroService;
import com.example.ada_locatecar.Services.ClienteService;
import com.example.ada_locatecar.Utils.TipoCarroEnum;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Aluguel {

    private Long id;
    private LocalDateTime dataHoraAlguel;
    private LocalDateTime dataHoraDevolucao;

    private String localAluguel;
    private String localDevolucao;

    private Long clienteId;

    private Long carroId;
    private double valorLocacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraAlguel() {
        return dataHoraAlguel;
    }

    public void setDataHoraAlguel(LocalDateTime dataHoraAlguel) {
        this.dataHoraAlguel = dataHoraAlguel;
    }

    public LocalDateTime getDataHoraDevolucao() {
        return dataHoraDevolucao;
    }

    public String getLocalAluguel() {
        return localAluguel;
    }

    public void setLocalAluguel(String localAluguel) {
        this.localAluguel = localAluguel;
    }

    public String getLocalDevolucao() {
        return localDevolucao;
    }

    public void setLocalDevolucao(String localDevolucao) {
        this.localDevolucao = localDevolucao;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getCarroId() {
        return carroId;
    }

    public void setCarroId(Long carroId) {
        this.carroId = carroId;
    }

    public double getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(double valorLocacao) {
        this.valorLocacao = valorLocacao;
    }



    public Aluguel(){
        this.dataHoraAlguel = LocalDateTime.now();
        this.dataHoraDevolucao = null;
        this.localAluguel = "";
        this.localDevolucao = "";
        this.valorLocacao = 0.0;
    }
    public Aluguel(LocalDateTime dataHoraAlguel, String localAluguel){
        this.dataHoraAlguel = dataHoraAlguel;
        this.localAluguel = localAluguel;
        this.valorLocacao = 0.0;
    }

    public Aluguel(LocalDateTime dataHoraAlguel, String localAluguel, String localDevolucao, Long clienteId, Long carroId){
        this.dataHoraAlguel = dataHoraAlguel;
        this.dataHoraDevolucao = null;
        this.localAluguel = localAluguel;
        this.localDevolucao = localDevolucao;
        this.clienteId = clienteId;
        this.carroId = carroId;
    }



    public double calcularAluguel() throws SQLException, ClassNotFoundException {
        ClienteService clienteService = new ClienteService();
        CarroService carroService = new CarroService();
        Pessoa cliente = clienteService.getById(this.clienteId);
        Carro carro = carroService.getById(this.carroId);
        Long dias = ChronoUnit.DAYS.between(dataHoraAlguel, dataHoraDevolucao);
        if("PEQUENO".equalsIgnoreCase(carro.getTipo())){
            valorLocacao = dias* TipoCarroEnum.PEQUENO.getValorLocacao();
        } else if ("MEDIO".equalsIgnoreCase(carro.getTipo())) {
            valorLocacao = dias*TipoCarroEnum.MEDIO.getValorLocacao();
        }else{
            valorLocacao = dias*TipoCarroEnum.SUV.getValorLocacao();
        }


        if("CPF".equalsIgnoreCase(cliente.getDocumento())&&(dias>5)){
            valorLocacao = valorLocacao*.95;
        }else if("CNPJ".equalsIgnoreCase(cliente.getDocumento())&&(dias>3)){
            valorLocacao = valorLocacao*.9;
        }

        return valorLocacao;
    }


    public void setDataHoraDevolucao(LocalDateTime dataHoraDevolucao) {
        this.dataHoraDevolucao = dataHoraDevolucao;
    }
}
