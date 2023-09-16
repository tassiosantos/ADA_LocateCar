package com.example.ada_locatecar.Utils;

import java.sql.SQLException;

public interface ObjetoAtualizadoListener<T> {

    void onObjetoAtualizado(T objeto) throws SQLException;

}

