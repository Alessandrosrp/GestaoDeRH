package com.workly.api.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> dados) {
        System.out.println("==========================\nDEBUG: Entrou no método login");  // 🔹 print inicial

        String tipo = dados.get("tipo");
        String usuario = dados.get("usuario");
        String senha = dados.get("senha");
        boolean sucesso = false;

        System.out.println("\nDEBUG (BACKEND) tipo = " + tipo);
        System.out.println("DEBUG (BACKEND) usuario = " + usuario);
        System.out.println("DEBUG (BACKEND) senha = " + senha);

        try (Connection conn = com.workly.api.criarperfil.Conexao.conectar()) {
            if (conn == null) {
                System.out.println("\nDEBUG: Conexão nula!");
            }

            String sql;
            if ("Usuario".equals(tipo)) {
                sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
            } else if ("Empresa".equals(tipo)) {
                sql = "SELECT * FROM empresa WHERE nome = ? AND senha = ?";
            } else {
                System.out.println("\nDEBUG: Tipo inválido recebido: " + tipo);
                throw new Exception("Tipo inválido");
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println("\nDEBUG: Executando query com usuario=" + usuario + " senha=" + senha);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\nDEBUG: Login OK!");
                sucesso = true;
            }
        } catch (Exception e) {
            System.out.println("\nDEBUG: Login falhou!");
            e.printStackTrace();
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("sucesso", sucesso);

        if (sucesso) {
            String rota = "Usuario".equals(tipo) ? "/html/telainicial.html" : "/html/telainicial2.html";
            resposta.put("rota", rota);
        } else {
            resposta.put("mensagem", "Login inválido!");
        }

        System.out.println("\nDEBUG: Retornando resposta: " + resposta);
        return resposta;
    }
}
