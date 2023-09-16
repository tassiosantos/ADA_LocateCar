package com.example.ada_locatecar.Interfaces;



import java.sql.SQLException;
import java.util.List;

public interface Repository<T, ID> {


    T add(T entity) throws SQLException, ClassNotFoundException;

    T update(T entity) throws SQLException, ClassNotFoundException;


    T getById(Long ID) throws SQLException, ClassNotFoundException;

    List<T> getAll() throws SQLException;




}
