package com.example.ada_locatecar.Repositories;

import com.example.ada_locatecar.Entities.Abstracts.Carro;
import com.example.ada_locatecar.Entities.CarroPequeno;
import com.example.ada_locatecar.Entities.CarroMedio;
import com.example.ada_locatecar.Entities.CarroSUV;
import com.example.ada_locatecar.H2DataBase.H2DataBase;
import com.example.ada_locatecar.Interfaces.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ada_locatecar.H2DataBase.H2DataBase.conn;
import static com.example.ada_locatecar.H2DataBase.H2DataBase.pstmt;

public class CarroRepository implements Repository<Carro, Long> {


    @Override
    public Carro add(Carro entity) throws SQLException, ClassNotFoundException {
        H2DataBase.connect();
        Long id = H2DataBase.getNext("carro");
        String sql = "INSERT INTO Carro (id, marca, modelo, tipo, placa, cor, alugado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        H2DataBase.pstmt = conn.prepareStatement(sql);
        H2DataBase.pstmt.setLong(1, id);
        H2DataBase.pstmt.setString(2, entity.getMarca());
        H2DataBase.pstmt.setString(3, entity.getModelo());
        H2DataBase.pstmt.setString(4, entity.getTipo());
        H2DataBase.pstmt.setString(5, entity.getPlaca());
        H2DataBase.pstmt.setString(6, entity.getCor());
        H2DataBase.pstmt.setBoolean(7, false);

        int retorno = H2DataBase.pstmt.executeUpdate();

        System.out.println("Registro em Carro inserido com sucesso!");
        if (retorno == -1) {
            H2DataBase.disconnect();
            return null;
        } else {
            H2DataBase.disconnect();
            return entity;
        }


    }

