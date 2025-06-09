package com.workly.api.mensagem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class mensagem2Controller {

    @FXML
    private Button mensagem_btn;

    @FXML
    void mensagem_botao(ActionEvent event) {
        Stage loginStage = (Stage) mensagem_btn.getScene().getWindow();
        loginStage.close();
    }

}
