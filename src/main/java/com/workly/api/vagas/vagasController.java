package com.workly.api.vagas;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.Connection;

public class vagasController {

    @FXML
    private Button btn_registro;

    @FXML
    private Button btn_sair;

    @FXML
    private TextField contato_txt;

    @FXML
    private ComboBox<String> curso_comboBox;

    @FXML
    private TextField descricao_txt;

    @FXML
    private ComboBox<String> vaga_comboBox;

    @FXML
    private TextField empresa_txt;

    @FXML
    private ImageView imgFoto;
    @FXML
    private String caminhoFoto;

    @FXML
    private ComboBox<String> nivel_comboBox;

    @FXML
    void empresa_texto(ActionEvent event) {

    }
    @FXML
    void initialize() {
        vaga_comboBox.getItems().addAll("Estagio", "Trabalho");
        curso_comboBox.getItems().addAll("ADS", "Medicina", "Engenharia", "Outro");
        nivel_comboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        imgFoto.setOnMouseClicked((MouseEvent event) -> {
            selecionaFoto();
    });	
    }

    public void selecionaFoto() {
        FileChooser f= new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("imagens", "*.jpg"));
        File file = f.showOpenDialog(new Stage());
        if (file != null) {
            imgFoto.setImage(new Image("file:///" + file.getAbsolutePath()));
            caminhoFoto = file.getAbsolutePath();
        }
    } 
    
    @FXML
    void contato_texto(ActionEvent event) {

    }

    @FXML
    void curso_combo(ActionEvent event) {

    }

    @FXML
    void descricao_texto(ActionEvent event) {

    }

    @FXML
    void funcao_registro(ActionEvent event) throws IOException {
        String empresa = empresa_txt.getText();
        String contato = contato_txt.getText();
        String tipo = vaga_comboBox.getSelectionModel().getSelectedItem();
        String curso = curso_comboBox.getSelectionModel().getSelectedItem();
        String nivel = nivel_comboBox.getSelectionModel().getSelectedItem();
        String descricao = descricao_txt.getText();

        if (contato.isEmpty() || descricao.isEmpty() || contato.isEmpty() || curso.isEmpty()) {
            System.out.println("Preencha todos os campos");
            return;
        }
        try (Connection conn = Conexao.conectar()) {
            String sql = "INSERT INTO vagas (empresa, contato, tipo, curso, descricao, foto, nivel) VALUES (?, ?, ?, ?, ?, ?, ?)";
            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, empresa);
            stmt.setString(2, contato);
            stmt.setString(3, tipo); 
            stmt.setString(4, curso);  
            stmt.setString(5, descricao);
            stmt.setString(6, caminhoFoto);
            stmt.setString(7, nivel);
            stmt.executeUpdate();
            System.out.println("Vaga cadastrado com sucesso!");
            }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            descricao_txt.clear();
            contato_txt.clear();
            curso_comboBox.getSelectionModel().clearSelection();
            vaga_comboBox.getSelectionModel().clearSelection();}
            empresa_txt.clear();
            nivel_comboBox.getSelectionModel().clearSelection();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/mensagem/mensagem.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastro Concluído");
            stage.setScene(new Scene(root));
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
    void nivel_combo(ActionEvent event) {
    }

    @FXML
    void vaga_combo(ActionEvent event) {

    }

}