    @Override
    public Carro update(Carro entity) throws SQLException, ClassNotFoundException {
        try {
            H2DataBase.connect();
            String sql = "UPDATE Carro SET marca = ?, modelo = ?, tipo = ?, placa = ?, cor = ?, alugado = ? WHERE id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, entity.getMarca());
            pstmt.setString(2, entity.getModelo());
            pstmt.setString(3, entity.getTipo());
            pstmt.setString(4, entity.getPlaca());
            pstmt.setString(5, entity.getCor());
            pstmt.setBoolean(6, entity.isAlugado());
            pstmt.setLong(7, entity.getId());


            int retorno = H2DataBase.pstmt.executeUpdate();

            System.out.println("Registro em Carro inserido com sucesso!");
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
    public void delete(Carro entity) {

    }

    @Override
    public Carro getById(Long id) throws SQLException, ClassNotFoundException {
        H2DataBase.connect();
        Carro carro = null;
        try {
            String sql = "SELECT * FROM Carro WHERE id = " + String.valueOf(id);
            pstmt = conn.prepareStatement(sql);

            H2DataBase.rs = pstmt.executeQuery();

            if (H2DataBase.rs.next()) {
                if ("PEQUENO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroPequeno();
                } else if("MEDIO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroMedio();
                }else{
                    carro = new CarroSUV();
                }
                carro.setId(H2DataBase.rs.getLong("id"));
                carro.setMarca(H2DataBase.rs.getString("marca"));
                carro.setModelo(H2DataBase.rs.getString("modelo"));
                carro.setTipo(H2DataBase.rs.getString("tipo"));
                carro.setPlaca(H2DataBase.rs.getString("placa"));
                carro.setCor(H2DataBase.rs.getString("cor"));
                carro.setAlugado(H2DataBase.rs.getBoolean("alugado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }
        return carro;
    }


    public List<Carro> getAlugado(Boolean alugado) throws SQLException, ClassNotFoundException {
        List<Carro> carros = new ArrayList<>();
        try {
            H2DataBase.connect();
            String sql = "SELECT * FROM Carro WHERE alugado = ?";
            // Criar um PreparedStatement
            H2DataBase.pstmt = conn.prepareStatement(sql);
            H2DataBase.pstmt.setBoolean(1, alugado);

            // Executar a consulta
            H2DataBase.rs = pstmt.executeQuery();

            // Obter o resultado da consulta


            // Processar os resultados e criar objetos Carro
            while (H2DataBase.rs.next()) {
                Carro carro;
                if ("PEQUENO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroPequeno();
                } else if("MEDIO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroMedio();
                }else{
                    carro = new CarroSUV();
                }
                carro.setId(H2DataBase.rs.getLong("id"));
                carro.setMarca(H2DataBase.rs.getString("marca"));
                carro.setModelo(H2DataBase.rs.getString("modelo"));
                carro.setTipo(H2DataBase.rs.getString("tipo"));
                carro.setPlaca(H2DataBase.rs.getString("placa"));
                carro.setCor(H2DataBase.rs.getString("cor"));
                carro.setAlugado(H2DataBase.rs.getBoolean("alugado"));
                carros.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }
        return carros;
    }


    public Carro getByPlaca(String placa) throws SQLException, ClassNotFoundException {
        H2DataBase.connect();
        String sql = "SELECT * FROM Carro WHERE placa = ?";
        Carro carro = null;
        // Criar um PreparedStatement
        H2DataBase.pstmt = conn.prepareStatement(sql);
        H2DataBase.pstmt.setString(1, placa);

        // Executar a consulta
        H2DataBase.rs = pstmt.executeQuery();

        // Obter o resultado da consulta
        if (H2DataBase.rs.next()) {
            if ("PEQUENO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                carro = new CarroPequeno();
            } else if("MEDIO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                carro = new CarroMedio();
            }else{
                carro = new CarroSUV();
            }
            carro.setId(H2DataBase.rs.getLong("id"));
            carro.setMarca(H2DataBase.rs.getString("marca"));
            carro.setModelo(H2DataBase.rs.getString("modelo"));
            carro.setTipo(H2DataBase.rs.getString("tipo"));
            carro.setPlaca(H2DataBase.rs.getString("placa"));
            carro.setCor(H2DataBase.rs.getString("cor"));
            carro.setAlugado(H2DataBase.rs.getBoolean("alugado"));

            H2DataBase.disconnect();
            return  carro;

        } else {
            H2DataBase.disconnect();
            return null;
        }
    }


    @Override
    public List<Carro> getAll() throws SQLException {
        List<Carro> carros = new ArrayList<>();

        try {
            H2DataBase.connect();
            // SQL para selecionar todas as carros
            String sql = "SELECT * FROM Carro";

            // Criar um PreparedStatement
            pstmt = conn.prepareStatement(sql);

            // Executar a consulta
            H2DataBase.rs = pstmt.executeQuery();

            // Processar os resultados e criar objetos Carro
            while (H2DataBase.rs.next()) {
                Carro carro;
                if ("PEQUENO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroPequeno();
                } else if("MEDIO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroMedio();
                }else{
                    carro = new CarroSUV();
                }
                carro.setId(H2DataBase.rs.getLong("id"));
                carro.setMarca(H2DataBase.rs.getString("marca"));
                carro.setModelo(H2DataBase.rs.getString("modelo"));
                carro.setTipo(H2DataBase.rs.getString("tipo"));
                carro.setPlaca(H2DataBase.rs.getString("placa"));
                carro.setCor(H2DataBase.rs.getString("cor"));
                carro.setAlugado(H2DataBase.rs.getBoolean("alugado"));
                carros.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }

        return carros;
    }

    public List<Carro> getByMarca(String marca) throws SQLException {
        List<Carro> carros = new ArrayList<>();

        try {
            H2DataBase.connect();
            // SQL para selecionar todas as carros
            String sql = "SELECT * FROM Carro WHERE marca LIKE ?";

            // Criar um PreparedStatement
            pstmt = conn.prepareStatement(sql);
            H2DataBase.pstmt.setString(1, "%" + marca + "%");

            // Executar a consulta
            H2DataBase.rs = pstmt.executeQuery();

            // Processar os resultados e criar objetos Carro
            while (H2DataBase.rs.next()) {
                Carro carro;
                if ("PEQUENO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroPequeno();
                } else if("MEDIO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroMedio();
                }else{
                    carro = new CarroSUV();
                }
                carro.setId(H2DataBase.rs.getLong("id"));
                carro.setMarca(H2DataBase.rs.getString("marca"));
                carro.setModelo(H2DataBase.rs.getString("modelo"));
                carro.setTipo(H2DataBase.rs.getString("tipo"));
                carro.setPlaca(H2DataBase.rs.getString("placa"));
                carro.setCor(H2DataBase.rs.getString("cor"));
                carro.setAlugado(H2DataBase.rs.getBoolean("alugado"));
                carros.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }

        return carros;
    }


    public List<Carro> getByModelo(String modelo) throws SQLException {
        List<Carro> carros = new ArrayList<>();

        try {
            H2DataBase.connect();
            // SQL para selecionar todas as carros
            String sql = "SELECT * FROM Carro WHERE modelo LIKE ?";

            // Criar um PreparedStatement
            pstmt = conn.prepareStatement(sql);
            H2DataBase.pstmt.setString(1, "%" + modelo + "%");

            // Executar a consulta
            H2DataBase.rs = pstmt.executeQuery();

            // Processar os resultados e criar objetos Carro
            while (H2DataBase.rs.next()) {
                Carro carro;
                if ("PEQUENO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroPequeno();
                } else if("MEDIO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroMedio();
                }else{
                    carro = new CarroSUV();
                }
                carro.setId(H2DataBase.rs.getLong("id"));
                carro.setMarca(H2DataBase.rs.getString("marca"));
                carro.setModelo(H2DataBase.rs.getString("modelo"));
                carro.setTipo(H2DataBase.rs.getString("tipo"));
                carro.setPlaca(H2DataBase.rs.getString("placa"));
                carro.setCor(H2DataBase.rs.getString("cor"));
                carro.setAlugado(H2DataBase.rs.getBoolean("alugado"));
                carros.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }

        return carros;
    }

    public List<Carro> getByMarcaAndModelo(String marca, String modelo) throws SQLException {
        List<Carro> carros = new ArrayList<>();

        try {
            H2DataBase.connect();
            // SQL para selecionar todas as carros
            String sql = "SELECT * FROM Carro WHERE marca LIKE ? AND modelo LIKE ?;";

            // Criar um PreparedStatement
            pstmt = conn.prepareStatement(sql);
            H2DataBase.pstmt.setString(1, "%" + marca + "%");
            H2DataBase.pstmt.setString(2, "%" + modelo + "%");

            // Executar a consulta
            H2DataBase.rs = pstmt.executeQuery();

            // Processar os resultados e criar objetos Carro
            while (H2DataBase.rs.next()) {
                Carro carro;
                if ("PEQUENO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroPequeno();
                } else if("MEDIO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroMedio();
                }else{
                    carro = new CarroSUV();
                }
                carro.setId(H2DataBase.rs.getLong("id"));
                carro.setMarca(H2DataBase.rs.getString("marca"));
                carro.setModelo(H2DataBase.rs.getString("modelo"));
                carro.setTipo(H2DataBase.rs.getString("tipo"));
                carro.setPlaca(H2DataBase.rs.getString("placa"));
                carro.setCor(H2DataBase.rs.getString("cor"));
                carro.setAlugado(H2DataBase.rs.getBoolean("alugado"));
                carros.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }

        return carros;
    }

    public List<Carro> getAllFree() throws SQLException {
        List<Carro> carros = new ArrayList<>();

        try {
            H2DataBase.connect();
            // SQL para selecionar todas as carros
            String sql = "SELECT * FROM Carro WHERE alugado = false";

            // Criar um PreparedStatement
            pstmt = conn.prepareStatement(sql);

            // Executar a consulta
            H2DataBase.rs = pstmt.executeQuery();

            // Processar os resultados e criar objetos Carro
            while (H2DataBase.rs.next()) {
                Carro carro;
                if ("PEQUENO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroPequeno();
                } else if("MEDIO".equalsIgnoreCase(H2DataBase.rs.getString("tipo"))) {
                    carro = new CarroMedio();
                }else{
                    carro = new CarroSUV();
                }
                carro.setId(H2DataBase.rs.getLong("id"));
                carro.setMarca(H2DataBase.rs.getString("marca"));
                carro.setModelo(H2DataBase.rs.getString("modelo"));
                carro.setTipo(H2DataBase.rs.getString("tipo"));
                carro.setPlaca(H2DataBase.rs.getString("placa"));
                carro.setCor(H2DataBase.rs.getString("cor"));
                carro.setAlugado(H2DataBase.rs.getBoolean("alugado"));
                carros.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            H2DataBase.disconnect();
        }

        return carros;
    }
}




