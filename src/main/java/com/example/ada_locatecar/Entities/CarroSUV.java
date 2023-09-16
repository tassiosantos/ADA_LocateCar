package com.example.ada_locatecar.Entities;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Utils.TipoCarroEnum;

public class CarroSUV extends Carro {
    public CarroSUV(String marca, String modelo, String placa, String cor) {
        super(marca, modelo, TipoCarroEnum.SUV.toString(), placa, cor);
    }


    public CarroSUV() {

    }

    public CarroSUV(String marca, String modelo, String tipo, String placa, String cor) {
        super(marca, modelo, tipo, placa, cor);
    }
}
