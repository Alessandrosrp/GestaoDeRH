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
@RequestMapping("/api/curriculos")
public class configuracaoRestController {

    // ========================= SALVAR =========================
    @PostMapping("/salvar")
    public Map<String, Object> salvarCurriculo(@RequestBody Map<String, String> dados) {
        System.out.println("==========================");
        System.out.println("DEBUG: Entrou no método salvarCurriculo");
        System.out.println("DEBUG (BACKEND) Dados recebidos: " + dados);

        Map<String, Object> resposta = new HashMap<>();
        boolean sucesso = false;

        String id = dados.get("id");
        String usuario = dados.get("usuario");
        String descricao = dados.get("descricao");
        String contato = dados.get("contato");
        String tipo = dados.get("tipo");
        String curso = dados.get("curso");
        String nivel = dados.get("nivel");

        // Validação
        if (usuario == null || descricao == null || contato == null || tipo == null || curso == null || nivel == null
                || usuario.isEmpty() || descricao.isEmpty() || contato.isEmpty() || tipo.isEmpty() || curso.isEmpty() || nivel.isEmpty()) {
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
                sql = "UPDATE curriculo SET usuario=?, descricao=?, contato=?, tipo=?, curso=?, nivel=? WHERE id=?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, usuario);
                    stmt.setString(2, descricao);
                    stmt.setString(3, contato);
                    stmt.setString(4, tipo);
                    stmt.setString(5, curso);
                    stmt.setString(6, nivel);
                    stmt.setInt(7, Integer.parseInt(id));

                    System.out.println("DEBUG: Executando UPDATE do curriculo com id=" + id);
                    int linhas = stmt.executeUpdate();
                    sucesso = linhas > 0;
                }
            } else {
                sql = "INSERT INTO curriculo (usuario, descricao, contato, tipo, curso, nivel) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, usuario);
                    stmt.setString(2, descricao);
                    stmt.setString(3, contato);
                    stmt.setString(4, tipo);
                    stmt.setString(5, curso);
                    stmt.setString(6, nivel);

                    System.out.println("DEBUG: Executando INSERT do curriculo");
                    int linhas = stmt.executeUpdate();
                    sucesso = linhas > 0;
                }
            }

            resposta.put("sucesso", sucesso);
            resposta.put("mensagem", sucesso ? 
                (id != null && !id.isEmpty() ? "Currículo atualizado com sucesso!" : "Currículo criado com sucesso!") :
                "Nenhuma alteração realizada.");

        } catch (Exception e) {
            System.out.println("DEBUG: Erro ao salvar curriculo!");
            e.printStackTrace();
            resposta.put("sucesso", false);
            resposta.put("mensagem", "Erro ao salvar curriculo: " + e.getMessage());
        }

        System.out.println("DEBUG: Retornando resposta: " + resposta);
        return resposta;
    }

    // ========================= BUSCAR =========================
    @GetMapping("/buscar/{usuarioOuId}")
    public List<Map<String, Object>> buscarCurriculo(@PathVariable String usuarioOuId) {
        System.out.println("==========================");
        System.out.println("DEBUG: Entrou no método buscarCurriculo");
        System.out.println("DEBUG (BACKEND) Valor recebido do frontend: " + usuarioOuId);

        List<Map<String, Object>> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar()) {
            if (conn == null) System.out.println("DEBUG: Conexão nula!");

            boolean isNumeric = false;
            int id = -1;
            try {
                id = Integer.parseInt(usuarioOuId);
                isNumeric = true;
            } catch (NumberFormatException ignored) {}

            String sql;
            if (isNumeric) {
                sql = "SELECT * FROM curriculo WHERE id=?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    System.out.println("DEBUG: Executando SELECT por ID=" + id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) lista.add(extrairCurriculo(rs));
                    }
                }
            } else {
                sql = "SELECT * FROM curriculo WHERE LOWER(usuario) LIKE LOWER(?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, "%" + usuarioOuId + "%");
                    System.out.println("DEBUG: Executando SELECT por nome LIKE %" + usuarioOuId + "%");
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) lista.add(extrairCurriculo(rs));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("DEBUG: Erro ao buscar curriculo!");
            e.printStackTrace();
        }

        System.out.println("DEBUG: FIM buscar. Registros encontrados: " + lista.size());
        return lista;
    }

    // ========================= EXCLUIR =========================
    @DeleteMapping("/excluir/{id}")
    public Map<String, Object> excluirCurriculo(@PathVariable String id) {
        System.out.println("==========================");
        System.out.println("DEBUG: Entrou no método excluirCurriculo | id=" + id);
        Map<String, Object> resposta = new HashMap<>();
        boolean sucesso = false;

        try (Connection conn = Conexao.conectar()) {
            String sql = "DELETE FROM curriculo WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(id));
                System.out.println("DEBUG: Executando DELETE id=" + id);
                int linhas = stmt.executeUpdate();
                sucesso = linhas > 0;
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Erro ao excluir curriculo!");
            e.printStackTrace();
        }

        resposta.put("sucesso", sucesso);
        resposta.put("mensagem", sucesso ? "Currículo excluído com sucesso!" : "Currículo não encontrado.");
        System.out.println("DEBUG: Retornando resposta: " + resposta);
        return resposta;
    }

    // ========================= MÉTODO AUXILIAR =========================
    private Map<String, Object> extrairCurriculo(ResultSet rs) throws Exception {
        Map<String, Object> curriculo = new HashMap<>();
        curriculo.put("id", rs.getInt("id"));
        curriculo.put("usuario", rs.getString("usuario"));
        curriculo.put("descricao", rs.getString("descricao"));
        curriculo.put("contato", rs.getString("contato"));
        curriculo.put("tipo", rs.getString("tipo"));
        curriculo.put("curso", rs.getString("curso"));
        curriculo.put("nivel", rs.getString("nivel"));
        return curriculo;
    }
}
