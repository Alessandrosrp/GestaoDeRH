package com.workly.api.empresascadastradas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.workly.api.cadastroempresa.empresa;
import com.workly.api.criarperfil.Conexao;
import com.workly.api.mensagem.mensagem3Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
<<<<<<< HEAD
import javafx.scene.control.Button;
=======

import javafx.scene.control.Button;

>>>>>>> c894e01cb230bcf8f1b14a5f6090c6cf0a33695a
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
<<<<<<< HEAD
=======
import javafx.scene.control.ButtonType;
>>>>>>> c894e01cb230bcf8f1b14a5f6090c6cf0a33695a
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
<<<<<<< HEAD
=======

>>>>>>> c894e01cb230bcf8f1b14a5f6090c6cf0a33695a

public class empresascadastradasController {

    @FXML
    private TableColumn<empresa, String> bairro;

    @FXML
    private Button btn_configuracoes;

    @FXML
    private Button btn_pesquisa;

    @FXML
    private Button btn_voltar;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_excluir;

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
    empresa e = empresasdisponiveis_table.getSelectionModel().getSelectedItem();
    if (e != null) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setStyle("-fx-background-color: #1B4965;");

        // Campos editáveis
        TextField nomeField = new TextField(e.getNome());
        TextField cnpjField = new TextField(e.getCnpj());
        TextField cepField = new TextField(e.getCep());
        TextField enderecoField = new TextField(e.getEndereco());
        TextField cidadeField = new TextField(e.getCidade());
        TextField bairroField = new TextField(e.getBairro());
        TextField rhField = new TextField(e.getRh());
        TextField ramoField = new TextField(e.getRamo());
        TextField emailField = new TextField(e.getEmail());
        TextField filialField = new TextField(e.getFilial());
        TextField estagiarioField = new TextField(e.getEstagiario());
        TextField funcionariosField = new TextField(e.getFuncionarios());
        TextField inscricaoField = new TextField(e.getInscricao());
        TextField responsavelField = new TextField(e.getResponsavel());
        TextField telefoneField = new TextField(e.getTelefones());
        TextField comoField = new TextField(e.getComo());
        TextField matriculaField = new TextField(e.getMatricula());

        String labelStyle = "-fx-font-weight: bold; -fx-text-fill: #ffffffff;";

        grid.add(new Label("Nome:") {{ setStyle(labelStyle); }}, 0, 0); grid.add(nomeField, 1, 0);
        grid.add(new Label("CNPJ:") {{ setStyle(labelStyle); }}, 0, 1); grid.add(cnpjField, 1, 1);
        grid.add(new Label("CEP:") {{ setStyle(labelStyle); }}, 0, 2); grid.add(cepField, 1, 2);
        grid.add(new Label("Endereço:") {{ setStyle(labelStyle); }}, 0, 3); grid.add(enderecoField, 1, 3);
        grid.add(new Label("Cidade:") {{ setStyle(labelStyle); }}, 0, 4); grid.add(cidadeField, 1, 4);
        grid.add(new Label("Bairro:") {{ setStyle(labelStyle); }}, 0, 5); grid.add(bairroField, 1, 5);
        grid.add(new Label("RH:") {{ setStyle(labelStyle); }}, 0, 6); grid.add(rhField, 1, 6);
        grid.add(new Label("Ramo:") {{ setStyle(labelStyle); }}, 0, 7); grid.add(ramoField, 1, 7);
        grid.add(new Label("Email:") {{ setStyle(labelStyle); }}, 0, 8); grid.add(emailField, 1, 8);
        grid.add(new Label("Filial:") {{ setStyle(labelStyle); }}, 0, 9); grid.add(filialField, 1, 9);
        grid.add(new Label("Estagiário:") {{ setStyle(labelStyle); }}, 0, 10); grid.add(estagiarioField, 1, 10);
        grid.add(new Label("Funcionários:") {{ setStyle(labelStyle); }}, 0, 11); grid.add(funcionariosField, 1, 11);
        grid.add(new Label("Inscrição:") {{ setStyle(labelStyle); }}, 0, 12); grid.add(inscricaoField, 1, 12);
        grid.add(new Label("Responsável:") {{ setStyle(labelStyle); }}, 0, 13); grid.add(responsavelField, 1, 13);
        grid.add(new Label("Telefone:") {{ setStyle(labelStyle); }}, 0, 14); grid.add(telefoneField, 1, 14);
        grid.add(new Label("Como conheceu:") {{ setStyle(labelStyle); }}, 0, 15); grid.add(comoField, 1, 15);
        grid.add(new Label("Matrícula:") {{ setStyle(labelStyle); }}, 0, 16); grid.add(matriculaField, 1, 16);

