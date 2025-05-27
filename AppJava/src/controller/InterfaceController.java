package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;


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

    // Ainda não usado
   // @FXML
   // private Button btnTela1;

    // Ainda não usado
    //@FXML
   // private Button btnTela2;

    // Método para inicializar a interface com o nome de usuário
    public void initializeInterface(String username) {
        testeLabel.setText("Olá, " + username);
        initialCenterContent = (Region) rootPane.getCenter();
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

    @FXML
    private void loadHome() throws IOException {
        rootPane.setCenter(initialCenterContent);
    }

    @FXML
    private void loadTela1() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Patrimonio.fxml"));
            Region tela1 = loader.load();
            BagController bagController = loader.getController();
            bagController.setMainController(this);
            rootPane.setCenter(tela1);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar Tela 1: " + e.getMessage());
        }
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
        // Carregar o FXML de Tela 2
        FXMLLoader prevInventario = new FXMLLoader(getClass().getResource("/view/PreviaInventarioPatrimonio.fxml"));
        Region PrevInventario = prevInventario.load();
        PreviewBagController previaController = prevInventario.getController();
        previaController.setMainController(this);
        rootPane.setCenter(PrevInventario);  // Substitui o conteúdo central
    }
}
