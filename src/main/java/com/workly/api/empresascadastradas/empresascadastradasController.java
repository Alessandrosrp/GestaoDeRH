package com.workly.api.empresascadastradas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.workly.api.cadastroempresa.empresa;
import com.workly.api.criarperfil.Conexao;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class empresascadastradasController {

    @FXML
    private TableColumn<empresa, String> bairro;

    @FXML
    private Button btn_configuracoes;

    @FXML
    private Button btn_pesquisa;

    @FXML
    private TableColumn<empresa, String> cep;

    @FXML
    private TableColumn<empresa, String> cidade;

    @FXML
    private TableColumn<empresa, String> cnpj;

    @FXML
    private TableColumn<empresa, String> colunaId;

    @FXML
    private TableColumn<empresa, String> como;

    @FXML
    private TableColumn<empresa, String> email;

    @FXML
    private TableView<empresa> empresasdisponiveis_table;

    @FXML
    private TableColumn<empresa, String> endereco;

    @FXML
    private TableColumn<empresa, String> estagiario;

    @FXML
    private TableColumn<empresa, String> filial;

    @FXML
    private TableColumn<empresa, String> funcionarios;

    @FXML
    private TableColumn<empresa, String> inscricao;

    @FXML
    private TableColumn<empresa, String> matricula;

    @FXML
    private TableColumn<empresa, String> nome;

    @FXML
    private TextField pesquisa_txt;

    @FXML
    private TableColumn<empresa, String> ramo;

    @FXML
    private TableColumn<empresa, String> responsavel;

    @FXML
    private TableColumn<empresa, String> rh;

    @FXML
    private TableColumn<empresa, String> telefone;


     @FXML
    public void initialize() {
        try (Connection conn = Conexao.conectar()) {
            String query = "SELECT * FROM empresacadastrada";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<empresa> dados = new ArrayList<>();
            while (rs.next()) {
                empresa e = new empresa(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cnpj"),
                rs.getString("cep"),
                rs.getString("endereco"),
                rs.getString("cidade"),
                rs.getString("bairro"),
                rs.getString("rh"),
                rs.getString("ramo"),
                rs.getString("email"),
                rs.getString("filial"),
                rs.getString("estagiario"),
                rs.getString("funcionarios"),
                rs.getString("inscricao"),
                rs.getString("responsavel"),
                rs.getString("telefones"),
                rs.getString("como"),
                rs.getString("matricula")
            );

                dados.add(e);
            }

            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
            cep.setCellValueFactory(new PropertyValueFactory<>("cep"));
            endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            cidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
            bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
            rh.setCellValueFactory(new PropertyValueFactory<>("rh"));
            ramo.setCellValueFactory(new PropertyValueFactory<>("ramo"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            filial.setCellValueFactory(new PropertyValueFactory<>("filial"));
            estagiario.setCellValueFactory(new PropertyValueFactory<>("estagiario"));
            funcionarios.setCellValueFactory(new PropertyValueFactory<>("funcionarios"));
            inscricao.setCellValueFactory(new PropertyValueFactory<>("inscricao"));
            responsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
            telefone.setCellValueFactory(new PropertyValueFactory<>("telefones"));
            como.setCellValueFactory(new PropertyValueFactory<>("como"));
            matricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));


            empresasdisponiveis_table.setItems(FXCollections.observableArrayList(dados));

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar empresas: " + e.getMessage());
        }
    }

    @FXML
    void empresasdisponiveis_tableMouseClicked(MouseEvent event) {
        }


    @FXML
    void btn_pesquisavaga(ActionEvent event) {

    }

    @FXML
    void configuracoes(ActionEvent event) {

    }

    @FXML
    void pesquisa_texto(ActionEvent event) {

    }

}
