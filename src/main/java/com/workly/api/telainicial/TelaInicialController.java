package com.workly.api.telainicial;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.workly.api.criarperfil.Conexao;
import com.workly.api.curriculo.vagas;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


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
    private Button btn_pesquisa;
    @FXML
    private TextField pesquisa_txt;
     @FXML
    private Button btn_cadastrarempresa;

    @FXML
    private TableView<vagas> vagasdisponiveis_table;

    @FXML
    private TableColumn<vagas, Integer> colunaId;
    @FXML
    private TableColumn<vagas, String> colunaEmpresa;
    @FXML
    private TableColumn<vagas, String> colunaContato;
    @FXML
    private TableColumn<vagas, String> colunaTipo;
    @FXML
    private TableColumn<vagas, String> colunaCurso;
    @FXML
    private TableColumn<vagas, String> colunaNivel;
    @FXML
    private TableColumn<vagas, String> colunaDescricao;
    
    @FXML
    public void initialize() {
        try {
            // Criar uma consulta SQL
            String query = "SELECT * FROM vagas";

            // Executar a consulta SQL
            try (Connection conn = Conexao.conectar()) {
                vagasdisponiveis_table.setBackground(new Background(new BackgroundFill(Color.valueOf("#ffffffff"), CornerRadii.EMPTY, Insets.EMPTY)));                
                Statement stmt = conn.createStatement();
                colunaId.setStyle("-fx-background-color:  #1B4965; -fx-text-fill: #ffffffff;");
                colunaEmpresa.setStyle("-fx-background-color:   #1B4965; -fx-text-fill: #ffffffff;");
                colunaDescricao.setStyle("-fx-background-color:   #1B4965; -fx-text-fill: #ffffffff;");
                colunaContato.setStyle("-fx-background-color:   #1B4965; -fx-text-fill: #ffffffff;");
                colunaTipo.setStyle("-fx-background-color:   #1B4965; -fx-text-fill: #ffffffff;");
                colunaCurso.setStyle("-fx-background-color:   #1B4965; -fx-text-fill: #ffffffff;");
                colunaNivel.setStyle("-fx-background-color:   #1B4965; -fx-text-fill: #ffffffff;");
                
                colunaId.setPrefWidth(60);
                colunaEmpresa.setPrefWidth(82);
                colunaDescricao.setPrefWidth(180);
                colunaContato.setPrefWidth(80);
                colunaTipo.setPrefWidth(74);
                colunaCurso.setPrefWidth(72);
                colunaNivel.setPrefWidth(73);

                ResultSet rs = stmt.executeQuery(query);
                List<vagas> dados = new ArrayList<>();
                while (rs.next()) {
                    vagas vaga = new vagas(rs.getInt("id"), rs.getString("empresa"), rs.getString("descricao"),
                            rs.getString("contato"), rs.getString("tipo"), rs.getString("curso"),
                            rs.getString("nivel"), rs.getString("foto"));
                    dados.add(vaga);
                }

                // Configurar as colunas da tabela
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                colunaContato.setCellValueFactory(new PropertyValueFactory<>("contato"));
                colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
                colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));

                // Atualizar a tabela com os dados
                vagasdisponiveis_table.setItems(FXCollections.observableArrayList(dados));

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro ao executar a consulta SQL: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro: " + e.getMessage());
        }
    }

    @FXML
    void vagasdisponiveis_tableMouseClicked(MouseEvent event) {
        vagas v = vagasdisponiveis_table.getSelectionModel().getSelectedItem();
        if (v != null) {
            // Crie um novo painel para exibir as informações adicionais
            VBox detalhesPanel = new VBox();
            detalhesPanel.setPadding(new Insets(10));
            detalhesPanel.setSpacing(10);
            detalhesPanel.setStyle("-fx-background-color:   #1B4965;");


            // Adicione as informações adicionais ao painel
            Label empresaLabel = new Label("Nome: " + v.getEmpresa());
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
            imagemView.setFitWidth(100);
            imagemView.setFitHeight(100);
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
    void btn_pesquisavaga(ActionEvent event) {
        String texto = pesquisa_txt.getText().toLowerCase();

        if (texto.isEmpty()) {
            // Recarrega todas as vagas se a caixa de pesquisa estiver vazia
            try (Connection conn = Conexao.conectar()) {
                String query = "SELECT * FROM vagas";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                List<vagas> dados = new ArrayList<>();
                while (rs.next()) {
                    vagas vaga = new vagas(rs.getInt("id"), rs.getString("empresa"), rs.getString("descricao"),
                            rs.getString("contato"), rs.getString("tipo"), rs.getString("curso"),
                            rs.getString("nivel"), rs.getString("foto"));
                    dados.add(vaga);
                }
                vagasdisponiveis_table.setItems(FXCollections.observableArrayList(dados));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        // Se tiver texto, filtra a tabela
        vagasdisponiveis_table.setItems(FXCollections.observableArrayList(
                vagasdisponiveis_table.getItems().stream()
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
    void cadastrarcurriculo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workly/api/curriculo/curriculo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
            stage.setTitle("Criar Perfil");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btn_cadastrarperfil.getScene().getWindow();
            loginStage.close();
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
    void cadastrarempresa(ActionEvent event) {
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
    void sair(ActionEvent event) {
        System.exit(0);
    }
}