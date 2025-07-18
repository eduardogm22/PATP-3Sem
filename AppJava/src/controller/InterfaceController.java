package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.scene.*;
public class InterfaceController {
    @FXML
    private Label testeLabel;
    // Ainda não usado
    @FXML
    private VBox sidebar;
    private Stage stage;
    // armazenamento do conteúdo inicial do borderpane para restaurar a posição da home
    private Region initialCenterContent;
    @FXML
    private Button btnMinimize;
    @FXML
    private Button btnClose;
    @FXML
    private BorderPane rootPane;
    public void initializeInterface(String username) {
        testeLabel.setText("Olá, " + username);
        initialCenterContent = (Region) rootPane.getCenter();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void handleMinimize() {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void loadHome() throws IOException {
        rootPane.setCenter(initialCenterContent);
    }

    @FXML
    private void cadItem() throws IOException {
        FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("/view/CadastroItensPatrimonio.fxml"));
        Region cadItemTela = loaderItem.load();
        rootPane.setCenter(cadItemTela);
    }

    public BorderPane getRootPane() {
        return rootPane;
    }

    @FXML
    private void previewInventario() throws IOException {
        FXMLLoader prevInventario = new FXMLLoader(getClass().getResource("/view/PreviaInventarioPatrimonio.fxml"));
        Region PreviaInventario = prevInventario.load();
        PreviewBagController previewController = prevInventario.getController();
        previewController.setMainController(this);
        rootPane.setCenter(PreviaInventario);  // Substitui o conteúdo central
    }
    @FXML
    private void abrirTelaLogs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogsView.fxml"));
            StackPane logsView = loader.load();
            rootPane.setCenter(logsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
