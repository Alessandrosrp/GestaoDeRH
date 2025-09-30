package com.workly.api.criarperfil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CriaPerfilRestController {

    @PostMapping("/cadastro")
    public Map<String, Object> criarPerfil(@RequestBody Map<String, String> dados) {
        System.out.println("==========================\nDEBUG: Entrou no método criarPerfil");

        String email = dados.get("email");
        String nome = dados.get("nome");
        String senha = dados.get("senha");
        String tipo = dados.get("tipo");
        String matricula = dados.get("matricula");

        

        System.out.println("\nDEBUG (BACKEND) tipo = " + tipo);
        System.out.println("DEBUG (BACKEND) email = " + email);
        System.out.println("DEBUG (BACKEND) nome = " + nome);
        System.out.println("DEBUG (BACKEND) senha = " + senha);
        System.out.println("DEBUG (BACKEND) matricula = " + matricula);

        boolean sucesso = false;

        try (Connection conn = com.workly.api.criarperfil.Conexao.conectar()) {
            if (conn == null) {
                System.out.println("\nDEBUG: Conexão nula!");
            }

            String sql;
            if ("Usuario".equalsIgnoreCase(tipo)) {
                sql = "INSERT INTO usuario (email, nome, senha, tipo, matricula) VALUES (?, ?, ?, ?, ?)";
            } else if ("Empresa".equalsIgnoreCase(tipo)) {
                sql = "INSERT INTO empresa (email, nome, senha, matricula) VALUES (?, ?, ?, ?)";
            } else {
                System.out.println("\nDEBUG: Tipo inválido recebido: " + tipo);
                throw new Exception("Tipo inválido");
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, nome);
            stmt.setString(3, senha);
            stmt.setString(4, tipo);
            stmt.setString(5, matricula);


            System.out.println("\nDEBUG: Executando INSERT -> " + sql);
            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("\nDEBUG: Cadastro realizado com sucesso!");
                sucesso = true;
            } else {
                System.out.println("\nDEBUG: Nenhuma linha inserida!");
            }

        } catch (Exception e) {
            System.out.println("\nDEBUG: Erro no cadastro!");
            e.printStackTrace();
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("sucesso", sucesso);

        if (sucesso) {
            resposta.put("mensagem", "Cadastro realizado com sucesso!");
            resposta.put("rota", "/html/teladelogin.html"); // redireciona para tela de login
        } else {
            resposta.put("mensagem", "Erro ao criar perfil!");
        }

        System.out.println("\nDEBUG: Retornando resposta: " + resposta);
        return resposta;
    }
}
