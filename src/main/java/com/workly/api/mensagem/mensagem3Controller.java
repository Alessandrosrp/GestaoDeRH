package com.workly.api.mensagem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class mensagem3Controller {

    @FXML
    private Button mensagem_btn;

    @FXML
    private Button mensagem_btn1;

    @FXML
    void mensagem_botao(ActionEvent event) {
        Image icon = new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png"));
        Stage stage = (Stage) mensagem_btn.getScene().getWindow();
        stage.getIcons().add(icon);
    }

}
