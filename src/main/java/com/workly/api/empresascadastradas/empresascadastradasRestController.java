package com.workly.api.empresascadastradas;

import com.workly.api.criarperfil.Conexao;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@RestController
@CrossOrigin(origins = "*") 
public class empresascadastradasRestController {

    @GetMapping("/listar-empresas")
    public List<Map<String, Object>> listarEmpresas() {
        List<Map<String, Object>> empresas = new ArrayList<>();

        try (Connection conn = Conexao.conectar()) {
            if (conn == null) return empresas;

            String sql = "SELECT * FROM empresacadastrada";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
}
