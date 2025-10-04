package com.workly.api.mensagem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class mensagem3Controller {

    @FXML
    private Button mensagem_btn; // Botão Sim

    @FXML
    private Button mensagem_btn1; // Botão Não

    private boolean confirmado = false;

    @FXML
    private void mensagem_botao(ActionEvent event) {
        Object source = event.getSource();
        if (source == mensagem_btn) {
            confirmado = true; // usuário confirmou
        } else if (source == mensagem_btn1) {
            confirmado = false; // usuário cancelou
        }

        // Fecha a janela do diálogo
        Stage stage = (Stage) ((Button) source).getScene().getWindow();
        stage.close();
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}
