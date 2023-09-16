package com.example.ada_locatecar.Repositories;

import com.example.ada_locatecar.Entities.Aluguel;
import com.example.ada_locatecar.H2DataBase.H2DataBase;
import com.example.ada_locatecar.Interfaces.Repository;

import static com.example.ada_locatecar.H2DataBase.H2DataBase.conn;
import static com.example.ada_locatecar.H2DataBase.H2DataBase.pstmt;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AluguelRepository implements Repository<Aluguel, Long> {


    @Override
    public Aluguel add(Aluguel entity) throws SQLException, ClassNotFoundException {
                H2DataBase.connect();

                // Recupere o próximo ID da sequência
                Long id = H2DataBase.getNext("aluguel");

                String sql = "INSERT INTO Aluguel (id, dataHoraAluguel, dataHoraDevolucao, localAluguel, localDevolucao, clienteId, carroId, valorLocacao) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setLong(1, id);
                    pstmt.setTimestamp(2, Timestamp.valueOf(entity.getDataHoraAlguel()));
                    pstmt.setTimestamp(3, null);
                    pstmt.setString(4, entity.getLocalAluguel());
                    pstmt.setString(5, entity.getLocalDevolucao());
                    pstmt.setLong(6, entity.getClienteId());
                    pstmt.setLong(7, entity.getCarroId());
                    pstmt.setDouble(8, entity.getValorLocacao());

                    int retorno = pstmt.executeUpdate();

                    if (retorno == 1) {
                        // Recupere o ID gerado automaticamente
                        ResultSet generatedKeys = pstmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            entity.setId(generatedKeys.getLong(1));
                        } else {
                            throw new SQLException("Falha ao recuperar o ID gerado para o aluguel.");
                        }

                        System.out.println("Registro em Aluguel inserido com sucesso!");
                        return entity;
                    } else {
                        throw new SQLException("Falha ao inserir registro em Aluguel.");
                    }
                } finally {
                    H2DataBase.disconnect();
                }
    }





    @Override
    public Aluguel update(Aluguel entity) throws SQLException, ClassNotFoundException {
            try {
                H2DataBase.connect();
                String sql = "UPDATE Aluguel SET dataHoraAluguel = ?, dataHoraDevolucao = ?, localAluguel = ?, " +
                        "localDevolucao = ?, clienteId = ?, carroId = ?, valorLocacao = ? WHERE id = ?";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setObject(1, entity.getDataHoraAlguel());
                    pstmt.setObject(2, entity.getDataHoraDevolucao());
                    pstmt.setString(3, entity.getLocalAluguel());
                    pstmt.setString(4, entity.getLocalDevolucao());
                    pstmt.setLong(5, entity.getClienteId());
                    pstmt.setLong(6, entity.getCarroId());
                    pstmt.setDouble(7, entity.getValorLocacao());
                    pstmt.setLong(8, entity.getId());

                    int retorno = pstmt.executeUpdate();

                    if (retorno == 1) {
                        System.out.println("Registro em Aluguel atualizado com sucesso!");
                        return entity;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                H2DataBase.disconnect();
            }
            return null;
    }



    @Override
    public Aluguel getById(Long id) throws SQLException, ClassNotFoundException {
        H2DataBase.connect();
        Aluguel aluguel = null;
        try {
            String sql = "SELECT * FROM Aluguel WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, id);

                H2DataBase.rs = pstmt.executeQuery();

                if (H2DataBase.rs.next()) {
                    aluguel = new Aluguel();
                    aluguel.setId(H2DataBase.rs.getLong("id"));
                    aluguel.setDataHoraAlguel(H2DataBase.rs.getTimestamp("dataHoraAluguel").toLocalDateTime());
                    aluguel.setDataHoraDevolucao(H2DataBase.rs.getTimestamp("dataHoraDevolucao").toLocalDateTime());
                    aluguel.setLocalAluguel(H2DataBase.rs.getString("localAluguel"));
                    aluguel.setLocalDevolucao(H2DataBase.rs.getString("localDevolucao"));
                    aluguel.setClienteId(H2DataBase.rs.getLong("clienteId"));
                    aluguel.setCarroId(H2DataBase.rs.getLong("carroId"));
                    aluguel.setValorLocacao(H2DataBase.rs.getDouble("valorLocacao"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }
        return aluguel;
    }

    @Override
    public List<Aluguel> getAll() throws SQLException {
        List<Aluguel> alugueis = new ArrayList<>();

        try {
            H2DataBase.connect();
            // SQL para selecionar todos os aluguéis
            String sql = "SELECT * FROM Aluguel";

            // Criar um PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Executar a consulta
                H2DataBase.rs = pstmt.executeQuery();

                // Processar os resultados e criar objetos Aluguel
                while (H2DataBase.rs.next()) {
                    Aluguel aluguel = new Aluguel();
                    aluguel.setId(H2DataBase.rs.getLong("id"));
                    aluguel.setDataHoraAlguel(H2DataBase.rs.getTimestamp("dataHoraAluguel").toLocalDateTime());
                    Timestamp timestamp = H2DataBase.rs.getTimestamp("dataHoraDevolucao");
                    if (timestamp != null) {
                        aluguel.setDataHoraDevolucao(H2DataBase.rs.getTimestamp("dataHoraDevolucao").toLocalDateTime());
                    } else {
                        aluguel.setDataHoraDevolucao(null);
                    }
                    aluguel.setLocalAluguel(H2DataBase.rs.getString("localAluguel"));
                    aluguel.setLocalDevolucao(H2DataBase.rs.getString("localDevolucao"));
                    aluguel.setClienteId(H2DataBase.rs.getLong("clienteId"));
                    aluguel.setCarroId(H2DataBase.rs.getLong("carroId"));
                    aluguel.setValorLocacao(H2DataBase.rs.getDouble("valorLocacao"));
                    alugueis.add(aluguel);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }

        return alugueis;
    }
}
