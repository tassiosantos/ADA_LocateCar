package com.example.ada_locatecar.Utils;

public enum TipoCarroEnum {

    PEQUENO(100.00),
    MEDIO(150.00),
    SUV(200.00);

    private final double valorLocacao;
    TipoCarroEnum(double i) {
        this.valorLocacao = i;
    }

    public double getValorLocacao(){
        return valorLocacao;
    }


}
