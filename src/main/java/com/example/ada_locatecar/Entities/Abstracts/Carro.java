package com.example.ada_locatecar.Entities.Abstracts;

import com.example.ada_locatecar.Utils.TipoCarroEnum;

public abstract class Carro{
    private Long id;
    private  String marca;
    private String modelo;

    private String tipo;
    private String placa;
    private String cor;

    private boolean alugado;

    public Carro(String marca, String modelo, String tipo, String placa, String cor){
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.placa = placa;
        this.cor = cor;
        this.alugado = false;

    }

    public Carro() {

    }


    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
