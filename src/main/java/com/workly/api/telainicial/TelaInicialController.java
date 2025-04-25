package com.workly.api.telainicial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TelaInicialController {

    @FXML
    private Button btn_cadastrarperfil;

    @FXML
    private Button btn_configuracoes;

    @FXML
    private Button btn_criarperfil;

    @FXML
    private Button btn_esqueceusenha;

    @FXML
    void cadastrarcurriculo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/curriculo/curriculo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Curriculo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de curriculo: " + e.getMessage());
        }
    }

    @FXML
    void configuracoes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/configuracao/configuracao.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Configurar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de curriculo: " + e.getMessage());
        }
    }

    @FXML
    void criarconta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/criarperfil/criarperfil.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Criar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela criar conta: " + e.getMessage());
        }
    }
    @FXML
    void esqueceusenha(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/esqueceusenha/esqueceusenha.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Editar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de curriculo: " + e.getMessage());
        }
    }
    }