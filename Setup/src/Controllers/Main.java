package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Главный класс программы
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL is = getClass().getResource("/FXML/Authorization.fxml");
        Parent root = FXMLLoader.load(is);
        primaryStage.setTitle("Авторизация");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
