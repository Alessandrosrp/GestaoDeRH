package com.workly.api.esqueceusenha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alterar-senha")
public class AlterarSenhaRest {

    @PutMapping
    public Map<String, Object> alterarSenha(@RequestBody Map<String, String> dados) {
        String usuario = dados.get("usuario");
        String matricula = dados.get("matricula");
        String senha = dados.get("senha");       
        String novaSenha = dados.get("novaSenha");

        boolean atualizou = false;
        String mensagem = "";

        
        if (!senha.equals(novaSenha)) {
            mensagem = "A senha atual e a nova senha não conferem!";
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("atualizou", false);
            resposta.put("mensagem", mensagem);
            return resposta;
        }

        try (Connection conn = com.workly.api.criarperfil.Conexao.conectar()) {
            
            String sql = "UPDATE usuario SET senha = ? WHERE nome = ? AND matricula = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, novaSenha);
            stmt.setString(2, usuario);
            stmt.setString(3, matricula);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                atualizou = true;
                mensagem = "Senha alterada com sucesso!";
            } else {
                mensagem = "Usuário ou matrícula não encontrados.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro interno no servidor.";
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("atualizou", atualizou);
        resposta.put("mensagem", mensagem);
        return resposta;
    }

}
