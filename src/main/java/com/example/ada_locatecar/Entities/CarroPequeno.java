package com.example.ada_locatecar.Entities;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Utils.TipoCarroEnum;

public class CarroPequeno extends Carro {
    public CarroPequeno(String marca, String modelo, String placa, String cor) {
        super(marca, modelo, TipoCarroEnum.PEQUENO.toString(), placa, cor);
    }

    public CarroPequeno() {

    }

    public CarroPequeno(String marca, String modelo, String tipo, String placa, String cor) {
        super(marca, modelo, tipo, placa, cor);
    }
}
