package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
public static Stage stg;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //primarystage is the initial stage, need change to home page
        this.stg = primaryStage;
        //Parent root = FXMLLoader.load(getClass().getResource("ui/login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("ui/home.fxml")); //if change to this one won't work
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hotdesking-Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //launch(args);
        Application.launch(Main.class,args);
    }
}
