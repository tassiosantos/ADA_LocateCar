package com.example.ada_locatecar.Interfaces;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T, ID> {


    T add(T entity) throws SQLException, ClassNotFoundException;

    T update(T entity) throws SQLException, ClassNotFoundException;

    void delete(T entity);

    T getById(Long id) throws SQLException, ClassNotFoundException;

    List<T> getAll() throws SQLException;




}
