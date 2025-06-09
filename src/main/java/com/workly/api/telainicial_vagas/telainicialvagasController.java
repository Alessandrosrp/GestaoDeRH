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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

public class telainicialvagasController {

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
    private TableView<curriculo> vagasdisponiveis_table;
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
    try {
        // Criar uma consulta SQL
        String query = "SELECT * FROM curriculo";

        // Executar a consulta SQL
        try (Connection conn = Conexao.conectar()) {
            vagasdisponiveis_table.setBackground(new Background(new BackgroundFill(Color.valueOf("#f2f2f2"), CornerRadii.EMPTY, Insets.EMPTY)));                
            Statement stmt = conn.createStatement();
            colunaId.setStyle("-fx-background-color: #84bfc3; -fx-text-fill: #333;");
            colunaUsuario.setStyle("-fx-background-color:  #84bfc3; -fx-text-fill: #333;");
            colunaDescricao.setStyle("-fx-background-color:  #84bfc3; -fx-text-fill: #333;");
            colunaContato.setStyle("-fx-background-color:  #84bfc3; -fx-text-fill: #333;");
            colunaTipo.setStyle("-fx-background-color:  #84bfc3; -fx-text-fill: #333;");
            colunaCurso.setStyle("-fx-background-color:  #84bfc3; -fx-text-fill: #333;");
            colunaNivel.setStyle("-fx-background-color:  #84bfc3; -fx-text-fill: #333;");
            
            colunaId.setPrefWidth(60);
            colunaUsuario.setPrefWidth(82);
            colunaDescricao.setPrefWidth(180);
            colunaContato.setPrefWidth(80);
            colunaTipo.setPrefWidth(74);
            colunaCurso.setPrefWidth(72);
            colunaNivel.setPrefWidth(73);

            ResultSet rs = stmt.executeQuery(query);
            List<curriculo> dados = new ArrayList<>();
            while (rs.next()) {
                curriculo curr = new curriculo(rs.getInt("id"), rs.getString("usuario"), rs.getString("descricao"),
                        rs.getString("contato"), rs.getString("tipo"), rs.getString("curso"),
                        rs.getString("nivel"), rs.getString("foto"));
                dados.add(curr);
            }

            // Configurar as colunas da tabela
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
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
        curriculo v = vagasdisponiveis_table.getSelectionModel().getSelectedItem();
        if (v != null) {
            // Crie um novo painel para exibir as informações adicionais
            VBox detalhesPanel = new VBox();
            detalhesPanel.setPadding(new Insets(10));
            detalhesPanel.setSpacing(10);
            detalhesPanel.setStyle("-fx-background-color:  #84bfc3;"); // Adicionei essa linha


            // Adicione as informações adicionais ao painel
            Label empresaLabel = new Label("Nome: " + v.getUsuario());
            Label descricaoLabel = new Label("Descrição: " + v.getDescricao());
            Label contatoLabel = new Label("Contato: " + v.getContato());
            Label tipoLabel = new Label("Tipo: " + v.getTipo());
            Label cursoLabel = new Label("Curso: " + v.getCurso());
            Label nivelLabel = new Label("Nível: " + v.getNivel());

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
        // Recarrega todos os currículos se a caixa de pesquisa estiver vazia
        try (Connection conn = Conexao.conectar()) {
            String query = "SELECT * FROM curriculo"; // Mudando "vagas" para "curriculo"
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            List<curriculo> dados = new ArrayList<>();
            while (rs.next()) {
                curriculo curr = new curriculo(rs.getInt("id"), rs.getString("usuario"), rs.getString("descricao"),
                        rs.getString("contato"), rs.getString("tipo"), rs.getString("curso"),
                        rs.getString("nivel"), rs.getString("foto"));
                dados.add(curr);
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