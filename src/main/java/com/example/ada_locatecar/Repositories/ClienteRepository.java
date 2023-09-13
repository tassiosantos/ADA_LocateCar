package com.example.ada_locatecar.Repositories;

import com.example.ada_locatecar.Entities.ClienteCPF;
import com.example.ada_locatecar.Interfaces.Repository;
import com.example.ada_locatecar.Utils.Tipo;

public class ClienteCPFRepository implements Repository<ClienteCPF, Long> {

    double valor = Tipo.MEDIO.getValorLocacao();
    @Override
    public ClienteCPF add(ClienteCPF cliente) {
        return null;
    }






}
