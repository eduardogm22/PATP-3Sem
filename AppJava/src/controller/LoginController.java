package AppJava.src.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private void handleLogin() {
        String username = this.username.getText();
        String password = this.password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Aviso", "Os campos não podem estar vazios", Alert.AlertType.WARNING);
        } else if (username.equals("admin") && password.equals("admin")) {
            String usuario = this.username.getText();
            showAlert("Sucesso", "Login efetuado com sucesso", Alert.AlertType.INFORMATION);
            sceneInterface(usuario);  // Chama a função para abrir a interface após o login bem-sucedido
        } else {
            showAlert("Erro", "Usuário ou senha incorretos", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void sceneInterface(String usuario) {
        try {
            // Carregar o FXML da interface
            /* Toda a configuração de resolução da interfacePatrimonio é feita aqui
             quando instanciada a interface, ela recebe a largura e altura
             da tela exceto a barra de tarefa */

            FXMLLoader interfaceLoader = new FXMLLoader(getClass().getResource("/AppJava/src/view/InterfacePatrimonio.fxml"));
            Parent interfaceRoot = interfaceLoader.load();
            Stage interfaceStage = new Stage();
            Scene interfaceScene = new Scene(interfaceRoot);
            // Obter o controller para inicializar com o nome de usuário
            InterfaceController interfaceController = interfaceLoader.getController();
            interfaceController.initializeInterface(usuario);
            interfaceStage.setScene(interfaceScene);
            // remover as bordas e os botões minimizar, expandir e fechar
            interfaceStage.initStyle(StageStyle.UNDECORATED);
            // Obter a área visível da tela
            javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            // Definir a largura e altura da janela para ocupar toda a área visível da tela, exceto a barra de tarefas
            interfaceStage.setWidth(screenBounds.getWidth());
            interfaceStage.setHeight(screenBounds.getHeight());
            interfaceStage.setX(screenBounds.getMinX());
            interfaceStage.setY(screenBounds.getMinY());

            // exibe a janela da interface
            interfaceStage.show();

            // Fecha a janela de login
            Stage currentStage = (Stage) username.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();  // Exibe erro caso não consiga carregar o FXML
        }
    }

}
