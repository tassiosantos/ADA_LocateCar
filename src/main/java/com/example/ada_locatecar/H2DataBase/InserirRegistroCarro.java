package com.example.ada_locatecar.H2DataBase;

import java.sql.*;

public class InserirRegistroCarro {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Configurar a conexão com o banco de dados (substitua as informações a seguir pelo seu banco de dados)
            String url = "jdbc:h2:~/test";
            String usuario = "sa";
            String senha = "";

            // Registrar o driver JDBC
            Class.forName("org.h2.Driver");

            // Conectar ao banco de dados
            conn = DriverManager.getConnection(url, usuario, senha);

//            String createCarroTable = "CREATE TABLE Carro (" +
//                    "id BIGINT PRIMARY KEY," +
//                    "marca VARCHAR(255)," +
//                    "modelo VARCHAR(255)," +
//                    "tipo VARCHAR(255)," +
//                    "placa VARCHAR(255)," +
//                    "cor VARCHAR(255)," +
//                    "alugado BOOLEAN" +
//                    ")";
//            Statement stmt2 = conn.createStatement();
//
//            stmt2.executeUpdate(createCarroTable);




            // SQL para inserir um registro em Carro
            String sql = "INSERT INTO Carro (id, marca, modelo, tipo, placa, cor, alugado) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Criar um PreparedStatement
            stmt = conn.prepareStatement(sql);

            // Definir os valores para os parâmetros
            stmt.setLong(1, 1L); // Substitua 1L pelo ID desejado
            stmt.setString(2, "Toyota");
            stmt.setString(3, "Corolla");
            stmt.setString(4, "Sedan");
            stmt.setString(5, "ABC123");
            stmt.setString(6, "Prata");
            stmt.setBoolean(7, false); // Carro não alugado

            // Executar a inserção
            stmt.executeUpdate();

            System.out.println("Registro em Carro inserido com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
