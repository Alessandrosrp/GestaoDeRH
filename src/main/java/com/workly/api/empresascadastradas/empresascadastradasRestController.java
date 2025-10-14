package com.workly.api.empresascadastradas;

import com.workly.api.criarperfil.Conexao;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/empresas")
public class empresascadastradasRestController {

    
    @GetMapping
    public List<Map<String, Object>> listarEmpresas() {
        List<Map<String, Object>> empresas = new ArrayList<>();

        try (Connection conn = Conexao.conectar()) {
            if (conn == null) return empresas;

            String sql = "SELECT * FROM empresacadastrada";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map<String, Object> emp = new HashMap<>();
                    emp.put("id", rs.getInt("id"));
                    emp.put("nome", rs.getString("nome"));
                    emp.put("matricula", rs.getString("matricula"));
                    emp.put("cnpj", rs.getString("cnpj"));
                    emp.put("cep", rs.getString("cep"));
                    emp.put("endereco", rs.getString("endereco"));
                    emp.put("cidade", rs.getString("cidade"));
                    emp.put("bairro", rs.getString("bairro"));
                    emp.put("rh", rs.getString("rh"));
                    emp.put("ramo", rs.getString("ramo"));
                    emp.put("email", rs.getString("email"));
                    emp.put("filial", rs.getString("filial"));
                    emp.put("estagiario", rs.getString("estagiario"));
                    emp.put("funcionarios", rs.getString("funcionarios"));
                    emp.put("inscricao", rs.getString("inscricao"));
                    emp.put("responsavel", rs.getString("responsavel"));
                    emp.put("telefones", rs.getString("telefones"));
                    emp.put("como", rs.getString("como"));
                    empresas.add(emp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empresas;
    }


    @PostMapping
    public String criarEmpresa(@RequestBody Map<String, String> novaEmpresa) {
        try (Connection conn = Conexao.conectar()) {
            if (conn == null) return "Erro de conexão";

            String sql = "INSERT INTO empresacadastrada (nome, matricula, cnpj, cep, endereco, cidade, bairro, rh, ramo, email, filial, estagiario, funcionarios, inscricao, responsavel, telefones, como) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, novaEmpresa.get("nome"));
                stmt.setString(2, novaEmpresa.get("matricula"));
                stmt.setString(3, novaEmpresa.get("cnpj"));
                stmt.setString(4, novaEmpresa.get("cep"));
                stmt.setString(5, novaEmpresa.get("endereco"));
                stmt.setString(6, novaEmpresa.get("cidade"));
                stmt.setString(7, novaEmpresa.get("bairro"));
                stmt.setString(8, novaEmpresa.get("rh"));
                stmt.setString(9, novaEmpresa.get("ramo"));
                stmt.setString(10, novaEmpresa.get("email"));
                stmt.setString(11, novaEmpresa.get("filial"));
                stmt.setString(12, novaEmpresa.get("estagiario"));
                stmt.setString(13, novaEmpresa.get("funcionarios"));
                stmt.setString(14, novaEmpresa.get("inscricao"));
                stmt.setString(15, novaEmpresa.get("responsavel"));
                stmt.setString(16, novaEmpresa.get("telefones"));
                stmt.setString(17, novaEmpresa.get("como"));

                stmt.executeUpdate();
                return "Empresa cadastrada com sucesso!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao cadastrar empresa.";
        }
    }

    @PutMapping("/{id}")
    public String editarEmpresa(@PathVariable int id, @RequestBody Map<String, String> empresaEditada) {
        try (Connection conn = Conexao.conectar()) {
            if (conn == null) return "Erro de conexão";

            String sql = "UPDATE empresacadastrada SET nome=?, matricula=?, cnpj=?, cep=?, endereco=?, cidade=?, bairro=?, rh=?, ramo=?, email=?, filial=?, estagiario=?, funcionarios=?, inscricao=?, responsavel=?, telefones=?, como=? WHERE id=?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, empresaEditada.get("nome"));
                stmt.setString(2, empresaEditada.get("matricula"));
                stmt.setString(3, empresaEditada.get("cnpj"));
                stmt.setString(4, empresaEditada.get("cep"));
                stmt.setString(5, empresaEditada.get("endereco"));
                stmt.setString(6, empresaEditada.get("cidade"));
                stmt.setString(7, empresaEditada.get("bairro"));
                stmt.setString(8, empresaEditada.get("rh"));
                stmt.setString(9, empresaEditada.get("ramo"));
                stmt.setString(10, empresaEditada.get("email"));
                stmt.setString(11, empresaEditada.get("filial"));
                stmt.setString(12, empresaEditada.get("estagiario"));
                stmt.setString(13, empresaEditada.get("funcionarios"));
                stmt.setString(14, empresaEditada.get("inscricao"));
                stmt.setString(15, empresaEditada.get("responsavel"));
                stmt.setString(16, empresaEditada.get("telefones"));
                stmt.setString(17, empresaEditada.get("como"));
                stmt.setInt(18, id);

                stmt.executeUpdate();
                return "Empresa atualizada com sucesso!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao atualizar empresa.";
        }
    }

   
    @DeleteMapping("/{id}")
    public String excluirEmpresa(@PathVariable int id) {
        try (Connection conn = Conexao.conectar()) {
            if (conn == null) return "Erro de conexão";

            String sql = "DELETE FROM empresacadastrada WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                return "Empresa excluída com sucesso!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir empresa.";
        }
    }
}
