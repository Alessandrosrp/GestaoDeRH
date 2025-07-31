package com.workly.api.login;

import java.io.IOException;

import org.springframework.stereotype.Controller;

import com.workly.api.criarperfil.Conexao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.sql.Connection;
import java.sql.SQLException;

@Controller
public class LoginController {
    @FXML
    private Button btn_esqueceusenha;

    @FXML
    private Button campocriarconta;

    @FXML
    private Button campologin;

    @FXML
    private PasswordField senhatxt;

    @FXML
    private TextField usuariotxt;

    @FXML
    private ComboBox<String> define_combo;

    @FXML
    void initialize() {
        define_combo.getItems().addAll("Empresa", "Usuario");
    };

    @FXML
    void define_comboBox(ActionEvent event) {

    }

    @FXML
    void esqueceu_senha(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/esqueceusenha/esqueceusenha.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
    		stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Esqueceu sua senha");
            stage.setScene(new Scene(root));
            stage.show();
    
            Stage loginStage = (Stage) campocriarconta.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de criar conta: " + e.getMessage());
        }
    }

    @FXML
    void criarconta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/criarperfil/criarperfil.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Criar Conta");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) campocriarconta.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de criar conta: " + e.getMessage());
        }
    }

@FXML
void fazerlogin(ActionEvent event) {
    String nome = usuariotxt.getText();
    String senha = senhatxt.getText();
    String tipoLogin = define_combo.getSelectionModel().getSelectedItem();

    if (tipoLogin.equals("Usuario")) {
        // Realizar autenticação de usuário
        try (Connection conn = Conexao.conectar()) {
            String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, senha);

            var resultado = stmt.executeQuery();

            if (resultado.next()) {
                // Login bem-sucedido, abrir tela inicial
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/telainicial/telainicial.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
                    stage.setTitle("Tela Inicial");
                    stage.setScene(new Scene(root));
                    stage.show();

                    Stage loginStage = (Stage) campologin.getScene().getWindow();
                    loginStage.close();
                } catch (IOException e) {
                    System.out.println("Erro ao carregar a tela inicial: " + e.getMessage());
                }
            } else {
                System.out.println("Usuário ou senha incorretos!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else if (tipoLogin.equals("Empresa")) {
        // Realizar autenticação de empresa
        try (Connection conn = Conexao.conectar()) {
            String sql = "SELECT * FROM empresa WHERE nome = ? AND senha = ?";
            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, senha);

            var resultado = stmt.executeQuery();

            if (resultado.next()) {
                // Login bem-sucedido, abrir tela inicial
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/telainicial_vagas/telainicial_vagas.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
                    stage.setTitle("Tela Inicial");
                    stage.setScene(new Scene(root));
                    stage.show();

                    Stage loginStage = (Stage) campologin.getScene().getWindow();
                    loginStage.close();
                } catch (IOException e) {
                    System.out.println("Erro ao carregar a tela inicial: " + e.getMessage());
                }
            } else {
                System.out.println("Empresa ou senha incorretos!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

}
