package com.example.ada_locatecar.Repositories;

import com.example.ada_locatecar.Entities.Abstracts.Pessoa;
import com.example.ada_locatecar.Entities.ClienteCNPJ;
import com.example.ada_locatecar.Entities.ClienteCPF;
import com.example.ada_locatecar.H2DataBase.H2DataBase;
import com.example.ada_locatecar.Interfaces.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ada_locatecar.H2DataBase.H2DataBase.conn;
import static com.example.ada_locatecar.H2DataBase.H2DataBase.pstmt;

public class ClienteRepository implements Repository<Pessoa, Long> {


    @Override
    public Pessoa add(Pessoa entity) throws SQLException, ClassNotFoundException {
        H2DataBase.connect();
        Long id = H2DataBase.getNext("pessoa");
        String sql = "INSERT INTO Pessoa (id, nome, tipoDocumento, documento, habilitacao, endereco) VALUES (?, ?, ?, ?, ?, ?)";
        H2DataBase.pstmt = conn.prepareStatement(sql);
        H2DataBase.pstmt.setLong(1, id);
        H2DataBase.pstmt.setString(2, entity.getNome());
        H2DataBase.pstmt.setString(3, entity.getTipoDocumento());
        H2DataBase.pstmt.setString(4, entity.getDocumento());
        H2DataBase.pstmt.setString(5, entity.getHabilitacao());
        H2DataBase.pstmt.setString(6, entity.getEndereco());

        int retorno = H2DataBase.pstmt.executeUpdate();

        System.out.println("Registro em Pessoa inserido com sucesso!");
        if (retorno == -1) {
            H2DataBase.disconnect();
            return null;
        } else {
            H2DataBase.disconnect();
            return entity;
        }


    }

    @Override
    public Pessoa update(Pessoa entity) throws SQLException, ClassNotFoundException {
        try {
            H2DataBase.connect();
            String sql = "UPDATE Pessoa SET nome = ?, tipoDocumento = ?, documento = ?, habilitacao = ?, endereco = ? WHERE id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, entity.getNome());
            pstmt.setString(2, entity.getTipoDocumento());
            pstmt.setString(3, entity.getDocumento());
            pstmt.setString(4, entity.getHabilitacao());
            pstmt.setString(5, entity.getEndereco());
            pstmt.setLong(6, entity.getId());


            int retorno = H2DataBase.pstmt.executeUpdate();

            System.out.println("Registro em Pessoa inserido com sucesso!");
            if (retorno == 1) {
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }
        return null;
    }


     @Override
    public Pessoa getById(Long id) throws SQLException, ClassNotFoundException {
        H2DataBase.connect();
        Pessoa pessoa = null;
        try {
            String sql = "SELECT * FROM Pessoa WHERE id = " + String.valueOf(id);
            pstmt = conn.prepareStatement(sql);

            H2DataBase.rs = pstmt.executeQuery();

            if (H2DataBase.rs.next()) {
                if ("CPF".equalsIgnoreCase(H2DataBase.rs.getString("tipoDocumento"))) {
                    pessoa = new ClienteCPF();
                } else {
                    pessoa = new ClienteCNPJ();
                }
                pessoa.setId(H2DataBase.rs.getLong("id"));
                pessoa.setNome(H2DataBase.rs.getString("nome"));
                pessoa.setTipoDocumento(H2DataBase.rs.getString("tipoDocumento"));
                pessoa.setDocumento(H2DataBase.rs.getString("documento"));
                pessoa.setHabilitacao(H2DataBase.rs.getString("habilitacao"));
                pessoa.setEndereco(H2DataBase.rs.getString("endereco"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }
        return pessoa;
    }


    public Pessoa getByDocumento(String documento) throws SQLException, ClassNotFoundException {
        H2DataBase.connect();
        String sql = "SELECT * FROM Pessoa WHERE documento = ?";
        Pessoa pessoa = null;
        // Criar um PreparedStatement
        H2DataBase.pstmt = conn.prepareStatement(sql);
        H2DataBase.pstmt.setString(1, documento);

        // Executar a consulta
        H2DataBase.rs = pstmt.executeQuery();

        // Obter o resultado da consulta
        if (H2DataBase.rs.next()) {
            int count = H2DataBase.rs.getInt(1);
            if (H2DataBase.rs.getString("tipodocumento").equalsIgnoreCase("CPF")) {
                pessoa = new ClienteCPF();
            } else {
                pessoa = new ClienteCNPJ();
            }
            pessoa.setId(H2DataBase.rs.getLong("id"));
            pessoa.setNome(H2DataBase.rs.getString("nome"));
            pessoa.setTipoDocumento(H2DataBase.rs.getString("tipodocumento"));
            pessoa.setDocumento(H2DataBase.rs.getString("documento"));
            pessoa.setEndereco(H2DataBase.rs.getString("endereco"));

            H2DataBase.disconnect();
            return pessoa;

        } else {
            H2DataBase.disconnect();
            return null;
        }
    }

    @Override
    public List<Pessoa> getAll() throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();

        try {
            H2DataBase.connect();
            // SQL para selecionar todas as pessoas
            String sql = "SELECT * FROM Pessoa";

            // Criar um PreparedStatement
            pstmt = conn.prepareStatement(sql);

            // Executar a consulta
            H2DataBase.rs = pstmt.executeQuery();

            // Processar os resultados e criar objetos Pessoa
            while (H2DataBase.rs.next()) {
                Pessoa pessoa;
                if (H2DataBase.rs.getString("tipodocumento").equalsIgnoreCase("CPF")) {
                    pessoa = new ClienteCPF();
                } else {
                    pessoa = new ClienteCNPJ();
                }
                pessoa.setId(H2DataBase.rs.getLong("id"));
                pessoa.setNome(H2DataBase.rs.getString("nome"));
                pessoa.setTipoDocumento(H2DataBase.rs.getString("tipoDocumento"));
                pessoa.setDocumento(H2DataBase.rs.getString("documento"));
                pessoa.setHabilitacao(H2DataBase.rs.getString("habilitacao"));
                pessoa.setEndereco(H2DataBase.rs.getString("endereco"));
                pessoas.add(pessoa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }

        return pessoas;
    }

    public List<Pessoa> getByNome(String nome) throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();

        try {
            H2DataBase.connect();
            // SQL para selecionar todas as pessoas
            String sql = "SELECT * FROM Pessoa WHERE nome LIKE ?";

            // Criar um PreparedStatement
            pstmt = conn.prepareStatement(sql);
            H2DataBase.pstmt.setString(1, "%" + nome + "%");

            // Executar a consulta
            H2DataBase.rs = pstmt.executeQuery();

            // Processar os resultados e criar objetos Pessoa
            while (H2DataBase.rs.next()) {
                Pessoa pessoa;
                if (H2DataBase.rs.getString("tipodocumento").equalsIgnoreCase("CPF")) {
                    pessoa = new ClienteCPF();
                } else {
                    pessoa = new ClienteCNPJ();
                }
                pessoa.setId(H2DataBase.rs.getLong("id"));
                pessoa.setNome(H2DataBase.rs.getString("nome"));
                pessoa.setTipoDocumento(H2DataBase.rs.getString("tipoDocumento"));
                pessoa.setDocumento(H2DataBase.rs.getString("documento"));
                pessoa.setHabilitacao(H2DataBase.rs.getString("habilitacao"));
                pessoa.setEndereco(H2DataBase.rs.getString("endereco"));
                pessoas.add(pessoa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }

        return pessoas;
    }
}




