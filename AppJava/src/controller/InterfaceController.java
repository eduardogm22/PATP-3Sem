package AppJava.src.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;


public class InterfaceController {

    @FXML
    private Label testeLabel;

    // Ainda não usado
    @FXML
    private VBox sidebar;

    private Stage stage;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnClose;


    // Ainda não usado
    @FXML
    private Button btnTela1;

    // Ainda não usado
    @FXML
    private Button btnTela2;

    // Método para inicializar a interface com o nome de usuário
    public void initializeInterface(String username) {
        testeLabel.setText("Olá, " + username);
    }

    // Método para fechar a janela
    @FXML
    private void handleClose() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    // Método para minimizar a janela
    @FXML
    private void handleMinimize() {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

}
