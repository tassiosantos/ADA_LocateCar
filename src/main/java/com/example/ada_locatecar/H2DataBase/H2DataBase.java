package com.example.ada_locatecar.H2DataBase;

import java.sql.*;

import org.h2.Driver;
import org.h2.tools.Server;

public class H2DataBase {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";
    public static Connection conn = null;
    public static Statement stmt = null;

    public static PreparedStatement pstmt = null;

    public static ResultSet rs = null;
    public static void init() throws ClassNotFoundException, SQLException {

        try {
            connect();
            stmt = conn.createStatement();
            System.out.println("Creating table in given database...");
            // Excluir as tabelas se elas existirem
            String dropCarroTable = "DROP TABLE IF EXISTS Carro";
            String dropPessoaTable = "DROP TABLE IF EXISTS Pessoa";
            String dropAluguelTable = "DROP TABLE IF EXISTS Aluguel";

            stmt.executeUpdate(dropAluguelTable);
            stmt.executeUpdate(dropCarroTable);
            stmt.executeUpdate(dropPessoaTable);

            // Criar as tabelas
            String createCarroTable = "CREATE TABLE Carro (" +
                    "id BIGINT PRIMARY KEY," +
                    "marca VARCHAR(255)," +
                    "modelo VARCHAR(255)," +
                    "tipo VARCHAR(255)," +
                    "placa VARCHAR(255)," +
                    "cor VARCHAR(255)," +
                    "alugado BOOLEAN" +
                    ")";

            String createPessoaTable = "CREATE TABLE Pessoa (" +
                    "id BIGINT PRIMARY KEY," +
                    "nome VARCHAR(255)," +
                    "tipoDocumento VARCHAR(255)," +
                    "documento VARCHAR(255)," +
                    "habilitacao VARCHAR(255)," +
                    "endereco VARCHAR(255)" +
                    ")";

            String createAluguelTable = "CREATE TABLE Aluguel (" +
                    "id BIGINT PRIMARY KEY," +
                    "dataHoraAluguel TIMESTAMP," +
                    "dataHoraDevolucao TIMESTAMP NULL," +
                    "localAluguel VARCHAR(255)," +
                    "localDevolucao VARCHAR(255)," +
                    "clienteId BIGINT," +
                    "carroId BIGINT," +
                    "valorLocacao DOUBLE," +
                    "FOREIGN KEY (clienteId) REFERENCES Pessoa(id)," +
                    "FOREIGN KEY (carroId) REFERENCES Carro(id)" +
                    ")";


            stmt.executeUpdate(createCarroTable);
            stmt.executeUpdate(createPessoaTable);
            stmt.executeUpdate(createAluguelTable);

            System.out.println("Tabelas criadas com sucesso!");


        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

    }

    public static void disconnect() throws SQLException {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static Long getNext(String table) throws SQLException {
        String sql = "SELECT MAX(id) FROM " + table;
        ResultSet rs = null;
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        long proximoId = 1;
        if (rs.next()) {
            proximoId = rs.getLong(1) + 1;
        }
        return proximoId;
    }




    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        init();



    }

}
