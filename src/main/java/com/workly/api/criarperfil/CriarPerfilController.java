package com.workly.api.criarperfil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class CriarPerfilController {

    @FXML
    private PasswordField confirmar_senha;

    @FXML
    private TextField emailTextField;

    @FXML
    private ImageView imagemregistro;

    @FXML
    private Button registrarbtn;

    @FXML
    private Button sairbtn;

    @FXML
    private PasswordField senhatext;

    @FXML
    private ComboBox<String> tipo_usuario;

    @FXML
    private TextField usuario_text;

    @FXML
    void initialize() {
        tipo_usuario.getItems().addAll("Empresa", "Usuario");
    }

        @FXML
        void registrar_perfil(ActionEvent event) throws IOException {
            String email = emailTextField.getText();
            String nome = usuario_text.getText();
            String senha = senhatext.getText();
            String confirmarSenha = confirmar_senha.getText();
            String tipoLogin = tipo_usuario.getSelectionModel().getSelectedItem();
            
            if (senha.equals(confirmarSenha)) {
                if (tipoLogin != null && tipoLogin.equals("Usuario")) {
                    try (Connection conn = Conexao.conectar()) {
                        String sql = "INSERT INTO usuario (email, nome, senha) VALUES (?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, email);
                        stmt.setString(2, nome);
                        stmt.setString(3, senha);

                        int linhasAfetadas = stmt.executeUpdate();
                        if (linhasAfetadas > 0) {
                            System.out.println("Cadastro realizado com sucesso");
                            limparCampos();
                            exibirMensagemConfirmacao();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace(); 
                    }
                } else if (tipoLogin != null && tipoLogin.equals("Empresa")) {
                    try (Connection conn = Conexao.conectar()) {
                        String sql = "INSERT INTO empresa (email, nome, senha) VALUES (?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, email);
                        stmt.setString(2, nome);
                        stmt.setString(3, senha);

                        int linhasAfetadas = stmt.executeUpdate();
                        if (linhasAfetadas > 0) {
                            System.out.println("Cadastro realizado com sucesso");
                            limparCampos();
                            exibirMensagemConfirmacao();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Tipo de usuário não selecionado");
                }
            } else {
                System.out.println("Senhas não conferem");
            }
        }
        private void exibirMensagemConfirmacao() throws IOException {
            Stage criarPerfilStage = (Stage) registrarbtn.getScene().getWindow();
            criarPerfilStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/mensagem/mensagem.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
    		stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));            
            stage.setTitle("Cadastro Concluído");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // espera até que a janela seja fechada

            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/com/workly/api/login/login.fxml"));
            Parent rootLogin = loaderLogin.load();
            Stage stageLogin = new Stage();
            stageLogin.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stageLogin.setTitle("Tela Inicial");
            stageLogin.setScene(new Scene(rootLogin));
            stageLogin.show();
        }
        private void limparCampos() {
            emailTextField.clear();
            senhatext.clear();
            confirmar_senha.clear();
            usuario_text.clear();
            tipo_usuario.getSelectionModel().clearSelection();
        }

    @FXML
    void tipo_usuarioCombo(ActionEvent event) {

    }

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
}