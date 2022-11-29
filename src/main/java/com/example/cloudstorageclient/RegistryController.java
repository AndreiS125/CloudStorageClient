package com.example.cloudstorageclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;

import java.io.IOException;

import static com.example.cloudstorageclient.HelloApplication.n;
import static com.example.cloudstorageclient.HelloApplication.st;
import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

public class RegistryController {

    @FXML
    private Label err;

    @FXML
    private TextField loginField;

    @FXML
    private TextField regLoginField;

    @FXML
    private TextField regPasswordField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitReg;
    @FXML
    private Button submitReg1;

    @FXML
    void authed(MouseEvent event) {

        if(n.started){
            System.out.println("STARTED");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 372);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            st.setTitle("CloudStorage");
            st.setScene(scene);
        }
    }

    @FXML
    void regPerson(ActionEvent event) {
        Message msg = new Message();
        msg.typ= Message.MsgType.Login;
        msg.login = loginField.getText();
        msg.password = passwordField.getText();
        n.sendMSG(msg);


    }
    @FXML
    void regPerson1(ActionEvent event) {
        Message msg = new Message();
        msg.typ= Message.MsgType.Registration;
        msg.login = regLoginField.getText();
        msg.password = regPasswordField.getText();
        n.sendMSG(msg);

    }

}
