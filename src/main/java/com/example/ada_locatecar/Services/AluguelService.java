package com.example.ada_locatecar.Services;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Entities.Aluguel;
import com.example.ada_locatecar.Repositories.AluguelRepository;

import java.sql.SQLException;
import java.util.List;

public class AluguelService {

    private CarroService carroService;
    private AluguelRepository aluguelRepository;

    public AluguelService(){
        this.carroService = new CarroService();
        this.aluguelRepository = new AluguelRepository();
    }






    public void AlugarCarro(Aluguel aluguel) throws SQLException, ClassNotFoundException {
        Carro carro = carroService.getById(aluguel.getCarroId());
        carro.setAlugado(true);
        carroService.atualizarCarro(carro);
        aluguelRepository.add(aluguel);

    }

    public void DevolverCarro(Aluguel aluguel) throws SQLException, ClassNotFoundException {
        Carro carro = carroService.getById(aluguel.getCarroId());
        carro.setAlugado(false);
        aluguel.calcularAluguel();
        carroService.atualizarCarro(carro);
        aluguelRepository.update(aluguel);

    }

    public List<Aluguel> findAll() throws SQLException {
        return this.aluguelRepository.getAll();
    }
}
