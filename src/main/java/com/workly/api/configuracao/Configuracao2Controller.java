package com.workly.api.configuracao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.workly.api.criarperfil.Conexao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Configuracao2Controller {

    @FXML
    private Button btn_sair;

    @FXML
    private TextField contato_txt;

    @FXML
    private ComboBox<String> curso_combo;

    @FXML
    private TextField descricao_txt;

    @FXML
    private Button excluir_configuracao_btn;

    @FXML
    private ImageView imagemregistro;

    @FXML
    private TextField nivel_txt;

    @FXML
    private Button registrarbtn;

    @FXML
    private TextField usuario_txt;

    @FXML
    private ComboBox<String> tipodevaga_combo;

    @FXML
    private ComboBox<String> nivel_comboBox;

    @FXML
    private TextField id_txt;
    
    @FXML
    void initialize() {
        tipodevaga_combo.getItems().addAll("Estagio", "Trabalho");
        curso_combo.getItems().addAll("ADS", "Medicina", "Engenharia", "Outro");
        nivel_comboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    }

    @FXML
    void tipodevaga_comboBox(ActionEvent event) {

    }

    @FXML
    void confirmar_configuracao(ActionEvent event) throws IOException {
        String usuario = usuario_txt.getText();
        String descricao = descricao_txt.getText();
        String contato = contato_txt.getText();
        String tipo = tipodevaga_combo.getSelectionModel().getSelectedItem();
        String curso = curso_combo.getSelectionModel().getSelectedItem();
        String nivel = nivel_comboBox.getSelectionModel().getSelectedItem();
        
        if (usuario.isEmpty() || descricao.isEmpty() || contato.isEmpty() || nivel.isEmpty()) {
            System.out.println("Preencha todos os campos");
            return;
        }
        try (Connection conn = Conexao.conectar()) {
            String sql = "UPDATE vagas SET descricao = ?, contato = ?, nivel = ?, tipo = ?, curso = ? WHERE empresa = ?";
            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, descricao);
            stmt.setString(2, contato); 
            stmt.setString(3, nivel);
            stmt.setString(4, tipo);
            stmt.setString(5, curso);
            stmt.setString(6, usuario);
            stmt.executeUpdate();
            System.out.println("Curriculo atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            usuario_txt.clear();
            descricao_txt.clear();
            contato_txt.clear();
            nivel_comboBox.getSelectionModel().clearSelection();
            tipodevaga_combo.getSelectionModel().clearSelection();
            curso_combo.getSelectionModel().clearSelection();}

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/mensagem/mensagem3.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastro Concluído");
            stage.setScene(new Scene(root));
            stage.show();
    }

    @FXML
    void contato_texto(ActionEvent event) {

    }

    @FXML
    void curso_comboBox(ActionEvent event) {
    }

    @FXML
    void descricao_texto(ActionEvent event) {

    }

    @FXML
    void excluir_configuracao(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/mensagem/mensagem3.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Confirmação de exclusão");
        stage.setScene(new Scene(root));

        // Adicione um botão de confirmação e um botão de cancelamento
        Button mensagem_btn = (Button) root.lookup("#mensagem_btn");
        Button mensagem_btn1 = (Button) root.lookup("#mensagem_btn1");

        mensagem_btn.setOnAction(e -> {
            try (Connection conn = Conexao.conectar()) {
                String sql = "DELETE FROM curriculo WHERE id = ?";
                var stmt = conn.prepareStatement(sql);
                stmt.setString(1, id_txt.getText());
                stmt.executeUpdate();
                System.out.println("Dados excluidos com sucesso!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                id_txt.clear();
                stage.close();
            }
        });

        mensagem_btn1.setOnAction(e -> {
            stage.close();
        });

        stage.show();
    }

    @FXML
    void funcao_sair(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/telainicial_vagas/telainicial_vagas.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
    		stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Tela Inicial");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btn_sair.getScene().getWindow();
            loginStage.close();
    }

    @FXML
    void nivel_texto(ActionEvent event) {

    }

    @FXML
    void nivel_combo(ActionEvent event) {
    }

    @FXML
    void id_texto(ActionEvent event) {

    }
    @FXML
    void usuario_texto(ActionEvent event) {

    }

}