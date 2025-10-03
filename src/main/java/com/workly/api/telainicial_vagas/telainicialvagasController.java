package com.workly.api.telainicial_vagas;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.awt.Dimension;


import com.workly.api.criarperfil.Conexao;
import com.workly.api.curriculo.vagas;


public class telainicialvagasController {


    @FXML
    private Button btn_cadastrarempresa;
    @FXML
    private Button btn_cadastrarvaga;
    @FXML
    private Button btn_configuracoes;
    @FXML
    private Button btn_criarperfil;
    @FXML
    private Button btn_esqueceusenha;
    @FXML
    private Button btn_pesquisa;
    @FXML
    private TextField pesquisa_txt;
    @FXML
    private Button verempresasButton;


    @FXML
    private TableView<curriculo> curriculodisponiveis_table;
    @FXML
    private TableView<vagas> empresadisponiveis_table;

    @FXML
    private TableColumn<vagas, Integer> colunaId1;
    @FXML
    private TableColumn<vagas, String> colunaUsuario1;
    @FXML
    private TableColumn<vagas, String> colunaContato1;
    @FXML
    private TableColumn<vagas, String> colunaTipo1;
    @FXML
    private TableColumn<vagas, String> colunaCurso1;
    @FXML
    private TableColumn<vagas, String> colunaNivel1;
    @FXML
    private TableColumn<vagas, String> colunaDescricao1;

    @FXML
    private TableColumn<curriculo, Integer> colunaId;
    @FXML
    private TableColumn<curriculo, String> colunaUsuario;
    @FXML
    private TableColumn<curriculo, String> colunaContato;
    @FXML
    private TableColumn<curriculo, String> colunaTipo;
    @FXML
    private TableColumn<curriculo, String> colunaCurso;
    @FXML
    private TableColumn<curriculo, String> colunaNivel;
    @FXML
    private TableColumn<curriculo, String> colunaDescricao;
    
@FXML
public void initialize() {
    carregarCurriculos();
    carregarVagas();
}

    private void carregarCurriculos() {
        try (Connection conn = Conexao.conectar()) {
            String query = "SELECT * FROM curriculo";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            List<curriculo> dados = new ArrayList<>();
            while (rs.next()) {
                curriculo curr = new curriculo(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("descricao"),
                        rs.getString("contato"),
                        rs.getString("tipo"),
                        rs.getString("curso"),
                        rs.getString("nivel"),
                        rs.getString("foto"));
                dados.add(curr);
            }

            // Configurar colunas currículos
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
            colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            colunaContato.setCellValueFactory(new PropertyValueFactory<>("contato"));
            colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
            colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));

            curriculodisponiveis_table.setItems(FXCollections.observableArrayList(dados));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarVagas() {
        try (Connection conn = Conexao.conectar()) {
            String query = "SELECT * FROM vagas";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            List<vagas> dados = new ArrayList<>();
            while (rs.next()) {
                vagas vaga = new vagas(
                        rs.getInt("id"),
                        rs.getString("empresa"),  
                        rs.getString("descricao"),
                        rs.getString("contato"),
                        rs.getString("tipo"),
                        rs.getString("curso"),
                        rs.getString("nivel"),
                        rs.getString("foto")       
                );
                dados.add(vaga);
            }

            // Configurar colunas da tabela de vagas
            colunaId1.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaUsuario1.setCellValueFactory(new PropertyValueFactory<>("empresa")); 
            colunaDescricao1.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            colunaContato1.setCellValueFactory(new PropertyValueFactory<>("contato"));
            colunaTipo1.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            colunaCurso1.setCellValueFactory(new PropertyValueFactory<>("curso"));
            colunaNivel1.setCellValueFactory(new PropertyValueFactory<>("nivel"));

            empresadisponiveis_table.setItems(FXCollections.observableArrayList(dados));

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar vagas: " + e.getMessage());
        }
    }

