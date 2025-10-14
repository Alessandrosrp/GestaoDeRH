package com.workly.api.configuracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workly.api.criarperfil.Conexao;

@RestController
@RequestMapping("/api/vagas")
public class configuracao2RestController {

    @PostMapping("/salvar")
    public Map<String, Object> salvarVaga(@RequestBody Map<String, String> dados) {
        System.out.println("==========================\nDEBUG: Entrou no método salvarVaga");  // 🔹 print inicial

        Map<String, Object> resposta = new HashMap<>();
        boolean sucesso = false;

        String id = dados.get("id");
        String descricao = dados.get("descricao");
        String tipo = dados.get("tipo");
        String curso = dados.get("curso");
        String nivel = dados.get("nivel");
        String contato = dados.get("contato");
        String empresa = dados.get("empresa");

        System.out.println("DEBUG (BACKEND) Dados recebidos: " + dados);

        // Validação
        if (descricao == null || tipo == null || curso == null || nivel == null || contato == null || empresa == null
                || descricao.isEmpty() || tipo.isEmpty() || curso.isEmpty() || nivel.isEmpty() || contato.isEmpty() || empresa.isEmpty()) {
            resposta.put("sucesso", false);
            resposta.put("mensagem", "Todos os campos são obrigatórios.");
            System.out.println("DEBUG: Falha na validação de campos obrigatórios");
            return resposta;
        }

        try (Connection conn = Conexao.conectar()) {
            if (conn == null) {
                System.out.println("DEBUG: Conexão nula!");
            }

            String sql;
            if (id != null && !id.isEmpty()) {
                sql = "UPDATE vagas SET descricao=?, tipo=?, curso=?, nivel=?, contato=?, empresa=? WHERE id=?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, descricao);
                    stmt.setString(2, tipo);
                    stmt.setString(3, curso);
                    stmt.setString(4, nivel);
                    stmt.setString(5, contato);
                    stmt.setString(6, empresa);
                    stmt.setInt(7, Integer.parseInt(id));

                    System.out.println("DEBUG: Executando UPDATE da vaga com id=" + id);
                    int linhas = stmt.executeUpdate();
                    sucesso = linhas > 0;
                }
            } else {
                sql = "INSERT INTO vagas (descricao, tipo, curso, nivel, contato, empresa) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, descricao);
                    stmt.setString(2, tipo);
                    stmt.setString(3, curso);
                    stmt.setString(4, nivel);
                    stmt.setString(5, contato);
                    stmt.setString(6, empresa);

                    System.out.println("DEBUG: Executando INSERT da vaga");
                    int linhas = stmt.executeUpdate();
                    sucesso = linhas > 0;
                }
            }

            resposta.put("sucesso", sucesso);
            resposta.put("mensagem", sucesso ? (id != null && !id.isEmpty() ? "Vaga atualizada com sucesso!" : "Vaga criada com sucesso!") : "Nenhuma alteração realizada.");

        } catch (Exception e) {
            System.out.println("DEBUG: Erro ao salvar vaga!");
            e.printStackTrace();
            resposta.put("sucesso", false);
            resposta.put("mensagem", "Erro ao salvar vaga: " + e.getMessage());
        }

        System.out.println("DEBUG: Retornando resposta: " + resposta);
        return resposta;
    }

    @GetMapping("/buscar/{empresaOrId}")
    public List<Map<String, Object>> buscarVagasPorEmpresaOuId(@PathVariable String empresaOrId) {
        System.out.println("==========================\nDEBUG: Entrou no método buscarVagasPorEmpresaOuId");
        System.out.println("DEBUG (BACKEND) Valor recebido do frontend: " + empresaOrId);

        List<Map<String, Object>> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar()) {
            if (conn == null) System.out.println("DEBUG: Conexão nula!");

            boolean isNumeric = false;
            int id = -1;
            try {
                id = Integer.parseInt(empresaOrId);
                isNumeric = true;
            } catch (NumberFormatException ignored) {}

            String sql;
            if (isNumeric) {
                sql = "SELECT * FROM vagas WHERE id=?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    System.out.println("DEBUG: Executando SELECT por ID=" + id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) lista.add(extrairVaga(rs));
                    }
                }
            } else {
                sql = "SELECT * FROM vagas WHERE LOWER(empresa) LIKE LOWER(?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, "%" + empresaOrId + "%");
                    System.out.println("DEBUG: Executando SELECT por empresa LIKE %" + empresaOrId + "%");
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) lista.add(extrairVaga(rs));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("DEBUG: Erro ao buscar vagas!");
            e.printStackTrace();
        }

        System.out.println("DEBUG: FIM buscar. Registros encontrados: " + lista.size());
        return lista;
    }

    @DeleteMapping("/excluir/{id}")
    public Map<String, Object> excluirVaga(@PathVariable String id) {
        System.out.println("==========================\nDEBUG: Entrou no método excluirVaga | id=" + id);
        Map<String, Object> resposta = new HashMap<>();
        boolean sucesso = false;

        try (Connection conn = Conexao.conectar()) {
            String sql = "DELETE FROM vagas WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(id));
                System.out.println("DEBUG: Executando DELETE id=" + id);
                int linhas = stmt.executeUpdate();
                sucesso = linhas > 0;
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Erro ao excluir vaga!");
            e.printStackTrace();
        }

        resposta.put("sucesso", sucesso);
        resposta.put("mensagem", sucesso ? "Vaga excluída com sucesso!" : "Vaga não encontrada.");
        System.out.println("DEBUG: Retornando resposta: " + resposta);
        return resposta;
    }

    private Map<String, Object> extrairVaga(ResultSet rs) throws Exception {
        Map<String, Object> vaga = new HashMap<>();
        vaga.put("id", rs.getInt("id"));
        vaga.put("descricao", rs.getString("descricao"));
        vaga.put("tipo", rs.getString("tipo"));
        vaga.put("curso", rs.getString("curso"));
        vaga.put("nivel", rs.getString("nivel"));
        vaga.put("contato", rs.getString("contato"));
        vaga.put("empresa", rs.getString("empresa"));
        return vaga;
    }
}