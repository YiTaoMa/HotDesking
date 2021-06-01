package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stg = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("ui/home.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hotdesking-Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
