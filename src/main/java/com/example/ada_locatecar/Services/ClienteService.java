package com.example.ada_locatecar.Services;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Entities.ClienteCNPJ;
import com.example.ada_locatecar.Entities.ClienteCPF;
import com.example.ada_locatecar.Repositories.ClienteRepository;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {
//    private Cli


    private ClienteRepository clienteRepository = new ClienteRepository();



    public Pessoa adicionarCliente(String nome,String tipoDocumento, String documento, String habilitacao, String endereco) throws SQLException, ClassNotFoundException {
        if(verificarDocumento(documento)){
            return null;
        }else{
            if("CPF".equalsIgnoreCase(tipoDocumento)){
                return this.clienteRepository.add(new ClienteCPF(nome, tipoDocumento, documento, habilitacao, endereco) );
            }else{
                return this.clienteRepository.add(new ClienteCNPJ(nome, tipoDocumento, documento, habilitacao, endereco));
            }

        }


    }

    public Pessoa atualizarCliente(Pessoa cliente) throws SQLException, ClassNotFoundException {
        return this.clienteRepository.update(cliente);
    }

    private boolean verificarDocumento(String documento) throws SQLException, ClassNotFoundException {
        Pessoa pessoa = this.clienteRepository.getByDocumento(documento);
        return (pessoa == null?false:true);
    }

    public Pessoa getById(Long id) throws SQLException, ClassNotFoundException {
        return clienteRepository.getById(id);
    }

    public List<Pessoa> findAll() throws SQLException {
        return clienteRepository.getAll();
    }

    public List<Pessoa> findByName(String nome) throws SQLException {
        return clienteRepository.getByNome(nome);
    }
}