    @FXML
    void empresadisponiveis_tableMouseClicked(MouseEvent event) {
        vagas v = empresadisponiveis_table.getSelectionModel().getSelectedItem();
        if (v != null) {
            // Crie um novo painel para exibir as informações adicionais
            VBox detalhesPanel = new VBox();
            detalhesPanel.setPadding(new Insets(10));
            detalhesPanel.setSpacing(10);
            detalhesPanel.setStyle("-fx-background-color:   #1B4965;");


            // Adicione as informações adicionais ao painel
            Label empresaLabel = new Label("Empresa: " + v.getEmpresa());
            empresaLabel.setStyle("-fx-text-fill: white;");

            Label descricaoLabel = new Label("Descrição: " + v.getDescricao());
            descricaoLabel.setStyle("-fx-text-fill: white;");

            Label contatoLabel = new Label("Contato: " + v.getContato());
            contatoLabel.setStyle("-fx-text-fill: white;");

            Label tipoLabel = new Label("Tipo: " + v.getTipo());
            tipoLabel.setStyle("-fx-text-fill: white;");

            Label cursoLabel = new Label("Curso: " + v.getCurso());
            cursoLabel.setStyle("-fx-text-fill: white;");

            Label nivelLabel = new Label("Nível: " + v.getNivel());
            nivelLabel.setStyle("-fx-text-fill: white;");

            Dimension tamanho = new Dimension(500, 600);
            detalhesPanel.setPrefSize(tamanho.width, tamanho.height);
        
            // Adicione a foto ao painel
            ImageView imagemView = new ImageView();
            imagemView.setFitWidth(250);
            imagemView.setFitHeight(250);
            imagemView.setPreserveRatio(true);
    
            // Carregue a foto do caminho armazenado no banco de dados
            String caminhoFoto = v.getFoto();
            if (caminhoFoto != null) {
                imagemView.setImage(new Image("file:///" + caminhoFoto));
            } else {
                // Se a foto não existir, exiba uma imagem padrão
                imagemView.setImage(new Image("/com/workly/api/imagens/semfoto.png"));
            }
    
            detalhesPanel.getChildren().addAll(imagemView, empresaLabel, descricaoLabel, contatoLabel, tipoLabel, cursoLabel, nivelLabel);
    
            // Apresente o painel com as informações adicionais
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Detalhes da Vaga");
            stage.setScene(new Scene(detalhesPanel, 450, 500)); // Adicionei largura e altura para a cena
            stage.show();
        }
    }

