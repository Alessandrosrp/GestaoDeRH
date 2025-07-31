package com.workly.api.criarperfil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/workly";
        String user = "root";
        String password = "";
        
        return DriverManager.getConnection(url, user, password);
    }
}