        // Botões
        HBox botoes = new HBox(15);
        botoes.setPadding(new Insets(20, 0, 0, 0));

        Button salvarBtn = new Button("Salvar");
        salvarBtn.setStyle("-fx-background-color: #3E4C59; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8;");

        Button excluirBtn = new Button("Excluir");
        excluirBtn.setStyle("-fx-background-color: #d62828; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8;");

        Button cancelarBtn = new Button("❌ Cancelar");
        cancelarBtn.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8;");

        botoes.getChildren().addAll(salvarBtn, excluirBtn, cancelarBtn);
        GridPane.setColumnSpan(botoes, 2);
        grid.add(botoes, 0, 17);

        // Ações
        salvarBtn.setOnAction(ev -> {
            try (Connection conn = Conexao.conectar()) {
                String sql = "UPDATE empresacadastrada SET nome=?, cnpj=?, cep=?, endereco=?, cidade=?, bairro=?, " +
                        "rh=?, ramo=?, email=?, filial=?, estagiario=?, funcionarios=?, inscricao=?, responsavel=?, telefones=?, como=?, matricula=? " +
                        "WHERE id=?";
                var stmt = conn.prepareStatement(sql);
                stmt.setString(1, nomeField.getText());
                stmt.setString(2, cnpjField.getText());
                stmt.setString(3, cepField.getText());
                stmt.setString(4, enderecoField.getText());
                stmt.setString(5, cidadeField.getText());
                stmt.setString(6, bairroField.getText());
                stmt.setString(7, rhField.getText());
                stmt.setString(8, ramoField.getText());
                stmt.setString(9, emailField.getText());
                stmt.setString(10, filialField.getText());
                stmt.setString(11, estagiarioField.getText());
                stmt.setString(12, funcionariosField.getText());
                stmt.setString(13, inscricaoField.getText());
                stmt.setString(14, responsavelField.getText());
                stmt.setString(15, telefoneField.getText());
                stmt.setString(16, comoField.getText());
                stmt.setString(17, matriculaField.getText());
                stmt.setInt(18, e.getId());
                stmt.executeUpdate();

                new Alert(Alert.AlertType.INFORMATION, "Informações atualizadas com sucesso!").showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        excluirBtn.setOnAction(ev -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/mensagem/mensagem3.fxml"));
                Parent root = loader.load();

                mensagem3Controller controller = loader.getController();

                Stage dialog = new Stage();
                dialog.setTitle("Confirmação");
                dialog.setScene(new Scene(root));
                dialog.setResizable(false);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.showAndWait();

                if (controller.isConfirmado()) {
                    try (Connection conn = Conexao.conectar()) {
                        String sql = "DELETE FROM empresacadastrada WHERE id=?";
                        var stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, e.getId());
                        stmt.executeUpdate();

                        System.out.println("Empresa excluída com sucesso!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Exclusão cancelada pelo usuário.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });




        cancelarBtn.setOnAction(ev -> {
            Stage stage = (Stage) cancelarBtn.getScene().getWindow();
            stage.close();
        });

        // Exibe janela
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
        stage.setTitle("Editar Empresa");
        stage.setScene(new Scene(grid, 650, 750));
        stage.show();
    }
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

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/telainicial_vagas/telainicial_vagas.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
        stage.setTitle("Tela Inicial");
        stage.setScene(new Scene(root));
        stage.show();

        Stage loginStage = (Stage) btn_voltar.getScene().getWindow();
        loginStage.close();
        
    }

        @FXML
    void excluir(ActionEvent event) {

    }

        @FXML
    void editar(ActionEvent event) {

    }

}