        @FXML
    void curriculodisponiveis_tableMouseClicked(MouseEvent event) {
        curriculo curriculo = curriculodisponiveis_table.getSelectionModel().getSelectedItem();
        if (curriculo != null) {
            VBox detalhesPanel = new VBox();
            detalhesPanel.setPadding(new Insets(10));
            detalhesPanel.setSpacing(10);
            detalhesPanel.setStyle("-fx-background-color: #1B4965;");

            Label nomeLabel = new Label("Nome: " + curriculo.getUsuario());
            nomeLabel.setStyle("-fx-text-fill: white;");

            Label descricaoLabel = new Label("Descrição: " + curriculo.getDescricao());
            descricaoLabel.setStyle("-fx-text-fill: white;");

            Label contatoLabel = new Label("Contato: " + curriculo.getContato());
            contatoLabel.setStyle("-fx-text-fill: white;");

            Label tipoLabel = new Label("Tipo: " + curriculo.getTipo());
            tipoLabel.setStyle("-fx-text-fill: white;");

            Label cursoLabel = new Label("Curso: " + curriculo.getCurso());
            cursoLabel.setStyle("-fx-text-fill: white;");

            Label nivelLabel = new Label("Nível: " + curriculo.getNivel());
            nivelLabel.setStyle("-fx-text-fill: white;");

            Dimension tamanho = new Dimension(500, 600);
            detalhesPanel.setPrefSize(tamanho.width, tamanho.height);
        
            // Adicione a foto ao painel
            ImageView imagemView = new ImageView();
            imagemView.setFitWidth(250);
            imagemView.setFitHeight(250);
            imagemView.setPreserveRatio(true);
    
            // Carregue a foto do caminho armazenado no banco de dados
            String caminhoFoto = curriculo.getFoto();
            if (caminhoFoto != null) {
                imagemView.setImage(new Image("file:///" + caminhoFoto));
            } else {
                // Se a foto não existir, exiba uma imagem padrão
                imagemView.setImage(new Image("/com/workly/api/imagens/semfoto.png"));
            }
    
            detalhesPanel.getChildren().addAll(imagemView, nomeLabel, descricaoLabel, contatoLabel, tipoLabel, cursoLabel, nivelLabel);
    
            // Apresente o painel com as informações adicionais
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Detalhes da Vaga");
            stage.setScene(new Scene(detalhesPanel, 450, 500)); // Adicionei largura e altura para a cena
            stage.show();
        }
        }

@FXML
void btn_pesquisavaga(ActionEvent event) {
    String texto = pesquisa_txt.getText().toLowerCase();

    // 🔹 Se a barra de pesquisa estiver vazia → recarrega os dois
    if (texto.isEmpty()) {
        try (Connection conn = Conexao.conectar()) {
            // Recarregar currículos
            String queryCurriculos = "SELECT * FROM curriculo";
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(queryCurriculos);
            List<curriculo> dadosCurriculos = new ArrayList<>();
            while (rs1.next()) {
                curriculo curr = new curriculo(
                        rs1.getInt("id"),
                        rs1.getString("usuario"),
                        rs1.getString("descricao"),
                        rs1.getString("contato"),
                        rs1.getString("tipo"),
                        rs1.getString("curso"),
                        rs1.getString("nivel"),
                        rs1.getString("foto")
                );
                dadosCurriculos.add(curr);
            }
            curriculodisponiveis_table.setItems(FXCollections.observableArrayList(dadosCurriculos));

            // Recarregar empresas
            String queryEmpresas = "SELECT * FROM vagas";
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(queryEmpresas);
            List<vagas> dadosEmpresas = new ArrayList<>();
            while (rs2.next()) {
                vagas vaga = new vagas(
                        rs2.getInt("id"),
                        rs2.getString("empresa"),
                        rs2.getString("descricao"),
                        rs2.getString("contato"),
                        rs2.getString("tipo"),
                        rs2.getString("curso"),
                        rs2.getString("nivel"),
                        rs2.getString("foto")
                );
                dadosEmpresas.add(vaga);
            }
            empresadisponiveis_table.setItems(FXCollections.observableArrayList(dadosEmpresas));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    // 🔹 Se tiver texto, filtra as duas tabelas
    curriculodisponiveis_table.setItems(FXCollections.observableArrayList(
            curriculodisponiveis_table.getItems().stream()
                    .filter(curriculo ->
                            curriculo.getUsuario().toLowerCase().contains(texto) ||
                            curriculo.getDescricao().toLowerCase().contains(texto) ||
                            curriculo.getContato().toLowerCase().contains(texto) ||
                            curriculo.getTipo().toLowerCase().contains(texto) ||
                            curriculo.getCurso().toLowerCase().contains(texto) ||
                            curriculo.getNivel().toLowerCase().contains(texto)
                    )
                    .collect(Collectors.toList())
    ));

    empresadisponiveis_table.setItems(FXCollections.observableArrayList(
            empresadisponiveis_table.getItems().stream()
                    .filter(vaga ->
                            vaga.getEmpresa().toLowerCase().contains(texto) ||
                            vaga.getDescricao().toLowerCase().contains(texto) ||
                            vaga.getContato().toLowerCase().contains(texto) ||
                            vaga.getTipo().toLowerCase().contains(texto) ||
                            vaga.getCurso().toLowerCase().contains(texto) ||
                            vaga.getNivel().toLowerCase().contains(texto)
                    )
                    .collect(Collectors.toList())
    ));
}

    @FXML
    void pesquisa_texto(ActionEvent event) {

    }

    @FXML
    void cadastrarvaga(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/vagas/vagas.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Criar Perfil");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btn_cadastrarvaga.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de curriculo: " + e.getMessage());
        }
    }

    @FXML
    void configuracoes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/configuracao/configuracao2.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Configurar Perfil");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btn_configuracoes.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de curriculo: " + e.getMessage());
        }
    }

     @FXML
    void cadvaga(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/cadastroempresa/CadastroEmpresa.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Cadastro de Empresa");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btn_configuracoes.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de cadastro de empresa: " + e.getMessage());

        }
    }

    @FXML
    void criarconta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/criarperfil/criarperfil.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Criar Perfil");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btn_criarperfil.getScene().getWindow();
            loginStage.close();
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
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Esqueceu sua senha");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btn_esqueceusenha.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela esqueceu senha: " + e.getMessage());
        }
    }

    @FXML
    void visualizaempresas(ActionEvent event) {
                try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/empresascadastradas/empresascadastradas.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Esqueceu sua senha");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btn_esqueceusenha.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela esqueceu senha: " + e.getMessage());
        }

    }

    @FXML
    void sair(ActionEvent event) {
        System.exit(0);
    }
}