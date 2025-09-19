package com.workly.api.cadastroempresa;


import com.workly.api.criarperfil.Conexao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

@RestController
public class CadastroEmpresaRestController {

    @PostMapping("/cadastrar-empresa")
    public ResponseEntity<Map<String, Object>> cadastrarEmpresa(@RequestBody Map<String, String> dados) {
        System.out.println("==========================\nDEBUG: Entrou no método cadastrarEmpresa");

        
        String nome = dados.get("nome");
        String cnpj = dados.get("cnpj");
        String cep = dados.get("cep");
        String endereco = dados.get("endereco");
        String cidade = dados.get("cidade");
        String bairro = dados.get("bairro");
        String rh = dados.get("rh");
        String ramo = dados.get("ramo");
        String email = dados.get("email");
        String filial = dados.get("filial");
        String estagiario = dados.get("estagiario");
        String funcionarios = dados.get("funcionarios");
        String inscricao = dados.get("inscricao");
        String responsavel = dados.get("responsavel");
        String telefones = dados.get("telefones");
        String como = dados.get("como");

       
        Map<String, Object> resposta = new HashMap<>();

        // Valida se algum campo obrigatório está vazio
        if (nome == null || nome.isEmpty() || cnpj == null || cnpj.isEmpty() || cep == null || cep.isEmpty() || endereco == null || endereco.isEmpty() || cidade == null || cidade.isEmpty() || bairro == null || bairro.isEmpty() || ramo == null || ramo.isEmpty() || email == null || email.isEmpty() || filial == null || filial.isEmpty() || estagiario == null || estagiario.isEmpty() || funcionarios == null || funcionarios.isEmpty() || inscricao == null || inscricao.isEmpty() || responsavel == null || responsavel.isEmpty() || telefones == null || telefones.isEmpty() || como == null || como.isEmpty()) {
            resposta.put("sucesso", false);
            resposta.put("mensagem", "Todos os campos devem ser preenchidos.");
            System.out.println("\nDEBUG: Falha no cadastro. Campo vazio.");
            return new ResponseEntity<>(resposta, HttpStatus.BAD_REQUEST);
        }

        try (Connection conn = Conexao.conectar()) {
            if (conn == null) {
                resposta.put("sucesso", false);
                resposta.put("mensagem", "Erro ao conectar com o banco de dados.");
                System.out.println("\nDEBUG: Conexão nula!");
                return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String sql = "INSERT INTO empresacadastrada (nome, cnpj, cep, endereco, cidade, bairro, rh, ramo, email, filial, estagiario, funcionarios, inscricao, responsavel, telefones, como) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nome);
                stmt.setString(2, cnpj);
                stmt.setString(3, cep);
                stmt.setString(4, endereco);
                stmt.setString(5, cidade);
                stmt.setString(6, bairro);
                stmt.setString(7, rh);
                stmt.setString(8, ramo);
                stmt.setString(9, email);
                stmt.setString(10, filial);
                stmt.setString(11, estagiario);
                stmt.setString(12, funcionarios);
                stmt.setString(13, inscricao);
                stmt.setString(14, responsavel);
                stmt.setString(15, telefones);
                stmt.setString(16, como);
                
                int linhasAfetadas = stmt.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    resposta.put("sucesso", true);
                    resposta.put("mensagem", "Empresa cadastrada com sucesso!");
                    System.out.println("\nDEBUG: Empresa cadastrada com sucesso!");
                    return new ResponseEntity<>(resposta, HttpStatus.CREATED);
                } else {
                    resposta.put("sucesso", false);
                    resposta.put("mensagem", "Nenhum dado foi inserido.");
                    System.out.println("\nDEBUG: Falha ao inserir no banco de dados.");
                    return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

        } catch (SQLException e) {
            resposta.put("sucesso", false);
            resposta.put("mensagem", "Erro ao acessar o banco de dados: " + e.getMessage());
            System.out.println("\nDEBUG: Erro de SQL!");
            e.printStackTrace();
            return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
