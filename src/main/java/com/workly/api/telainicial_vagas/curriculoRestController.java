package com.workly.api.telainicial_vagas;

import com.workly.api.criarperfil.Conexao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/curriculo")
public class curriculoRestController {

    private static final String UPLOAD_DIR = "C:/workly/uploads/";

    
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> cadastrarCurriculo(
            @RequestParam String usuario,
            @RequestParam String contato,
            @RequestParam String tipo,
            @RequestParam String curso,
            @RequestParam String nivel,
            @RequestParam String descricao,
            @RequestPart(required = false) MultipartFile foto) {

        Map<String, Object> resposta = new HashMap<>();

       
        if (usuario.isBlank() || contato.isBlank() || tipo.isBlank() || curso.isBlank() || nivel.isBlank() || descricao.isBlank()) {
            resposta.put("sucesso", false);
            resposta.put("mensagem", "Todos os campos obrigatórios devem ser preenchidos.");
            return new ResponseEntity<>(resposta, HttpStatus.BAD_REQUEST);
        }

      
        if (usuario.length() > 100) {
            return tamanhoExcedido("Usuario", 100);
        }
        if (contato.length() > 100) {
            return tamanhoExcedido("Contato", 100);
        }
        if (tipo.length() > 50) {
            return tamanhoExcedido("Tipo", 50);
        }
        if (curso.length() > 50) {
            return tamanhoExcedido("Curso", 50);
        }
        if (nivel.length() > 50) {
            return tamanhoExcedido("Nivel", 50);
        }
        if (descricao.length() > 500) {
            return tamanhoExcedido("Descricao", 500);
        }

        try (Connection conn = Conexao.conectar()) {
            if (conn == null) {
                resposta.put("sucesso", false);
                resposta.put("mensagem", "Erro ao conectar com o banco de dados.");
                return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String caminhoFoto = null;
            if (foto != null && !foto.isEmpty()) {
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) dir.mkdirs();
                String nomeArquivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
                File destino = new File(dir, nomeArquivo);
                foto.transferTo(destino);
                caminhoFoto = "uploads/" + nomeArquivo;
            }

            String sql = "INSERT INTO curriculo (usuario, contato, tipo, curso, nivel, descricao, foto) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, usuario);
                stmt.setString(2, contato);
                stmt.setString(3, tipo);
                stmt.setString(4, curso);
                stmt.setString(5, nivel);
                stmt.setString(6, descricao);
                stmt.setString(7, caminhoFoto);

                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    ResultSet keys = stmt.getGeneratedKeys();
                    int id = keys.next() ? keys.getInt(1) : 0;
                    resposta.put("sucesso", true);
                    resposta.put("mensagem", "Currículo cadastrado com sucesso!");
                    resposta.put("id", id);
                    return new ResponseEntity<>(resposta, HttpStatus.CREATED);
                } else {
                    resposta.put("sucesso", false);
                    resposta.put("mensagem", "Erro ao inserir dados no banco.");
                    return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            resposta.put("sucesso", false);
            resposta.put("mensagem", "Erro: " + e.getMessage());
            return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Map<String, Object>> tamanhoExcedido(String campo, int max) {
        Map<String, Object> r = new HashMap<>();
        r.put("sucesso", false);
        r.put("mensagem", campo + " excede o tamanho máximo de " + max + " caracteres.");
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

   
    @GetMapping
    public List<Map<String, Object>> listarCurriculos() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT * FROM curriculo";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> c = new HashMap<>();
                c.put("id", rs.getInt("id"));
                c.put("usuario", rs.getString("usuario"));
                c.put("contato", rs.getString("contato"));
                c.put("tipo", rs.getString("tipo"));
                c.put("curso", rs.getString("curso"));
                c.put("nivel", rs.getString("nivel"));
                c.put("descricao", rs.getString("descricao"));
                c.put("foto", rs.getString("foto"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable int id) {
        String sql = "SELECT * FROM curriculo WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Map<String, Object> c = new HashMap<>();
                c.put("id", rs.getInt("id"));
                c.put("usuario", rs.getString("usuario"));
                c.put("contato", rs.getString("contato"));
                c.put("tipo", rs.getString("tipo"));
                c.put("curso", rs.getString("curso"));
                c.put("nivel", rs.getString("nivel"));
                c.put("descricao", rs.getString("descricao"));
                c.put("foto", rs.getString("foto"));
                return ResponseEntity.ok(c);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Currículo não encontrado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mensagem", e.getMessage()));
        }
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> excluirCurriculo(@PathVariable int id) {
        String sql = "DELETE FROM curriculo WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                return ResponseEntity.ok(Map.of("mensagem", "Currículo excluído com sucesso"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Currículo não encontrado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mensagem", e.getMessage()));
        }
    }
}
