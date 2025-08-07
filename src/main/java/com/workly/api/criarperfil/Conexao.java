package com.workly.api.criarperfil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conectar() throws SQLException {
        String url = "jdbc:mysql://26.19.38.21:3306/workly";
        String user = "TestVPN";
        String password = "";
        
        return DriverManager.getConnection(url, user, password);
    }
}