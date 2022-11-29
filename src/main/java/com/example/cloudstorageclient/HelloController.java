package com.example.cloudstorageclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.example.cloudstorageclient.HelloApplication.*;

public class HelloController {

    @FXML
    private ListView<String> fileList;
    @FXML
    private ListView<String> fileList1;

    @FXML
    void del(ActionEvent event) {
        String s = fileList1.getFocusModel().getFocusedItem();
        System.out.println("We tryed to del file "+s);
        Message m = new Message();
        Gson g = new GsonBuilder().create();
        m.typ= Message.MsgType.FileDelete;
        m.login=login;
        m.password=password;
        m.filename=s;
        n.sendMSG(m);
    }

    @FXML
    void download(ActionEvent event) {
        String s = fileList.getFocusModel().getFocusedItem();
        Message m = new Message();
        m.typ= Message.MsgType.FileRequest;
        m.login=login;
        m.password=password;
        m.filename=s;
        n.sendMSG(m);
    }

    @FXML
    void lod(ActionEvent event) {

        String s = fileList1.getFocusModel().getFocusedItem();
        System.out.println("We tryed to load file "+s);
        Message m = new Message();
        byte[] b = new byte[0];
        try {
            b = Files.readAllBytes(Paths.get("saves/"+s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String sе = "";
        for(byte by:b){
            sе=sе+String.valueOf(by);
        }
        m.file=new String(b);
        Gson g = new GsonBuilder().create();
        m.typ= Message.MsgType.FileUpload;
        m.login=login;
        m.password=password;
        m.filename=s;
        n.sendMSG(m);

    }

    @FXML
    void upd(ActionEvent event) {
        String s = fileList1.getFocusModel().getFocusedItem();
        System.out.println("We tryed to load file "+s);
        Message m = new Message();
        byte[] b = new byte[0];
        try {
            b = Files.readAllBytes(Paths.get("saves/"+s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String sе = "";
        for(byte by:b){
            sе=sе+String.valueOf(by);
        }
        m.file=new String(b);
        Gson g = new GsonBuilder().create();
        m.typ= Message.MsgType.FileUpdate;
        m.login=login;
        m.password=password;
        m.filename=s;
        n.sendMSG(m);
    }
    @FXML
    void updlist(MouseEvent event) {
        ObservableList<String> a = fileList1.getItems();
        a.removeAll();
        a.clear();
        fileList1.setItems(a);
        ObservableList<String> a1 = fileList.getItems();
        a1.removeAll();
        a1.clear();
        fileList.setItems(a1);
        Stream<Path> lol=null;
        try {
            lol= Files.list(Paths.get("saves/"));
        } catch (IOException e) {
            try {
                Files.createDirectory(Paths.get("saves/"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        for(Path p :lol.toList()){
            fileList1.getItems().add(p.getFileName().toString());
        }

        Message m = new Message();
        m.typ= Message.MsgType.FileList;
        m.login=login;
        m.password=password;
        n.sendMSG(m);
        try {
            for(String p :n.files.split(":")){
                fileList.getItems().add(p);
            }
        }
        catch (Exception exception){

        }


    }

}


