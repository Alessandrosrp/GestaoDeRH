package com.workly.api.cadastroempresa;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CadastroEmpresaController {

    @FXML
    private TextField bairroField;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField cepField;

    @FXML
    private TextField cidadeField;

    @FXML
    private TextField cnpjField;

    @FXML
    private TextField conhecimentoCarreiraField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField enderecoField;

    @FXML
    private TextField filiaisField;

    @FXML
    private TextField inscricaoEstadualField;

    @FXML
    private TextField nomeEmpresaField;

    @FXML
    private TextField numEstagiariosField;

    @FXML
    private TextField numFuncionariosField;

    @FXML
    private CheckBox possuiRhCheckBox;

    @FXML
    private TextField responsavelContratacoesField;

    @FXML
    private TextField telefonesField;

    @FXML
    void onCancelar(ActionEvent event) {
        try {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/telainicial/telainicial.fxml"));
            Parent root = loader.load();

        
            Stage novaJanela = new Stage();
            novaJanela.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            novaJanela.setTitle("Tela Inicial");
            novaJanela.setScene(new Scene(root));
            novaJanela.show();

        
            Stage janelaAtual = (Stage) btnCancelar.getScene().getWindow();
            janelaAtual.close();

        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela inicial: " + e.getMessage());
        }
    }



    @FXML
    void onSalvar(ActionEvent event) {

    }

}
