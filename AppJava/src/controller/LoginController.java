package controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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
                showAlert("Sucesso", "Login efeutado com sucesso", Alert.AlertType.INFORMATION);
                sceneInterface(username);
            }else {
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
            FXMLLoader interfaceLoader = new FXMLLoader(getClass().getResource("/view/InterfacePatrimonio.fxml"));
            Parent interfaceRoot = interfaceLoader.load();
            Stage interfaceStage = new Stage();
            Scene interfaceScene = new Scene(interfaceRoot);

            InterfaceController interfaceController = interfaceLoader.getController();
            interfaceController.initializeInterface(usuario);

            // Definir sem bordas
            interfaceStage.initStyle(StageStyle.UNDECORATED);

            // Obter apenas a área visível da tela (sem barra de tarefas)
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

            interfaceStage.setScene(interfaceScene);
            interfaceStage.setX(visualBounds.getMinX());
            interfaceStage.setY(visualBounds.getMinY());
            interfaceStage.setWidth(visualBounds.getWidth());
            interfaceStage.setHeight(visualBounds.getHeight());
            interfaceStage.setResizable(false);
            interfaceStage.show();

            Stage currentStage = (Stage) username.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
