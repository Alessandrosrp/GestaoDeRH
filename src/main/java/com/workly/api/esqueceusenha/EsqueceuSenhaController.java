package com.workly.api.esqueceusenha;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;


import com.workly.api.criarperfil.Conexao;


public class EsqueceuSenhaController {

    @FXML
    private Button confirmar_btn;

    @FXML
    private TextField confirmar_senha_text;

    @FXML
    private ImageView imagemregistro;

    @FXML
    private Button sairbtn;

    @FXML
    private TextField senha_txt;

    @FXML
    private TextField usuario_text;

    @FXML
    void btn_sair(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/login/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
		    stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Tela Inicial");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) sairbtn.getScene().getWindow();
            loginStage.close();
    }

    @FXML
    void confirmar_redefinicao(ActionEvent event) throws IOException {
        String nome = usuario_text.getText();
        String senha = senha_txt.getText();
        String confirmar_senha = confirmar_senha_text.getText();

        if (!senha.equals(confirmar_senha)) {
            System.out.println("Senhas diferentes!");
            return;
        }

        boolean atualizou = false;

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);

            // Atualiza usuario
            String sqlUsuario = "UPDATE usuario SET senha = ? WHERE nome = ?";
            var stmtUsuario = conn.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, senha);
            stmtUsuario.setString(2, nome);
            int linhasUsuario = stmtUsuario.executeUpdate();

            // Atualiza empresa
            String sqlEmpresa = "UPDATE empresa SET senha = ? WHERE nome = ?";
            var stmtEmpresa = conn.prepareStatement(sqlEmpresa);
            stmtEmpresa.setString(1, senha);
            stmtEmpresa.setString(2, nome);
            int linhasEmpresa = stmtEmpresa.executeUpdate();

            if (linhasUsuario > 0 || linhasEmpresa > 0) {
                conn.commit();
                atualizou = true;
                System.out.println("Senha redefinida com sucesso!");
            } else {
                conn.rollback();
                System.out.println("Nenhum usuário ou empresa encontrados com esse nome.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        usuario_text.clear();
        senha_txt.clear();
        confirmar_senha_text.clear();

        if (atualizou) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/mensagem/mensagem2.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastro Concluído");
            stage.setScene(new Scene(root));
            stage.show();
            
            
            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/com/workly/api/login/login.fxml"));
            Parent rootLogin = loaderLogin.load();
            Stage stageLogin = new Stage();
            stageLogin.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stageLogin.setTitle("Tela Inicial");
            stageLogin.setScene(new Scene(rootLogin));
            stageLogin.show();
        }

    }
}