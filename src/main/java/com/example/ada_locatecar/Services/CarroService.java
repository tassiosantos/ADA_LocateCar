package com.example.ada_locatecar.Services;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Entities.CarroPequeno;
import com.example.ada_locatecar.Entities.CarroMedio;
import com.example.ada_locatecar.Entities.CarroSUV;
import com.example.ada_locatecar.H2DataBase.H2DataBase;
import com.example.ada_locatecar.Repositories.CarroRepository;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class CarroService {
//    private Cli


    private CarroRepository carroRepository = new CarroRepository();



    public Carro adicionarCarro(String marca,String modelo, String tipo, String placa, String cor, Boolean alugado) throws SQLException, ClassNotFoundException {
        Carro carro;
        if(verificarPlaca(placa)){
            return null;
        }else{
            if ("PEQUENO".equalsIgnoreCase(tipo)) {
                carro = new CarroPequeno(marca, modelo, tipo, placa, cor);
            } else if("MEDIO".equalsIgnoreCase(tipo)) {
                carro = new CarroMedio(marca, modelo, tipo, placa, cor);
            }else{
                carro = new CarroSUV(marca, modelo, tipo, placa, cor);
            }
            return carroRepository.add(carro);

        }
    }


    public Carro atualizarCarro(Carro carro) throws SQLException, ClassNotFoundException {
        return this.carroRepository.update(carro);
    }

    private boolean verificarPlaca(String placa) throws SQLException, ClassNotFoundException {
        Carro carro = this.carroRepository.getByPlaca(placa);
        return (carro == null?false:true);
    }

    public Carro getById(Long id) throws SQLException, ClassNotFoundException {
        return carroRepository.getById(id);
    }

    public List<Carro> findAll() throws SQLException {
        return carroRepository.getAll();
    }

    public List<Carro> findByMarca(String marca) throws SQLException {
        return carroRepository.getByMarca(marca);
    }


    public List<Carro> findByModelo(String modelo) throws SQLException {
        return carroRepository.getByModelo(modelo);
    }

    public List<Carro> findByMarcaAndModelo(String marca, String modelo) throws SQLException {
        return carroRepository.getByMarcaAndModelo(marca, modelo);
    }

    public List<Carro> getAllFree() throws SQLException {
        return  this.carroRepository.getAllFree();
    }
}
