package com.example.cloudstorageclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Network n;
    public static String login;
    public static String password;
    public static Stage st;
    @Override
    public void start(Stage stage){
        st=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registry.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 372);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("CloudStorage");
        stage.setScene(scene);
        stage.show();
        n = new Network();

            new Thread(() ->{
                try {
                    n.Network();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();


    }

    public static void main(String[] args) {
        launch();
    }
}