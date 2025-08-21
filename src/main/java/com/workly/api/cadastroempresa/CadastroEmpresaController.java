package com.workly.api.cadastroempresa;

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
    private TextField comoconheceuField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField enderecoField;

    @FXML
    private TextField estagiarioField;

    @FXML
    private TextField filialField;

    @FXML
    private TextField funcionariosField;

    @FXML
    private TextField inscricaoField;

    @FXML
    private TextField nomeEmpresaField;

    @FXML
    private TextField possuiRhTextfield;

    @FXML
    private TextField ramoAtividadeField;

    @FXML
    private TextField responsavelField;

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
    void onSalvar(ActionEvent event) throws IOException {

        String nome = nomeEmpresaField.getText();
        String cnpj = cnpjField.getText();
        String cep = cepField.getText();
        String endereco = enderecoField.getText();
        String cidade = cidadeField.getText();
        String bairro = bairroField.getText();
        String rh = possuiRhTextfield.getText();        
        String ramo = ramoAtividadeField.getText();
        String email = emailField.getText();
        String filial = filialField.getText();
        String estagiario = estagiarioField.getText();
        String funcionarios = funcionariosField.getText();
        String inscricao = inscricaoField.getText();
        String responsavel = responsavelField.getText();
        String telefones = telefonesField.getText();
        String como = comoconheceuField.getText();

        if  (nome.isEmpty() || cnpj.isEmpty() || cep.isEmpty() || endereco.isEmpty() || cidade.isEmpty() || bairro.isEmpty() || ramo.isEmpty() || email.isEmpty() || filial.isEmpty() || estagiario.isEmpty() || funcionarios.isEmpty() || inscricao.isEmpty() || responsavel.isEmpty() || telefones.isEmpty() || como.isEmpty()) {
            System.out.println("Preencha todos os campos");
            return;
        }
        
        try (Connection conn = Conexao.conectar()) {
            String sql = "INSERT INTO empresacadastrada (nome, cnpj, cep, endereco, cidade, bairro, rh, ramo, email, filial, estagiario, funcionarios, inscricao, responsavel, telefones, como) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cnpj);
            stmt.setString(3, cep);
            stmt.setString(4, endereco); 
            stmt.setString(5, cidade);  
            stmt.setString(6, bairro);
            stmt.setString(7, rh);
            stmt.setString(8, ramo);
            stmt.setString(9, email);
            stmt.setString(10, filial);
            stmt.setString(11, estagiario);
            stmt.setString(12, funcionarios);
            stmt.setString(13, inscricao);
            stmt.setString(14, responsavel);
            stmt.setString(15, telefones);
            stmt.setString(16, como);
            System.out.println("Empresa cadastrada com sucesso!");
            }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            nomeEmpresaField.clear();
            cnpjField.clear();
            cepField.clear();
            enderecoField.clear();
            cidadeField.clear();
            bairroField.clear();
            ramoAtividadeField.clear();
            emailField.clear();
            filialField.clear();
            estagiarioField.clear();
            funcionariosField.clear();
            inscricaoField.clear();
            responsavelField.clear();
            telefonesField.clear();
            comoconheceuField.clear();
            possuiRhTextfield.clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/mensagem/mensagem.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Cadastro de Curriculo");
            stage.setScene(new Scene(root));
            stage.show();
    }

    }
}
