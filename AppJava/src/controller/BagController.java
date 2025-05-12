package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;

import java.io.IOException;

public class BagController {

    private InterfaceController mainController;

    public void setMainController(InterfaceController controller) {
        this.mainController = controller;
    }

    @FXML
    private Button btnCad;

    @FXML
    private void abrirCadastro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PreviaInventarioPatrimonio.fxml"));
        Region telaCadastro = loader.load();

        // Acessa o BorderPane da Interface Principal e substitui o conteúdo central
        mainController.getRootPane().setCenter(telaCadastro);
    }
}
