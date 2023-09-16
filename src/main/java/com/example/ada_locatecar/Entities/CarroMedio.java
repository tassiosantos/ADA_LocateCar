package com.example.ada_locatecar.Entities;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Utils.TipoCarroEnum;

public class CarroMedio extends Carro {
    public CarroMedio(String marca, String modelo, String placa, String cor) {
        super(marca, modelo, TipoCarroEnum.MEDIO.toString(), placa, cor);
    }

    public CarroMedio() {
        super();
    }

    public CarroMedio(String marca, String modelo, String tipo, String placa, String cor) {
        super(marca, modelo, tipo, placa, cor);
    }
}
