package com.example.ada_locatecar.Entities;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;

public class ClienteCNPJ extends Pessoa {


    public ClienteCNPJ(String nome, String tipoDocumento, String documento, String habilitacao, String endereco) {
        super(nome, tipoDocumento, documento, habilitacao, endereco);
    }

    public ClienteCNPJ() {
        super();
    }
}

