package AppJava.src;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.lang.Exception;

import java.io.IOException;
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/AppJava/src/view/login.fxml"));
            Parent loginRoot = loaderLogin.load();

            Scene loginScene = new Scene(loginRoot);
            primaryStage.setTitle("Login");
            primaryStage.setScene(loginScene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Exibe erro caso não consiga carregar o FXML
        }
    }
    public static void main(String[] args){
        launch(args); // <- Roda os argumentos da classe principal App

    }
}
