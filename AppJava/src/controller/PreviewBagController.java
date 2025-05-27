package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import controller.InterfaceController;
import java.io.IOException;

public class PreviewBagController {
    private InterfaceController mainController;
    public void setMainController(InterfaceController controller) {
        this.mainController = controller;
    }

    @FXML
    private void abrirCadastro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CadastroItensPatrimonio.fxml"));
        Region tela = loader.load();
        mainController.getRootPane().setCenter(tela);
    }
}