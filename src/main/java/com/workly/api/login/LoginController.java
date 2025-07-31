package com.workly.api.login;

import java.io.IOException;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Controller
public class LoginController {

    @FXML
    private Button campocriarconta;

    @FXML
    private Button campologin;

    @FXML
    private TextField senhatxt;

    @FXML
    private TextField usuariotxt;

@FXML
void criarconta(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/criarperfil/criarperfil.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Criar Conta");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        System.out.println("Erro ao carregar a tela de criar conta: " + e.getMessage());
    }
}

@FXML
void fazerlogin(ActionEvent event) {
    String usuario = usuariotxt.getText();
    String senha = senhatxt.getText();

    if(usuario.equals("admin") && senha.equals("admin")) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/telainicial/telainicial.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Tela Inicial");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela inicial: " + e.getMessage());
        }
    }
}

    }