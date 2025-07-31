package com.workly.api.mensagem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class mensagemController {

    @FXML
    private Button mensagem_btn;

@FXML
void mensagem_botao(ActionEvent event) throws Exception {
    Stage criarPerfilStage = (Stage) mensagem_btn.getScene().getWindow();
    Image icon = new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png"));
    criarPerfilStage.getIcons().add(icon);
    criarPerfilStage.close();

}
    }
